package dev.nesk.akkurate

import dev.nesk.akkurate.api.*
import dev.nesk.akkurate.api.annotations.Validate
import dev.nesk.akkurate.api.constraints.builders.*
import dev.nesk.akkurate.api.constraints.explain
import dev.nesk.akkurate.api.constraints.withPath
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

data class CompanyValidationContext(val repository: CompanyRepository)
class CompanyRepository {
    suspend fun hasCompanyWithName(name: String): Boolean = TODO()
}

suspend fun computeSomeData() {}

val validate = Validator.suspendable<CompanyValidationContext, Company> { (repository) ->
    // TODO: This is a perfect example for conditional constraints. Imagine you allow company names with at least 1 char, time
    //  passes and you have some one-char company names in your database, but now you want to raise the minimum char count
    //  to 3, without changing the older companies. Ìf you just write `minLength(3); inexistant(name)` and the user provides
    //  a 2 chars name which already exists, he will get two validation errors; one about the minimum length and one about
    //  the already existing name. We only want the use to get the error about the minimum length, so we could write
    //  `if (minLength(3)) { inexistant(name) }`, that way we check the database only when the name is already long enough.
    name {
        minLength(3) explain "{value} is too short"
        maxLength(50) explain "{value} is too long"
        constrain { repository.hasCompanyWithName(it) } explain "A company already exists with name {value}"
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

    val validateWithContext = validate(CompanyValidationContext(CompanyRepository()))

    when (val result = validateWithContext(company)) {
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
        validateWithContext(company).orThrow()
    } catch (e: ValidationException) {
        println(e.errors.groupByPath())
    }
}

/**
 * - atomic, oneOf, allOf
 * - composable validation
 * - traversal with nullable values
 */
