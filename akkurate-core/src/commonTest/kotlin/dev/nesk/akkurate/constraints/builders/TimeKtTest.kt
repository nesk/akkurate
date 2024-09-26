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
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class TimeKtTest {
    //region isNegative
    @Test
    fun __Duration_isNegative__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isNegative().satisfied)
    }

    @Test
    fun __Duration_isNegative__succeeds_when_the_value_is_negative() {
        assertTrue(Validatable((-1).seconds).isNegative().satisfied)
    }

    @Test
    fun __Duration_isNegative__fails_when_the_value_is_positive_or_zero() {
        assertFalse(Validatable(1.seconds).isNegative().satisfied, "The constraint is not satisfied when the value is positive")
        assertFalse(Validatable(0.seconds).isNegative().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isNegativeOrZero
    @Test
    fun __Duration_isNegativeOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isNegativeOrZero().satisfied)
    }

    @Test
    fun __Duration_isNegativeOrZero__succeeds_when_the_value_is_negative_or_zero() {
        assertTrue(Validatable((-1).seconds).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is negative")
        assertTrue(Validatable(0.seconds).isNegativeOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Duration_isNegativeOrZero__fails_when_the_value_is_positive() {
        assertFalse(Validatable(1.seconds).isNegativeOrZero().satisfied)
    }
    //endregion

    //region isPositive
    @Test
    fun __Duration_isPositive__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isPositive().satisfied)
    }

    @Test
    fun __Duration_isPositive__succeeds_when_the_value_is_positive() {
        assertTrue(Validatable(1.seconds).isPositive().satisfied)
    }

    @Test
    fun __Duration_isPositive__fails_when_the_value_is_negative_or_zero() {
        assertFalse(Validatable((-1).seconds).isPositive().satisfied, "The constraint is not satisfied when the value is negative")
        assertFalse(Validatable(0.seconds).isPositive().satisfied, "The constraint is not satisfied when the value is zero")
    }
    //endregion

    //region isPositiveOrZero
    @Test
    fun __Duration_isPositiveOrZero__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isPositiveOrZero().satisfied)
    }

    @Test
    fun __Duration_isPositiveOrZero__succeeds_when_the_value_is_positive_or_zero() {
        assertTrue(Validatable(1.seconds).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is positive")
        assertTrue(Validatable(0.seconds).isPositiveOrZero().satisfied, "The constraint is satisfied when the value is zero")
    }

    @Test
    fun __Duration_isPositiveOrZero__fails_when_the_value_is_negative() {
        assertFalse(Validatable((-1).seconds).isPositiveOrZero().satisfied)
    }
    //endregion

    //region isLowerThan
    @Test
    fun __Duration_isLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isLowerThan(3.seconds).satisfied)
    }

    @Test
    fun __Duration_isLowerThan__succeeds_when_the_value_is_lower_than_the_provided_one() {
        assertTrue(Validatable(2.seconds).isLowerThan(3.seconds).satisfied)
    }

    @Test
    fun __Duration_isLowerThan__fails_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(2.seconds).isLowerThan(1.seconds).satisfied,
            "The constraint is not satisfied when the value is greater than the provided one"
        )
        assertFalse(Validatable(2.seconds).isLowerThan(2.seconds).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isLowerThanOrEqualTo
    @Test
    fun __Duration_isLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isLowerThanOrEqualTo(3.seconds).satisfied)
    }

    @Test
    fun __Duration_isLowerThanOrEqualTo__succeeds_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(2.seconds).isLowerThanOrEqualTo(3.seconds).satisfied,
            "The constraint is satisfied when the value is lower than the provided one"
        )
        assertTrue(
            Validatable(2.seconds).isLowerThanOrEqualTo(2.seconds).satisfied,
            "The constraint is satisfied when the value is equal to the provided one"
        )
    }

    @Test
    fun __Duration_isLowerThanOrEqualTo__fails_when_the_value_is_greater_than_the_provided_one() {
        assertFalse(Validatable(2.seconds).isLowerThanOrEqualTo(1.seconds).satisfied)
    }
    //endregion

    //region isGreaterThan
    @Test
    fun __Duration_isGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isGreaterThan(3.seconds).satisfied)
    }

    @Test
    fun __Duration_isGreaterThan__succeeds_when_the_value_is_greater_than_the_provided_one() {
        assertTrue(Validatable(2.seconds).isGreaterThan(1.seconds).satisfied)
    }

    @Test
    fun __Duration_isGreaterThan__fails_when_the_value_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(2.seconds).isGreaterThan(3.seconds).satisfied,
            "The constraint is not satisfied when the value is lower than the provided one"
        )
        assertFalse(
            Validatable(2.seconds).isGreaterThan(2.seconds).satisfied,
            "The constraint is not satisfied when the value is equal to the provided one"
        )
    }
    //endregion

    //region isGreaterThanOrEqualTo
    @Test
    fun __Duration_isGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isGreaterThanOrEqualTo(3.seconds).satisfied)
    }

    @Test
    fun __Duration_isGreaterThanOrEqualTo__succeeds_when_the_value_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(2.seconds).isGreaterThanOrEqualTo(1.seconds).satisfied,
            "The constraint is satisfied when the value is greater than the provided one"
        )
        assertTrue(
            Validatable(2.seconds).isGreaterThanOrEqualTo(2.seconds).satisfied,
            "The constraint is satisfied when the value is equal to the provided one"
        )
    }

    @Test
    fun __Duration_isGreaterThanOrEqualTo__fails_when_the_value_is_lower_than_the_provided_one() {
        assertFalse(Validatable(2.seconds).isGreaterThanOrEqualTo(3.seconds).satisfied)
    }
    //endregion

    //region isBetween
    @Test
    fun __Duration_isBetween__succeeds_with_a_closed_range_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isBetween(3.seconds..9.seconds).satisfied)
    }

    @Test
    fun __Duration_isBetween__succeeds_with_a_closed_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2.seconds).isBetween(1.seconds..3.seconds).satisfied)
    }

    @Test
    fun __Duration_isBetween__fails_with_a_closed_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2.seconds).isBetween(3.seconds..5.seconds).satisfied)
    }

    @Test
    fun __Duration_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_null() {
        assertTrue(Validatable<Duration?>(null).isBetween(3.seconds..<9.seconds).satisfied)
    }

    @Test
    fun __Duration_isBetween__succeeds_with_an_open_ended_range_when_the_value_is_within_the_provided_range() {
        assertTrue(Validatable(2.seconds).isBetween(1.seconds..<3.seconds).satisfied)
    }

    @Test
    fun __Duration_isBetween__fails_with_an_open_ended_range_when_the_value_is_outside_the_provided_range() {
        assertFalse(Validatable(2.seconds).isBetween(1.seconds..<2.seconds).satisfied)
    }
    //endregion


}

