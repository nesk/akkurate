/**
 * — Assertions should be extension functions for userland extendability. Shadowing can be solved with renamed imports.
 * — Types with interfaces are easy to mimic with the delegation pattern: `class Derived(b: Base) : Base by b`
 * — implementing interfaces seems a bit too difficult and might not be a good idea, for example the size of a collection should return `ValidatableInt` instead of `Int`.
 * — native types might need to be manually implemented to ensure a better API and speed up the KSP generation
 * — always implement equals, hashcode, toString
 * — putting the validation extension functions on `Validatable<T>` is better than the specific interfaces, it allows the `ValidatableCompound` to reuse them
 *      — WARNING! When using an invoke, for example on a `ValidatableCompany`, the fields like `name` will not be available.
 *        A solution would be to declare the fields as extension properties on the `Validatable<Company>` class. WARNING: too much imports?
 */

package dev.nesk.akkurate.gen

import dev.nesk.akkurate.Company
import dev.nesk.akkurate.Plan
import dev.nesk.akkurate.User
import dev.nesk.akkurate.api.Constraint
import dev.nesk.akkurate.api.Validatable
import java.time.Instant
import kotlin.reflect.KClass

inline fun Validator(klass: KClass<Long>, block: ValidatableLong.() -> Unit) {}
interface ValidatableLong : Validatable<Long>

inline fun Validator(klass: KClass<Int>, block: ValidatableInt.() -> Unit) {}
interface ValidatableInt : Validatable<Int>

inline fun Validator(klass: KClass<String>, block: ValidatableString.() -> Unit) {}
interface ValidatableString : Validatable<String>

fun Validatable<String>.minLength(length: Int): Constraint<String> = TODO()
fun Validatable<String>.maxLength(length: Int): Constraint<String> = TODO()
inline fun <T> Validator(klass: KClass<Collection<T>>, block: ValidatableCollection<T>.() -> Unit) {}
interface ValidatableCollection<out T> : Validatable<Collection<T>>, Iterable<T>

fun <T> Validatable<Collection<T>>.minSize(length: Int): Constraint<Collection<T>> = TODO()
fun <T> Validatable<Collection<T>>.maxSize(length: Int): Constraint<Collection<T>> = TODO()
inline fun <T> Validator(klass: KClass<Set<T>>, block: ValidatableSet<T>.() -> Unit) {}
interface ValidatableSet<out T> : ValidatableCollection<T>

inline fun Validator(klass: KClass<Instant>, block: ValidatableInstant.() -> Unit) {}
interface ValidatableInstant : Validatable<Instant> {
    val firstName: ValidatableString
    val seconds: ValidatableLong
    val nanos: ValidatableInt
}

fun Validatable<Instant>.before(other: Instant): Constraint<Instant> = TODO()
inline fun Validator(klass: KClass<Company>, block: ValidatableCompany.() -> Unit) {}
interface ValidatableCompany : Validatable<Company> {
    val name: ValidatableString
    val plan: ValidatablePlan
    val users: ValidatableSet<ValidatableUser>
}

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
