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

//region negative
private const val negativeMessage = "Must be negative"

@JvmName("shortNegative")
public fun Validatable<Short?>.negative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("intNegative")
public fun Validatable<Int?>.negative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("longNegative")
public fun Validatable<Long?>.negative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("floatNegative")
public fun Validatable<Float?>.negative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }

@JvmName("doubleNegative")
public fun Validatable<Double?>.negative(): Constraint =
    constrainIfNotNull { it < 0 } otherwise { negativeMessage }
//endregion

//region negativeOrZero
private const val negativeOrZeroMessage = "Must be negative or equal to zero"

@JvmName("shortNegativeOrZero")
public fun Validatable<Short?>.negativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("intNegativeOrZero")
public fun Validatable<Int?>.negativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("longNegativeOrZero")
public fun Validatable<Long?>.negativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("floatNegativeOrZero")
public fun Validatable<Float?>.negativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }

@JvmName("doubleNegativeOrZero")
public fun Validatable<Double?>.negativeOrZero(): Constraint =
    constrainIfNotNull { it <= 0 } otherwise { negativeOrZeroMessage }
//endregion

//region positive
private const val positiveMessage = "Must be positive"

@JvmName("shortPositive")
public fun Validatable<Short?>.positive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("intPositive")
public fun Validatable<Int?>.positive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("longPositive")
public fun Validatable<Long?>.positive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("floatPositive")
public fun Validatable<Float?>.positive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }

@JvmName("doublePositive")
public fun Validatable<Double?>.positive(): Constraint =
    constrainIfNotNull { it > 0 } otherwise { positiveMessage }
//endregion

//region positiveOrZero
private const val positiveOrZeroMessage = "Must be positive or equal to zero"

@JvmName("shortPositiveOrZero")
public fun Validatable<Short?>.positiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("intPositiveOrZero")
public fun Validatable<Int?>.positiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("longPositiveOrZero")
public fun Validatable<Long?>.positiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("floatPositiveOrZero")
public fun Validatable<Float?>.positiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }

@JvmName("doublePositiveOrZero")
public fun Validatable<Double?>.positiveOrZero(): Constraint =
    constrainIfNotNull { it >= 0 } otherwise { positiveOrZeroMessage }
//endregion

//region lowerThan
private infix fun Constraint.otherwiseLowerThan(value: Number) = otherwise { "Must be lower than $value" }

public fun Validatable<Short?>.lowerThan(value: Short): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Int?>.lowerThan(value: Int): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Long?>.lowerThan(value: Long): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Float?>.lowerThan(value: Float): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value

public fun Validatable<Double?>.lowerThan(value: Double): Constraint =
    constrainIfNotNull { it < value } otherwiseLowerThan value
//endregion

//region lowerThanOrEqualTo
private infix fun Constraint.otherwiseLowerThanOrEqualTo(value: Number) = otherwise { "Must be lower than or equal to $value" }

public fun Validatable<Short?>.lowerThanOrEqualTo(value: Short): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Int?>.lowerThanOrEqualTo(value: Int): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Long?>.lowerThanOrEqualTo(value: Long): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Float?>.lowerThanOrEqualTo(value: Float): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value

public fun Validatable<Double?>.lowerThanOrEqualTo(value: Double): Constraint =
    constrainIfNotNull { it <= value } otherwiseLowerThanOrEqualTo value
//endregion

//region greaterThan
private infix fun Constraint.otherwiseGreaterThan(value: Number) = otherwise { "Must be greater than $value" }

public fun Validatable<Short?>.greaterThan(value: Short): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Int?>.greaterThan(value: Int): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Long?>.greaterThan(value: Long): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Float?>.greaterThan(value: Float): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value

public fun Validatable<Double?>.greaterThan(value: Double): Constraint =
    constrainIfNotNull { it > value } otherwiseGreaterThan value
//endregion

//region greaterThanOrEqualTo
private infix fun Constraint.otherwiseGreaterThanOrEqualTo(value: Number) = otherwise { "Must be greater than or equal to $value" }

public fun Validatable<Short?>.greaterThanOrEqualTo(value: Short): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Int?>.greaterThanOrEqualTo(value: Int): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Long?>.greaterThanOrEqualTo(value: Long): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Float?>.greaterThanOrEqualTo(value: Float): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value

public fun Validatable<Double?>.greaterThanOrEqualTo(value: Double): Constraint =
    constrainIfNotNull { it >= value } otherwiseGreaterThanOrEqualTo value
//endregion

//region between
@JvmName("shortBetween")
public fun Validatable<Short?>.between(range: IntRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun Validatable<Int?>.between(range: IntRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

public fun Validatable<Long?>.between(range: LongRange): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.first} and ${range.last} (inclusive)" }

@JvmName("closedFloatBetween")
public fun Validatable<Float?>.between(range: ClosedFloatingPointRange<Float>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@JvmName("closedDoubleBetween")
public fun Validatable<Double?>.between(range: ClosedFloatingPointRange<Double>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openFloatBetween")
public fun Validatable<Float?>.between(range: OpenEndRange<Float>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }

@OptIn(ExperimentalStdlibApi::class)
@JvmName("openDoubleBetween")
public fun Validatable<Double?>.between(range: OpenEndRange<Double>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion

//region integralCountEqualTo / fractionalCountEqualTo
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

@JvmName("shortIntegralCountEqualTo")
public fun Validatable<Short?>.integralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("intIntegralCountEqualTo")
public fun Validatable<Int?>.integralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("longIntegralCountEqualTo")
public fun Validatable<Long?>.integralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = false) otherwiseIntegralCountEqualTo count

@JvmName("floatIntegralCountEqualTo")
public fun Validatable<Float?>.integralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("doubleIntegralCountEqualTo")
public fun Validatable<Double?>.integralCountEqualTo(count: Int): Constraint =
    constrainIntegralPart(count, runDecimalChecks = true) otherwiseIntegralCountEqualTo count

@JvmName("floatDecimalCountEqualTo")
public fun Validatable<Float?>.fractionalCountEqualTo(count: Int): Constraint =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count

@JvmName("doubleDecimalCountEqualTo")
public fun Validatable<Double?>.fractionalCountEqualTo(count: Int): Constraint =
    constrainDecimalPart(count) otherwiseFractionalCountEqualTo count
//endregion
