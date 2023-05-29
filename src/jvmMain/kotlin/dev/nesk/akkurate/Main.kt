package dev.nesk.akkurate

import dev.nesk.akkurate.api.*
import dev.nesk.akkurate.gen.*
import java.time.Instant

@Validate
data class Company(val name: String, val optionalShortName: String, val plan: Plan, val users: Set<User>)

@Validate
enum class Plan(val maximumUserCount: Int) {
    FREE(1),
    BASIC(5),
    PREMIUM(15),
}

@Validate
data class User(val firstName: String, val middleName: String, val lastName: String, val birthDay: Instant)

suspend fun computeSomeData() {}

val validate = Validator.suspendable<Company> {
    name {
        minLength(3) explain "{value} is too short"
        maxLength(50) explain "{value} is too long"
    }

    optionalShortName.onlyIf({ notEmpty() }) {
        minLength(2)
        maxLength(10)
    }

    if (optionalShortName.matches { notEmpty() }) {
        optionalShortName.minLength(2)
        optionalShortName.maxLength(10)
    }

    for (user in users) user {
//        allOf {
            (firstName and middleName and lastName) {
                minLength(3)
                maxLength(50)
                computeSomeData()
            }
//        } explain "Names must be between 3 and 50 chars."
    }

    users.each {
        computeSomeData()
        birthDay.before(Instant.now())
    }

    val maxSeats = plan.value.maximumUserCount
    users.maxSize(maxSeats) explain { "Your plan is limited to $maxSeats seats." } withPath "company.seats"
}

suspend fun main() {
    val johann = User("Johann", "Jesse", "Pardanaud", Instant.now())
    val company = Company("NESK", "NK", Plan.BASIC, setOf(johann))

    when (val result = validate(company)) {
        ValidationResult.Success -> println("Success!")
        is ValidationResult.Failure -> {
            val (errors, company) = result
            println("Failure with company ${company.name}…")
            for (fieldErrors in errors.groupByPath().values) {
                println("Field \"${fieldErrors.path}\":")
                for (message in fieldErrors.errorMessages) {
                    println("- $message")
                }
            }
        }
    }

    try {
        validate(company).orThrow()
    } catch (e: ValidationException) {
        println(e.errors.groupByPath())
    }
}

/**
 * - atomic, oneOf, allOf
 * - composable validation
 * - context
 */
