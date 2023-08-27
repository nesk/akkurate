package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!! THE FOLLOWING CODE MUST BE SYNCED ACROSS `Array.kt`, `Collection.kt` AND `Map.kt` FILES !!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * The validation API is the same across `Array`, `Collection` and `Map` types but, due to missing
 * union types in Kotlin, we must duplicate the code for each of those types.
 */

// TODO: add contains/doesNotContain

@JvmName("arrayHasSizeEqualTo")
public fun <T> Validatable<Array<out T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

@JvmName("arrayHasSizeLowerThan")
public fun <T> Validatable<Array<out T>?>.hasSizeLowerThan(size: Int): Constraint =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

@JvmName("arrayHasSizeLowerThanOrEqualTo")
public fun <T> Validatable<Array<out T>?>.hasSizeLowerThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

@JvmName("arrayHasSizeGreaterThan")
public fun <T> Validatable<Array<out T>?>.hasSizeGreaterThan(size: Int): Constraint =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

@JvmName("arrayHasSizeGreaterThanOrEqualTo")
public fun <T> Validatable<Array<out T>?>.hasSizeGreaterThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

@JvmName("arrayHasSizeBetween")
public fun <T> Validatable<Array<out T>?>.hasSizeBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }
