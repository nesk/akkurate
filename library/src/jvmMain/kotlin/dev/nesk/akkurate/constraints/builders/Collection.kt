/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * The validatable [Collection] must be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { isNotEmpty() }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(emptyList()) // Failure (message: Must not be empty)
 * ```
 */
@JvmName("collectionIsEmpty")
public fun <T> Validatable<Collection<T>?>.isEmpty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

/**
 * The validatable [Collection] must not be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { isEmpty() }
 * validate(emptyList()) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: Must be empty)
 * ```
 */
@JvmName("collectionIsNotEmpty")
public fun <T> Validatable<Collection<T>?>.isNotEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

/**
 * The validatable [Collection] must have a size equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeEqualTo(3) }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(listOf('a', 'b')) // Failure (message: The number of items must be equal to 3)
 * ```
 */
@JvmName("collectionHasSizeEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

/**
 * The validatable [Collection] must have a size different from [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeNotEqualTo(3) }
 * validate(listOf('a', 'b')) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: The number of items must be different from 3)
 * ```
 */
@JvmName("collectionHasSizeNotEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeNotEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size != size } otherwise { "The number of items must be different from $size" }

/**
 * The validatable [Collection] must have a size lower than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeLowerThan(3) }
 * validate(listOf('a', 'b')) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: The number of items must be lower than 3)
 * ```
 */
@JvmName("collectionHasSizeLowerThan")
public fun <T> Validatable<Collection<T>?>.hasSizeLowerThan(size: Int): Constraint =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

/**
 * The validatable [Collection] must have a size lower than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeLowerThanOrEqualTo(2) }
 * validate(listOf('a')) // Success
 * validate(listOf('a', 'b')) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: The number of items must be lower than or equal to 2)
 * ```
 */
@JvmName("collectionHasSizeLowerThanOrEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeLowerThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

/**
 * The validatable [Collection] must have a size greater than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeGreaterThan(2) }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(listOf('a', 'b')) // Failure (message: The number of items must be greater than 2)
 * ```
 */
@JvmName("collectionHasSizeGreaterThan")
public fun <T> Validatable<Collection<T>?>.hasSizeGreaterThan(size: Int): Constraint =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

/**
 * The validatable [Collection] must have a size greater than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeGreaterThanOrEqualTo(2) }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(listOf('a', 'b')) // Success
 * validate(listOf('a')) // Failure (message: The number of items must be greater than or equal to 2)
 * ```
 */
@JvmName("collectionHasSizeGreaterThanOrEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeGreaterThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

/**
 * The validatable [Collection] must have a size [within the provided range][range] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { hasSizeBetween(1..2) }
 * validate(emptyList()) // Failure (message: The number of items must be between 1 and 2)
 * validate(listOf('a')) // Success
 * validate(listOf('a', 'b')) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: The number of items must be between 1 and 2)
 * ```
 */
@JvmName("collectionHasSizeBetween")
public fun <T> Validatable<Collection<T>?>.hasSizeBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }

/**
 * The validatable [Collection] must contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { isContaining('b') }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(emptyList()) // Failure (message: Must contain "b")
 * ```
 */
@JvmName("collectionIsContaining")
public fun <T> Validatable<Collection<T>?>.isContaining(element: T): Constraint =
    constrainIfNotNull { it.contains(element) } otherwise { "Must contain \"$element\"" }

/**
 * The validatable [Collection] must not contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Collection<Char>> { isNotContaining('b') }
 * validate(emptyList()) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: Must not contain "b")
 * ```
 */
@JvmName("collectionIsNotContaining")
public fun <T> Validatable<Collection<T>?>.isNotContaining(element: T): Constraint =
    constrainIfNotNull { !it.contains(element) } otherwise { "Must not contain \"$element\"" }
