package dev.nesk.akkurate.api

import kotlin.reflect.KProperty1
import kotlin.reflect.KFunction1

annotation class Validate

class Validator<T> {
    companion object {
        inline operator fun <T> invoke(block: Validatable<T>.() -> Unit): (T) -> Unit = TODO()
        fun <T> suspendable(block: suspend Validatable<T>.() -> Unit): suspend (T) -> Unit = TODO()
    }
}

interface Validatable<T> {
    val path: List<String>
    val value: T

    // those were imagined for caching all the Validatable decorators but since they are bound to a class/property couple
    // and not to specific instances, I'm not sure they really bring anything worth
    fun <V> getValidatableValue(property: KProperty1<T, V>): Validatable<V>
    fun <V> getValidatableValue(property: KFunction1<T, V>): Validatable<V>
}

inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit) = this.block()

interface Constraint<T> {
    val value: T
    fun matches(): Boolean // TODO: find a better name to describe the fact it will never be used as a constraint, you only check the value
    var message: String
    var path: List<String>
}

inline infix fun <T> Constraint<T>.ifMatches(block: () -> Unit) = if (matches()) block() else Unit

inline infix fun <T> Constraint<T>.explain(messageBuilder: (value: T) -> String) = apply { message = messageBuilder(value) }
infix fun <T> Constraint<T>.explain(message: String): Constraint<T> = explain { message.replace("{value", it.toString()) }

inline infix fun <T> Constraint<T>.withPath(pathBuilder: (oldPath: List<String>) -> List<String>) = apply { path = pathBuilder(path) }
inline infix fun <T> Constraint<T>.withPath(pathBuilder: (oldPath: List<String>) -> String) = apply { path = listOf(pathBuilder(path)) }
infix fun <T> Constraint<T>.withPath(path: List<String>) = apply { this.path = path }
infix fun <T> Constraint<T>.withPath(fragment: String) = apply { path = listOf(fragment) }
