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
 * The validatable [Map] must be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isNotEmpty() }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(emptyMap()) // Failure (message: Must not be empty)
 * ```
 */
@JvmName("mapIsEmpty")
public fun <T> Validatable<Map<*, T>?>.isEmpty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

/**
 * The validatable [Map] must not be empty when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isEmpty() }
 * validate(emptyMap()) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: Must be empty)
 * ```
 */
@JvmName("mapIsNotEmpty")
public fun <T> Validatable<Map<*, T>?>.isNotEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

/**
 * The validatable [Map] must have a size equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeEqualTo(3) }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(mapOf('a' to 1, 'b' to 2)) // Failure (message: The number of items must be equal to 3)
 * ```
 */
@JvmName("mapHasSizeEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

/**
 * The validatable [Map] must have a size different from [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeNotEqualTo(3) }
 * validate(mapOf('a' to 1, 'b' to 2)) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: The number of items must be different from 3)
 * ```
 */
@JvmName("mapHasSizeNotEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeNotEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size != size } otherwise { "The number of items must be different from $size" }

/**
 * The validatable [Map] must have a size lower than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeLowerThan(3) }
 * validate(mapOf('a' to 1, 'b' to 2)) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: The number of items must be lower than 3)
 * ```
 */
@JvmName("mapHasSizeLowerThan")
public fun <T> Validatable<Map<*, T>?>.hasSizeLowerThan(size: Int): Constraint =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

/**
 * The validatable [Map] must have a size lower than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeLowerThanOrEqualTo(2) }
 * validate(mapOf('a' to 1)) // Success
 * validate(mapOf('a' to 1, 'b' to 2)) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: The number of items must be lower than or equal to 2)
 * ```
 */
@JvmName("mapHasSizeLowerThanOrEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeLowerThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

/**
 * The validatable [Map] must have a size greater than [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeGreaterThan(2) }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(mapOf('a' to 1, 'b' to 2)) // Failure (message: The number of items must be greater than 2)
 * ```
 */
@JvmName("mapHasSizeGreaterThan")
public fun <T> Validatable<Map<*, T>?>.hasSizeGreaterThan(size: Int): Constraint =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

/**
 * The validatable [Map] must have a size greater than or equal to [size] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeGreaterThanOrEqualTo(2) }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(mapOf('a' to 1, 'b' to 2)) // Success
 * validate(mapOf('a' to 1)) // Failure (message: The number of items must be greater than or equal to 2)
 * ```
 */
@JvmName("mapHasSizeGreaterThanOrEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeGreaterThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

/**
 * The validatable [Map] must have a size [within the provided range][range] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { hasSizeBetween(1..2) }
 * validate(emptyMap()) // Failure (message: The number of items must be between 1 and 2)
 * validate(mapOf('a' to 1)) // Success
 * validate(mapOf('a' to 1, 'b' to 2)) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: The number of items must be between 1 and 2)
 * ```
 */
@JvmName("mapHasSizeBetween")
public fun <T> Validatable<Map<*, T>?>.hasSizeBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }

/**
 * The validatable [Map] must contain [the provided key][key] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isContainingKey('b') }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(emptyMap()) // Failure (message: Must contain key "b")
 * ```
 */
public fun <K> Validatable<Map<out K, *>?>.isContainingKey(key: K): Constraint =
    constrainIfNotNull { it.containsKey(key) } otherwise { "Must contain key \"$key\"" }

/**
 * The validatable [Map] must not contain [the provided key][key] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isNotContainingKey('b') }
 * validate(emptyMap()) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: Must not contain key "b")
 * ```
 */
public fun <K> Validatable<Map<out K, *>?>.isNotContainingKey(key: K): Constraint =
    constrainIfNotNull { !it.containsKey(key) } otherwise { "Must not contain key \"$key\"" }

/**
 * The validatable [Map] must contain [the provided value][value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isContainingValue(2) }
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Success
 * validate(emptyMap()) // Failure (message: Must contain value "2")
 * ```
 */
public fun <V> Validatable<Map<*, V>?>.isContainingValue(value: V): Constraint =
    constrainIfNotNull { it.containsValue(value) } otherwise { "Must contain value \"$value\"" }

/**
 * The validatable [Map] must not contain [the provided value][value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Map<Char, Int>> { isNotContainingValue(2) }
 * validate(emptyMap()) // Success
 * validate(mapOf('a' to 1, 'b' to 2, 'c' to 3)) // Failure (message: Must not contain value "2")
 * ```
 */
public fun <V> Validatable<Map<*, V>?>.isNotContainingValue(value: V): Constraint =
    constrainIfNotNull { !it.containsValue(value) } otherwise { "Must not contain value \"$value\"" }
