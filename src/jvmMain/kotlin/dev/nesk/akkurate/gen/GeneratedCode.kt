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

// String
fun Validatable<String>.minLength(length: Int): Constraint<String> = TODO()
fun Validatable<String>.maxLength(length: Int): Constraint<String> = TODO()

// Iterables (see: https://kotlinlang.slack.com/archives/C0B8MA7FA/p1683643554178309)
operator fun <T> Validatable<out Iterable<T>>.iterator(): Iterator<T> = TODO()
fun <T> Validatable<out Collection<T>>.minSize(length: Int): Constraint<Collection<T>> = TODO()
fun <T> Validatable<out Collection<T>>.maxSize(length: Int): Constraint<Collection<T>> = TODO()

// Instant
val Validatable<Instant>.seconds: Validatable<Long> get() = TODO()
val Validatable<Instant>.nanos: Validatable<Int> get() = TODO()
fun Validatable<Instant>.before(other: Instant): Constraint<Instant> = TODO()

// Company
val Validatable<Company>.name: Validatable<String> get() = TODO()
val Validatable<Company>.plan: Validatable<Plan> get() = TODO()
val Validatable<Company>.users: Validatable<Set<Validatable<User>>> get() = TODO()

// Plan
val Validatable<Plan>.maximumUserCount: Validatable<Int> get() = TODO()

// User
val Validatable<User>.firstName: Validatable<String> get() = TODO()
val Validatable<User>.lastName: Validatable<String> get() = TODO()
val Validatable<User>.birthDay: Validatable<Instant> get() = TODO()
