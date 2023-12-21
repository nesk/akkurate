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

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.getVisibility
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import dev.nesk.akkurate.annotations.ExperimentalAkkurateCompilerApi
import dev.nesk.akkurate.annotations.Validate
import java.io.OutputStreamWriter
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

@ExperimentalAkkurateCompilerApi
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
        private val kMutableProperty1Class = KMutableProperty1::class.asClassName()
    }

    private var validatableClasses: Set<String> = config.normalizedValidatableClasses
    private var validatablePackages: Set<String> = config.normalizedValidatablePackages

    /**
     * The unique name of the declaration inside its package.
     */
    private val KSDeclaration.uniqueNameInPackage: String
        get() = (parentDeclaration?.uniqueNameInPackage ?: "") + simpleName.asString().replaceFirstChar { it.uppercase() }

    /**
     * The destination package of the accessor.
     */
    private val PropertySpec.destinationPackageName: String
        get() {
            val wrappedType = (receiverType as ParameterizedTypeName).typeArguments.first()
            val wrappedPackageName = when (wrappedType) {
                is ClassName -> wrappedType.packageName
                is ParameterizedTypeName -> wrappedType.rawType.packageName
                else -> "default.package.name"
            }
            return "${config.normalizedPrependPackagesWith}.$wrappedPackageName.${config.normalizedAppendPackagesWith}".trim('.')
        }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val providedClassesDeclarations = validatableClasses
            .map(resolver::getKSNameFromString)
            .mapNotNull { name ->
                resolver.getClassDeclarationByName(name).also {
                    if (it == null) {
                        logger.warn("Unable to find class provided in 'validatablesClasses' option: '${name.asString()}'")
                    }
                }
            }

        val providedPackagesDeclarations = validatablePackages.flatMap {
            resolver.getDeclarationsFromPackage(it)
                .filterIsInstance<KSClassDeclaration>()
        }

        val annotatedDeclarations = resolver.getSymbolsWithAnnotation(Validate::class.qualifiedName!!).filterIsInstance<KSClassDeclaration>()
        logger.info("Found ${annotatedDeclarations.count()} classes annotated with @Validate.")

        val classDeclarations = (providedClassesDeclarations + providedPackagesDeclarations + annotatedDeclarations)
            .flatMap { it.listWithAllDeepChildrenClasses }
            .filterNot { it.classKind == ClassKind.ANNOTATION_CLASS || it.classKind == ClassKind.OBJECT } // Filter out annotation classes and objects
            .toSet()

        // Create two accessors for each property of a class, the second one enables an easy traversal within a nullable structure.
        val accessors: Set<PropertySpec> = buildSet {
            for (classDeclaration in classDeclarations) {
                logger.info("Processing class '${classDeclaration.qualifiedName!!.asString()}'.")
                for (property in classDeclaration.getAllProperties()) {
                    if (
                        !property.isPublic // Until issue #15 is fixed, we handle only public properties to avoid unexpected bugs (https://github.com/nesk/akkurate/issues/15)
                        || property.extensionReceiver != null // Support extension properties aren't supported
                    ) continue

                    logger.info("Processing property '${property.simpleName.asString()}'.")
                    val rootProperty = property.topMostPublicOverridee
                    add(rootProperty.toValidatablePropertySpec())
                    add(rootProperty.toValidatablePropertySpec(withNullableReceiver = true))
                }
            }
        }

        // Group the accessors by their package and filter out those in the `kotlin` package
        val groupedAccessors = accessors
            .groupBy { it.destinationPackageName }
            .filterNot { (packageName, packageAccessors) ->
                (packageName.split('.').first() == "kotlin").also {
                    logger.warnIf(it, "Skipping ${packageAccessors.size} accessors in the '$packageName' package because they're in the 'kotlin' package")
                }
            }

        // Generate a file for each package with the corresponding accessors
        for ((packageName, packageAccessors) in groupedAccessors) {
            val fileName = "ValidationAccessors"
            logger.info("Writing accessors with namespace '$packageName' to file '$fileName.kt'.")

            val fileBuilder = FileSpec.builder(packageName, fileName)
                .addAnnotation(suppressUselessCast) // Needed for the bug-fixing cast

            packageAccessors.forEach(fileBuilder::addProperty)

            // TODO: change the ALL_FILES
            codeGenerator.createNewFile(Dependencies.ALL_FILES, packageName, fileBuilder.name, "kt").use { output ->
                OutputStreamWriter(output).use { fileBuilder.build().writeTo(it) }
            }
        }

        logger.info("A total of ${classDeclarations.count()} classes and ${accessors.size} properties were processed.")

        // Empty the sets to avoid processing those classes/packages again on the next processing round.
        validatableClasses = emptySet()
        validatablePackages = emptySet()

        return emptyList()
    }

    /**
     * Generates an extension property for the property of a validatable class.
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
    private fun KSPropertyDeclaration.toValidatablePropertySpec(withNullableReceiver: Boolean = false): PropertySpec {
        val nullabilityText = if (withNullableReceiver) "Nullable" else ""

        val receiver = closestClassDeclaration()!!
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
                            .addMember("name = %S", "validatable$nullabilityText${uniqueNameInPackage}")
                            .build()
                    )

                    // FIXME: The cast is a workaround for https://youtrack.jetbrains.com/issue/KT-59493 and https://youtrack.jetbrains.com/issue/KT-62543
                    addStatement(
                        "return %M(%T::%N as %T)",
                        validatableOfFunction,
                        receiverType,
                        propertyName,
                        if (isMutable) kMutableProperty1Class else kProperty1Class,
                    )
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

    /**
     * Generates a list of all the children classes, including the deep ones and the current one.
     */
    private val KSClassDeclaration.listWithAllDeepChildrenClasses: List<KSClassDeclaration>
        get() = listOf(this) + this.declarations
            .filterIsInstance<KSClassDeclaration>()
            .flatMap { it.listWithAllDeepChildrenClasses }
            .toSet()

    /**
     * Determines if a declaration is public or not.
     *
     * Handles [a bug in ksp](https://github.com/google/ksp/issues/1515) where the visibility of some properties can't be resolved.
     */
    private val KSDeclaration.isPublic: Boolean
        get() {
            if (parentDeclaration?.isPublic == false) {
                return false
            }

            val visibility: Visibility = try {
                getVisibility()
            } catch (e: IllegalStateException) {
                // Rethrow the exception if it's not about an unhandled visibility.
                if (e.message?.startsWith("unhandled visibility") != true) {
                    throw e
                }
                // If the visibility can't be resolved, default to private visibility.
                Visibility.PRIVATE
            }

            return visibility == Visibility.PUBLIC
        }

    /**
     * Returns the top most public overridee of the property.
     *
     * For example, look at the `size` property of the following code:
     *
     * ```
     * class EmptyList : List<Nothing> {
     *     override val size: Int = 0
     * }
     * ```
     *
     * Calling `findOverridee()` on it will return [List.size]; calling `topMostOveridee` will return [Collection.size].
     *
     * This extension property avoids generating too many accessors. Instead of generating an accessor for
     * [List.size] and another one for [Set.size], we generate a single accessor for [Collection.size].
     */
    private val KSPropertyDeclaration.topMostPublicOverridee: KSPropertyDeclaration
        get() {
            var mostPublicOverridee = this
            var currentOverridee = this.findOverridee()
            while (currentOverridee != null) {
                if (currentOverridee.isPublic) {
                    mostPublicOverridee = currentOverridee
                }
                currentOverridee = currentOverridee.findOverridee()
            }
            return mostPublicOverridee
        }

    private fun KSPLogger.warnIf(condition: Boolean, message: String, symbol: KSNode? = null) {
        if (condition) {
            warn(message, symbol)
        }
    }
}
