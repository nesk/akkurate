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

@JvmName("mapHasSizeEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size == size } otherwise { "The number of items must be equal to $size" }

@JvmName("mapHasSizeNotEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeNotEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size != size } otherwise { "The number of items must be different from $size" }

@JvmName("mapHasSizeLowerThan")
public fun <T> Validatable<Map<*, T>?>.hasSizeLowerThan(size: Int): Constraint =
    constrainIfNotNull { it.size < size } otherwise { "The number of items must be lower than $size" }

@JvmName("mapHasSizeLowerThanOrEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeLowerThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size <= size } otherwise { "The number of items must be lower than or equal to $size" }

@JvmName("mapHasSizeGreaterThan")
public fun <T> Validatable<Map<*, T>?>.hasSizeGreaterThan(size: Int): Constraint =
    constrainIfNotNull { it.size > size } otherwise { "The number of items must be greater than $size" }

@JvmName("mapHasSizeGreaterThanOrEqualTo")
public fun <T> Validatable<Map<*, T>?>.hasSizeGreaterThanOrEqualTo(size: Int): Constraint =
    constrainIfNotNull { it.size >= size } otherwise { "The number of items must be greater than or equal to $size" }

@JvmName("mapHasSizeBetween")
public fun <T> Validatable<Map<*, T>?>.hasSizeBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.size in range } otherwise { "The number of items must be between ${range.first} and ${range.last}" }
