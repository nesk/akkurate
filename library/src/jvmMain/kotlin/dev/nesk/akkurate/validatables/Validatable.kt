package dev.nesk.akkurate.validatables

import dev.nesk.akkurate.Path
import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.ConstraintDescriptor
import dev.nesk.akkurate.constraints.ConstraintViolation
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

public class Validatable<out T> internal constructor(private val wrappedValue: T, pathSegment: String? = null, internal val parent: Validatable<*>? = null) {
    private val path: Path = buildList {
        addAll(parent?.path ?: emptyList())
        if (!pathSegment.isNullOrEmpty()) {
            add(pathSegment)
        }
    }

    public fun path(): Path = path

    public fun unwrap(): T = wrappedValue

    internal val constraints: MutableSet<ConstraintDescriptor> by BubblingConstraints(parent)

    public fun registerConstraint(constraint: Constraint) {
        if (!constraint.satisfied) constraints.add(constraint)
    }

    public fun registerConstraint(constraint: ConstraintViolation) {
        constraints.add(constraint)
    }

    /**
     * Indicates whether some other object is "equal to" this validatable.
     *
     * Validatables are only compared against the value returned by [unwrap].
     * This allows easy comparisons between two validatables:
     *
     * ```
     * Validator<UserRegistration> {
     *     constrain { password == passwordConfirmation }
     * }
     * ```
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Validatable<*>

        return wrappedValue == other.wrappedValue
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hashcode is only produced from the value returned by [unwrap],
     * according to [equals] implementation.
     */
    override fun hashCode(): Int = wrappedValue?.hashCode() ?: 0

    override fun toString(): String = "Validatable(unwrap=$wrappedValue, path=$path)"

    /**
     * Allows a [Validatable] to use the constraints collection of its parent or,
     * if it has no parent, instantiates a new dedicated collection to store them.
     */
    private class BubblingConstraints(val parent: Validatable<*>? = null) {
        val constraints by lazy { mutableSetOf<ConstraintDescriptor>() }
        operator fun getValue(thisRef: Validatable<*>, property: KProperty<*>) = parent?.constraints ?: constraints
    }
}

public inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = this.block()

public fun <T : Any, V> Validatable<T>.validatableOf(getter: KProperty1<T, V>): Validatable<V> {
    return Validatable(getter.get(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfProperty")
public fun <T : Any?, V> Validatable<T>.validatableOf(getter: KProperty1<T & Any, V>): Validatable<V?> {
    return Validatable(unwrap()?.let { getter.get(it) }, getter.name, this)
}

public fun <T : Any, V> Validatable<T>.validatableOf(getter: KFunction1<T, V>): Validatable<V> {
    return Validatable(getter.invoke(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfFunction")
public fun <T : Any?, V> Validatable<T>.validatableOf(getter: KFunction1<T & Any, V>): Validatable<V?> {
    return Validatable(unwrap()?.let { getter.invoke(it) }, getter.name, this)
}
