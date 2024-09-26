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
import kotlin.time.Duration

/**
 * The validatable [Duration] must be negative when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isNegative() }
 * validate((-1).seconds) // Success
 * validate(0.seconds) // Failure (message: Must be negative)
 * validate(1.seconds) // Failure (message: Must be negative)
 * ```
 */
public fun Validatable<Duration?>.isNegative(): Constraint =
    constrainIfNotNull { it.isNegative() && it != Duration.ZERO } otherwise { "Must be negative" }

/**
 * The validatable [Duration] must be negative or zero when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isNegativeOrZero() }
 * validate((-1).seconds) // Success
 * validate(0.seconds) // Success
 * validate(1.seconds) // Failure (message: Must be negative or equal to zero)
 * ```
 */
public fun Validatable<Duration?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it.isNegative() || it == Duration.ZERO } otherwise { "Must be negative or equal to zero" }

/**
 * The validatable [Duration] must be positive when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isPositive() }
 * validate(1.seconds) // Success
 * validate(0.seconds) // Failure (message: Must be positive)
 * validate((-1).seconds) // Failure (message: Must be positive)
 * ```
 */
public fun Validatable<Duration?>.isPositive(): Constraint =
    constrainIfNotNull { !it.isNegative() && it != Duration.ZERO } otherwise { "Must be positive" }

/**
 * The validatable [Duration] must be positive or zero when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isPositiveOrZero() }
 * validate(1.seconds) // Success
 * validate(0.seconds) // Success
 * validate((-1).seconds) // Failure (message: Must be positive or equal to zero)
 * ```
 */
public fun Validatable<Duration?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { !it.isNegative() || it == Duration.ZERO } otherwise { "Must be positive or equal to zero" }

/**
 * The validatable [Duration] must be lower than [value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isLowerThan(Duration.ZERO) }
 * validate((-1).seconds) // Success
 * validate(0.seconds) // Failure (message: Must be lower than 0s)
 * validate(1.seconds) // Failure (message: Must be lower than 0s)
 * ```
 */
public fun Validatable<Duration?>.isLowerThan(value: Duration): Constraint =
    constrainIfNotNull { it < value } otherwise { "Must be lower than $value" }

/**
 * The validatable [Duration] must be lower than or equal to [value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isLowerThanOrEqualTo(Duration.ZERO) }
 * validate((-1).seconds) // Success
 * validate(0.seconds) // Success
 * validate(1.seconds) // Failure (message: Must be lower than or equal to 0s)
 * ```
 */
public fun Validatable<Duration?>.isLowerThanOrEqualTo(value: Duration): Constraint =
    constrainIfNotNull { it <= value } otherwise { "Must be lower than or equal to $value" }

/**
 * The validatable [Duration] must be greater than [value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isGreaterThan(Duration.ZERO) }
 * validate(1.seconds) // Success
 * validate(0.seconds) // Failure (message: Must be greater than 0s)
 * validate((-1).seconds) // Failure (message: Must be greater than 0s)
 * ```
 */
public fun Validatable<Duration?>.isGreaterThan(value: Duration): Constraint =
    constrainIfNotNull { it > value } otherwise { "Must be greater than $value" }

/**
 * The validatable [Duration] must be greater than or equal to [value] when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> { isGreaterThanOrEqualTo(Duration.ZERO) }
 * validate(1.seconds) // Success
 * validate(0.seconds) // Success
 * validate((-1).seconds) // Failure (message: Must be greater than or equal to 0s)
 * ```
 */
public fun Validatable<Duration?>.isGreaterThanOrEqualTo(value: Duration): Constraint =
    constrainIfNotNull { it >= value } otherwise { "Must be greater than or equal to $value" }

//region isBetween
/**
 * The validatable [Duration] must be in [range] (lower and upper bounds included) when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> {
 *     val max = 10.seconds
 *     isBetween(Duration.ZERO..max)
 * }
 * validate(5.seconds) // Success
 * validate(10.seconds) // Success
 * validate(15.seconds) // Failure (message: Must be between 0s and 10s (inclusive))
 * ```
 */
public fun Validatable<Duration?>.isBetween(range: ClosedRange<Duration>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

/**
 * The validatable [Duration] must be in [range] (upper bound excluded) when this constraint is applied.
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Duration> {
 *     val max = 10.seconds
 *     isBetween(Duration.ZERO..<max)
 * }
 * validate(5.seconds) // Success
 * validate(10.seconds) // Failure (message: Must be between 0s and 10s (exclusive))
 * validate(15.seconds) // Failure (message: Must be between 0s and 10s (exclusive))
 * ```
 */
public fun Validatable<Duration?>.isBetween(range: OpenEndRange<Duration>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion
