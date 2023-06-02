package dev.nesk.akkurate.api

import dev.nesk.akkurate.api.constraints.Constraint
import kotlin.reflect.KProperty1
import kotlin.reflect.KFunction1

interface Validatable<T> {
    val path: List<String>
    val value: T // TODO: maybe an `unwrap()` method would be easier for the user to understand?

    // those were imagined for caching all the Validatable decorators but since they are bound to a class/property couple
    // and not to specific instances, I'm not sure they really bring anything worth
    fun <V> getValidatableValue(property: KProperty1<T, V>): Validatable<V>
    fun <V> getValidatableValue(property: KFunction1<T, V>): Validatable<V>
}

inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit) = this.block()
inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint = TODO()
fun <T> Validatable<T>.validateWith(validator: Validator.Runner<T>): Unit = TODO()
suspend fun <T> Validatable<T>.validateWith(validator: Validator.SuspendableRunner<T>): Unit = TODO()

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
interface CompoundValidatable<T> {
    val validatables: Set<Validatable<T>>

    infix fun and(other: Validatable<T>): CompoundValidatable<T>
    infix fun and(other: CompoundValidatable<T>): CompoundValidatable<T>
}

inline operator fun <T> CompoundValidatable<T>.invoke(block: Validatable<T>.() -> Unit) = validatables.forEach { it.block() }
infix fun <T1, T2> CompoundValidatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()
infix fun <T1, T2: Any?> CompoundValidatable<T1>.and(other: CompoundValidatable<T2>): CompoundValidatable<Any?> = TODO()
infix fun <T> Validatable<T>.and(other: Validatable<T>): CompoundValidatable<T> = TODO()
infix fun <T1, T2: Any?> Validatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()
