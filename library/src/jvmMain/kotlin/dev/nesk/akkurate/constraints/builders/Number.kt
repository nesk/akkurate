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

//region isNotNaN
@JvmName("floatIsNotNaN")
public fun Validatable<Float?>.isNotNaN(): Constraint =
    constrainIfNotNull { !it.isNaN() } otherwise { "Must be a valid number" }

@JvmName("doubleIsNotNaN")
public fun Validatable<Double?>.isNotNaN(): Constraint =
    constrainIfNotNull { !it.isNaN() } otherwise { "Must be a valid number" }
//endregion

//region isFinite
@JvmName("floatIsFinite")
public fun Validatable<Float?>.isFinite(): Constraint =
    constrainIfNotNull { it.isFinite() } otherwise { "Must be finite" }

@JvmName("doubleIsFinite")
public fun Validatable<Double?>.isFinite(): Constraint =
    constrainIfNotNull { it.isFinite() } otherwise { "Must be finite" }
//endregion

//region isInfinite
@JvmName("floatIsInfinite")
public fun Validatable<Float?>.isInfinite(): Constraint =
    constrainIfNotNull { it.isInfinite() } otherwise { "Must be infinite" }

@JvmName("doubleIsInfinite")
public fun Validatable<Double?>.isInfinite(): Constraint =
    constrainIfNotNull { it.isInfinite() } otherwise { "Must be infinite" }
//endregion

//region isNegative
private const val negativeMessage = "Must be negative"

@JvmName("shortIsNegative")
public fun Validatable<Short?>.isNegative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("intIsNegative")
public fun Validatable<Int?>.isNegative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("longIsNegative")
public fun Validatable<Long?>.isNegative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("floatIsNegative")
public fun Validatable<Float?>.isNegative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("doubleIsNegative")
public fun Validatable<Double?>.isNegative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }
//endregion

//region isNegativeOrZero
private const val negativeOrZeroMessage = "Must be negative or equal to zero"

@JvmName("shortIsNegativeOrZero")
public fun Validatable<Short?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("intIsNegativeOrZero")
public fun Validatable<Int?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("longIsNegativeOrZero")
public fun Validatable<Long?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("floatIsNegativeOrZero")
public fun Validatable<Float?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("doubleIsNegativeOrZero")
public fun Validatable<Double?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }
//endregion

//region isPositive
private const val positiveMessage = "Must be positive"

@JvmName("shortIsPositive")
public fun Validatable<Short?>.isPositive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("intIsPositive")
public fun Validatable<Int?>.isPositive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("longIsPositive")
public fun Validatable<Long?>.isPositive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("floatIsPositive")
public fun Validatable<Float?>.isPositive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("doubleIsPositive")
public fun Validatable<Double?>.isPositive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }
//endregion

//region isPositiveOrZero
private const val positiveOrZeroMessage = "Must be positive or equal to zero"

@JvmName("shortIsPositiveOrZero")
public fun Validatable<Short?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("intIsPositiveOrZero")
public fun Validatable<Int?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("longIsPositiveOrZero")
public fun Validatable<Long?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("floatIsPositiveOrZero")
public fun Validatable<Float?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("doubleIsPositiveOrZero")
public fun Validatable<Double?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }
//endregion

//region isLowerThan
private infix fun Constraint.otherwiseLowerThan(value: Number) = otherwise { "Must be lower than $value" }

public fun Validatable<Short?>.isLowerThan(value: Short): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Int?>.isLowerThan(value: Int): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Long?>.isLowerThan(value: Long): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Float?>.isLowerThan(value: Float): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Double?>.isLowerThan(value: Double): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value
//endregion

//region isLowerThanOrEqualTo
private infix fun Constraint.otherwiseLowerThanOrEqualTo(value: Number) = otherwise { "Must be lower than or equal to $value" }

public fun Validatable<Short?>.isLowerThanOrEqualTo(value: Short): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Int?>.isLowerThanOrEqualTo(value: Int): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Long?>.isLowerThanOrEqualTo(value: Long): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Float?>.isLowerThanOrEqualTo(value: Float): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Double?>.isLowerThanOrEqualTo(value: Double): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value
//endregion

