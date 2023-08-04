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

//region past
private const val pastMessage = "Must be in the past"

@JvmName("instantPast")
public fun Validatable<Instant?>.past(): Constraint =
    constrainIfNotNull { it < currentInstant } otherwise { pastMessage }

@JvmName("localDateTimePast")
public fun Validatable<LocalDateTime?>.past(): Constraint =
    constrainIfNotNull { it < currentLocalDateTime } otherwise { pastMessage }

@JvmName("zonedDateTimePast")
public fun Validatable<ZonedDateTime?>.past(): Constraint =
    constrainIfNotNull { it < currentZonedDateTime } otherwise { pastMessage }
//endregion

//region pastOrPresent
private const val pastOrPresentMessage = "Must be in the past or present"

@JvmName("instantPastOrPresent")
public fun Validatable<Instant?>.pastOrPresent(): Constraint =
    constrainIfNotNull { it <= currentInstant } otherwise { pastOrPresentMessage }

@JvmName("localDateTimePastOrPresent")
public fun Validatable<LocalDateTime?>.pastOrPresent(): Constraint =
    constrainIfNotNull { it <= currentLocalDateTime } otherwise { pastOrPresentMessage }

@JvmName("zonedDateTimePastOrPresent")
public fun Validatable<ZonedDateTime?>.pastOrPresent(): Constraint =
    constrainIfNotNull { it <= currentZonedDateTime } otherwise { pastOrPresentMessage }
//endregion

//region future
private const val futureMessage = "Must be in the future"

@JvmName("instantFuture")
public fun Validatable<Instant?>.future(): Constraint =
    constrainIfNotNull { it > currentInstant } otherwise { futureMessage }

@JvmName("localDateTimeFuture")
public fun Validatable<LocalDateTime?>.future(): Constraint =
    constrainIfNotNull { it > currentLocalDateTime } otherwise { futureMessage }

@JvmName("zonedDateTimeFuture")
public fun Validatable<ZonedDateTime?>.future(): Constraint =
    constrainIfNotNull { it > currentZonedDateTime } otherwise { futureMessage }
//endregion

//region futureOrPresent
private const val futureOrPresentMessage = "Must be in the future or present"

@JvmName("instantFutureOrPresent")
public fun Validatable<Instant?>.futureOrPresent(): Constraint =
    constrainIfNotNull { it >= currentInstant } otherwise { futureOrPresentMessage }

@JvmName("localDateTimeFutureOrPresent")
public fun Validatable<LocalDateTime?>.futureOrPresent(): Constraint =
    constrainIfNotNull { it >= currentLocalDateTime } otherwise { futureOrPresentMessage }

@JvmName("zonedDateTimeFutureOrPresent")
public fun Validatable<ZonedDateTime?>.futureOrPresent(): Constraint =
    constrainIfNotNull { it >= currentZonedDateTime } otherwise { futureOrPresentMessage }
//endregion

//region before
private infix fun Constraint.otherwiseBefore(other: Any) = otherwise { "Must be before \"$other\"" }

public fun Validatable<Instant?>.before(other: Instant): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<LocalDateTime?>.before(other: LocalDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other

public fun Validatable<ZonedDateTime?>.before(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it < other } otherwiseBefore other
//endregion

//region beforeOrEqualTo
private infix fun Constraint.otherwiseBeforeOrEqual(other: Any) = otherwise { "Must be before or equal to \"$other\"" }

public fun Validatable<Instant?>.beforeOrEqualTo(other: Instant): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun Validatable<LocalDateTime?>.beforeOrEqualTo(other: LocalDateTime): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other

public fun Validatable<ZonedDateTime?>.beforeOrEqualTo(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it <= other } otherwiseBeforeOrEqual other
//endregion

//region after
private infix fun Constraint.otherwiseAfter(other: Any) = otherwise { "Must be after \"$other\"" }

public fun Validatable<Instant?>.after(other: Instant): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<LocalDateTime?>.after(other: LocalDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other

public fun Validatable<ZonedDateTime?>.after(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it > other } otherwiseAfter other
//endregion

//region afterOrEqualTo
private infix fun Constraint.otherwiseAfterOrEqual(other: Any) = otherwise { "Must be after or equal to \"$other\"" }

public fun Validatable<Instant?>.afterOrEqualTo(other: Instant): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<LocalDateTime?>.afterOrEqualTo(other: LocalDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other

public fun Validatable<ZonedDateTime?>.afterOrEqualTo(other: ZonedDateTime): Constraint =
    constrainIfNotNull { it >= other } otherwiseAfterOrEqual other
//endregion
