package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable

public operator fun <T> Validatable<Iterable<T>>.iterator(): Iterator<Validatable<T>> = TODO()
public inline fun <T> Validatable<Iterable<T>>.each(block: Validatable<T>.() -> Unit): Unit = TODO()