//region isGreaterThan
private infix fun Constraint.otherwiseGreaterThan(value: Number) = otherwise { "Must be greater than $value" }

public fun Validatable<Short?>.isGreaterThan(value: Short): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Int?>.isGreaterThan(value: Int): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Long?>.isGreaterThan(value: Long): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Float?>.isGreaterThan(value: Float): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Double?>.isGreaterThan(value: Double): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value
//endregion

//region isGreaterThanOrEqualTo
private infix fun Constraint.otherwiseGreaterThanOrEqualTo(value: Number) = otherwise { "Must be greater than or equal to $value" }

public fun Validatable<Short?>.isGreaterThanOrEqualTo(value: Short): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Int?>.isGreaterThanOrEqualTo(value: Int): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Long?>.isGreaterThanOrEqualTo(value: Long): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Float?>.isGreaterThanOrEqualTo(value: Float): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Double?>.isGreaterThanOrEqualTo(value: Double): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value
//endregion

//region isBetween
@JvmName("shortIsBetween")
public fun Validatable<Short?>.isBetween(range: IntRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun Validatable<Int?>.isBetween(range: IntRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun Validatable<Long?>.isBetween(range: LongRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

@JvmName("closedFloatIsBetween")
public fun Validatable<Float?>.isBetween(range: ClosedFloatingPointRange<Float>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@JvmName("closedDoubleIsBetween")
public fun Validatable<Double?>.isBetween(range: ClosedFloatingPointRange<Double>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openFloatIsBetween")
public fun Validatable<Float?>.isBetween(range: OpenEndRange<Float>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openDoubleIsBetween")
public fun Validatable<Double?>.isBetween(range: OpenEndRange<Double>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion

//region hasIntegralCountEqualTo / hasFractionalCountEqualTo
// TODO: support ranges for digit count?

private fun requireCountGreaterThanZero(count: Int) {
    require(count > 0) { "'count' cannot be lower than 1" }
}

private fun Validatable<Number?>.constrainDigitCount(
    runDecimalChecks: Boolean,
    block: (integralCount: Int, fractionalCount: Int?) -> Boolean,
): Constraint {
    return constrainIfNotNull {
        if (runDecimalChecks) {
            val floatValue = it.toFloat()
            if (floatValue.isNaN() || floatValue.isInfinite()) return@constrainIfNotNull false
        }

        val parts = it.toString().split('.')
        block(parts.first().trimStart('-').length, parts.getOrNull(1)?.length)
    }
}

private fun Validatable<Number?>.constrainIntegralPart(count: Int, runDecimalChecks: Boolean): Constraint {
    requireCountGreaterThanZero(count)
    return constrainDigitCount(runDecimalChecks) { integralCount, _ ->
        integralCount == count
    }
}

private fun Validatable<Number?>.constrainDecimalPart(count: Int): Constraint {
    requireCountGreaterThanZero(count)
    return constrainDigitCount(runDecimalChecks = true) { _, fractionalCount ->
        if (fractionalCount == null) false else fractionalCount == count
    }
}

private infix fun Constraint.otherwiseIntegralCountEqualTo(count: Int) = otherwise { "Must contain $count integral digits" }
private infix fun Constraint.otherwiseFractionalCountEqualTo(count: Int) = otherwise { "Must contain $count fractional digits" }

@JvmName("shortHasIntegralCountEqualTo")
public fun Validatable<Short?>.hasIntegralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("intHasIntegralCountEqualTo")
public fun Validatable<Int?>.hasIntegralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("longHasIntegralCountEqualTo")
public fun Validatable<Long?>.hasIntegralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("floatHasIntegralCountEqualTo")
public fun Validatable<Float?>.hasIntegralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("doubleHasIntegralCountEqualTo")
public fun Validatable<Double?>.hasIntegralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("floatHasFractionalCountEqualTo")
public fun Validatable<Float?>.hasFractionalCountEqualTo(count: Int): Constraint =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count

@JvmName("doubleHasFractionalCountEqualTo")
public fun Validatable<Double?>.hasFractionalCountEqualTo(count: Int): Constraint =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count
//endregion
