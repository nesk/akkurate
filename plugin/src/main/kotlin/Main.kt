import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.nesk.akkurate.Validatable
import dev.nesk.akkurate.annotations.Validate
import java.io.OutputStreamWriter

class AkkurateProcessor(private val codeGenerator: CodeGenerator, private val logger: KSPLogger): SymbolProcessor {
    private var output: String = ""

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val entitiesPerPackage = resolver.getSymbolsWithAnnotation(Validate::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.typeParameters.isEmpty() }
            .groupBy { it.packageName.asString() }

        for ((packageName, entities) in entitiesPerPackage) {
            val subPackageName = "$packageName.akkurate"
            val fileBuilder = FileSpec.builder(subPackageName, "Validatables")

            for (entity in entities) {
                for (property in entity.getAllProperties()) {
                    fileBuilder.addProperty(
                        PropertySpec.builder(property.simpleName.asString(), property.type.toTypeName().toValidatableType())
                            .receiver(entity.toTypeName().toValidatableType())
                            .getter(
                                FunSpec.getterBuilder()
                                    .addAnnotation(
                                        AnnotationSpec.builder(JvmName::class)
                                            .addMember("name = %S", "validatable${entity.simpleName.asString().replaceFirstChar { it.uppercase() }}${property.simpleName.asString().replaceFirstChar { it.uppercase() }}")
                                            .build()
                                    )
                                    .addStatement("return getValidatableValue(%T::%N)", entity.toTypeName(), property.toMemberName())
                                    .build()
                            )
                            .build()
                    )
                }
            }

            // TODO: change the ALL_FILES
            codeGenerator.createNewFile(Dependencies.ALL_FILES, subPackageName, "Validatables", "kt").use { output ->
                OutputStreamWriter(output).use { fileBuilder.build().writeTo(it) }
            }
        }

        return emptyList()
    }

    private fun KSDeclaration.toClassName(): ClassName = ClassName(packageName.asString(), simpleName.asString())
    private fun KSDeclaration.toTypeName(): TypeName = toClassName()
    private fun KSTypeReference.toTypeName(): TypeName = resolve().toTypeName()

    private fun KSType.toTypeName(): TypeName {
        val typeName = ClassName(declaration.packageName.asString(), declaration.simpleName.asString())
        return if (arguments.isEmpty()) typeName else {
            typeName.parameterizedBy(arguments.mapNotNull { it.type }.map { it.toTypeName() })
        }
    }

    private fun KSPropertyDeclaration.toMemberName() = MemberName(parentDeclaration!!.toClassName(), simpleName.asString())

    private fun TypeName.toValidatableType() = Validatable::class.asClassName().parameterizedBy(this)
}

class AkkurateProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return AkkurateProcessor(environment.codeGenerator, environment.logger)
    }
}
