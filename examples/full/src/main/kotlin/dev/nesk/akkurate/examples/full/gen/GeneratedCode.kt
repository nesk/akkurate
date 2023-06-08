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

package dev.nesk.akkurate.examples.full.gen

import dev.nesk.akkurate.Validatable
import java.time.Instant

// Iterables (see: https://kotlinlang.slack.com/archives/C0B8MA7FA/p1683643554178309)
operator fun <T> Validatable<out Iterable<T>>.iterator(): Iterator<Validatable<T>> = TODO()
inline fun <T> Validatable<out Iterable<T>>.each(block: Validatable<T>.() -> Unit): Unit = TODO()

// Instant
val Validatable<Instant>.epochSeconds: Validatable<Long> get() = getValidatableValue(Instant::getEpochSecond)
val Validatable<Instant>.nanos: Validatable<Int> get() = getValidatableValue(Instant::getNano)
