package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * All generated temporal values depend on this clock. Its internal visibility allows to change it during testing.
 */
internal var clock = Clock.systemDefaultZone()

private val currentInstant get() = Instant.now(clock)
private val currentLocalDateTime get() = LocalDateTime.now(clock)
private val currentZonedDateTime get() = ZonedDateTime.now(clock)

//region isInPast
private const val pastMessage = "Must be in the past"

@JvmName("instantIsInPast")
public fun Validatable<Instant?>.isInPast(): Constraint =
    constrainIfNotNull { it < currentInstant } otherwise { pastMessage }

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

public fun Validatable<LocalDateTime?>.isBefore(other: LocalDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<ZonedDateTime?>.isBefore(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other
//endregion

//region isBeforeOrEqualTo
private infix fun Constraint.otherwiseBeforeOrEqual(other: Any) = otherwise { "Must be before or equal to \"$other\"" }

public fun Validatable<Instant?>.isBeforeOrEqualTo(other: Instant): Constraint =
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

public fun Validatable<LocalDateTime?>.isAfter(other: LocalDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<ZonedDateTime?>.isAfter(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other
//endregion

//region isAfterOrEqualTo
private infix fun Constraint.otherwiseAfterOrEqual(other: Any) = otherwise { "Must be after or equal to \"$other\"" }

public fun Validatable<Instant?>.isAfterOrEqualTo(other: Instant): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<LocalDateTime?>.isAfterOrEqualTo(other: LocalDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<ZonedDateTime?>.isAfterOrEqualTo(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other
//endregion
