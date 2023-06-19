package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.validatables.Validatable

public interface Constraint {
    public val satisfied: Boolean
    public operator fun component1(): Boolean = satisfied

    public var message: String?
    public var path: List<String>?
}

// TODO: Create functions with blocks to generate message and path only when the constraint is not satisfied? Could be useful for slow operations.
public infix fun Constraint.explain(message: String): Constraint = apply { this.message = message }
public infix fun Constraint.withPath(path: List<String>): Constraint = apply { this.path = path }
public infix fun Constraint.withPath(fragment: String): Constraint = apply { path = listOf(fragment) }

public inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint = TODO()
public inline fun <T> Validatable<out T?>.constrainIfNotNull(block: (value: T) -> Boolean): Constraint = TODO()
