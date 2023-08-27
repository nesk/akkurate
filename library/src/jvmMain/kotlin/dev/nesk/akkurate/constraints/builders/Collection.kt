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

@JvmName("collectionHasSizeEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

@JvmName("collectionHasSizeLowerThan")
public fun <T> Validatable<Collection<T>?>.hasSizeLowerThan(size: Int): Constraint =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

@JvmName("collectionHasSizeLowerThanOrEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeLowerThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

@JvmName("collectionHasSizeGreaterThan")
public fun <T> Validatable<Collection<T>?>.hasSizeGreaterThan(size: Int): Constraint =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

@JvmName("collectionHasSizeGreaterThanOrEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeGreaterThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

@JvmName("collectionHasSizeBetween")
public fun <T> Validatable<Collection<T>?>.hasSizeBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }
