package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalStdlibApi::class)
class NumberTest {
    //region isNotNaN
    @Test
    fun `'Float isNotNaN' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isNotNaN().satisfied)
    }

    @Test
    fun `'Float isNotNaN' succeeds when the value is different from NaN`() {
        assertTrue(Validatable(0f).isNotNaN().satisfied)
    }

    @Test
    fun `'Float isNotNaN' fails when the value is equal to NaN`() {
        assertFalse(Validatable(Float.NaN).isNotNaN().satisfied)
    }

    @Test
    fun `'Double isNotNaN' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isNotNaN().satisfied)
    }

    @Test
    fun `'Double isNotNaN' succeeds when the value is different from NaN`() {
        assertTrue(Validatable(0.0).isNotNaN().satisfied)
    }

    @Test
    fun `'Double isNotNaN' fails when the value is equal to NaN`() {
        assertFalse(Validatable(Double.NaN).isNotNaN().satisfied)
    }
    //endregion

    //region isFinite
    @Test
    fun `'Float isFinite' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isFinite().satisfied)
    }

    @Test
    fun `'Float isFinite' succeeds when the value is finite`() {
        assertTrue(Validatable(0f).isFinite().satisfied)
    }

    @Test
    fun `'Float isFinite' fails when the value is infinite`() {
        assertFalse(Validatable(Float.POSITIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to POSITIVE_INFINITY")
        assertFalse(Validatable(Float.NEGATIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun `'Double isFinite' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isFinite().satisfied)
    }

    @Test
    fun `'Double isFinite' succeeds when the value is finite`() {
        assertTrue(Validatable(0.0).isFinite().satisfied)
    }

    @Test
    fun `'Double isFinite' fails when the value is infinite`() {
        assertFalse(Validatable(Double.POSITIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to POSITIVE_INFINITY")
        assertFalse(Validatable(Double.NEGATIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to NEGATIVE_INFINITY")
    }
    //endregion

    //region isFinite
    @Test
    fun `'Float isInfinite' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isInfinite().satisfied)
    }

    @Test
    fun `'Float isInfinite' succeeds when the value is infinite`() {
        assertTrue(Validatable(Float.POSITIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to POSITIVE_INFINITY")
        assertTrue(Validatable(Float.NEGATIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun `'Float isInfinite' fails when the value is finite`() {
        assertFalse(Validatable(0f).isInfinite().satisfied)
    }

    @Test
    fun `'Double isInfinite' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isInfinite().satisfied)
    }

    @Test
    fun `'Double isInfinite' succeeds when the value is infinite`() {
        assertTrue(Validatable(Double.POSITIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to POSITIVE_INFINITY")
        assertTrue(Validatable(Double.NEGATIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun `'Double isInfinite' fails when the value is finite`() {
        assertFalse(Validatable(0.0).isInfinite().satisfied)
    }
    //endregion

    //region isNegative
    @Test
    fun `'Short isNegative' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isNegative().satisfied)
    }

    @Test
    fun `'Short isNegative' succeeds when the value is negative`() {
        assertTrue(Validatable((-1).toShort()).isNegative().satisfied)
    }

    @Test
    fun `'Short isNegative' fails when the value is positive or zero`() {
        assertFalse(Validatable((1).toShort()).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable((0).toShort()).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Int isNegative' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isNegative().satisfied)
    }

    @Test
    fun `'Int isNegative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1).isNegative().satisfied)
    }

    @Test
    fun `'Int isNegative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Long isNegative' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isNegative().satisfied)
    }

    @Test
    fun `'Long isNegative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1L).isNegative().satisfied)
    }

    @Test
    fun `'Long isNegative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1L).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0L).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Float isNegative' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isNegative().satisfied)
    }

    @Test
    fun `'Float isNegative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1f).isNegative().satisfied)
    }

    @Test
    fun `'Float isNegative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1f).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0f).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Double isNegative' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isNegative().satisfied)
    }

    @Test
    fun `'Double isNegative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1.0).isNegative().satisfied)
    }

    @Test
    fun `'Double isNegative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1.0).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0.0).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isNegativeOrZero
    @Test
    fun `'Short isNegativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Short isNegativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable((-1).toShort()).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable((0).toShort()).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Short isNegativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable((1).toShort()).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Int isNegativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Int isNegativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Int isNegativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Long isNegativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Long isNegativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1L).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0L).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Long isNegativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1L).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Float isNegativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Float isNegativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1f).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0f).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Float isNegativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1f).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Double isNegativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun `'Double isNegativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1.0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0.0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Double isNegativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1.0).isNegativeOrZero().satisfied)
    }
    //endregion

    //region isPositive
    @Test
    fun `'Short isPositive' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isPositive().satisfied)
    }

    @Test
    fun `'Short isPositive' succeeds when the value is positive`() {
        assertTrue(Validatable((1).toShort()).isPositive().satisfied)
    }

    @Test
    fun `'Short isPositive' fails when the value is negative or zero`() {
        assertFalse(Validatable((-1).toShort()).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable((0).toShort()).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Int isPositive' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isPositive().satisfied)
    }

    @Test
    fun `'Int isPositive' succeeds when the value is positive`() {
        assertTrue(Validatable(1).isPositive().satisfied)
    }

    @Test
    fun `'Int isPositive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Long isPositive' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isPositive().satisfied)
    }

    @Test
    fun `'Long isPositive' succeeds when the value is positive`() {
        assertTrue(Validatable(1L).isPositive().satisfied)
    }

    @Test
    fun `'Long isPositive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1L).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0L).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Float isPositive' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isPositive().satisfied)
    }

    @Test
    fun `'Float isPositive' succeeds when the value is positive`() {
        assertTrue(Validatable(1f).isPositive().satisfied)
    }

    @Test
    fun `'Float isPositive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1f).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0f).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Double isPositive' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isPositive().satisfied)
    }

    @Test
    fun `'Double isPositive' succeeds when the value is positive`() {
        assertTrue(Validatable(1.0).isPositive().satisfied)
    }

    @Test
    fun `'Double isPositive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1.0).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0.0).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isPositiveOrZero
    @Test
    fun `'Short isPositiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Short isPositiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable((1).toShort()).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable((0).toShort()).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Short isPositiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable((-1).toShort()).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Int isPositiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Int isPositiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Int isPositiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Long isPositiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Long isPositiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1L).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0L).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Long isPositiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1L).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Float isPositiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Float isPositiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1f).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0f).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Float isPositiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1f).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Double isPositiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun `'Double isPositiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1.0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0.0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Double isPositiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1.0).isPositiveOrZero().satisfied)
    }
    //endregion

    //region isLowerThan
    @Test
    fun `'Short isLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Short isLowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable((2).toShort()).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Short isLowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable((2).toShort()).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable((2).toShort()).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int isLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Int isLowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Int isLowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long isLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Long isLowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2L).isLowerThan(3).satisfied)
    }

    @Test
    fun `'Long isLowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2L).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2L).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float isLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isLowerThan(3f).satisfied)
    }

    @Test
    fun `'Float isLowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2f).isLowerThan(3f).satisfied)
    }

    @Test
    fun `'Float isLowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2f).isLowerThan(1f).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2f).isLowerThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double isLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isLowerThan(3.0).satisfied)
    }

    @Test
    fun `'Double isLowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2.0).isLowerThan(3.0).satisfied)
    }

    @Test
    fun `'Double isLowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2.0).isLowerThan(1.0).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2.0).isLowerThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isLowerThanOrEqualTo
    @Test
    fun `'Short isLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Short isLowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable((2).toShort()).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable((2).toShort()).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Short isLowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable((2).toShort()).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Int isLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int isLowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int isLowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Long isLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long isLowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2L).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2L).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long isLowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2L).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Float isLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isLowerThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Float isLowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2f).isLowerThanOrEqualTo(3f).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2f).isLowerThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float isLowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2f).isLowerThanOrEqualTo(1f).satisfied)
    }

    @Test
    fun `'Double isLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isLowerThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun `'Double isLowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2.0).isLowerThanOrEqualTo(3.0).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2.0).isLowerThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double isLowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2.0).isLowerThanOrEqualTo(1.0).satisfied)
    }
    //endregion

    //region isGreaterThan
    @Test
    fun `'Short isGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun `'Short isGreaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable((2).toShort()).isGreaterThan(1).satisfied)
    }

    @Test
    fun `'Short isGreaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable((2).toShort()).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable((2).toShort()).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int isGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun `'Int isGreaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2).isGreaterThan(1).satisfied)
    }

    @Test
    fun `'Int isGreaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long isGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun `'Long isGreaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2L).isGreaterThan(1).satisfied)
    }

    @Test
    fun `'Long isGreaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2L).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2L).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float isGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isGreaterThan(3f).satisfied)
    }

    @Test
    fun `'Float isGreaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2f).isGreaterThan(1f).satisfied)
    }

    @Test
    fun `'Float isGreaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2f).isGreaterThan(3f).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2f).isGreaterThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double isGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isGreaterThan(3.0).satisfied)
    }

    @Test
    fun `'Double isGreaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2.0).isGreaterThan(1.0).satisfied)
    }

    @Test
    fun `'Double isGreaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2.0).isGreaterThan(3.0).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2.0).isGreaterThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isGreaterThanOrEqualTo
    @Test
    fun `'Short isGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Short isGreaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable((2).toShort()).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable((2).toShort()).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Short isGreaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable((2).toShort()).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int isGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int isGreaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int isGreaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long isGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long isGreaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2L).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2L).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long isGreaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2L).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Float isGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).isGreaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Float isGreaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2f).isGreaterThanOrEqualTo(1f).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2f).isGreaterThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float isGreaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2f).isGreaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Double isGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).isGreaterThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun `'Double isGreaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2.0).isGreaterThanOrEqualTo(1.0).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2.0).isGreaterThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double isGreaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2.0).isGreaterThanOrEqualTo(3.0).satisfied)
    }
    //endregion

    //region isBetween
    @Test
    fun `'Short isBetween' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).isBetween(3..9).satisfied)
    }

    @Test
    fun `'Short isBetween' succeeds when the value is within the provided range`() {
        assertTrue(Validatable((2).toShort()).isBetween(1..3).satisfied)
    }

    @Test
    fun `'Short isBetween' fails when the value is outside the provided range`() {
        assertFalse(Validatable((2).toShort()).isBetween(3..5).satisfied)
    }

    @Test
    fun `'Int isBetween' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).isBetween(3..9).satisfied)
    }

    @Test
    fun `'Int isBetween' succeeds when the value is within the provided range`() {
        assertTrue(Validatable(2).isBetween(1..3).satisfied)
    }

    @Test
    fun `'Int isBetween' fails when the value is outside the provided range`() {
        assertFalse(Validatable(2).isBetween(3..5).satisfied)
    }

    @Test
    fun `'Long isBetween' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).isBetween(3L..9L).satisfied)
    }

    @Test
    fun `'Long isBetween' succeeds when the value is within the provided range`() {
        assertTrue(Validatable(2L).isBetween(1L..3L).satisfied)
    }

    @Test
    fun `'Long isBetween' fails when the value is outside the provided range`() {
        assertFalse(Validatable(2L).isBetween(3L..5L).satisfied)
    }

    @Test
    fun `'Float isBetween' succeeds with a closed range when the value is null`() {
        assertTrue(Validatable<Float?>(null).isBetween(3f..9f).satisfied)
    }

    @Test
    fun `'Float isBetween' succeeds with a closed range when the value is within the provided range`() {
        assertTrue(Validatable(2f).isBetween(1f..3f).satisfied)
    }

    @Test
    fun `'Float isBetween' fails with a closed range when the value is outside the provided range`() {
        assertFalse(Validatable(2f).isBetween(3f..5f).satisfied)
    }

    @Test
    fun `'Double isBetween' succeeds with a closed range when the value is null`() {
        assertTrue(Validatable<Double?>(null).isBetween(3.0..9.0).satisfied)
    }

    @Test
    fun `'Double isBetween' succeeds with a closed range when the value is within the provided range`() {
        assertTrue(Validatable(2.0).isBetween(1.0..3.0).satisfied)
    }

    @Test
    fun `'Double isBetween' fails with a closed range when the value is outside the provided range`() {
        assertFalse(Validatable(2.0).isBetween(3.0..5.0).satisfied)
    }

    @Test
    fun `'Float isBetween' succeeds with an open-ended range when the value is null`() {
        assertTrue(Validatable<Float?>(null).isBetween(3f..<9f).satisfied)
    }

    @Test
    fun `'Float isBetween' succeeds with an open-ended range when the value is within the provided range`() {
        assertTrue(Validatable(2f).isBetween(1f..<3f).satisfied)
    }

    @Test
    fun `'Float isBetween' fails with an open-ended range when the value is outside the provided range`() {
        assertFalse(Validatable(2f).isBetween(1f..<2f).satisfied)
    }

    @Test
    fun `'Double isBetween' succeeds with an open-ended range when the value is null`() {
        assertTrue(Validatable<Double?>(null).isBetween(3.0..<9.0).satisfied)
    }

    @Test
    fun `'Double isBetween' succeeds with an open-ended range when the value is within the provided range`() {
        assertTrue(Validatable(2.0).isBetween(1.0..<3.0).satisfied)
    }

    @Test
    fun `'Double isBetween' fails with an open-ended range when the value is outside the provided range`() {
        assertFalse(Validatable(2.0).isBetween(1.0..<2.0).satisfied)
    }
    //endregion

    //region hasIntegralCountEqualTo
    @Test
    fun `'Short hasIntegralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Short hasIntegralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable((123).toShort()).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun `'Short hasIntegralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable((123).toShort()).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable((-123).toShort()).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Short hasIntegralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable((123).toShort()).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Int hasIntegralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Int hasIntegralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun `'Int hasIntegralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Int hasIntegralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Long hasIntegralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Long hasIntegralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123L).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun `'Long hasIntegralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123L).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123L).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Long hasIntegralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123L).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Float hasIntegralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Float hasIntegralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45f).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun `'Float hasIntegralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45f).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45f).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Float hasIntegralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45f).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Double hasIntegralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Double hasIntegralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun `'Double hasIntegralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Double hasIntegralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45).hasIntegralCountEqualTo(5).satisfied)
    }
    //endregion

    //region hasFractionalCountEqualTo
    @Test
    fun `'Float hasFractionalCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45f).hasFractionalCountEqualTo(0)
        }
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45f).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45f).hasFractionalCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Double hasFractionalCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Double hasFractionalCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45).hasFractionalCountEqualTo(0)
        }
    }

    @Test
    fun `'Double hasFractionalCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Double hasFractionalCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45).hasFractionalCountEqualTo(5).satisfied)
    }
    //endregion
}
