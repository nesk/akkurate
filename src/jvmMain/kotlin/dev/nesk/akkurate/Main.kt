package dev.nesk.akkurate

import java.time.Instant
import kotlin.reflect.KClass

@IsValidatable
data class Company(val name: String, val plan: Plan, val users: Set<User>)

@IsValidatable
enum class Plan(val maximumUserCount: Int) {
    FREE(1), BASIC(5), PREMIUM(15)
}

@IsValidatable
data class User(val firstName: String, val lastName: String, val birthDay: Instant)

val validateCompany = Validator(Company::class) {
    name { // read-only
        minLength(3)
        maxLength(50)
    }

    for (user: ValidatableUser in users) with(user) {
        firstName and lastName { // composite validation with infix function
            minLength(3)
            maxLength(50)
        }
        birthDay.before(Instant.now())
    }

    users.size.lowerThanOrEqual(plan.value.maximumUserCount)
}

// ============================================================================================================================
// =============================================== KSP GENERATED CODE =========================================================
// ============================================================================================================================
//
// - Assertions should be extension functions for userland extendability. Shadowing can be solved with renamed imports.
// - Types with interfaces are easy to mimic with the delegation pattern: `class Derived(b: Base) : Base by b`
// - always implement equals, hashcode, toString

inline fun <T> Validator(block: Validatable<T>.() -> Unit) {}
interface Validatable<out ValueType> {
    val path: List<String>
    val value: ValueType
}

inline fun Validator(klass: KClass<String>, block: ValidatableString.() -> Unit) {}
interface ValidatableString : Validatable<String> {
    operator fun invoke(block: ValidatableString.() -> Unit) {
        this.block()
    }
}
fun ValidatableString.minLength(length: Int): Unit = TODO()
fun ValidatableString.maxLength(length: Int): Unit = TODO()

inline fun Validator(klass: KClass<Plan>, block: ValidatablePlan.() -> Unit) {}
interface ValidatablePlan : Validatable<Plan> {
    val maximumUserCount: Int
}

inline fun Validator(klass: KClass<User>, block: ValidatableUser.() -> Unit) {}
interface ValidatableUser : Validatable<User> {
    val firstName: ValidatableString
    val lastName: ValidatableString
    val birthDay: ValidatableInstant
}

inline fun Validator(klass: KClass<Instant>, block: ValidatableInstant.() -> Unit) {}
interface ValidatableInstant : Validatable<Instant> {
    val firstName: ValidatableString
    val seconds: ValidatableLong
    val nanos: ValidatableInt
}

inline fun Validator(klass: KClass<Long>, block: ValidatableLong.() -> Unit) {}
interface ValidatableLong : Validatable<Long>

inline fun Validator(klass: KClass<Int>, block: ValidatableInt.() -> Unit) {}
interface ValidatableInt : Validatable<Int>

inline fun Validator(klass: KClass<Company>, block: ValidatableCompany.() -> Unit) {}
interface ValidatableCompany : Validatable<Company> {
    val name: ValidatableString
    val plan: ValidatablePlan
    val users: Set<User>
}
