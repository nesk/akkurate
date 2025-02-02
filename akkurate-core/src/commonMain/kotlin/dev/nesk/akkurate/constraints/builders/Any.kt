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
import dev.nesk.akkurate.constraints.GenericConstraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.DefaultMetadataType
import dev.nesk.akkurate.validatables.GenericValidatable
import dev.nesk.akkurate.validatables.Validatable
import kotlin.jvm.JvmName

/**
 * The validatable value must be null when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Int?> { isNull() }
 * validate(null) // Success
 * validate(1234) // Failure (message: Must be null)
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isNull(): GenericConstraint<MetadataType> = constrain { it == null } otherwise { "Must be null" }

/**
 * The validatable value must not be null when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Int?> { isNotNull() }
 * validate(1234) // Success
 * validate(null) // Failure (message: Must not be null)
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isNotNull(): GenericConstraint<MetadataType> = constrain { it != null } otherwise { "Must not be null" }

/**
 * The validatable value must be equal to [other] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<String> { isEqualTo("foo") }
 * validate("foo") // Success
 * validate("bar") // Failure (message: Must be equal to "foo")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isEqualTo(other: T?): GenericConstraint<MetadataType> = constrain { it == other } otherwise { "Must be equal to \"$other\"" }

/**
 * The validatable value must be equal to [other] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Pair<String, String>> { first.isEqualTo(second) }
 * validate("foo" to "foo") // Success
 * validate("foo" to "bar") // Failure (message: Must be equal to "bar")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isEqualTo(other: Validatable<T>): GenericConstraint<MetadataType> = constrain { it == other.unwrap() } otherwise { "Must be equal to \"${other.unwrap()}\"" }

/**
 * The validatable value must be different from [other] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<String> { isNotEqualTo("foo") }
 * validate("bar") // Success
 * validate("foo") // Failure (message: Must be different from "foo")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isNotEqualTo(other: T?): GenericConstraint<MetadataType> = constrain { it != other } otherwise { "Must be different from \"$other\"" }

/**
 * The validatable value must be different from [other] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Pair<String, String>> { first.isNotEqualTo(second) }
 * validate("foo" to "bar") // Success
 * validate("foo" to "foo") // Failure (message: Must be different from "foo")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isNotEqualTo(other: Validatable<T>): GenericConstraint<MetadataType> = constrain { it != other.unwrap() } otherwise { "Must be different from \"${other.unwrap()}\"" }

/**
 * The validatable value must be identical to [other] when this constraint is applied.
 *
 * The values are considered identical [if they are referentially equal.](https://kotlinlang.org/docs/equality.html#referential-equality)
 *
 * Code example:
 *
 * ```
 * data object Foo
 * data object Bar
 *
 * val validate = Validator<Any> { isIdenticalTo(Foo) }
 * validate(Foo) // Success
 * validate(Bar) // Failure (message: Must be identical to "Foo")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isIdenticalTo(other: T?): GenericConstraint<MetadataType> = constrain { it === other } otherwise { "Must be identical to \"$other\"" }

/**
 * The validatable value must not be identical to [other] when this constraint is applied.
 *
 * The values are considered not identical [if they are not referentially equal.](https://kotlinlang.org/docs/equality.html#referential-equality)
 *
 * Code example:
 *
 * ```
 * data object Foo
 * data object Bar
 *
 * val validate = Validator<Any> { isNotIdenticalTo(Foo) }
 * validate(Bar) // Success
 * validate(Foo) // Failure (message: Must not be identical to "Foo")
 * ```
 */
public fun <T, MetadataType> GenericValidatable<T?, MetadataType>.isNotIdenticalTo(other: T?): GenericConstraint<MetadataType> = constrain { it !== other } otherwise { "Must not be identical to \"$other\"" }

/**
 * The validatable value must be an instance of type parameter R when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Any> { isInstanceOf<String>() }
 * validate("foo") // Success
 * validate(123) // Failure (message: Must be an instance of "kotlin.String")
 * ```
 */
public inline fun <reified T> Validatable<*>.isInstanceOf(): Constraint =
    this.isInstanceOf<T, DefaultMetadataType>()

@JvmName("genericIsInstanceOf")
public inline fun <reified T, MetadataType> GenericValidatable<*, MetadataType>.isInstanceOf(): GenericConstraint<MetadataType> =
    constrain { it is T } otherwise { "Must be an instance of \"${T::class.simpleName}\"" }

/**
 * The validatable value must not be an instance of type parameter R when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Any> { isNotInstanceOf<String>() }
 * validate(123) // Success
 * validate("foo") // Failure (message: Must not be an instance of "kotlin.String")
 * ```
 */
public inline fun <reified T> Validatable<*>.isNotInstanceOf(): Constraint =
    this.isNotInstanceOf<T, DefaultMetadataType>()

@JvmName("genericIsNotInstanceOf")
public inline fun <reified T, MetadataType> GenericValidatable<*, MetadataType>.isNotInstanceOf(): GenericConstraint<MetadataType> =
    constrain { it !is T } otherwise { "Must not be an instance of \"${T::class.simpleName}\"" }
