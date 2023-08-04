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

    //region negative
    @Test
    fun `'Short negative' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).negative().satisfied)
    }

    @Test
    fun `'Short negative' succeeds when the value is negative`() {
        assertTrue(Validatable((-1).toShort()).negative().satisfied)
    }

    @Test
    fun `'Short negative' fails when the value is positive or zero`() {
        assertFalse(Validatable((1).toShort()).negative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable((0).toShort()).negative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Int negative' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).negative().satisfied)
    }

    @Test
    fun `'Int negative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1).negative().satisfied)
    }

    @Test
    fun `'Int negative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1).negative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0).negative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Long negative' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).negative().satisfied)
    }

    @Test
    fun `'Long negative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1L).negative().satisfied)
    }

    @Test
    fun `'Long negative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1L).negative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0L).negative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Float negative' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).negative().satisfied)
    }

    @Test
    fun `'Float negative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1f).negative().satisfied)
    }

    @Test
    fun `'Float negative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1f).negative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0f).negative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Double negative' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).negative().satisfied)
    }

    @Test
    fun `'Double negative' succeeds when the value is negative`() {
        assertTrue(Validatable(-1.0).negative().satisfied)
    }

    @Test
    fun `'Double negative' fails when the value is positive or zero`() {
        assertFalse(Validatable(1.0).negative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0.0).negative().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region negativeOrZero
    @Test
    fun `'Short negativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).negativeOrZero().satisfied)
    }

    @Test
    fun `'Short negativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable((-1).toShort()).negativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable((0).toShort()).negativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Short negativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable((1).toShort()).negativeOrZero().satisfied)
    }

    @Test
    fun `'Int negativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).negativeOrZero().satisfied)
    }

    @Test
    fun `'Int negativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1).negativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0).negativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Int negativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1).negativeOrZero().satisfied)
    }

    @Test
    fun `'Long negativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).negativeOrZero().satisfied)
    }

    @Test
    fun `'Long negativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1L).negativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0L).negativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Long negativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1L).negativeOrZero().satisfied)
    }

    @Test
    fun `'Float negativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).negativeOrZero().satisfied)
    }

    @Test
    fun `'Float negativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1f).negativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0f).negativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Float negativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1f).negativeOrZero().satisfied)
    }

    @Test
    fun `'Double negativeOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).negativeOrZero().satisfied)
    }

    @Test
    fun `'Double negativeOrZero' succeeds when the value is negative or zero`() {
        assertTrue(Validatable(-1.0).negativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0.0).negativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Double negativeOrZero' fails when the value is positive`() {
        assertFalse(Validatable(1.0).negativeOrZero().satisfied)
    }
    //endregion

    //region positive
    @Test
    fun `'Short positive' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).positive().satisfied)
    }

    @Test
    fun `'Short positive' succeeds when the value is positive`() {
        assertTrue(Validatable((1).toShort()).positive().satisfied)
    }

    @Test
    fun `'Short positive' fails when the value is negative or zero`() {
        assertFalse(Validatable((-1).toShort()).positive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable((0).toShort()).positive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Int positive' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).positive().satisfied)
    }

    @Test
    fun `'Int positive' succeeds when the value is positive`() {
        assertTrue(Validatable(1).positive().satisfied)
    }

    @Test
    fun `'Int positive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1).positive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0).positive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Long positive' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).positive().satisfied)
    }

    @Test
    fun `'Long positive' succeeds when the value is positive`() {
        assertTrue(Validatable(1L).positive().satisfied)
    }

    @Test
    fun `'Long positive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1L).positive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0L).positive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Float positive' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).positive().satisfied)
    }

    @Test
    fun `'Float positive' succeeds when the value is positive`() {
        assertTrue(Validatable(1f).positive().satisfied)
    }

    @Test
    fun `'Float positive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1f).positive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0f).positive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun `'Double positive' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).positive().satisfied)
    }

    @Test
    fun `'Double positive' succeeds when the value is positive`() {
        assertTrue(Validatable(1.0).positive().satisfied)
    }

    @Test
    fun `'Double positive' fails when the value is negative or zero`() {
        assertFalse(Validatable(-1.0).positive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0.0).positive().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region positiveOrZero
    @Test
    fun `'Short positiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).positiveOrZero().satisfied)
    }

    @Test
    fun `'Short positiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable((1).toShort()).positiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable((0).toShort()).positiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Short positiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable((-1).toShort()).positiveOrZero().satisfied)
    }

    @Test
    fun `'Int positiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).positiveOrZero().satisfied)
    }

    @Test
    fun `'Int positiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1).positiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0).positiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Int positiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1).positiveOrZero().satisfied)
    }

    @Test
    fun `'Long positiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).positiveOrZero().satisfied)
    }

    @Test
    fun `'Long positiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1L).positiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0L).positiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Long positiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1L).positiveOrZero().satisfied)
    }

    @Test
    fun `'Float positiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).positiveOrZero().satisfied)
    }

    @Test
    fun `'Float positiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1f).positiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0f).positiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Float positiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1f).positiveOrZero().satisfied)
    }

    @Test
    fun `'Double positiveOrZero' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).positiveOrZero().satisfied)
    }

    @Test
    fun `'Double positiveOrZero' succeeds when the value is positive or zero`() {
        assertTrue(Validatable(1.0).positiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0.0).positiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun `'Double positiveOrZero' fails when the value is negative`() {
        assertFalse(Validatable(-1.0).positiveOrZero().satisfied)
    }
    //endregion

    //region lowerThan
    @Test
    fun `'Short lowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).lowerThan(3).satisfied)
    }

    @Test
    fun `'Short lowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable((2).toShort()).lowerThan(3).satisfied)
    }

    @Test
    fun `'Short lowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable((2).toShort()).lowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable((2).toShort()).lowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int lowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).lowerThan(3).satisfied)
    }

    @Test
    fun `'Int lowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2).lowerThan(3).satisfied)
    }

    @Test
    fun `'Int lowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2).lowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2).lowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long lowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).lowerThan(3).satisfied)
    }

    @Test
    fun `'Long lowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2L).lowerThan(3).satisfied)
    }

    @Test
    fun `'Long lowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2L).lowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2L).lowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float lowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).lowerThan(3f).satisfied)
    }

    @Test
    fun `'Float lowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2f).lowerThan(3f).satisfied)
    }

    @Test
    fun `'Float lowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2f).lowerThan(1f).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2f).lowerThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double lowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).lowerThan(3.0).satisfied)
    }

    @Test
    fun `'Double lowerThan' succeeds when the value is lower than the provided one`() {
        assertTrue(Validatable(2.0).lowerThan(3.0).satisfied)
    }

    @Test
    fun `'Double lowerThan' fails when the value is greater than or equal to the provided one`() {
        assertFalse(Validatable(2.0).lowerThan(1.0).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2.0).lowerThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region lowerThanOrEqualTo
    @Test
    fun `'Short lowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).lowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Short lowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable((2).toShort()).lowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable((2).toShort()).lowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Short lowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable((2).toShort()).lowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Int lowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).lowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int lowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2).lowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2).lowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int lowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2).lowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Long lowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).lowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long lowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2L).lowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2L).lowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long lowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2L).lowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun `'Float lowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).lowerThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Float lowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2f).lowerThanOrEqualTo(3f).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2f).lowerThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float lowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2f).lowerThanOrEqualTo(1f).satisfied)
    }

    @Test
    fun `'Double lowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).lowerThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun `'Double lowerThanOrEqualTo' succeeds when the value is lower than or equal to the provided one`() {
        assertTrue(Validatable(2.0).lowerThanOrEqualTo(3.0).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2.0).lowerThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double lowerThanOrEqualTo' fails when the value is greater than the provided one`() {
        assertFalse(Validatable(2.0).lowerThanOrEqualTo(1.0).satisfied)
    }
    //endregion

    //region greaterThan
    @Test
    fun `'Short greaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).greaterThan(3).satisfied)
    }

    @Test
    fun `'Short greaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable((2).toShort()).greaterThan(1).satisfied)
    }

    @Test
    fun `'Short greaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable((2).toShort()).greaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable((2).toShort()).greaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int greaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).greaterThan(3).satisfied)
    }

    @Test
    fun `'Int greaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2).greaterThan(1).satisfied)
    }

    @Test
    fun `'Int greaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2).greaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2).greaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long greaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).greaterThan(3).satisfied)
    }

    @Test
    fun `'Long greaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2L).greaterThan(1).satisfied)
    }

    @Test
    fun `'Long greaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2L).greaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2L).greaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float greaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).greaterThan(3f).satisfied)
    }

    @Test
    fun `'Float greaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2f).greaterThan(1f).satisfied)
    }

    @Test
    fun `'Float greaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2f).greaterThan(3f).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2f).greaterThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double greaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).greaterThan(3.0).satisfied)
    }

    @Test
    fun `'Double greaterThan' succeeds when the value is greater than the provided one`() {
        assertTrue(Validatable(2.0).greaterThan(1.0).satisfied)
    }

    @Test
    fun `'Double greaterThan' fails when the value is lower than or equal to the provided one`() {
        assertFalse(Validatable(2.0).greaterThan(3.0).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2.0).greaterThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region greaterThanOrEqualTo
    @Test
    fun `'Short greaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Short greaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable((2).toShort()).greaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable((2).toShort()).greaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Short greaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable((2).toShort()).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int greaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Int greaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2).greaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2).greaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Int greaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long greaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Long greaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2L).greaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2L).greaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Long greaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2L).greaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun `'Float greaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).greaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Float greaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2f).greaterThanOrEqualTo(1f).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2f).greaterThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Float greaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2f).greaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun `'Double greaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).greaterThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun `'Double greaterThanOrEqualTo' succeeds when the value is greater than or equal to the provided one`() {
        assertTrue(Validatable(2.0).greaterThanOrEqualTo(1.0).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2.0).greaterThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Double greaterThanOrEqualTo' fails when the value is lower than the provided one`() {
        assertFalse(Validatable(2.0).greaterThanOrEqualTo(3.0).satisfied)
    }
    //endregion

    //region between
    @Test
    fun `'Short between' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).between(3..9).satisfied)
    }

    @Test
    fun `'Short between' succeeds when the value is within the provided range`() {
        assertTrue(Validatable((2).toShort()).between(1..3).satisfied)
    }

    @Test
    fun `'Short between' fails when the value is outside the provided range`() {
        assertFalse(Validatable((2).toShort()).between(3..5).satisfied)
    }

    @Test
    fun `'Int between' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).between(3..9).satisfied)
    }

    @Test
    fun `'Int between' succeeds when the value is within the provided range`() {
        assertTrue(Validatable(2).between(1..3).satisfied)
    }

    @Test
    fun `'Int between' fails when the value is outside the provided range`() {
        assertFalse(Validatable(2).between(3..5).satisfied)
    }

    @Test
    fun `'Long between' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).between(3L..9L).satisfied)
    }

    @Test
    fun `'Long between' succeeds when the value is within the provided range`() {
        assertTrue(Validatable(2L).between(1L..3L).satisfied)
    }

    @Test
    fun `'Long between' fails when the value is outside the provided range`() {
        assertFalse(Validatable(2L).between(3L..5L).satisfied)
    }

    @Test
    fun `'Float between' succeeds with a closed range when the value is null`() {
        assertTrue(Validatable<Float?>(null).between(3f..9f).satisfied)
    }

    @Test
    fun `'Float between' succeeds with a closed range when the value is within the provided range`() {
        assertTrue(Validatable(2f).between(1f..3f).satisfied)
    }

    @Test
    fun `'Float between' fails with a closed range when the value is outside the provided range`() {
        assertFalse(Validatable(2f).between(3f..5f).satisfied)
    }

    @Test
    fun `'Double between' succeeds with a closed range when the value is null`() {
        assertTrue(Validatable<Double?>(null).between(3.0..9.0).satisfied)
    }

    @Test
    fun `'Double between' succeeds with a closed range when the value is within the provided range`() {
        assertTrue(Validatable(2.0).between(1.0..3.0).satisfied)
    }

    @Test
    fun `'Double between' fails with a closed range when the value is outside the provided range`() {
        assertFalse(Validatable(2.0).between(3.0..5.0).satisfied)
    }

    @Test
    fun `'Float between' succeeds with an open-ended range when the value is null`() {
        assertTrue(Validatable<Float?>(null).between(3f..<9f).satisfied)
    }

    @Test
    fun `'Float between' succeeds with an open-ended range when the value is within the provided range`() {
        assertTrue(Validatable(2f).between(1f..<3f).satisfied)
    }

    @Test
    fun `'Float between' fails with an open-ended range when the value is outside the provided range`() {
        assertFalse(Validatable(2f).between(1f..<2f).satisfied)
    }

    @Test
    fun `'Double between' succeeds with an open-ended range when the value is null`() {
        assertTrue(Validatable<Double?>(null).between(3.0..<9.0).satisfied)
    }

    @Test
    fun `'Double between' succeeds with an open-ended range when the value is within the provided range`() {
        assertTrue(Validatable(2.0).between(1.0..<3.0).satisfied)
    }

    @Test
    fun `'Double between' fails with an open-ended range when the value is outside the provided range`() {
        assertFalse(Validatable(2.0).between(1.0..<2.0).satisfied)
    }
    //endregion

    //region integralCountEqualTo
    @Test
    fun `'Short integralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Short?>(null).integralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Short integralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable((123).toShort()).integralCountEqualTo(0)
        }
    }

    @Test
    fun `'Short integralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable((123).toShort()).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable((-123).toShort()).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Short integralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable((123).toShort()).integralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Int integralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Int?>(null).integralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Int integralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123).integralCountEqualTo(0)
        }
    }

    @Test
    fun `'Int integralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Int integralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123).integralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Long integralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Long?>(null).integralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Long integralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123L).integralCountEqualTo(0)
        }
    }

    @Test
    fun `'Long integralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123L).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123L).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Long integralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123L).integralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Float integralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).integralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Float integralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45f).integralCountEqualTo(0)
        }
    }

    @Test
    fun `'Float integralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45f).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45f).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Float integralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45f).integralCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Double integralCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).integralCountEqualTo(3).satisfied)
    }

    @Test
    fun `'Double integralCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45).integralCountEqualTo(0)
        }
    }

    @Test
    fun `'Double integralCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45).integralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun `'Double integralCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45).integralCountEqualTo(5).satisfied)
    }
    //endregion

    //region fractionalCountEqualTo
    @Test
    fun `'Float fractionalCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).fractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float fractionalCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45f).fractionalCountEqualTo(0)
        }
    }

    @Test
    fun `'Float fractionalCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45f).fractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float fractionalCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45f).fractionalCountEqualTo(5).satisfied)
    }

    @Test
    fun `'Double fractionalCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Double?>(null).fractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Double fractionalCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertThrows<IllegalArgumentException> {
            Validatable(123.45).fractionalCountEqualTo(0)
        }
    }

    @Test
    fun `'Double fractionalCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45).fractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Double fractionalCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45).fractionalCountEqualTo(5).satisfied)
    }
    //endregion
}
