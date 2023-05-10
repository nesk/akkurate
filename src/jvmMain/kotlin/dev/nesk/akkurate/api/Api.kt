package dev.nesk.akkurate.api

import kotlin.reflect.KProperty1
import kotlin.reflect.KFunction1

annotation class Validate

inline fun <T> Validator(block: Validatable<T>.() -> Unit): (T) -> Unit = TODO()

interface Validatable<T> {
    val path: List<String>
    val value: T

    // those were imagined for caching all the Validatable decorators but since they are bound to a class/property couple
    // and not to specific instances, I'm not sure they really bring anything worth
    fun <V> getValidatableValue(property: KProperty1<T, V>): Validatable<V>
    fun <V> getValidatableValue(property: KFunction1<T, V>): Validatable<V>
}
operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit) = this.block()

interface Constraint<T> {
    infix fun explain(messageBuilder: (value: T) -> String): Constraint<T>
    infix fun withPath(pathBuilder: (oldPath: List<String>) -> List<String>): Constraint<T>
}
infix fun <T> Constraint<T>.explain(message: String): Constraint<T> = explain { message }
infix fun <T> Constraint<T>.withPath(path: List<String>): Constraint<T> = withPath { path }
infix fun <T> Constraint<T>.withPath(fragment: String): Constraint<T> = withPath(listOf(fragment))
