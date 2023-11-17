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
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

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
public fun <T> Validatable<T?>.isNull(): Constraint = constrain { it == null } otherwise { "Must be null" }

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
public fun <T> Validatable<T?>.isNotNull(): Constraint = constrain { it != null } otherwise { "Must not be null" }

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
public fun <T> Validatable<T?>.isEqualTo(other: T?): Constraint = constrain { it == other } otherwise { "Must be equal to \"$other\"" }

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
public fun <T> Validatable<T?>.isEqualTo(other: Validatable<T>): Constraint = constrain { it == other.unwrap() } otherwise { "Must be equal to \"${other.unwrap()}\"" }

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
public fun <T> Validatable<T?>.isNotEqualTo(other: T?): Constraint = constrain { it != other } otherwise { "Must be different from \"$other\"" }

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
public fun <T> Validatable<T?>.isNotEqualTo(other: Validatable<T>): Constraint = constrain { it != other.unwrap() } otherwise { "Must be different from \"${other.unwrap()}\"" }

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
public fun <T> Validatable<T?>.isIdenticalTo(other: T?): Constraint = constrain { it === other } otherwise { "Must be identical to \"$other\"" }

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
public fun <T> Validatable<T?>.isNotIdenticalTo(other: T?): Constraint = constrain { it !== other } otherwise { "Must not be identical to \"$other\"" }
