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

import dev.nesk.akkurate.test.Validatable
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalStdlibApi::class)
class NumberTest {
    //region isNotNaN
    @Test
    fun __Float_isNotNaN__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isNotNaN().satisfied)
    }

    @Test
    fun __Float_isNotNaN__succeeds_when_the_value_is_different_from_NaN() {
        assertTrue(Validatable(0f).isNotNaN().satisfied)
    }

    @Test
    fun __Float_isNotNaN__fails_when_the_value_is_equal_to_NaN() {
        assertFalse(Validatable(Float.NaN).isNotNaN().satisfied)
    }

    @Test
    fun __Double_isNotNaN__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isNotNaN().satisfied)
    }

    @Test
    fun __Double_isNotNaN__succeeds_when_the_value_is_different_from_NaN() {
        assertTrue(Validatable(0.0).isNotNaN().satisfied)
    }

    @Test
    fun __Double_isNotNaN__fails_when_the_value_is_equal_to_NaN() {
        assertFalse(Validatable(Double.NaN).isNotNaN().satisfied)
    }
    //endregion

    //region isFinite
    @Test
    fun __Float_isFinite__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isFinite().satisfied)
    }

    @Test
    fun __Float_isFinite__succeeds_when_the_value_is_finite() {
        assertTrue(Validatable(0f).isFinite().satisfied)
    }

    @Test
    fun __Float_isFinite__fails_when_the_value_is_infinite() {
        assertFalse(Validatable(Float.POSITIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to POSITIVE_INFINITY")
        assertFalse(Validatable(Float.NEGATIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun __Double_isFinite__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isFinite().satisfied)
    }

    @Test
    fun __Double_isFinite__succeeds_when_the_value_is_finite() {
        assertTrue(Validatable(0.0).isFinite().satisfied)
    }

    @Test
    fun __Double_isFinite__fails_when_the_value_is_infinite() {
        assertFalse(Validatable(Double.POSITIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to POSITIVE_INFINITY")
        assertFalse(Validatable(Double.NEGATIVE_INFINITY).isFinite().satisfied, "The constraint is not satisfied when the value is equal to NEGATIVE_INFINITY")
    }
    //endregion

    //region isFinite
    @Test
    fun __Float_isInfinite__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isInfinite().satisfied)
    }

    @Test
    fun __Float_isInfinite__succeeds_when_the_value_is_infinite() {
        assertTrue(Validatable(Float.POSITIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to POSITIVE_INFINITY")
        assertTrue(Validatable(Float.NEGATIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun __Float_isInfinite__fails_when_the_value_is_finite() {
        assertFalse(Validatable(0f).isInfinite().satisfied)
    }

    @Test
    fun __Double_isInfinite__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isInfinite().satisfied)
    }

    @Test
    fun __Double_isInfinite__succeeds_when_the_value_is_infinite() {
        assertTrue(Validatable(Double.POSITIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to POSITIVE_INFINITY")
        assertTrue(Validatable(Double.NEGATIVE_INFINITY).isInfinite().satisfied, "The constraint is satisfied when the value is equal to NEGATIVE_INFINITY")
    }

    @Test
    fun __Double_isInfinite__fails_when_the_value_is_finite() {
        assertFalse(Validatable(0.0).isInfinite().satisfied)
    }
    //endregion

    //region isNegative
    @Test
    fun __Short_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isNegative().satisfied)
    }

    @Test
    fun __Short_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable((-1).toShort()).isNegative().satisfied)
    }

    @Test
    fun __Short_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable((1).toShort()).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable((0).toShort()).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Int_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isNegative().satisfied)
    }

    @Test
    fun __Int_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable(-1).isNegative().satisfied)
    }

    @Test
    fun __Int_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable(1).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Long_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isNegative().satisfied)
    }

    @Test
    fun __Long_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable(-1L).isNegative().satisfied)
    }

    @Test
    fun __Long_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable(1L).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0L).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Float_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isNegative().satisfied)
    }

    @Test
    fun __Float_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable(-1f).isNegative().satisfied)
    }

    @Test
    fun __Float_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable(1f).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0f).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Double_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isNegative().satisfied)
    }

    @Test
    fun __Double_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable(-1.0).isNegative().satisfied)
    }

    @Test
    fun __Double_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable(1.0).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0.0).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isNegativeOrZero
    @Test
    fun __Short_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Short_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable((-1).toShort()).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable((0).toShort()).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Short_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable((1).toShort()).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Int_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Int_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable(-1).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Int_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable(1).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Long_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Long_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable(-1L).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0L).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Long_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable(1L).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Float_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Float_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable(-1f).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0f).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Float_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable(1f).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Double_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Double_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable(-1.0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0.0).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Double_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable(1.0).isNegativeOrZero().satisfied)
    }
    //endregion

    //region isPositive
    @Test
    fun __Short_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isPositive().satisfied)
    }

    @Test
    fun __Short_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable((1).toShort()).isPositive().satisfied)
    }

    @Test
    fun __Short_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable((-1).toShort()).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable((0).toShort()).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Int_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isPositive().satisfied)
    }

    @Test
    fun __Int_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable(1).isPositive().satisfied)
    }

    @Test
    fun __Int_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable(-1).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Long_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isPositive().satisfied)
    }

    @Test
    fun __Long_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable(1L).isPositive().satisfied)
    }

    @Test
    fun __Long_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable(-1L).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0L).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Float_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isPositive().satisfied)
    }

    @Test
    fun __Float_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable(1f).isPositive().satisfied)
    }

    @Test
    fun __Float_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable(-1f).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0f).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }

    @Test
    fun __Double_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isPositive().satisfied)
    }

    @Test
    fun __Double_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable(1.0).isPositive().satisfied)
    }

    @Test
    fun __Double_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable(-1.0).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0.0).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isPositiveOrZero
    @Test
    fun __Short_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Short_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable((1).toShort()).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable((0).toShort()).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Short_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable((-1).toShort()).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Int_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Int_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable(1).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Int_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable(-1).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Long_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Long_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable(1L).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0L).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Long_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable(-1L).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Float_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Float_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable(1f).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0f).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Float_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable(-1f).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Double_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Double_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable(1.0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0.0).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Double_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable(-1.0).isPositiveOrZero().satisfied)
    }
    //endregion

    //region isLowerThan
    @Test
    fun __Short_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun __Short_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable((2).toShort()).isLowerThan(3).satisfied)
    }

    @Test
    fun __Short_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable((2).toShort()).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable((2).toShort()).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Int_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun __Int_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable(2).isLowerThan(3).satisfied)
    }

    @Test
    fun __Int_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Long_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isLowerThan(3).satisfied)
    }

    @Test
    fun __Long_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable(2L).isLowerThan(3).satisfied)
    }

    @Test
    fun __Long_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2L).isLowerThan(1).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2L).isLowerThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Float_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isLowerThan(3f).satisfied)
    }

    @Test
    fun __Float_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable(2f).isLowerThan(3f).satisfied)
    }

    @Test
    fun __Float_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2f).isLowerThan(1f).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2f).isLowerThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Double_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isLowerThan(3.0).satisfied)
    }

    @Test
    fun __Double_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable(2.0).isLowerThan(3.0).satisfied)
    }

    @Test
    fun __Double_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2.0).isLowerThan(1.0).satisfied, "The constraint is not satisfied when the value is greater than the provided one")
        assertFalse(Validatable(2.0).isLowerThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isLowerThanOrEqualTo
    @Test
    fun __Short_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Short_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable((2).toShort()).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable((2).toShort()).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Short_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable((2).toShort()).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun __Int_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Int_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Int_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable(2).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun __Long_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isLowerThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Long_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2L).isLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2L).isLowerThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Long_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable(2L).isLowerThanOrEqualTo(1).satisfied)
    }

    @Test
    fun __Float_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isLowerThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun __Float_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2f).isLowerThanOrEqualTo(3f).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2f).isLowerThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Float_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable(2f).isLowerThanOrEqualTo(1f).satisfied)
    }

    @Test
    fun __Double_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isLowerThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun __Double_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2.0).isLowerThanOrEqualTo(3.0).satisfied, "The constraint is satisfied when the value is lower than the provided one")
        assertTrue(Validatable(2.0).isLowerThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Double_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable(2.0).isLowerThanOrEqualTo(1.0).satisfied)
    }
    //endregion

    //region isGreaterThan
    @Test
    fun __Short_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun __Short_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable((2).toShort()).isGreaterThan(1).satisfied)
    }

    @Test
    fun __Short_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable((2).toShort()).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable((2).toShort()).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Int_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun __Int_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable(2).isGreaterThan(1).satisfied)
    }

    @Test
    fun __Int_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Long_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isGreaterThan(3).satisfied)
    }

    @Test
    fun __Long_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable(2L).isGreaterThan(1).satisfied)
    }

    @Test
    fun __Long_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2L).isGreaterThan(3).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2L).isGreaterThan(2).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Float_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isGreaterThan(3f).satisfied)
    }

    @Test
    fun __Float_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable(2f).isGreaterThan(1f).satisfied)
    }

    @Test
    fun __Float_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2f).isGreaterThan(3f).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2f).isGreaterThan(2f).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Double_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isGreaterThan(3.0).satisfied)
    }

    @Test
    fun __Double_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable(2.0).isGreaterThan(1.0).satisfied)
    }

    @Test
    fun __Double_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable(2.0).isGreaterThan(3.0).satisfied, "The constraint is not satisfied when the value is lower than the provided one")
        assertFalse(Validatable(2.0).isGreaterThan(2.0).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isGreaterThanOrEqualTo
    @Test
    fun __Short_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Short_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable((2).toShort()).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable((2).toShort()).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Short_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable((2).toShort()).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Int_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Int_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Int_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable(2).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Long_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Long_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2L).isGreaterThanOrEqualTo(1).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2L).isGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Long_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable(2L).isGreaterThanOrEqualTo(3).satisfied)
    }

    @Test
    fun __Float_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isGreaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun __Float_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2f).isGreaterThanOrEqualTo(1f).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2f).isGreaterThanOrEqualTo(2f).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Float_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable(2f).isGreaterThanOrEqualTo(3f).satisfied)
    }

    @Test
    fun __Double_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isGreaterThanOrEqualTo(3.0).satisfied)
    }

    @Test
    fun __Double_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable(2.0).isGreaterThanOrEqualTo(1.0).satisfied, "The constraint is satisfied when the value is greater than the provided one")
        assertTrue(Validatable(2.0).isGreaterThanOrEqualTo(2.0).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Double_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable(2.0).isGreaterThanOrEqualTo(3.0).satisfied)
    }
    //endregion

    //region isBetween
    @Test
    fun __Short_isBetween__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).isBetween(3..9).satisfied)
    }

    @Test
    fun __Short_isBetween__succeeds_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable((2).toShort()).isBetween(1..3).satisfied)
    }

    @Test
    fun __Short_isBetween__fails_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable((2).toShort()).isBetween(3..5).satisfied)
    }

    @Test
    fun __Int_isBetween__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).isBetween(3..9).satisfied)
    }

    @Test
    fun __Int_isBetween__succeeds_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2).isBetween(1..3).satisfied)
    }

    @Test
    fun __Int_isBetween__fails_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2).isBetween(3..5).satisfied)
    }

    @Test
    fun __Long_isBetween__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).isBetween(3L..9L).satisfied)
    }

    @Test
    fun __Long_isBetween__succeeds_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2L).isBetween(1L..3L).satisfied)
    }

    @Test
    fun __Long_isBetween__fails_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2L).isBetween(3L..5L).satisfied)
    }

    @Test
    fun __Float_isBetween__succeeds_with_a_closed_range_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isBetween(3f..9f).satisfied)
    }

    @Test
    fun __Float_isBetween__succeeds_with_a_closed_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2f).isBetween(1f..3f).satisfied)
    }

    @Test
    fun __Float_isBetween__fails_with_a_closed_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2f).isBetween(3f..5f).satisfied)
    }

    @Test
    fun __Double_isBetween__succeeds_with_a_closed_range_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isBetween(3.0..9.0).satisfied)
    }

    @Test
    fun __Double_isBetween__succeeds_with_a_closed_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2.0).isBetween(1.0..3.0).satisfied)
    }

    @Test
    fun __Double_isBetween__fails_with_a_closed_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2.0).isBetween(3.0..5.0).satisfied)
    }

    @Test
    fun __Float_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).isBetween(3f..<9f).satisfied)
    }

    @Test
    fun __Float_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2f).isBetween(1f..<3f).satisfied)
    }

    @Test
    fun __Float_isBetween__fails_with_an_open_ended_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2f).isBetween(1f..<2f).satisfied)
    }

    @Test
    fun __Double_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).isBetween(3.0..<9.0).satisfied)
    }

    @Test
    fun __Double_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2.0).isBetween(1.0..<3.0).satisfied)
    }

    @Test
    fun __Double_isBetween__fails_with_an_open_ended_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2.0).isBetween(1.0..<2.0).satisfied)
    }
    //endregion

    //region hasIntegralCountEqualTo
    @Test
    fun __Short_hasIntegralCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Short?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun __Short_hasIntegralCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable((123).toShort()).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun __Short_hasIntegralCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable((123).toShort()).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable((-123).toShort()).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun __Short_hasIntegralCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable((123).toShort()).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun __Int_hasIntegralCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Int?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun __Int_hasIntegralCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun __Int_hasIntegralCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable(123).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun __Int_hasIntegralCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable(123).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun __Long_hasIntegralCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Long?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun __Long_hasIntegralCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123L).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun __Long_hasIntegralCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable(123L).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123L).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun __Long_hasIntegralCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable(123L).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun __Float_hasIntegralCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Float?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun __Float_hasIntegralCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123.45f).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun __Float_hasIntegralCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable(123.45f).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45f).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun __Float_hasIntegralCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable(123.45f).hasIntegralCountEqualTo(5).satisfied)
    }

    @Test
    fun __Double_hasIntegralCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).hasIntegralCountEqualTo(3).satisfied)
    }

    @Test
    fun __Double_hasIntegralCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123.45).hasIntegralCountEqualTo(0)
        }
    }

    @Test
    fun __Double_hasIntegralCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable(123.45).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a positive number")
        assertTrue(Validatable(-123.45).hasIntegralCountEqualTo(3).satisfied, "The constraint is satisfied with a negative number")
    }

    @Test
    fun __Double_hasIntegralCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable(123.45).hasIntegralCountEqualTo(5).satisfied)
    }
    //endregion

    //region hasFractionalCountEqualTo
    @Test
    fun __Double_hasFractionalCountEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Double?>(null).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun __Double_hasFractionalCountEqualTo__throws_an_exception_when_the_provided_count_is_lower_than_1() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123.45).hasFractionalCountEqualTo(0)
        }
    }

    @Test
    fun __Double_hasFractionalCountEqualTo__succeeds_when_the_number_of_digits_is_equal_to_the_provided_one() {
        assertTrue(Validatable(123.45).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun __Double_hasFractionalCountEqualTo__fails_when_the_number_of_digits_is_different_than_the_provided_one() {
        assertFalse(Validatable(123.45).hasFractionalCountEqualTo(5).satisfied)
    }
    //endregion
}
