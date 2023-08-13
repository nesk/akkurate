package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.ConstraintViolationSet

public sealed interface ValidationResult<out T> {
    public fun orThrow()

    public class Success<T> internal constructor(public val value: T) : ValidationResult<T> {
        public operator fun component1(): T = value

        override fun orThrow(): Unit = Unit

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success<*>

            return value == other.value
        }

        override fun hashCode(): Int = value?.hashCode() ?: 0
        override fun toString(): String = "Success(value=$value)"
    }

    // This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
    // https://youtrack.jetbrains.com/issue/KT-11914
    public class Failure internal constructor(public val violations: ConstraintViolationSet) : ValidationResult<Nothing> {
        public operator fun component1(): ConstraintViolationSet = violations

        override fun orThrow(): Nothing = throw Exception(violations)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Failure

            return violations == other.violations
        }

        override fun hashCode(): Int = violations.hashCode()
        override fun toString(): String = "Failure(violations=$violations)"
    }

    public class Exception internal constructor(public val violations: ConstraintViolationSet) : RuntimeException()
}
