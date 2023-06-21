package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.validatables.Validatable

// This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
// https://youtrack.jetbrains.com/issue/KT-11914
public class Constraint(public val satisfied: Boolean, public var path: List<String>) {
    public var message: String? = null

    public operator fun component1(): Boolean = satisfied

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Constraint

        if (satisfied != other.satisfied) return false
        if (path != other.path) return false
        return message == other.message
    }

    override fun hashCode(): Int {
        var result = satisfied.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }
}

public infix fun Constraint.explain(message: String): Constraint = apply { this.message = message }
public infix fun Constraint.explain(block: () -> String): Constraint = apply {
    if (!satisfied) message = block()
}

public infix fun Constraint.withPath(path: List<String>): Constraint = apply { this.path = path }
public infix fun Constraint.withPath(block: (previousPath: List<String>) -> List<String>): Constraint = apply {
    if (!satisfied) path = block(path)
}

public inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint {
    return Constraint(block(unwrap()), path())
        .also(::registerConstraint)
}

public inline fun <T> Validatable<out T?>.constrainIfNotNull(block: (value: T) -> Boolean): Constraint {
    val constraint = unwrap()
        ?.let { value -> constrain { block(value) } }
        ?: constrain { true } // We do not want the constraint to fail when the value is null.
    return constraint.also(::registerConstraint)
}
