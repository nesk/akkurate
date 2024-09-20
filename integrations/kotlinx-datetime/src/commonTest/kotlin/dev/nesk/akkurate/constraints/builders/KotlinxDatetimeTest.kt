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
import kotlinx.datetime.*
import kotlin.test.*
import kotlin.time.Duration.Companion.seconds

class KotlinxDatetimeTest {
    companion object {
        private val fewSeconds = 5.seconds
        private val fewDays = DatePeriod(days = 2)
    }

    private val fixedClock = object : Clock {
        override fun now(): Instant = LocalDateTime(2024, Month.AUGUST, 31, 12, 30, 15).toInstant(TimeZone.UTC)
    }
    private val presentInstant = fixedClock.now()
    private val presentLocalDateTime = presentInstant.toLocalDateTime(TimeZone.UTC)
    private val presentLocalDate = presentLocalDateTime.date
    private val presentLocalTime = presentLocalDateTime.time

    private lateinit var originalClock: Clock
    private lateinit var originalTimezone: TimeZone

    @BeforeTest
    fun before() {
        originalClock = clock
        clock = fixedClock
        originalTimezone = timezone
        timezone = TimeZone.UTC
    }

    @AfterTest
    fun after() {
        clock = originalClock
        timezone = originalTimezone
    }

