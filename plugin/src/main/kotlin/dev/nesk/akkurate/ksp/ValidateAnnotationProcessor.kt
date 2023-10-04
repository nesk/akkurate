/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nesk.akkurate.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import dev.nesk.akkurate.annotations.Validate
import java.io.OutputStreamWriter
import kotlin.reflect.KProperty1

public class ValidateAnnotationProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val config: ValidateAnnotationProcessorConfig,
) : SymbolProcessor {
    public companion object {
        // The KSP plugin can't depend on the library, because the latter already depends on the KSP plugin, which would create
        // a circular dependency; so we must manually create name references for some symbols contained in the library.
        private val validatableOfFunction = MemberName("dev.nesk.akkurate.validatables", "validatableOf")
        private val validatableClass = ClassName("dev.nesk.akkurate.validatables", "Validatable")

        private val suppressUselessCast = AnnotationSpec.builder(Suppress::class).addMember("%S", "USELESS_CAST").build()
        private val kProperty1Class = KProperty1::class.asClassName()
    }

    private var validatableClasses: Set<String> = config.normalizedValidatableClasses

    /**
     * All the generated accessors for each property of the validatables.
     *
     * An accessor is an extension property for a [Validatable] receiver, returning the property value wrapped in another [Validatable].
     *
     * @see KSPropertyDeclaration.toValidatablePropertySpec
     */
    private val accessors: MutableSet<PropertySpec> = mutableSetOf()

    /**
     * The name of the declaration with an uppercase-first character.
     */
    private val KSDeclaration.capitalizedName: String get() = simpleName.asString().replaceFirstChar { it.uppercase() }

    /**
     * The package name of the generic type wrapped by the [Validatable].
     */
    private val PropertySpec.originalPackageName: String
        get() {
            val wrappedType = (receiverType as ParameterizedTypeName).typeArguments.first()
            return when (wrappedType) {
                is ClassName -> wrappedType.packageName
                is ParameterizedTypeName -> wrappedType.rawType.packageName
                else -> "default.package.name"
            }
        }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbolsFromOptions = validatableClasses
            .map(resolver::getKSNameFromString)
            .mapNotNull { name ->
                resolver.getClassDeclarationByName(name).also {
                    if (it == null) {
                        logger.warn("Unable to find class provided in 'validatablesClasses' option: '${name.asString()}'")
                    }
                }
            }

        val annotatedSymbols = resolver.getSymbolsWithAnnotation(Validate::class.qualifiedName!!).filterIsInstance<KSClassDeclaration>()
        logger.info("Found ${annotatedSymbols.count()} classes annotated with @Validate.")

        val validatables = symbolsFromOptions + annotatedSymbols

        // Create two accessors for each property of a validatable, the second one enables an easy traversal within a nullable structure.
        for (validatable in validatables) {
            logger.info("Processing class '${validatable.qualifiedName!!.asString()}' with properties:")
            for (property in validatable.getAllProperties()) {
                logger.info("  ${property.simpleName.asString()}")
                accessors += property.toValidatablePropertySpec(validatable)
                accessors += property.toValidatablePropertySpec(validatable, withNullableReceiver = true)
            }
        }

        // Group the accessors by their package and generate a file for each package with the corresponding accessors.
        accessors
            .groupBy { it.originalPackageName }
            .forEach { (packageName, accessors) ->
                val fileName = "ValidationAccessors"
                val newPackageName = "${config.normalizedOverrideOriginalPackageWith ?: packageName}.${config.normalizedappendPackagesWith ?: ""}".trim { it == '.' }
                logger.info("Writing accessors with namespace '$newPackageName' to file '$fileName.kt'.")

                val fileBuilder = FileSpec.builder(newPackageName, fileName)
                accessors.forEach(fileBuilder::addProperty)

                // TODO: change the ALL_FILES
                codeGenerator.createNewFile(Dependencies.ALL_FILES, newPackageName, fileBuilder.name, "kt").use { output ->
                    OutputStreamWriter(output).use { fileBuilder.build().writeTo(it) }
                }
            }

        logger.info("A total of ${validatables.count()} classes and ${accessors.size} properties were processed.")

        validatableClasses = emptySet() // Empty the set to avoid processing those classes again on the next processing round.
        accessors.clear()

        return emptyList()
    }

    /**
     * Generates an extension property for the property of a validatable.
     *
     * Take this data class as an example:
     * ```
     * data class User(val name: String)
     * ```
     * Running this method on it will generate the following extension property:
     * ```
     * public val Validatable<User>.name: Validatable<String>
     *   @JvmName(name = "validatableUserName")
     *   get() = createValidatable(`value`.name)
     * ```
     *
     * If we force a nullable receiver with `withNullableReceiver = true`, it will generate:
     * ```
     * public val Validatable<User?>.name: Validatable<String?>
     *   @JvmName(name = "validatableNullableUserName")
     *   get() = createValidatable(`value`?.name)
     * ```
     * The `@JvmName` annotation is here to distinguish the signature of the two getters, since the JVM
     * ignores the nullability.
     *
     * If the property is already nullable like `val name: String?`, generating the extension property
     * with `withNullableReceiver = false` will keep the nullability on the return type:
     * ```
     * public val Validatable<User>.name: Validatable<String?>
     *   @JvmName(name = "validatableUserName")
     *   get() = createValidatable(`value`.name)
     * ```
     */
    private fun KSPropertyDeclaration.toValidatablePropertySpec(receiver: KSClassDeclaration, withNullableReceiver: Boolean = false): PropertySpec {
        val nullabilityText = if (withNullableReceiver) "Nullable" else ""

        val receiverClassName = receiver.toClassName()
        val receiverTypeParams = receiver.typeParameters.toTypeParameterResolver()
        val receiverType = receiver.asType(emptyList()).toTypeName(receiverTypeParams)
        val validatableReceiverType = receiverType.toValidatableType(forceNullable = withNullableReceiver)

        val propertyName = simpleName.asString()
        val validatablePropertyType = type.resolve().toTypeName(receiverTypeParams).toValidatableType(forceNullable = withNullableReceiver)

        return PropertySpec.builder(propertyName, validatablePropertyType).apply {
            receiver(validatableReceiverType)

            // Walk the type variable names, strip them of their variance, and add them to the property.
            validatableReceiverType.walkTypeVariableNames().forEach {
                addTypeVariable(TypeVariableName(it.name, it.bounds))
            }

            // Add a KDoc to explain this property is a validatable accessor. If available, provide the original documentation too.
            addKdoc(buildCodeBlock {
                add("[${validatableClass.simpleName}] accessor of [%T.%N]", receiverClassName, propertyName)
                docString?.let { add("\n\n" + it.trimIndent()) }
            })

            getter(
                FunSpec.getterBuilder().apply {
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember("name = %S", "validatable$nullabilityText${receiver.capitalizedName}${capitalizedName}")
                            .build()
                    )

                    // FIXME: The cast is a workaround for https://youtrack.jetbrains.com/issue/KT-59493, it can be removed with KT v1.9.20.
                    if (withNullableReceiver) {
                        addAnnotation(suppressUselessCast)
                        addStatement("return %M(%T::%N as %T)", validatableOfFunction, receiverType, propertyName, kProperty1Class)
                    } else {
                        addStatement("return %M(%T::%N)", validatableOfFunction, receiverType, propertyName)
                    }
                }.build()
            )
        }.build()
    }

    /**
     * Wraps a type inside a [Validatable] one.
     *
     * @param forceNullable Force the wrapped type to be nullable. For example, wrapping the `String` type will produce `Validatable<String?>`.
     */
    private fun TypeName.toValidatableType(forceNullable: Boolean): ParameterizedTypeName {
        val parameterType = if (forceNullable) this.copy(nullable = true) else this
        return validatableClass.parameterizedBy(parameterType)
    }

    /**
     * Gets a sequence for visiting the type variables of this type and its descendants.
     */
    private fun ParameterizedTypeName.walkTypeVariableNames(): Sequence<TypeVariableName> = sequence {
        for (typeArgument in typeArguments) {
            when (typeArgument) {
                is TypeVariableName -> yield(typeArgument)
                is ParameterizedTypeName -> yieldAll(typeArgument.walkTypeVariableNames())
                else -> Unit
            }
        }
    }
}
