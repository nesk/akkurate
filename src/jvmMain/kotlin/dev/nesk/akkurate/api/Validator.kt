package dev.nesk.akkurate.api

interface Validator {
    companion object {
        operator fun <T> invoke(block: Validatable<T>.() -> Unit) = Common(block)
        fun <T> suspendable(block: suspend Validatable<T>.() -> Unit) = Suspendable(block)
    }

    class Common<T>(private val block: Validatable<T>.() -> Unit) {
        operator fun invoke(value: T): ValidationResult<T> = TODO()
    }

    class Suspendable<T>(private val block: suspend Validatable<T>.() -> Unit) {
        suspend operator fun invoke(value: T): ValidationResult<T> = TODO()
    }
}