    //region isInPast
    @Test
    fun __Instant_isInPast__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isInPast().satisfied)
    }

    @Test
    fun __Instant_isInPast__succeeds_when_the_value_is_in_the_past() {
        assertTrue(Validatable(presentInstant - fewSeconds).isInPast().satisfied)
    }

    @Test
    fun __Instant_isInPast__fails_when_the_value_is_in_the_future_or_present() {
        assertFalse(Validatable(presentInstant + fewSeconds).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentInstant).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalDate_isInPast__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isInPast().satisfied)
    }

    @Test
    fun __LocalDate_isInPast__succeeds_when_the_value_is_in_the_past() {
        assertTrue(Validatable(presentLocalDate - fewDays).isInPast().satisfied)
    }

    @Test
    fun __LocalDate_isInPast__fails_when_the_value_is_in_the_future_or_present() {
        assertFalse(Validatable(presentLocalDate + fewDays).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentLocalDate).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalTime_isInPast__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isInPast().satisfied)
    }

    @Test
    fun __LocalTime_isInPast__succeeds_when_the_value_is_in_the_past() {
        assertTrue(Validatable(presentLocalTime.minusFewSeconds()).isInPast().satisfied)
    }

    @Test
    fun __LocalTime_isInPast__fails_when_the_value_is_in_the_future_or_present() {
        assertFalse(Validatable(presentLocalTime.plusFewSeconds()).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentLocalTime).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalDateTime_isInPast__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isInPast().satisfied)
    }

    @Test
    fun __LocalDateTime_isInPast__succeeds_when_the_value_is_in_the_past() {
        assertTrue(Validatable(presentLocalDateTime.minusFewSeconds()).isInPast().satisfied)
    }

    @Test
    fun __LocalDateTime_isInPast__fails_when_the_value_is_in_the_future_or_present() {
        assertFalse(Validatable(presentLocalDateTime.plusFewSeconds()).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentLocalDateTime).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region isInPastOrIsPresent
    @Test
    fun __Instant_isInPastOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __Instant_isInPastOrIsPresent__succeeds_when_the_value_is_in_the_past_or_present() {
        assertTrue(Validatable(presentInstant - fewSeconds).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __Instant_isInPastOrIsPresent__fails_when_the_value_is_in_the_future() {
        assertFalse(Validatable(presentInstant + fewSeconds).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDate_isInPastOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDate_isInPastOrIsPresent__succeeds_when_the_value_is_in_the_past_or_present() {
        assertTrue(Validatable(presentLocalDate - fewDays).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDate).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalDate_isInPastOrIsPresent__fails_when_the_value_is_in_the_future() {
        assertFalse(Validatable(presentLocalDate + fewDays).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalTime_isInPastOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalTime_isInPastOrIsPresent__succeeds_when_the_value_is_in_the_past_or_present() {
        assertTrue(Validatable(presentLocalTime.minusFewSeconds()).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalTime).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalTime_isInPastOrIsPresent__fails_when_the_value_is_in_the_future() {
        assertFalse(Validatable(presentLocalTime.plusFewSeconds()).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDateTime_isInPastOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDateTime_isInPastOrIsPresent__succeeds_when_the_value_is_in_the_past_or_present() {
        assertTrue(Validatable(presentLocalDateTime.minusFewSeconds()).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalDateTime_isInPastOrIsPresent__fails_when_the_value_is_in_the_future() {
        assertFalse(Validatable(presentLocalDateTime.plusFewSeconds()).isInPastOrIsPresent().satisfied)
    }
    //endregion

    //region isInFuture
    @Test
    fun __Instant_isInFuture__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isInFuture().satisfied)
    }

    @Test
    fun __Instant_isInFuture__succeeds_when_the_value_is_in_the_future() {
        assertTrue(Validatable(presentInstant + fewSeconds).isInFuture().satisfied)
    }

    @Test
    fun __Instant_isInFuture__fails_when_the_value_is_in_the_past_or_present() {
        assertFalse(Validatable(presentInstant - fewSeconds).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentInstant).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalDate_isInFuture__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isInFuture().satisfied)
    }

    @Test
    fun __LocalDate_isInFuture__succeeds_when_the_value_is_in_the_future() {
        assertTrue(Validatable(presentLocalDate + fewDays).isInFuture().satisfied)
    }

    @Test
    fun __LocalDate_isInFuture__fails_when_the_value_is_in_the_past_or_present() {
        assertFalse(Validatable(presentLocalDate - fewDays).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentLocalDate).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalTime_isInFuture__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isInFuture().satisfied)
    }

    @Test
    fun __LocalTime_isInFuture__succeeds_when_the_value_is_in_the_future() {
        assertTrue(Validatable(presentLocalTime.plusFewSeconds()).isInFuture().satisfied)
    }

    @Test
    fun __LocalTime_isInFuture__fails_when_the_value_is_in_the_past_or_present() {
        assertFalse(Validatable(presentLocalTime.minusFewSeconds()).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentLocalTime).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun __LocalDateTime_isInFuture__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isInFuture().satisfied)
    }

    @Test
    fun __LocalDateTime_isInFuture__succeeds_when_the_value_is_in_the_future() {
        assertTrue(Validatable(presentLocalDateTime.plusFewSeconds()).isInFuture().satisfied)
    }

    @Test
    fun __LocalDateTime_isInFuture__fails_when_the_value_is_in_the_past_or_present() {
        assertFalse(Validatable(presentLocalDateTime.minusFewSeconds()).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentLocalDateTime).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region isInFutureOrIsPresent
    @Test
    fun __Instant_isInFutureOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __Instant_isInFutureOrIsPresent__succeeds_when_the_value_is_in_the_future_or_present() {
        assertTrue(Validatable(presentInstant + fewSeconds).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __Instant_isInFutureOrIsPresent__fails_when_the_value_is_in_the_past() {
        assertFalse(Validatable(presentInstant - fewSeconds).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDate_isInFutureOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDate_isInFutureOrIsPresent__succeeds_when_the_value_is_in_the_future_or_present() {
        assertTrue(Validatable(presentLocalDate + fewDays).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDate).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalDate_isInFutureOrIsPresent__fails_when_the_value_is_in_the_past() {
        assertFalse(Validatable(presentLocalDate - fewDays).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalTime_isInFutureOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalTime_isInFutureOrIsPresent__succeeds_when_the_value_is_in_the_future_or_present() {
        assertTrue(Validatable(presentLocalTime.plusFewSeconds()).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalTime).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalTime_isInFutureOrIsPresent__fails_when_the_value_is_in_the_past() {
        assertFalse(Validatable(presentLocalTime.minusFewSeconds()).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDateTime_isInFutureOrIsPresent__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun __LocalDateTime_isInFutureOrIsPresent__succeeds_when_the_value_is_in_the_future_or_present() {
        assertTrue(Validatable(presentLocalDateTime.plusFewSeconds()).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun __LocalDateTime_isInFutureOrIsPresent__fails_when_the_value_is_in_the_past() {
        assertFalse(Validatable(presentLocalDateTime.minusFewSeconds()).isInFutureOrIsPresent().satisfied)
    }
    //endregion

    //region isBefore
    @Test
    fun __Instant_isBefore__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isBefore(presentInstant).satisfied)
    }

    @Test
    fun __Instant_isBefore__succeeds_when_the_value_is_before_the_provided_one() {
        assertTrue(Validatable(presentInstant).isBefore(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun __Instant_isBefore__fails_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertFalse(Validatable(presentInstant).isBefore(presentInstant - fewSeconds).satisfied, "The constraint is not satisfied when the value is after the provided one")
        assertFalse(Validatable(presentInstant).isBefore(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDate_isBefore__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isBefore(presentLocalDate).satisfied)
    }

    @Test
    fun __LocalDate_isBefore__succeeds_when_the_value_is_before_the_provided_one() {
        assertTrue(Validatable(presentLocalDate).isBefore(presentLocalDate + fewDays).satisfied)
    }

    @Test
    fun __LocalDate_isBefore__fails_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalDate).isBefore(presentLocalDate - fewDays).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentLocalDate).isBefore(presentLocalDate).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalTime_isBefore__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isBefore(presentLocalTime).satisfied)
    }

    @Test
    fun __LocalTime_isBefore__succeeds_when_the_value_is_before_the_provided_one() {
        assertTrue(Validatable(presentLocalTime).isBefore(presentLocalTime.plusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalTime_isBefore__fails_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalTime).isBefore(presentLocalTime.minusFewSeconds()).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentLocalTime).isBefore(presentLocalTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDateTime_isBefore__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isBefore(presentLocalDateTime).satisfied)
    }

    @Test
    fun __LocalDateTime_isBefore__succeeds_when_the_value_is_before_the_provided_one() {
        assertTrue(Validatable(presentLocalDateTime).isBefore(presentLocalDateTime.plusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalDateTime_isBefore__fails_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalDateTime).isBefore(presentLocalDateTime.minusFewSeconds()).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).isBefore(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isBeforeOrEqualTo
    @Test
    fun __Instant_isBeforeOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isBeforeOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun __Instant_isBeforeOrEqualTo__succeeds_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertTrue(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant + fewSeconds).satisfied, "The constraint is satisfied when the value is before the provided one")
        assertTrue(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Instant_isBeforeOrEqualTo__fails_when_the_value_is_after_the_provided_one() {
        assertFalse(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun __LocalDate_isBeforeOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isBeforeOrEqualTo(presentLocalDate).satisfied)
    }

    @Test
    fun __LocalDate_isBeforeOrEqualTo__succeeds_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalDate).isBeforeOrEqualTo(presentLocalDate + fewDays).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentLocalDate).isBeforeOrEqualTo(presentLocalDate).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDate_isBeforeOrEqualTo__fails_when_the_value_is_after_the_provided_one() {
        assertFalse(Validatable(presentLocalDate).isBeforeOrEqualTo(presentLocalDate - fewDays).satisfied)
    }

    @Test
    fun __LocalTime_isBeforeOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isBeforeOrEqualTo(presentLocalTime).satisfied)
    }

    @Test
    fun __LocalTime_isBeforeOrEqualTo__succeeds_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalTime).isBeforeOrEqualTo(presentLocalTime.plusFewSeconds()).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentLocalTime).isBeforeOrEqualTo(presentLocalTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalTime_isBeforeOrEqualTo__fails_when_the_value_is_after_the_provided_one() {
        assertFalse(Validatable(presentLocalTime).isBeforeOrEqualTo(presentLocalTime.minusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalDateTime_isBeforeOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isBeforeOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun __LocalDateTime_isBeforeOrEqualTo__succeeds_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime.plusFewSeconds()).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDateTime_isBeforeOrEqualTo__fails_when_the_value_is_after_the_provided_one() {
        assertFalse(Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime.minusFewSeconds()).satisfied)
    }
    //endregion

    //region isAfter
    @Test
    fun __Instant_isAfter__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isAfter(presentInstant).satisfied)
    }

    @Test
    fun __Instant_isAfter__succeeds_when_the_value_is_after_the_provided_one() {
        assertTrue(Validatable(presentInstant).isAfter(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun __Instant_isAfter__fails_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertFalse(Validatable(presentInstant).isAfter(presentInstant + fewSeconds).satisfied, "The constraint is not satisfied when the value is before the provided one")
        assertFalse(Validatable(presentInstant).isAfter(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDate_isAfter__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isAfter(presentLocalDate).satisfied)
    }

    @Test
    fun __LocalDate_isAfter__succeeds_when_the_value_is_after_the_provided_one() {
        assertTrue(Validatable(presentLocalDate).isAfter(presentLocalDate - fewDays).satisfied)
    }

    @Test
    fun __LocalDate_isAfter__fails_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalDate).isAfter(presentLocalDate + fewDays).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentLocalDate).isAfter(presentLocalDate).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalTime_isAfter__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isAfter(presentLocalTime).satisfied)
    }

    @Test
    fun __LocalTime_isAfter__succeeds_when_the_value_is_after_the_provided_one() {
        assertTrue(Validatable(presentLocalTime).isAfter(presentLocalTime.minusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalTime_isAfter__fails_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalTime).isAfter(presentLocalTime.plusFewSeconds()).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentLocalTime).isAfter(presentLocalTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDateTime_isAfter__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isAfter(presentLocalDateTime).satisfied)
    }

    @Test
    fun __LocalDateTime_isAfter__succeeds_when_the_value_is_after_the_provided_one() {
        assertTrue(Validatable(presentLocalDateTime).isAfter(presentLocalDateTime.minusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalDateTime_isAfter__fails_when_the_value_is_before_or_equal_to_the_provided_one() {
        assertFalse(
            Validatable(presentLocalDateTime).isAfter(presentLocalDateTime.plusFewSeconds()).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).isAfter(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isAfterOrEqualTo
    @Test
    fun __Instant_isAfterOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<Instant?>(null).isAfterOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun __Instant_isAfterOrEqualTo__succeeds_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertTrue(Validatable(presentInstant).isAfterOrEqualTo(presentInstant - fewSeconds).satisfied, "The constraint is satisfied when the value is after the provided one")
        assertTrue(Validatable(presentInstant).isAfterOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __Instant_isAfterOrEqualTo__fails_when_the_value_is_before_the_provided_one() {
        assertFalse(Validatable(presentInstant).isAfterOrEqualTo(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun __LocalDate_isAfterOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDate?>(null).isAfterOrEqualTo(presentLocalDate).satisfied)
    }

    @Test
    fun __LocalDate_isAfterOrEqualTo__succeeds_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalDate).isAfterOrEqualTo(presentLocalDate - fewDays).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentLocalDate).isAfterOrEqualTo(presentLocalDate).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDate_isAfterOrEqualTo__fails_when_the_value_is_before_the_provided_one() {
        assertFalse(Validatable(presentLocalDate).isAfterOrEqualTo(presentLocalDate + fewDays).satisfied)
    }

    @Test
    fun __LocalTime_isAfterOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalTime?>(null).isAfterOrEqualTo(presentLocalTime).satisfied)
    }

    @Test
    fun __LocalTime_isAfterOrEqualTo__succeeds_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalTime).isAfterOrEqualTo(presentLocalTime.minusFewSeconds()).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentLocalTime).isAfterOrEqualTo(presentLocalTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalTime_isAfterOrEqualTo__fails_when_the_value_is_before_the_provided_one() {
        assertFalse(Validatable(presentLocalTime).isAfterOrEqualTo(presentLocalTime.plusFewSeconds()).satisfied)
    }

    @Test
    fun __LocalDateTime_isAfterOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<LocalDateTime?>(null).isAfterOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun __LocalDateTime_isAfterOrEqualTo__succeeds_when_the_value_is_after_or_equal_to_the_provided_one() {
        assertTrue(
            Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime.minusFewSeconds()).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun __LocalDateTime_isAfterOrEqualTo__fails_when_the_value_is_before_the_provided_one() {
        assertFalse(Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime.plusFewSeconds()).satisfied)
    }
    //endregion

    private fun LocalTime.minusFewSeconds() =
        LocalTime.fromNanosecondOfDay(toNanosecondOfDay() - fewSeconds.inWholeNanoseconds)

    private fun LocalTime.plusFewSeconds() =
        LocalTime.fromNanosecondOfDay(toNanosecondOfDay() + fewSeconds.inWholeNanoseconds)

    private fun LocalDateTime.minusFewSeconds() = (toInstant(timezone) - fewSeconds).toLocalDateTime(timezone)

    private fun LocalDateTime.plusFewSeconds() = (toInstant(timezone) + fewSeconds).toLocalDateTime(timezone)
}
