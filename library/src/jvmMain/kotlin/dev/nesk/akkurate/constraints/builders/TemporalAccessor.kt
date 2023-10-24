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
public fun Validatable<Instant?>.isInPast(): Constraint =
    constrainIfNotNull { it < currentInstant } otherwise { pastMessage }

@JvmName("localDateIsInPast")
public fun Validatable<LocalDate?>.isInPast(): Constraint =
    constrainIfNotNull { it < currentLocalDate } otherwise { pastMessage }

@JvmName("localDateTimeIsInPast")
public fun Validatable<LocalDateTime?>.isInPast(): Constraint =
    constrainIfNotNull { it < currentLocalDateTime } otherwise { pastMessage }

@JvmName("zonedDateTimeIsInPast")
public fun Validatable<ZonedDateTime?>.isInPast(): Constraint =
    constrainIfNotNull { it < currentZonedDateTime } otherwise { pastMessage }
//endregion

//region isInPastOrIsPresent
private const val pastOrPresentMessage = "Must be in the past or present"

@JvmName("instantIsInPastOrIsPresent")
public fun Validatable<Instant?>.isInPastOrIsPresent(): Constraint =
    constrainIfNotNull { it <= currentInstant } otherwise { pastOrPresentMessage }

@JvmName("localDateIsInPastOrIsPresent")
public fun Validatable<LocalDate?>.isInPastOrIsPresent(): Constraint =
    constrainIfNotNull { it <= currentLocalDate } otherwise { pastOrPresentMessage }

@JvmName("localDateTimeIsInPastOrIsPresent")
public fun Validatable<LocalDateTime?>.isInPastOrIsPresent(): Constraint =
    constrainIfNotNull { it <= currentLocalDateTime } otherwise { pastOrPresentMessage }

@JvmName("zonedDateTimeIsInPastOrIsPresent")
public fun Validatable<ZonedDateTime?>.isInPastOrIsPresent(): Constraint =
    constrainIfNotNull { it <= currentZonedDateTime } otherwise { pastOrPresentMessage }
//endregion

//region isInFuture
private const val futureMessage = "Must be in the future"

@JvmName("instantFuture")
public fun Validatable<Instant?>.isInFuture(): Constraint =
    constrainIfNotNull { it > currentInstant } otherwise { futureMessage }

@JvmName("localDateFuture")
public fun Validatable<LocalDate?>.isInFuture(): Constraint =
    constrainIfNotNull { it > currentLocalDate } otherwise { futureMessage }

@JvmName("localDateTimeFuture")
public fun Validatable<LocalDateTime?>.isInFuture(): Constraint =
    constrainIfNotNull { it > currentLocalDateTime } otherwise { futureMessage }

@JvmName("zonedDateTimeFuture")
public fun Validatable<ZonedDateTime?>.isInFuture(): Constraint =
    constrainIfNotNull { it > currentZonedDateTime } otherwise { futureMessage }
//endregion

//region isInFutureOrIsPresent
private const val futureOrPresentMessage = "Must be in the future or present"

@JvmName("instantIsInFutureOrIsPresent")
public fun Validatable<Instant?>.isInFutureOrIsPresent(): Constraint =
    constrainIfNotNull { it >= currentInstant } otherwise { futureOrPresentMessage }

@JvmName("localDateIsInFutureOrIsPresent")
public fun Validatable<LocalDate?>.isInFutureOrIsPresent(): Constraint =
    constrainIfNotNull { it >= currentLocalDate } otherwise { futureOrPresentMessage }

@JvmName("localDateTimeIsInFutureOrIsPresent")
public fun Validatable<LocalDateTime?>.isInFutureOrIsPresent(): Constraint =
    constrainIfNotNull { it >= currentLocalDateTime } otherwise { futureOrPresentMessage }

@JvmName("zonedDateTimeIsInFutureOrIsPresent")
public fun Validatable<ZonedDateTime?>.isInFutureOrIsPresent(): Constraint =
    constrainIfNotNull { it >= currentZonedDateTime } otherwise { futureOrPresentMessage }
//endregion

//region isBefore
private infix fun Constraint.otherwiseBefore(other: Any) = otherwise { "Must be before \"$other\"" }

