package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Path
import dev.nesk.akkurate.PathBuilder
import dev.nesk.akkurate.validatables.Validatable

// This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
// https://youtrack.jetbrains.com/issue/KT-11914
// TODO: what about `Constraint<out T>`?
public class Constraint(public val satisfied: Boolean, public var validatable: Validatable<*>) {
    private var customPath: Path? = null

    public var path: Path
        get() = customPath ?: validatable.path()
        set(value) {
            customPath = value
        }

    public var message: String? = null

    public operator fun component1(): Boolean = satisfied

    /**
     * Indicates whether some other object is "equal to" this constraint.
     *
     * Constraints are compared against the following properties:
     *
     * * [satisfied]
     * * [path]
     * * [message]
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Constraint

        if (satisfied != other.satisfied) return false
        if (path != other.path) return false
        return message == other.message
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hashcode is produced from the following properties:
     *
     * * [satisfied]
     * * [path]
     * * [message]
     *
     * Those criteria were chosen specifically to ensure, in a set, only a single occurrence
     * of a message can be used for each path.
     */
    override fun hashCode(): Int {
        var result = satisfied.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Constraint(satisfied=$satisfied, validatable=$validatable, path=$customPath, message=$message)"
    }
}

public inline infix fun Constraint.explain(block: () -> String): Constraint = apply {
    if (!satisfied) message = block()
}

public inline infix fun Constraint.withPath(block: PathBuilder.(originalPath: Path) -> Path): Constraint {
    if (!satisfied) {
        path = PathBuilder(validatable).block(validatable.path())
    }
    return this
}

public inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint {
    return Constraint(block(unwrap()), this)
        .also(::registerConstraint)
}

public inline fun <T> Validatable<out T?>.constrainIfNotNull(block: (value: T) -> Boolean): Constraint {
    val constraint = unwrap()
        ?.let { value -> constrain { block(value) } }
        ?: constrain { true } // We do not want the constraint to fail when the value is null.
    return constraint.also(::registerConstraint)
}
