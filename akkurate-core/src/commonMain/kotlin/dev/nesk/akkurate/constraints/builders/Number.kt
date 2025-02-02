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

//region isNotNaN
@JvmName("floatIsNotNaN")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isNotNaN(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNaN() } otherwise { "Must be a valid number" }

@JvmName("doubleIsNotNaN")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isNotNaN(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNaN() } otherwise { "Must be a valid number" }
//endregion

//region isFinite
@JvmName("floatIsFinite")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isFinite(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isFinite() } otherwise { "Must be finite" }

@JvmName("doubleIsFinite")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isFinite(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isFinite() } otherwise { "Must be finite" }
//endregion

//region isInfinite
@JvmName("floatIsInfinite")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isInfinite(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isInfinite() } otherwise { "Must be infinite" }

@JvmName("doubleIsInfinite")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isInfinite(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isInfinite() } otherwise { "Must be infinite" }
//endregion

//region isNegative
private const val negativeMessage = "Must be negative"

@JvmName("shortIsNegative")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("intIsNegative")
public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("longIsNegative")
public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("floatIsNegative")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("doubleIsNegative")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }
//endregion

//region isNegativeOrZero
private const val negativeOrZeroMessage = "Must be negative or equal to zero"

@JvmName("shortIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("intIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("longIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("floatIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("doubleIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }
//endregion

//region isPositive
private const val positiveMessage = "Must be positive"

@JvmName("shortIsPositive")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("intIsPositive")
public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("longIsPositive")
public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("floatIsPositive")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("doubleIsPositive")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }
//endregion

//region isPositiveOrZero
private const val positiveOrZeroMessage = "Must be positive or equal to zero"

@JvmName("shortIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("intIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("longIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("floatIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("doubleIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }
//endregion

//region isLowerThan
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseLowerThan(value: Number) = otherwise { "Must be lower than $value" }

public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isLowerThan(value: Short): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isLowerThan(value: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isLowerThan(value: Long): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isLowerThan(value: Float): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isLowerThan(value: Double): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwiseLowerThan value
//endregion

//region isLowerThanOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseLowerThanOrEqualTo(value: Number) = otherwise { "Must be lower than or equal to $value" }

public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isLowerThanOrEqualTo(value: Short): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isLowerThanOrEqualTo(value: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isLowerThanOrEqualTo(value: Long): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isLowerThanOrEqualTo(value: Float): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isLowerThanOrEqualTo(value: Double): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value
//endregion

//region isGreaterThan
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseGreaterThan(value: Number) = otherwise { "Must be greater than $value" }

public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isGreaterThan(value: Short): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isGreaterThan(value: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isGreaterThan(value: Long): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isGreaterThan(value: Float): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isGreaterThan(value: Double): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwiseGreaterThan value
//endregion

//region isGreaterThanOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseGreaterThanOrEqualTo(value: Number) = otherwise { "Must be greater than or equal to $value" }

public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isGreaterThanOrEqualTo(value: Short): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isGreaterThanOrEqualTo(value: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isGreaterThanOrEqualTo(value: Long): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isGreaterThanOrEqualTo(value: Float): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isGreaterThanOrEqualTo(value: Double): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value
//endregion

//region isBetween
@JvmName("shortIsBetween")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.isBetween(range: IntRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun <MetadataType> GenericValidatable<Int?, MetadataType>.isBetween(range: IntRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun <MetadataType> GenericValidatable<Long?, MetadataType>.isBetween(range: LongRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

@JvmName("closedFloatIsBetween")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isBetween(range: ClosedFloatingPointRange<Float>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@JvmName("closedDoubleIsBetween")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isBetween(range: ClosedFloatingPointRange<Double>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openFloatIsBetween")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.isBetween(range: OpenEndRange<Float>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openDoubleIsBetween")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.isBetween(range: OpenEndRange<Double>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion

//region hasIntegralCountEqualTo / hasFractionalCountEqualTo
// TODO: support ranges for digit count?

private fun requireCountGreaterThanZero(count: Int) {
    require(count > 0) { "'count' cannot be lower than 1" }
}

private fun <MetadataType> GenericValidatable<Number?, MetadataType>.constrainDigitCount(
    runDecimalChecks: Boolean,
    block: (integralCount: Int, fractionalCount: Int?) -> Boolean,
): GenericConstraint<MetadataType> {
    return constrainIfNotNull {
        if (runDecimalChecks) {
            val floatValue = it.toFloat()
            if (floatValue.isNaN() || floatValue.isInfinite()) return@constrainIfNotNull false
        }

        val parts = it.toString().split('.')
        block(parts.first().trimStart('-').length, parts.getOrNull(1)?.length)
    }
}

private fun <MetadataType> GenericValidatable<Number?, MetadataType>.constrainIntegralPart(count: Int, runDecimalChecks: Boolean): GenericConstraint<MetadataType> {
    requireCountGreaterThanZero(count)
    return constrainDigitCount(runDecimalChecks) { integralCount, _ ->
        integralCount == count
    }
}

internal fun <MetadataType> GenericValidatable<Number?, MetadataType>.constrainDecimalPart(count: Int): GenericConstraint<MetadataType> {
    requireCountGreaterThanZero(count)
    return constrainDigitCount(runDecimalChecks = true) { _, fractionalCount ->
        if (fractionalCount == null) false else fractionalCount == count
    }
}

private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseIntegralCountEqualTo(count: Int) = otherwise { "Must contain $count integral digits" }
internal infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseFractionalCountEqualTo(count: Int) = otherwise { "Must contain $count fractional digits" }

@JvmName("shortHasIntegralCountEqualTo")
public fun <MetadataType> GenericValidatable<Short?, MetadataType>.hasIntegralCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("intHasIntegralCountEqualTo")
public fun <MetadataType> GenericValidatable<Int?, MetadataType>.hasIntegralCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("longHasIntegralCountEqualTo")
public fun <MetadataType> GenericValidatable<Long?, MetadataType>.hasIntegralCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("floatHasIntegralCountEqualTo")
public fun <MetadataType> GenericValidatable<Float?, MetadataType>.hasIntegralCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("doubleHasIntegralCountEqualTo")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.hasIntegralCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("doubleHasFractionalCountEqualTo")
public fun <MetadataType> GenericValidatable<Double?, MetadataType>.hasFractionalCountEqualTo(count: Int): GenericConstraint<MetadataType> =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count
//endregion
