package dev.nesk.akkurate.validatables

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.Constraint

public interface Validatable<T> {
    public fun path(): List<String>
    public fun unwrap(): T

    // TODO: Should this be part of the public API?
    public fun <V> createValidatable(value: V): Validatable<V>
}

public inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = this.block()
public inline fun <T> Validatable<T>.constrain(block: (value: T) -> Boolean): Constraint = TODO()
public inline fun <T> Validatable<out T?>.constrainIfNotNull(block: (value: T) -> Boolean): Constraint = TODO()
public fun <T> Validatable<T>.validateWith(validator: Validator.Runner<T>): Unit = TODO()
public suspend fun <T> Validatable<T>.validateWith(validator: Validator.SuspendableRunner<T>): Unit = TODO()
