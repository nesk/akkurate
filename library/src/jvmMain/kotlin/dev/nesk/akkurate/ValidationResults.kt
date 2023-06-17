package dev.nesk.akkurate

public sealed interface ValidationResult<out T> {
    /** @throws ValidationException */
    public fun orThrow()

    public object Success : ValidationResult<Nothing> {
        override fun orThrow(): Unit = Unit
    }

    // This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
    public class Failure<T> internal constructor(public val errors: ValidationErrors.FlatList, public val value: T) : ValidationResult<T> {
        public operator fun component1(): ValidationErrors.FlatList = errors
        public operator fun component2(): T = value

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

        override fun toString(): String = "Failure(errors=$errors, value=$value)"
    }
}

public class ValidationException internal constructor(public val errors: ValidationErrors.FlatList) : RuntimeException()
public sealed interface ValidationErrors {
    @JvmInline
    public value class FlatList(private val errors: Set<Field.Error>) : ValidationErrors, Set<Field.Error> by errors {
        public fun groupByPath(): GroupedByPath = TODO()
    }

    @JvmInline
    public value class GroupedByPath(private val errors: Map<List<String>, Field.Errors>) : ValidationErrors, Map<List<String>, Field.Errors> by errors
}

public interface Field {
    public val path: List<String>

    public interface Error : Field {
        public val errorMessage: String
    }

    public interface Errors : Field {
        public val errorMessages: Set<String>
    }
}
