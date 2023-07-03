package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable
import dev.nesk.akkurate.validatables.invoke

public operator fun <T> Validatable<Iterable<T>>.iterator(): Iterator<Validatable<T>> = unwrap()
    .asSequence()
    .withIndex()
    .map { Validatable(it.value, it.index.toString(), this) }
    .iterator()

public inline fun <T> Validatable<Iterable<T>>.each(block: Validatable<T>.() -> Unit) {
    for (row in this) row.invoke(block)
}
