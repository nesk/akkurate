package dev.nesk.akkurate.examples.full

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.accessors.each
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.*
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.explain
import dev.nesk.akkurate.constraints.withPath
import dev.nesk.akkurate.examples.full.validation.accessors.*
import dev.nesk.akkurate.validatables.and
import dev.nesk.akkurate.validatables.invoke
import dev.nesk.akkurate.validateWith
import java.time.Instant

@Validate
data class Holding(val name: String)

@Validate
data class Company(val name: String, val plan: Plan, val users: Set<User>, val holding: Holding? = null)

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

val config = Configuration(
    rootPath = listOf("company")
)

val validateCompany = Validator.suspendable<CompanyValidationContext, Company>(config) { (repository) ->
    holding.name {
        notNull()
        minLength(3)
        maxLength(50)
    }

    // TODO: This is a perfect example for conditional constraints. Imagine you allow company names with at least 1 char, time
    //  passes and you have some one-char company names in your database, but now you want to raise the minimum char count
    //  to 3, without changing the older companies. If you just write `minLength(3); inexistant(name)` and the user provides
    //  a 2 chars name which already exists, he will get two validation errors; one about the minimum length and one about
    //  the already existing name. We only want the use to get the error about the minimum length, so we could write
    //  `if (minLength(3)) { inexistant(name) }`, that way we check the database only when the name is already long enough.
    name {
        val (hasMinLen) = minLength(3) explain { "${unwrap()} is too short" }
        maxLength(50) explain { "${unwrap()} is too long" }

        if (hasMinLen) {
            constrain { repository.hasCompanyWithName(it) } explain { "A company already exists with name ${unwrap()}" }
        }
    }

    users.each { validateWith(validateUser) }

    val maxSeats = plan.unwrap().maximumUserCount
    users.maxSize(maxSeats) explain { "Your plan is limited to $maxSeats seats." } withPath { relative("seats") }
}

val validateUser = Validator<User> {
    (firstName and middleName and lastName) {
        minLength(3)
        maxLength(50)
    }

    birthDay.before(Instant.now())
}

suspend fun main() {
    val johann = User("Johann", "Jesse", "Pardanaud", Instant.now())
    val company = Company("NESK", Plan.BASIC, setOf(johann))

    val validateWithContext = validateCompany(CompanyValidationContext(CompanyRepository()))

    when (val result = validateWithContext(company)) {
        ValidationResult.Success -> println("Success!")
        is ValidationResult.Failure -> {
            val (violations, company) = result
            println("Failure with company ${company.name}…")
            for ((path, messages) in violations.byPath) {
                println("Field \"$path\":")
                for (message in messages) {
                    println("- $message")
                }
            }
        }
    }

    try {
        validateWithContext(company).orThrow()
    } catch (e: ValidationResult.Exception) {
        println(e.errors.byPath)
    }
}

/**
 * - atomic, oneOf, allOf
 * - conditional validation: `constraint.mute()` & `validatable.satisfies { constraint }`
 */
