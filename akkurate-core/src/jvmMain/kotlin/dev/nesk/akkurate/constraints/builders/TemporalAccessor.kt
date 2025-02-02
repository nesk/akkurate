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
import java.time.*

/**
 * All generated temporal values depend on this clock. Its internal visibility allows to change it during testing.
 */
internal var clock = Clock.systemDefaultZone()

private val currentInstant get() = Instant.now(clock)
private val currentLocalDate get() = LocalDate.now(clock)
private val currentLocalDateTime get() = LocalDateTime.now(clock)
private val currentZonedDateTime get() = ZonedDateTime.now(clock)

//region isInPast
private const val pastMessage = "Must be in the past"

@JvmName("instantIsInPast")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentInstant } otherwise { pastMessage }

@JvmName("localDateIsInPast")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentLocalDate } otherwise { pastMessage }

@JvmName("localDateTimeIsInPast")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentLocalDateTime } otherwise { pastMessage }

@JvmName("zonedDateTimeIsInPast")
public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentZonedDateTime } otherwise { pastMessage }
//endregion

//region isInPastOrIsPresent
private const val pastOrPresentMessage = "Must be in the past or present"

@JvmName("instantIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentInstant } otherwise { pastOrPresentMessage }

@JvmName("localDateIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentLocalDate } otherwise { pastOrPresentMessage }

@JvmName("localDateTimeIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentLocalDateTime } otherwise { pastOrPresentMessage }

@JvmName("zonedDateTimeIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentZonedDateTime } otherwise { pastOrPresentMessage }
//endregion

//region isInFuture
private const val futureMessage = "Must be in the future"

@JvmName("instantFuture")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentInstant } otherwise { futureMessage }

@JvmName("localDateFuture")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentLocalDate } otherwise { futureMessage }

@JvmName("localDateTimeFuture")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentLocalDateTime } otherwise { futureMessage }

@JvmName("zonedDateTimeFuture")
public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentZonedDateTime } otherwise { futureMessage }
//endregion

//region isInFutureOrIsPresent
private const val futureOrPresentMessage = "Must be in the future or present"

@JvmName("instantIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentInstant } otherwise { futureOrPresentMessage }

@JvmName("localDateIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentLocalDate } otherwise { futureOrPresentMessage }

@JvmName("localDateTimeIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentLocalDateTime } otherwise { futureOrPresentMessage }

@JvmName("zonedDateTimeIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentZonedDateTime } otherwise { futureOrPresentMessage }
//endregion

//region isBefore
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseBefore(other: Any) = otherwise { "Must be before \"$other\"" }

public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isBefore(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isBefore(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isBefore(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isBefore(other: ZonedDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other
//endregion

//region isBeforeOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseBeforeOrEqual(other: Any) = otherwise { "Must be before or equal to \"$other\"" }

public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isBeforeOrEqualTo(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isBeforeOrEqualTo(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isBeforeOrEqualTo(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isBeforeOrEqualTo(other: ZonedDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other
//endregion

//region isAfter
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseAfter(other: Any) = otherwise { "Must be after \"$other\"" }

public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isAfter(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isAfter(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isAfter(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isAfter(other: ZonedDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other
//endregion

//region isAfterOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseAfterOrEqual(other: Any) = otherwise { "Must be after or equal to \"$other\"" }

public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isAfterOrEqualTo(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isAfterOrEqualTo(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isAfterOrEqualTo(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun <MetadataType> GenericValidatable<ZonedDateTime?, MetadataType>.isAfterOrEqualTo(other: ZonedDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other
//endregion

//region isNegative
private const val negativeMessage = "Must be negative"

@JvmName("durationIsNegative")
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNegative && it != Duration.ZERO } otherwise { negativeMessage }

@JvmName("periodIsNegative")
public fun <MetadataType> GenericValidatable<Period?, MetadataType>.isNegative(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNegative && it != Period.ZERO } otherwise { negativeMessage }
//endregion

//region isNegativeOrZero
private const val negativeMessageOrZero = "Must be negative or equal to zero"

@JvmName("durationIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNegative || it == Duration.ZERO } otherwise { negativeMessageOrZero }

@JvmName("periodIsNegativeOrZero")
public fun <MetadataType> GenericValidatable<Period?, MetadataType>.isNegativeOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNegative || it == Period.ZERO } otherwise { negativeMessageOrZero }
//endregion

//region isPositive
private const val positiveMessage = "Must be positive"

@JvmName("durationIsPositive")
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNegative && it != Duration.ZERO } otherwise { positiveMessage }

@JvmName("periodIsPositive")
public fun <MetadataType> GenericValidatable<Period?, MetadataType>.isPositive(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNegative && it != Period.ZERO } otherwise { positiveMessage }
//endregion

//region isPositiveOrZero
private const val positiveMessageOrZero = "Must be positive or equal to zero"

@JvmName("durationIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNegative || it == Duration.ZERO } otherwise { positiveMessageOrZero }

@JvmName("periodIsPositiveOrZero")
public fun <MetadataType> GenericValidatable<Period?, MetadataType>.isPositiveOrZero(): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.isNegative || it == Period.ZERO } otherwise { positiveMessageOrZero }
//endregion

//region isLowerThan
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isLowerThan(value: Duration): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < value } otherwise { "Must be lower than $value" }
//endregion

//region isLowerThanOrEqualTo
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isLowerThanOrEqualTo(value: Duration): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= value } otherwise { "Must be lower than or equal to $value" }
//endregion

//region isGreaterThan
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isGreaterThan(value: Duration): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > value } otherwise { "Must be greater than $value" }
//endregion

//region isGreaterThanOrEqualTo
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isGreaterThanOrEqualTo(value: Duration): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= value } otherwise { "Must be greater than or equal to $value" }
//endregion

//region isBetween
public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isBetween(range: ClosedRange<Duration>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

public fun <MetadataType> GenericValidatable<Duration?, MetadataType>.isBetween(range: OpenEndRange<Duration>): GenericConstraint<MetadataType> =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion

