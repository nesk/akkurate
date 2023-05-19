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
data class User(val firstName: String, val lastName: String, val birthDay: Instant)

suspend fun computeSomeData() {}

val validateCompany = Validator.suspendable<Company> {
    name {
        minLength(3) explain "{value} is too short"
        maxLength(50) explain "{value} is too long"
    }

    optionalShortName.notEmpty().ifMatches {
        optionalShortName.minLength(2)
        optionalShortName.maxLength(10)
    }

    for (user in users) user {
        allOf {
            firstName and lastName {
                minLength(3)
                maxLength(50)
            }
        } explain "Names must be between 3 and 50 chars."
    }

    users.each {
        computeSomeData()
        birthDay.before(Instant.now())
    }

    val maxSeats = plan.value.maximumUserCount
    users.maxSize(maxSeats) explain { "Your plan is limited to $maxSeats seats." } withPath "company.seats"
}

suspend fun main() {
    val johann = User("Johann", "Pardanaud", Instant.now())
    val company = Company("NESK", "NK", Plan.BASIC, setOf(johann))
    validateCompany(company)
}

/**
 * - atomic, oneOf, allOf
 * - composable validation
 */
