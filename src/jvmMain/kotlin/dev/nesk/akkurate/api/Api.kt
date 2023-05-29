package dev.nesk.akkurate.api

import kotlin.reflect.KProperty1
import kotlin.reflect.KFunction1

annotation class Validate

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

interface Validatable<T> {
    val path: List<String>
    val value: T // TODO: maybe an `unwrap()` method would be easier for the user to understand?

    // those were imagined for caching all the Validatable decorators but since they are bound to a class/property couple
    // and not to specific instances, I'm not sure they really bring anything worth
    fun <V> getValidatableValue(property: KProperty1<T, V>): Validatable<V>
    fun <V> getValidatableValue(property: KFunction1<T, V>): Validatable<V>
}
inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit) = this.block()
inline fun <T> Validatable<T>.onlyIf(conditions: Validatable<T>.() -> Unit, block: Validatable<T>.() -> Unit): Unit = TODO()
inline fun <T> Validatable<T>.matches(block: Validatable<T>.() -> Unit): Boolean = TODO()

/*
    This way of managing compound validation doesn't allow to write `foo and bar {}` without parentheses around the compound: `(foo and bar) {}`
    This is due to Kotlin's precedence, if you write without parentheses, its like writing `foo and (bar {})`. We could solve this by applying the
    constraints first to the last validatable, which returns the lambda and applies to the previous validatable or compound. However this brings
    many issues:
        - since the lambda must be returned, the invoke operator can no longer be inline
        - without inline functions, the lambda can no longer contain suspending code
        - validatables are not validated in the same order they are written
        - compounds of multiple types are not supported because precedence applies the lambda to the last validatable, and not to the compound

    To ease development, the first release will make parentheses mandatory.
 */
interface CompoundValidatable<T> {
    val validatables: Set<Validatable<T>>

    infix fun and(other: Validatable<T>): CompoundValidatable<T>
    infix fun and(other: CompoundValidatable<T>): CompoundValidatable<T>
}
inline operator fun <T> CompoundValidatable<T>.invoke(block: Validatable<T>.() -> Unit) = validatables.forEach { it.block() }

infix fun <T1, T2> CompoundValidatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()
infix fun <T1, T2: Any?> CompoundValidatable<T1>.and(other: CompoundValidatable<T2>): CompoundValidatable<Any?> = TODO()

infix fun <T> Validatable<T>.and(other: Validatable<T>): CompoundValidatable<T> = TODO()
infix fun <T1, T2: Any?> Validatable<T1>.and(other: Validatable<T2>): CompoundValidatable<Any?> = TODO()

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
