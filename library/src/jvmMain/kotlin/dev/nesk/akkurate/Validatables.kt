package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.Constraint
import kotlin.reflect.KProperty1
import kotlin.reflect.KFunction1

public interface Validatable<T> {
    public val path: List<String> // TODO: change those for a get(), to avoid property clashing (imagine a model already has a `path` property?)
    public val value: T // TODO: maybe an `unwrap()` method would be easier for the user to understand?

    // those were imagined for caching all the Validatable decorators but since they are bound to a class/property couple
    // and not to specific instances, I'm not sure they really bring anything worth
    public fun <V> getValidatableValue(property: KProperty1<T, V>): Validatable<V>
    public fun <V> getValidatableValue(property: KFunction1<T, V>): Validatable<V>
}

public inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = this.block()
public inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint = TODO()
public fun <T> Validatable<T>.validateWith(validator: Validator.Runner<T>): Unit = TODO()
public suspend fun <T> Validatable<T>.validateWith(validator: Validator.SuspendableRunner<T>): Unit = TODO()

/*
    This way of managing compound validation doesn't allow to write `foo and bar {}` without parentheses around the compound: `(foo and bar) {}`
    This is due to Kotlin's precedence, if you write without parentheses, its like writing `foo and (bar {})`. We could solve this by applying the
    constraints first to the last validatable, which returns the lambda and applies to the previous validatable or compound. However this brings
    many issues:
        - since the lambda must be returned, the invoke operator can no longer be inline
        - without inline functions, the lambda can no longer contain suspending code
        - validatables are not validated in the same order they are written
        - compounds of multiple types are not supported because precedence applies the lambda to the last validatable, and not to the compound

    To ease development, the first release will make parentheses mandatory.
 */
public interface CompoundValidatable<T> {
    public val validatables: Set<Validatable<T>>

    public infix fun and(other: Validatable<T>): CompoundValidatable<T>
    public infix fun and(other: CompoundValidatable<T>): CompoundValidatable<T>
}

public inline operator fun <T> CompoundValidatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = validatables.forEach { it.block() }
public infix fun <T> Validatable<T>.and(other: Validatable<T>): CompoundValidatable<T> = TODO()

// TODO: find a solution to improve type combinations
//infix fun <T1, T2> CompoundValidatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()
//infix fun <T1, T2: Any?> CompoundValidatable<T1>.and(other: CompoundValidatable<T2>): CompoundValidatable<Any?> = TODO()
//infix fun <T1, T2: Any?> Validatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()
