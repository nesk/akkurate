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
 * The validatable [Array] must be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { isNotEmpty() }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(emptyArray()) // Failure (message: Must not be empty)
 * ```
 */
@JvmName("arrayIsEmpty")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.isEmpty(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

/**
 * The validatable [Array] must not be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { isEmpty() }
 * validate(emptyArray()) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: Must be empty)
 * ```
 */
@JvmName("arrayIsNotEmpty")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.isNotEmpty(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

/**
 * The validatable [Array] must have a size equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeEqualTo(3) }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(arrayOf('a', 'b')) // Failure (message: The number of items must be equal to 3)
 * ```
 */
@JvmName("arrayHasSizeEqualTo")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeEqualTo(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

/**
 * The validatable [Array] must have a size different from [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeNotEqualTo(3) }
 * validate(arrayOf('a', 'b')) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: The number of items must be different from 3)
 * ```
 */
@JvmName("arrayHasSizeNotEqualTo")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeNotEqualTo(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size != size } otherwise { "The number of items must be different from $size" }

/**
 * The validatable [Array] must have a size lower than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeLowerThan(3) }
 * validate(arrayOf('a', 'b')) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: The number of items must be lower than 3)
 * ```
 */
@JvmName("arrayHasSizeLowerThan")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeLowerThan(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

/**
 * The validatable [Array] must have a size lower than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeLowerThanOrEqualTo(2) }
 * validate(arrayOf('a')) // Success
 * validate(arrayOf('a', 'b')) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: The number of items must be lower than or equal to 2)
 * ```
 */
@JvmName("arrayHasSizeLowerThanOrEqualTo")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeLowerThanOrEqualTo(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

/**
 * The validatable [Array] must have a size greater than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeGreaterThan(2) }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(arrayOf('a', 'b')) // Failure (message: The number of items must be greater than 2)
 * ```
 */
@JvmName("arrayHasSizeGreaterThan")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeGreaterThan(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

/**
 * The validatable [Array] must have a size greater than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeGreaterThanOrEqualTo(2) }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(arrayOf('a', 'b')) // Success
 * validate(arrayOf('a')) // Failure (message: The number of items must be greater than or equal to 2)
 * ```
 */
@JvmName("arrayHasSizeGreaterThanOrEqualTo")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeGreaterThanOrEqualTo(size: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

/**
 * The validatable [Array] must have a size [within the provided range][range] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasSizeBetween(1..2) }
 * validate(emptyArray()) // Failure (message: The number of items must be between 1 and 2)
 * validate(arrayOf('a')) // Success
 * validate(arrayOf('a', 'b')) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: The number of items must be between 1 and 2)
 * ```
 */
@JvmName("arrayHasSizeBetween")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasSizeBetween(range: IntRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }

/**
 * The validatable [Array] must contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { isContaining('b') }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(emptyArray()) // Failure (message: Must contain "b")
 * ```
 */
@JvmName("arrayIsContaining")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.isContaining(element: T): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.contains(element) } otherwise { "Must contain \"$element\"" }

/**
 * The validatable [Array] must not contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { isNotContaining('b') }
 * validate(emptyArray()) // Success
 * validate(arrayOf('a', 'b', 'c')) // Failure (message: Must not contain "b")
 * ```
 */
@JvmName("arrayIsNotContaining")
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.isNotContaining(element: T): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.contains(element) } otherwise { "Must not contain \"$element\"" }

/**
 * The validatable [Array] must contain unique elements when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Array<Char>> { hasNoDuplicates() }
 * validate(arrayOf('a', 'b', 'c')) // Success
 * validate(arrayOf('a', 'b', 'c', 'a')) // Failure (message: Must contain unique elements)
 * ```
 */
public fun <T, MetadataType> GenericValidatable<Array<out T>?, MetadataType>.hasNoDuplicates(): GenericConstraint<MetadataType> =
    constrainIfNotNull { array -> array.toSet().size == array.size } otherwise { "Must contain unique elements" }
