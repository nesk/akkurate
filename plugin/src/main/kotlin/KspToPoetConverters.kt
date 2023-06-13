import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

internal fun KSDeclaration.toClassName(): ClassName {
    val simpleNames = mutableListOf(simpleName)

    var parent = parentDeclaration
    while (parent != null) {
        simpleNames += parent.simpleName
        parent = parent.parentDeclaration
    }

    return ClassName(packageName.asString(), *simpleNames.reversed().map(KSName::asString).toTypedArray())
}

internal fun KSDeclaration.toTypeName(): TypeName = toClassName()

internal fun KSType.toTypeName(): TypeName {
    val className = declaration.toClassName()
    val typeName = if (arguments.isEmpty()) className else {
        className.parameterizedBy(arguments.mapNotNull { it.type }.map { it.toTypeName() })
    }
    return typeName.copy(nullable = nullability == Nullability.NULLABLE)
}

internal fun KSTypeReference.toTypeName(): TypeName = resolve().toTypeName()

internal fun KSPropertyDeclaration.toMemberName() = MemberName(parentDeclaration!!.toClassName(), simpleName.asString())
