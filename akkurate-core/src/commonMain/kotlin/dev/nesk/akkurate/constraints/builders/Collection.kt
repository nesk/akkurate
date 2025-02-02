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

import dev.nesk.akkurate.constraints.GenericConstraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.GenericValidatable
import kotlin.jvm.JvmName

/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!! THE FOLLOWING CODE MUST BE SYNCED ACROSS MULTIPLE FILES: Array.kt, Iterable.kt, Collection.kt, Map.kt !!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * The validation API is nearly the same across `Array`, `Collection`, `Iterable` and `Map` types but, due to
 * missing union types in Kotlin, we must duplicate a lot of code between these types.
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.isEmpty(): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.isNotEmpty(): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeEqualTo(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeNotEqualTo(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeLowerThan(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeLowerThanOrEqualTo(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeGreaterThan(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeGreaterThanOrEqualTo(size: Int): GenericConstraint<MetadataType> =
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
public fun <T, MetadataType> GenericValidatable<Collection<T>?, MetadataType>.hasSizeBetween(range: IntRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }
