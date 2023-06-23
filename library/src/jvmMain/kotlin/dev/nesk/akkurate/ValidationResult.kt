package dev.nesk.akkurate

public sealed interface ValidationResult<out T> {
    public fun orThrow()

    public object Success : ValidationResult<Nothing> {
        override fun orThrow(): Unit = Unit
    }

    // This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
    // https://youtrack.jetbrains.com/issue/KT-11914
    public class Failure<T> internal constructor(public val errors: ValidationErrors, public val value: T) : ValidationResult<T> {
        public operator fun component1(): ValidationErrors = errors
        public operator fun component2(): T = value

        override fun orThrow(): Nothing = throw Exception(errors)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Failure<*>

            if (errors != other.errors) return false
            return value == other.value
        }

        override fun hashCode(): Int {
            var result = errors.hashCode()
            result = 31 * result + (value?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String = "Failure(errors=$errors, value=$value)"
    }

    public class Exception internal constructor(public val errors: ValidationErrors) : RuntimeException()
}
