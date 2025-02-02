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
 * The validatable [Iterable] must contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Iterable<Char>> { isContaining('b') }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(emptyList()) // Failure (message: Must contain "b")
 * ```
 */
@JvmName("iterableIsContaining")
public fun <T, MetadataType> GenericValidatable<Iterable<T>?, MetadataType>.isContaining(element: T): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.contains(element) } otherwise { "Must contain \"$element\"" }

/**
 * The validatable [Iterable] must not contain [element] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Iterable<Char>> { isNotContaining('b') }
 * validate(emptyList()) // Success
 * validate(listOf('a', 'b', 'c')) // Failure (message: Must not contain "b")
 * ```
 */
@JvmName("iterableIsNotContaining")
public fun <T, MetadataType> GenericValidatable<Iterable<T>?, MetadataType>.isNotContaining(element: T): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.contains(element) } otherwise { "Must not contain \"$element\"" }

/**
 * The validatable [Iterable] must contain unique elements when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Iterable<Char>> { hasNoDuplicates() }
 * validate(listOf('a', 'b', 'c')) // Success
 * validate(listOf('a', 'b', 'c', 'a')) // Failure (message: Must contain unique elements)
 * ```
 */
public fun <T, MetadataType> GenericValidatable<Iterable<T>?, MetadataType>.hasNoDuplicates(): GenericConstraint<MetadataType> =
    constrainIfNotNull { array -> array.toSet().size == array.count() } otherwise { "Must contain unique elements" }
