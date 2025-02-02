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
import dev.nesk.akkurate.validatables.Validatable
import kotlinx.datetime.*
import kotlin.jvm.JvmName

/**
 * All generated temporal values depend on this clock. Its internal visibility allows it to change during testing.
 */
internal var clock: Clock = Clock.System
internal var timezone = TimeZone.currentSystemDefault()

private val currentInstant get() = clock.now()
private val currentLocalDateTime get() = currentInstant.toLocalDateTime(timezone)
private val currentLocalDate get() = currentLocalDateTime.date
private val currentLocalTime get() = currentLocalDateTime.time

//region isInPast
private const val pastMessage = "Must be in the past"

/**
 * The validatable [Instant] must be in the past when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System`. Use [isBefore] to compare against an arbitrary [Instant].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Instant> { isInPast() }
 * val now = Clock.System.now()
 * validate(now - 5.seconds) // Success
 * validate(now + 5.seconds) // Failure (message: Must be in the past)
 * ```
 */
@JvmName("instantIsInPast")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentInstant } otherwise { pastMessage }

/**
 * The validatable [LocalDate] must be in the past when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBefore] to compare against an arbitrary [LocalDate].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDate> { isInPast() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
 * validate(now.minus(2, DateTimeUnit.DAY)) // Success
 * validate(now.plus(2, DateTimeUnit.DAY)) // Failure (message: Must be in the past)
 * ```
 */
@JvmName("localDateIsInPast")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentLocalDate } otherwise { pastMessage }

/**
 * The validatable [LocalTime] must be in the past when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBefore] to compare against an arbitrary [LocalTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalTime> { isInPast() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Failure (message: Must be in the past)
 * ```
 */
@JvmName("localTimeIsInPast")
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentLocalTime } otherwise { pastMessage }

/**
 * The validatable [LocalDateTime] must be in the past when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBefore] to compare against an arbitrary [LocalDateTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDateTime> { isInPast() }
 * val timezone = TimeZone.currentSystemDefault()
 * val now = Clock.System.now().toLocalDateTime(timezone)
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Success
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be in the past)
 * ```
 */
@JvmName("localDateTimeIsInPast")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInPast(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < currentLocalDateTime } otherwise { pastMessage }
//endregion

//region isInPastOrIsPresent
private const val pastOrPresentMessage = "Must be in the past or present"

/**
 * The validatable [Instant] must be in the past or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System`. Use [isBeforeOrEqualTo] to compare against an arbitrary [Instant].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Instant> { isInPastOrIsPresent() }
 * val now = Clock.System.now()
 * validate(now - 5.seconds) // Success
 * validate(now + 5.seconds) // Failure (message: Must be in the past or present)
 * ```
 */