public fun Validatable<Instant?>.isBefore(other: Instant): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<LocalDate?>.isBefore(other: LocalDate): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<LocalDateTime?>.isBefore(other: LocalDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<ZonedDateTime?>.isBefore(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other
//endregion

//region isBeforeOrEqualTo
private infix fun Constraint.otherwiseBeforeOrEqual(other: Any) = otherwise { "Must be before or equal to \"$other\"" }

public fun Validatable<Instant?>.isBeforeOrEqualTo(other: Instant): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun Validatable<LocalDate?>.isBeforeOrEqualTo(other: LocalDate): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun Validatable<LocalDateTime?>.isBeforeOrEqualTo(other: LocalDateTime): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun Validatable<ZonedDateTime?>.isBeforeOrEqualTo(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other
//endregion

//region isAfter
private infix fun Constraint.otherwiseAfter(other: Any) = otherwise { "Must be after \"$other\"" }

public fun Validatable<Instant?>.isAfter(other: Instant): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<LocalDate?>.isAfter(other: LocalDate): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<LocalDateTime?>.isAfter(other: LocalDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<ZonedDateTime?>.isAfter(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other
//endregion

//region isAfterOrEqualTo
private infix fun Constraint.otherwiseAfterOrEqual(other: Any) = otherwise { "Must be after or equal to \"$other\"" }

public fun Validatable<Instant?>.isAfterOrEqualTo(other: Instant): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<LocalDate?>.isAfterOrEqualTo(other: LocalDate): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<LocalDateTime?>.isAfterOrEqualTo(other: LocalDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<ZonedDateTime?>.isAfterOrEqualTo(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other
//endregion

//region isNegative
private const val negativeMessage = "Must be negative"

@JvmName("durationIsNegative")
public fun Validatable<Duration?>.isNegative(): Constraint =
    constrainIfNotNull { it.isNegative && it != Duration.ZERO } otherwise { negativeMessage }

@JvmName("periodIsNegative")
public fun Validatable<Period?>.isNegative(): Constraint =
    constrainIfNotNull { it.isNegative && it != Period.ZERO } otherwise { negativeMessage }
//endregion

//region isNegativeOrZero
private const val negativeMessageOrZero = "Must be negative"

@JvmName("durationIsNegativeOrZero")
public fun Validatable<Duration?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it.isNegative || it == Duration.ZERO } otherwise { negativeMessageOrZero }

@JvmName("periodIsNegativeOrZero")
public fun Validatable<Period?>.isNegativeOrZero(): Constraint =
    constrainIfNotNull { it.isNegative || it == Period.ZERO } otherwise { negativeMessageOrZero }
//endregion

//region isPositive
private const val positiveMessage = "Must be positive"

@JvmName("durationIsPositive")
public fun Validatable<Duration?>.isPositive(): Constraint =
    constrainIfNotNull { !it.isNegative && it != Duration.ZERO } otherwise { positiveMessage }

@JvmName("periodIsPositive")
public fun Validatable<Period?>.isPositive(): Constraint =
    constrainIfNotNull { !it.isNegative && it != Period.ZERO } otherwise { positiveMessage }
//endregion

//region isPositiveOrZero
private const val positiveMessageOrZero = "Must be positive"

@JvmName("durationIsPositiveOrZero")
public fun Validatable<Duration?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { !it.isNegative || it == Duration.ZERO } otherwise { positiveMessageOrZero }

@JvmName("periodIsPositiveOrZero")
public fun Validatable<Period?>.isPositiveOrZero(): Constraint =
    constrainIfNotNull { !it.isNegative || it == Period.ZERO } otherwise { positiveMessageOrZero }
//endregion

//region isLowerThan
public fun Validatable<Duration?>.isLowerThan(value: Duration): Constraint =
    constrainIfNotNull { it < value } otherwise { "Must be lower than $value" }
//endregion

//region isLowerThanOrEqualTo
public fun Validatable<Duration?>.isLowerThanOrEqualTo(value: Duration): Constraint =
    constrainIfNotNull { it <= value } otherwise { "Must be lower than or equal to $value" }
//endregion

//region isGreaterThan
public fun Validatable<Duration?>.isGreaterThan(value: Duration): Constraint =
    constrainIfNotNull { it > value } otherwise { "Must be greater than $value" }
//endregion

//region isGreaterThanOrEqualTo
public fun Validatable<Duration?>.isGreaterThanOrEqualTo(value: Duration): Constraint =
    constrainIfNotNull { it >= value } otherwise { "Must be greater than or equal to $value" }
//endregion

//region isBetween
public fun Validatable<Duration?>.isBetween(range: ClosedRange<Duration>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endInclusive} (inclusive)" }

public fun Validatable<Duration?>.isBetween(range: OpenEndRange<Duration>): Constraint =
    constrainIfNotNull { it in range } otherwise { "Must be between ${range.start} and ${range.endExclusive} (exclusive)" }
//endregion

