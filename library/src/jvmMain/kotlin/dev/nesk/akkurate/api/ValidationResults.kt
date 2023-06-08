package dev.nesk.akkurate.api

sealed interface ValidationResult<out T> {
    /** @throws ValidationException */
    fun orThrow()

    object Success: ValidationResult<Nothing> {
        override fun orThrow() = Unit
    }

    class Failure<T> internal constructor(val errors: ValidationErrors.FlatList, val value: T): ValidationResult<T> {
        operator fun component1() = errors
        operator fun component2() = value

        override fun orThrow(): Nothing = throw ValidationException(errors)

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

        override fun toString() = "Failure(errors=$errors, value=$value)"
    }
}

class ValidationException internal constructor(val errors: ValidationErrors.FlatList): RuntimeException()
sealed interface ValidationErrors {
    @JvmInline
    value class FlatList(private val errors: Set<Field.Error>): ValidationErrors, Set<Field.Error> by errors {
        fun groupByPath(): GroupedByPath = TODO()
    }

    @JvmInline
    value class GroupedByPath(private val errors: Map<List<String>, Field.Errors>): ValidationErrors, Map<List<String>, Field.Errors> by errors
}

interface Field {
    val path: List<String>

    interface Error: Field {
        val errorMessage: String
    }

    interface Errors: Field {
        val errorMessages: Set<String>
    }
}
