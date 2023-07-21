package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.ConstraintViolationSet

public sealed interface ValidationResult<out T> {
    public fun orThrow()

    // TODO: convert to a class to be future-proof when the API will be able to return a new version of the validated data?
    public object Success : ValidationResult<Nothing> {
        override fun orThrow(): Unit = Unit
    }

    // This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
    // https://youtrack.jetbrains.com/issue/KT-11914
    public class Failure<T> internal constructor(public val violations: ConstraintViolationSet, public val value: T) : ValidationResult<T> {
        public operator fun component1(): ConstraintViolationSet = violations
        public operator fun component2(): T = value

        override fun orThrow(): Nothing = throw Exception(violations)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Failure<*>

            if (violations != other.violations) return false
            return value == other.value
        }

        override fun hashCode(): Int {
            var result = violations.hashCode()
            result = 31 * result + (value?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String = "Failure(errors=$violations, value=$value)"
    }

    public class Exception internal constructor(public val violations: ConstraintViolationSet) : RuntimeException()
}
