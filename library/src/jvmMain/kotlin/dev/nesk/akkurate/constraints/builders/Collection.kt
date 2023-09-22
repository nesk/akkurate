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

// TODO: add contains/doesNotContain

@JvmName("collectionIsEmpty")
public fun <T> Validatable<Collection<T>?>.isEmpty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

@JvmName("collectionIsNotEmpty")
public fun <T> Validatable<Collection<T>?>.isNotEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

@JvmName("collectionHasSizeEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

@JvmName("collectionHasSizeNotEqualTo")
public fun <T> Validatable<Collection<T>?>.hasSizeNotEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size != size } otherwise { "The number of items must be different from $size" }

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
