package dev.nesk.akkurate.api

annotation class Validate
interface Constraint<T> {
    infix fun explain(messageBuilder: (T) -> String): Constraint<T>
    infix fun withPath(path: String): Constraint<T>
}

infix fun <T> Constraint<T>.explain(message: String): Constraint<T> = explain { message }
inline fun <T> Validator(block: Validatable<T>.() -> Unit) {}
interface Validatable<out T> {
    val path: List<String>
    val value: T
}

operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit) {
    this.block()
}
