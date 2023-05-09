package dev.nesk.akkurate

import dev.nesk.akkurate.api.*
import dev.nesk.akkurate.gen.*
import java.time.Instant

@Validate
data class Company(val name: String, val plan: Plan, val users: Set<User>)

@Validate
enum class Plan(val maximumUserCount: Int) {
    FREE(1), BASIC(5), PREMIUM(15)
}

@Validate
data class User(val firstName: String, val lastName: String, val birthDay: Instant)

val validateCompany = dev.nesk.akkurate.gen.Validator(Company::class) {
    name {
        minLength(3) explain { "${it.trim()} is too short" }
        maxLength(50) explain { "${it.trim()} is too long" }
    }

    for (user: ValidatableUser in users) with(user) {
        firstName and lastName {
            minLength(3)
            maxLength(50)
        } explain "Names must be between 3 and 50 chars."

        birthDay.before(Instant.now())
    }

    users.maxSize(plan.value.maximumUserCount) explain "Your plan is limited to {value} seats." withPath "company.seats"
}

/**
 * - support suspendable validation
 * - atomic, oneOf, allOf
 */
