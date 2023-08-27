package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import java.time.*
import kotlin.test.*

class TemporalAccessorTest {
    companion object {
        private val fewSeconds = Duration.ofSeconds(5)
    }

    private val presentInstant = Instant.now()
    private val fixedClock = Clock.fixed(presentInstant, ZoneOffset.UTC)
    private val presentLocalDateTime = LocalDateTime.now(fixedClock)
    private val presentZonedDateTime = ZonedDateTime.now(fixedClock)

    private lateinit var originalClock: Clock

    @BeforeTest
    fun before() {
        originalClock = clock
        clock = fixedClock
    }

    @AfterTest
    fun after() {
        clock = originalClock
    }

    //region isInPast
    @Test
    fun `'Instant isInPast' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isInPast().satisfied)
    }

    @Test
    fun `'Instant isInPast' succeeds when the value is in the isInPast`() {
        assertTrue(Validatable(presentInstant - fewSeconds).isInPast().satisfied)
    }

    @Test
    fun `'Instant isInPast' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentInstant + fewSeconds).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentInstant).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime isInPast' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isInPast().satisfied)
    }

    @Test
    fun `'LocalDateTime isInPast' succeeds when the value is in the isInPast`() {
        assertTrue(Validatable(presentLocalDateTime - fewSeconds).isInPast().satisfied)
    }

    @Test
    fun `'LocalDateTime isInPast' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentLocalDateTime + fewSeconds).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentLocalDateTime).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime isInPast' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isInPast().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInPast' succeeds when the value is in the isInPast`() {
        assertTrue(Validatable(presentZonedDateTime - fewSeconds).isInPast().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInPast' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentZonedDateTime + fewSeconds).isInPast().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentZonedDateTime).isInPast().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region isInPastOrIsPresent
    @Test
    fun `'Instant isInPastOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun `'Instant isInPastOrIsPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentInstant - fewSeconds).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'Instant isInPastOrIsPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentInstant + fewSeconds).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime isInPastOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime isInPastOrIsPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentLocalDateTime - fewSeconds).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime isInPastOrIsPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentLocalDateTime + fewSeconds).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInPastOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isInPastOrIsPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInPastOrIsPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentZonedDateTime - fewSeconds).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentZonedDateTime).isInPastOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime isInPastOrIsPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentZonedDateTime + fewSeconds).isInPastOrIsPresent().satisfied)
    }
    //endregion

    //region isInFuture
    @Test
    fun `'Instant isInFuture' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isInFuture().satisfied)
    }

    @Test
    fun `'Instant isInFuture' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentInstant + fewSeconds).isInFuture().satisfied)
    }

    @Test
    fun `'Instant isInFuture' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentInstant - fewSeconds).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentInstant).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime isInFuture' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isInFuture().satisfied)
    }

    @Test
    fun `'LocalDateTime isInFuture' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentLocalDateTime + fewSeconds).isInFuture().satisfied)
    }

    @Test
    fun `'LocalDateTime isInFuture' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentLocalDateTime - fewSeconds).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentLocalDateTime).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime isInFuture' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isInFuture().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInFuture' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentZonedDateTime + fewSeconds).isInFuture().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInFuture' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentZonedDateTime - fewSeconds).isInFuture().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentZonedDateTime).isInFuture().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region isInFutureOrIsPresent
    @Test
    fun `'Instant isInFutureOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun `'Instant isInFutureOrIsPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentInstant + fewSeconds).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'Instant isInFutureOrIsPresent' fails when the value is in the isInPast`() {
        assertFalse(Validatable(presentInstant - fewSeconds).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime isInFutureOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime isInFutureOrIsPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentLocalDateTime + fewSeconds).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime isInFutureOrIsPresent' fails when the value is in the isInPast`() {
        assertFalse(Validatable(presentLocalDateTime - fewSeconds).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInFutureOrIsPresent' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isInFutureOrIsPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime isInFutureOrIsPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentZonedDateTime + fewSeconds).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentZonedDateTime).isInFutureOrIsPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime isInFutureOrIsPresent' fails when the value is in the isInPast`() {
        assertFalse(Validatable(presentZonedDateTime - fewSeconds).isInFutureOrIsPresent().satisfied)
    }
    //endregion

    //region isBefore
    @Test
    fun `'Instant isBefore' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isBefore(presentInstant).satisfied)
    }

    @Test
    fun `'Instant isBefore' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentInstant).isBefore(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun `'Instant isBefore' fails when the value is after or equal to the provided one`() {
        assertFalse(Validatable(presentInstant).isBefore(presentInstant - fewSeconds).satisfied, "The constraint is not satisfied when the value is after the provided one")
        assertFalse(Validatable(presentInstant).isBefore(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime isBefore' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isBefore(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime isBefore' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentLocalDateTime).isBefore(presentLocalDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime isBefore' fails when the value is after or equal to the provided one`() {
        assertFalse(
            Validatable(presentLocalDateTime).isBefore(presentLocalDateTime - fewSeconds).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).isBefore(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime isBefore' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isBefore(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime isBefore' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentZonedDateTime).isBefore(presentZonedDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime isBefore' fails when the value is after or equal to the provided one`() {
        assertFalse(
            Validatable(presentZonedDateTime).isBefore(presentZonedDateTime - fewSeconds).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentZonedDateTime).isBefore(presentZonedDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isBeforeOrEqualTo
    @Test
    fun `'Instant isBeforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isBeforeOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun `'Instant isBeforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant + fewSeconds).satisfied, "The constraint is satisfied when the value is before the provided one")
        assertTrue(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Instant isBeforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentInstant).isBeforeOrEqualTo(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime isBeforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isBeforeOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime isBeforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(
            Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime + fewSeconds).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime isBeforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentLocalDateTime).isBeforeOrEqualTo(presentLocalDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime isBeforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isBeforeOrEqualTo(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime isBeforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(
            Validatable(presentZonedDateTime).isBeforeOrEqualTo(presentZonedDateTime + fewSeconds).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentZonedDateTime).isBeforeOrEqualTo(presentZonedDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime isBeforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentZonedDateTime).isBeforeOrEqualTo(presentZonedDateTime - fewSeconds).satisfied)
    }
    //endregion

    //region isAfter
    @Test
    fun `'Instant isAfter' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isAfter(presentInstant).satisfied)
    }

    @Test
    fun `'Instant isAfter' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentInstant).isAfter(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun `'Instant isAfter' fails when the value is before or equal to the provided one`() {
        assertFalse(Validatable(presentInstant).isAfter(presentInstant + fewSeconds).satisfied, "The constraint is not satisfied when the value is before the provided one")
        assertFalse(Validatable(presentInstant).isAfter(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime isAfter' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isAfter(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime isAfter' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentLocalDateTime).isAfter(presentLocalDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime isAfter' fails when the value is before or equal to the provided one`() {
        assertFalse(
            Validatable(presentLocalDateTime).isAfter(presentLocalDateTime + fewSeconds).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).isAfter(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime isAfter' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isAfter(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime isAfter' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentZonedDateTime).isAfter(presentZonedDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime isAfter' fails when the value is before or equal to the provided one`() {
        assertFalse(
            Validatable(presentZonedDateTime).isAfter(presentZonedDateTime + fewSeconds).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentZonedDateTime).isAfter(presentZonedDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region isAfterOrEqualTo
    @Test
    fun `'Instant isAfterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).isAfterOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun `'Instant isAfterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(Validatable(presentInstant).isAfterOrEqualTo(presentInstant - fewSeconds).satisfied, "The constraint is satisfied when the value is after the provided one")
        assertTrue(Validatable(presentInstant).isAfterOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Instant isAfterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentInstant).isAfterOrEqualTo(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime isAfterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).isAfterOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime isAfterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(
            Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime - fewSeconds).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime isAfterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentLocalDateTime).isAfterOrEqualTo(presentLocalDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime isAfterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).isAfterOrEqualTo(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime isAfterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(
            Validatable(presentZonedDateTime).isAfterOrEqualTo(presentZonedDateTime - fewSeconds).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentZonedDateTime).isAfterOrEqualTo(presentZonedDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime isAfterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentZonedDateTime).isAfterOrEqualTo(presentZonedDateTime + fewSeconds).satisfied)
    }
    //endregion
}
