package dev.nesk.akkurate.validatables


public interface Validatable<T> {
    public fun path(): List<String>
    public fun unwrap(): T

    // TODO: Should this be part of the public API?
    public fun <V> createValidatable(value: V): Validatable<V>
}

public inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = this.block()