@JvmName("instantIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentInstant } otherwise { pastOrPresentMessage }

/**
 * The validatable [LocalDate] must be in the past or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBeforeOrEqualTo] to compare against an arbitrary [LocalDate].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDate> { isInPastOrIsPresent() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
 * validate(now.minus(2, DateTimeUnit.DAY)) // Success
 * validate(now.plus(2, DateTimeUnit.DAY)) // Failure (message: Must be in the past or present)
 * ```
 */
@JvmName("localDateIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentLocalDate } otherwise { pastOrPresentMessage }

/**
 * The validatable [LocalTime] must be in the past or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBeforeOrEqualTo] to compare against an arbitrary [LocalTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalTime> { isInPastOrIsPresent() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Failure (message: Must be in the past or present)
 * ```
 */
@JvmName("localTimeIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentLocalTime } otherwise { pastOrPresentMessage }

/**
 * The validatable [LocalDateTime] must be in the past or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isBeforeOrEqualTo] to compare against an arbitrary [LocalDateTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDateTime> { isInPastOrIsPresent() }
 * val timezone = TimeZone.currentSystemDefault()
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Success
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be in the past or present)
 * ```
 */
@JvmName("localDateTimeIsInPastOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInPastOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= currentLocalDateTime } otherwise { pastOrPresentMessage }
//endregion

//region isInFuture
private const val futureMessage = "Must be in the future"

/**
 * The validatable [Instant] must be in the future when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System`. Use [isAfter] to compare against an arbitrary [Instant].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Instant> { isInFuture() }
 * val now = Clock.System.now()
 * validate(now + 5.seconds) // Success
 * validate(now - 5.seconds) // Failure (message: Must be in the future)
 * ```
 */
@JvmName("instantFuture")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentInstant } otherwise { futureMessage }

/**
 * The validatable [LocalDate] must be in the future when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfter] to compare against an arbitrary [LocalDate].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDate> { isInFuture() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
 * validate(now.plus(2, DateTimeUnit.DAY)) // Success
 * validate(now.minus(2, DateTimeUnit.DAY)) // Failure (message: Must be in the future)
 * ```
 */
@JvmName("localDateFuture")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentLocalDate } otherwise { futureMessage }

/**
 * The validatable [LocalTime] must be in the future when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfter] to compare against an arbitrary [LocalTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalTime> { isInFuture() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Failure (message: Must be in the future)
 * ```
 */
@JvmName("localTimeFuture")
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentLocalTime } otherwise { futureMessage }

/**
 * The validatable [LocalDateTime] must be in the future when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfter] to compare against an arbitrary [LocalDateTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDateTime> { isInFuture() }
 * val timezone = TimeZone.currentSystemDefault()
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Success
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be in the future)
 * ```
 */
@JvmName("localDateTimeFuture")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInFuture(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > currentLocalDateTime } otherwise { futureMessage }
//endregion

//region isInFutureOrIsPresent
private const val futureOrPresentMessage = "Must be in the future or present"

/**
 * The validatable [Instant] must be in the future or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System`. Use [isAfterOrEqualTo] to compare against an arbitrary [Instant].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<Instant> { isInFutureOrIsPresent() }
 * val now = Clock.System.now()
 * validate(now + 5.seconds) // Success
 * validate(now - 5.seconds) // Failure (message: Must be in the future or present)
 * ```
 */
@JvmName("instantIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentInstant } otherwise { futureOrPresentMessage }

/**
 * The validatable [LocalDate] must be in the future or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfterOrEqualTo] to compare against an arbitrary [LocalDate].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDate> { isInFutureOrIsPresent() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
 * validate(now.plus(2, DateTimeUnit.DAY)) // Success
 * validate(now.minus(2, DateTimeUnit.DAY)) // Failure (message: Must be in the future or present)
 * ```
 */
@JvmName("localDateIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentLocalDate } otherwise { futureOrPresentMessage }

/**
 * The validatable [LocalTime] must be in the future or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfterOrEqualTo] to compare against an arbitrary [LocalTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalTime> { isInFutureOrIsPresent() }
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Failure (message: Must be in the future or present)
 * ```
 */
@JvmName("localTimeIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentLocalTime } otherwise { futureOrPresentMessage }

/**
 * The validatable [LocalDateTime] must be in the future or present time when this constraint is applied.
 *
 * The comparison is made against the system clock `Clock.System` and time zone
 * `TimeZone.currentSystemDefault()`. Use [isAfterOrEqualTo] to compare against an arbitrary [LocalDateTime].
 *
 * Code example:
 *
 * ```
 * val validate = Validator<LocalDateTime> { isInFutureOrIsPresent() }
 * val timezone = TimeZone.currentSystemDefault()
 * val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Success
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be in the future or present)
 * ```
 */
@JvmName("localDateTimeIsInFutureOrIsPresent")
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isInFutureOrIsPresent(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= currentLocalDateTime } otherwise { futureOrPresentMessage }
//endregion

//region isBefore
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseBefore(other: Any) = otherwise { "Must be before \"$other\"" }

/**
 * The validatable [Instant] must be before [other] when this constraint is applied.
 *
 * Use [isInPast] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = Instant.parse("2024-08-31T12:30:15Z")
 * val validate = Validator<Instant> { isBefore(now) }
 * validate(now - 5.seconds) // Success
 * validate(now) // Failure (message: Must be before "2024-08-31T12:30:15Z")
 * validate(now + 5.seconds) // Failure (message: Must be before "2024-08-31T12:30:15Z")
 * ```
 */
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isBefore(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

/**
 * The validatable [LocalDate] must be before [other] when this constraint is applied.
 *
 * Use [isInPast] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDate.parse("2024-08-31")
 * val validate = Validator<LocalDate> { isBefore(now) }
 * validate(now.minus(2, DateTimeUnit.DAY)) // Success
 * validate(now) // Failure (message: Must be before "2024-08-31")
 * validate(now.plus(2, DateTimeUnit.DAY)) // Failure (message: Must be before "2024-08-31")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isBefore(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

/**
 * The validatable [LocalTime] must be before [other] when this constraint is applied.
 *
 * Use [isInPast] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalTime.parse("12:30:15")
 * val validate = Validator<LocalTime> { isBefore(now) }
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Success
 * validate(now) // Failure (message: Must be before "12:30:15")
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Failure (message: Must be before "12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isBefore(other: LocalTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other

/**
 * The validatable [LocalDateTime] must be before [other] when this constraint is applied.
 *
 * Use [isInPast] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDateTime.parse("2024-08-31T12:30:15")
 * val timezone = TimeZone.currentSystemDefault()
 * val validate = Validator<LocalDateTime> { isBefore(now) }
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Success
 * validate(now) // Failure (message: Must be before "2024-08-31T12:30:15")
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be before "2024-08-31T12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isBefore(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it < other } otherwiseBefore other
//endregion

//region isBeforeOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseBeforeOrEqual(other: Any) = otherwise { "Must be before or equal to \"$other\"" }

/**
 * The validatable [Instant] must be before or equal to [other] when this constraint is applied.
 *
 * Use [isInPastOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = Instant.parse("2024-08-31T12:30:15Z")
 * val validate = Validator<Instant> { isBeforeOrEqualTo(now) }
 * validate(now - 5.seconds) // Success
 * validate(now) // Success
 * validate(now + 5.seconds) // Failure (message: Must be before or equal to "2024-08-31T12:30:15Z")
 * ```
 */
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isBeforeOrEqualTo(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

/**
 * The validatable [LocalDate] must be before or equal to [other] when this constraint is applied.
 *
 * Use [isInPastOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDate.parse("2024-08-31")
 * val validate = Validator<LocalDate> { isBeforeOrEqualTo(now) }
 * validate(now.minus(2, DateTimeUnit.DAY)) // Success
 * validate(now) // Success
 * validate(now.plus(2, DateTimeUnit.DAY)) // Failure (message: Must be before or equal to "2024-08-31")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isBeforeOrEqualTo(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

/**
 * The validatable [LocalTime] must be before or equal to [other] when this constraint is applied.
 *
 * Use [isInPastOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalTime.parse("12:30:15")
 * val validate = Validator<LocalTime> { isBeforeOrEqualTo(now) }
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Success
 * validate(now) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Failure (message: Must be before or equal to "12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isBeforeOrEqualTo(other: LocalTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

/**
 * The validatable [LocalDateTime] must be before or equal to [other] when this constraint is applied.
 *
 * Use [isInPastOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDateTime.parse("2024-08-31T12:30:15")
 * val timezone = TimeZone.currentSystemDefault()
 * val validate = Validator<LocalDateTime> { isBeforeOrEqualTo(now) }
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Success
 * validate(now) // Success
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be before or equal to "2024-08-31T12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isBeforeOrEqualTo(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other
//endregion

//region isAfter
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseAfter(other: Any) = otherwise { "Must be after \"$other\"" }

/**
 * The validatable [Instant] must be after [other] when this constraint is applied.
 *
 * Use [isInFuture] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = Instant.parse("2024-08-31T12:30:15Z")
 * val validate = Validator<Instant> { isAfter(now) }
 * validate(now + 5.seconds) // Success
 * validate(now) // Failure (message: Must be after "2024-08-31T12:30:15Z")
 * validate(now - 5.seconds) // Failure (message: Must be after "2024-08-31T12:30:15Z")
 * ```
 */
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isAfter(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

/**
 * The validatable [LocalDate] must be after [other] when this constraint is applied.
 *
 * Use [isInFuture] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDate.parse("2024-08-31")
 * val validate = Validator<LocalDate> { isAfter(now) }
 * validate(now.plus(2, DateTimeUnit.DAY)) // Success
 * validate(now) // Failure (message: Must be after "2024-08-31")
 * validate(now.minus(2, DateTimeUnit.DAY)) // Failure (message: Must be after "2024-08-31")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isAfter(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

/**
 * The validatable [LocalTime] must be after [other] when this constraint is applied.
 *
 * Use [isInFuture] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalTime.parse("12:30:15")
 * val validate = Validator<LocalTime> { isAfter(now) }
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Success
 * validate(now) // Failure (message: Must be after "12:30:15")
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Failure (message: Must be after "12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isAfter(other: LocalTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other

/**
 * The validatable [LocalDateTime] must be after [other] when this constraint is applied.
 *
 * Use [isInFuture] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDateTime.parse("2024-08-31T12:30:15")
 * val timezone = TimeZone.currentSystemDefault()
 * val validate = Validator<LocalDateTime> { isAfter(now) }
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Success
 * validate(now) // Failure (message: Must be after "2024-08-31T12:30:15")
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be after "2024-08-31T12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isAfter(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it > other } otherwiseAfter other
//endregion

//region isAfterOrEqualTo
private infix fun <MetadataType> GenericConstraint<MetadataType>.otherwiseAfterOrEqual(other: Any) = otherwise { "Must be after or equal to \"$other\"" }

/**
 * The validatable [Instant] must be after or equal to [other] when this constraint is applied.
 *
 * Use [isInFutureOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = Instant.parse("2024-08-31T12:30:15Z")
 * val validate = Validator<Instant> { isAfterOrEqualTo(now) }
 * validate(now + 5.seconds) // Success
 * validate(now) // Success
 * validate(now - 5.seconds) // Failure (message: Must be after or equal to "2024-08-31T12:30:15Z")
 * ```
 */
public fun <MetadataType> GenericValidatable<Instant?, MetadataType>.isAfterOrEqualTo(other: Instant): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

/**
 * The validatable [LocalDate] must be after or equal to [other] when this constraint is applied.
 *
 * Use [isInFutureOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDate.parse("2024-08-31")
 * val validate = Validator<LocalDate> { isAfterOrEqualTo(now) }
 * validate(now.plus(2, DateTimeUnit.DAY)) // Success
 * validate(now) // Success
 * validate(now.minus(2, DateTimeUnit.DAY)) // Failure (message: Must be after or equal to "2024-08-31")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDate?, MetadataType>.isAfterOrEqualTo(other: LocalDate): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

/**
 * The validatable [LocalTime] must be after or equal to [other] when this constraint is applied.
 *
 * Use [isInFutureOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalTime.parse("12:30:15")
 * val validate = Validator<LocalTime> { isAfterOrEqualTo(now) }
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() + 5)) // Success
 * validate(now) // Success
 * validate(LocalTime.fromSecondOfDay(now.toSecondOfDay() - 5)) // Failure (message: Must be after or equal to "12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalTime?, MetadataType>.isAfterOrEqualTo(other: LocalTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

/**
 * The validatable [LocalDateTime] must be after or equal to [other] when this constraint is applied.
 *
 * Use [isInFutureOrIsPresent] to compare against the system clock `Clock.System`.
 *
 * Code example:
 *
 * ```
 * val now = LocalDateTime.parse("2024-08-31T12:30:15")
 * val timezone = TimeZone.currentSystemDefault()
 * val validate = Validator<LocalDateTime> { isAfterOrEqualTo(now) }
 * validate((now.toInstant(timezone) + 5.seconds).toLocalDateTime(timezone)) // Success
 * validate(now) // Success
 * validate((now.toInstant(timezone) - 5.seconds).toLocalDateTime(timezone)) // Failure (message: Must be after or equal to "2024-08-31T12:30:15")
 * ```
 */
public fun <MetadataType> GenericValidatable<LocalDateTime?, MetadataType>.isAfterOrEqualTo(other: LocalDateTime): GenericConstraint<MetadataType> =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other
//endregion
