import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

internal fun KSDeclaration.toClassName(): ClassName = ClassName(packageName.asString(), simpleName.asString())

internal fun KSDeclaration.toTypeName(): TypeName = toClassName()

internal fun KSTypeReference.toTypeName(): TypeName = resolve().toTypeName()

internal fun KSType.toTypeName(): TypeName {
    val className = ClassName(declaration.packageName.asString(), declaration.simpleName.asString())
    val typeName = if (arguments.isEmpty()) className else {
        className.parameterizedBy(arguments.mapNotNull { it.type }.map { it.toTypeName() })
    }
    return typeName.copy(nullable = nullability == Nullability.NULLABLE)
}

internal fun KSPropertyDeclaration.toMemberName() = MemberName(parentDeclaration!!.toClassName(), simpleName.asString())
