@file:JvmName("ConstraintKt")

package dev.nesk.akkurate.api.constraints

interface Constraint<T> {
    val value: T
    var message: String
    var path: List<String>
}

inline infix fun <T> Constraint<T>.explain(messageBuilder: (value: T) -> String) = apply { message = messageBuilder(value) }
infix fun <T> Constraint<T>.explain(message: String): Constraint<T> = explain { message.replace("{value", it.toString()) }

inline infix fun <T> Constraint<T>.withPath(pathBuilder: (oldPath: List<String>) -> List<String>) = apply { path = pathBuilder(path) }
inline infix fun <T> Constraint<T>.withPath(pathBuilder: (oldPath: List<String>) -> String) = apply { path = listOf(pathBuilder(path)) }
infix fun <T> Constraint<T>.withPath(path: List<String>) = apply { this.path = path }
infix fun <T> Constraint<T>.withPath(fragment: String) = apply { path = listOf(fragment) }
