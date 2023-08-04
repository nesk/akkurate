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

    //region past
    @Test
    fun `'Instant past' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).past().satisfied)
    }

    @Test
    fun `'Instant past' succeeds when the value is in the past`() {
        assertTrue(Validatable(presentInstant - fewSeconds).past().satisfied)
    }

    @Test
    fun `'Instant past' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentInstant + fewSeconds).past().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentInstant).past().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime past' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).past().satisfied)
    }

    @Test
    fun `'LocalDateTime past' succeeds when the value is in the past`() {
        assertTrue(Validatable(presentLocalDateTime - fewSeconds).past().satisfied)
    }

    @Test
    fun `'LocalDateTime past' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentLocalDateTime + fewSeconds).past().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentLocalDateTime).past().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime past' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).past().satisfied)
    }

    @Test
    fun `'ZonedDateTime past' succeeds when the value is in the past`() {
        assertTrue(Validatable(presentZonedDateTime - fewSeconds).past().satisfied)
    }

    @Test
    fun `'ZonedDateTime past' fails when the value is in the future or present`() {
        assertFalse(Validatable(presentZonedDateTime + fewSeconds).past().satisfied, "The constraint is not satisfied when the value is in the future")
        assertFalse(Validatable(presentZonedDateTime).past().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region pastOrPresent
    @Test
    fun `'Instant pastOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).pastOrPresent().satisfied)
    }

    @Test
    fun `'Instant pastOrPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentInstant - fewSeconds).pastOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).pastOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'Instant pastOrPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentInstant + fewSeconds).pastOrPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime pastOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).pastOrPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime pastOrPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentLocalDateTime - fewSeconds).pastOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).pastOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime pastOrPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentLocalDateTime + fewSeconds).pastOrPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime pastOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).pastOrPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime pastOrPresent' succeeds when the value is in the past or present`() {
        assertTrue(Validatable(presentZonedDateTime - fewSeconds).pastOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentZonedDateTime).pastOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime pastOrPresent' fails when the value is in the future`() {
        assertFalse(Validatable(presentZonedDateTime + fewSeconds).pastOrPresent().satisfied)
    }
    //endregion

    //region future
    @Test
    fun `'Instant future' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).future().satisfied)
    }

    @Test
    fun `'Instant future' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentInstant + fewSeconds).future().satisfied)
    }

    @Test
    fun `'Instant future' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentInstant - fewSeconds).future().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentInstant).future().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime future' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).future().satisfied)
    }

    @Test
    fun `'LocalDateTime future' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentLocalDateTime + fewSeconds).future().satisfied)
    }

    @Test
    fun `'LocalDateTime future' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentLocalDateTime - fewSeconds).future().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentLocalDateTime).future().satisfied, "The constraint is not satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime future' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).future().satisfied)
    }

    @Test
    fun `'ZonedDateTime future' succeeds when the value is in the future`() {
        assertTrue(Validatable(presentZonedDateTime + fewSeconds).future().satisfied)
    }

    @Test
    fun `'ZonedDateTime future' fails when the value is in the past or present`() {
        assertFalse(Validatable(presentZonedDateTime - fewSeconds).future().satisfied, "The constraint is not satisfied when the value is in the past")
        assertFalse(Validatable(presentZonedDateTime).future().satisfied, "The constraint is not satisfied when the value is present")
    }
    //endregion

    //region futureOrPresent
    @Test
    fun `'Instant futureOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).futureOrPresent().satisfied)
    }

    @Test
    fun `'Instant futureOrPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentInstant + fewSeconds).futureOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentInstant).futureOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'Instant futureOrPresent' fails when the value is in the past`() {
        assertFalse(Validatable(presentInstant - fewSeconds).futureOrPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime futureOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).futureOrPresent().satisfied)
    }

    @Test
    fun `'LocalDateTime futureOrPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentLocalDateTime + fewSeconds).futureOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentLocalDateTime).futureOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'LocalDateTime futureOrPresent' fails when the value is in the past`() {
        assertFalse(Validatable(presentLocalDateTime - fewSeconds).futureOrPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime futureOrPresent' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).futureOrPresent().satisfied)
    }

    @Test
    fun `'ZonedDateTime futureOrPresent' succeeds when the value is in the future or present`() {
        assertTrue(Validatable(presentZonedDateTime + fewSeconds).futureOrPresent().satisfied, "The constraint is satisfied when the value is in the past")
        assertTrue(Validatable(presentZonedDateTime).futureOrPresent().satisfied, "The constraint is satisfied when the value is present")
    }

    @Test
    fun `'ZonedDateTime futureOrPresent' fails when the value is in the past`() {
        assertFalse(Validatable(presentZonedDateTime - fewSeconds).futureOrPresent().satisfied)
    }
    //endregion

    //region before
    @Test
    fun `'Instant before' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).before(presentInstant).satisfied)
    }

    @Test
    fun `'Instant before' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentInstant).before(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun `'Instant before' fails when the value is after or equal to the provided one`() {
        assertFalse(Validatable(presentInstant).before(presentInstant - fewSeconds).satisfied, "The constraint is not satisfied when the value is after the provided one")
        assertFalse(Validatable(presentInstant).before(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime before' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).before(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime before' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentLocalDateTime).before(presentLocalDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime before' fails when the value is after or equal to the provided one`() {
        assertFalse(
            Validatable(presentLocalDateTime).before(presentLocalDateTime - fewSeconds).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).before(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime before' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).before(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime before' succeeds when the value is before the provided one`() {
        assertTrue(Validatable(presentZonedDateTime).before(presentZonedDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime before' fails when the value is after or equal to the provided one`() {
        assertFalse(
            Validatable(presentZonedDateTime).before(presentZonedDateTime - fewSeconds).satisfied,
            "The constraint is not satisfied when the value is after the provided one"
        )
        assertFalse(Validatable(presentZonedDateTime).before(presentZonedDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region beforeOrEqualTo
    @Test
    fun `'Instant beforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).beforeOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun `'Instant beforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(Validatable(presentInstant).beforeOrEqualTo(presentInstant + fewSeconds).satisfied, "The constraint is satisfied when the value is before the provided one")
        assertTrue(Validatable(presentInstant).beforeOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Instant beforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentInstant).beforeOrEqualTo(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime beforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).beforeOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime beforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(
            Validatable(presentLocalDateTime).beforeOrEqualTo(presentLocalDateTime + fewSeconds).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).beforeOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime beforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentLocalDateTime).beforeOrEqualTo(presentLocalDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime beforeOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).beforeOrEqualTo(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime beforeOrEqualTo' succeeds when the value is before or equal to the provided one`() {
        assertTrue(
            Validatable(presentZonedDateTime).beforeOrEqualTo(presentZonedDateTime + fewSeconds).satisfied,
            "The constraint is satisfied when the value is before the provided one"
        )
        assertTrue(Validatable(presentZonedDateTime).beforeOrEqualTo(presentZonedDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime beforeOrEqualTo' fails when the value is after the provided one`() {
        assertFalse(Validatable(presentZonedDateTime).beforeOrEqualTo(presentZonedDateTime - fewSeconds).satisfied)
    }
    //endregion

    //region after
    @Test
    fun `'Instant after' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).after(presentInstant).satisfied)
    }

    @Test
    fun `'Instant after' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentInstant).after(presentInstant - fewSeconds).satisfied)
    }

    @Test
    fun `'Instant after' fails when the value is before or equal to the provided one`() {
        assertFalse(Validatable(presentInstant).after(presentInstant + fewSeconds).satisfied, "The constraint is not satisfied when the value is before the provided one")
        assertFalse(Validatable(presentInstant).after(presentInstant).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime after' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).after(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime after' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentLocalDateTime).after(presentLocalDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime after' fails when the value is before or equal to the provided one`() {
        assertFalse(
            Validatable(presentLocalDateTime).after(presentLocalDateTime + fewSeconds).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentLocalDateTime).after(presentLocalDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime after' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).after(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime after' succeeds when the value is after the provided one`() {
        assertTrue(Validatable(presentZonedDateTime).after(presentZonedDateTime - fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime after' fails when the value is before or equal to the provided one`() {
        assertFalse(
            Validatable(presentZonedDateTime).after(presentZonedDateTime + fewSeconds).satisfied,
            "The constraint is not satisfied when the value is before the provided one"
        )
        assertFalse(Validatable(presentZonedDateTime).after(presentZonedDateTime).satisfied, "The constraint is not satisfied when the value is equal to the provided one")
    }
    //endregion

    //region afterOrEqualTo
    @Test
    fun `'Instant afterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Instant?>(null).afterOrEqualTo(presentInstant).satisfied)
    }

    @Test
    fun `'Instant afterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(Validatable(presentInstant).afterOrEqualTo(presentInstant - fewSeconds).satisfied, "The constraint is satisfied when the value is after the provided one")
        assertTrue(Validatable(presentInstant).afterOrEqualTo(presentInstant).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'Instant afterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentInstant).afterOrEqualTo(presentInstant + fewSeconds).satisfied)
    }

    @Test
    fun `'LocalDateTime afterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<LocalDateTime?>(null).afterOrEqualTo(presentLocalDateTime).satisfied)
    }

    @Test
    fun `'LocalDateTime afterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(
            Validatable(presentLocalDateTime).afterOrEqualTo(presentLocalDateTime - fewSeconds).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentLocalDateTime).afterOrEqualTo(presentLocalDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'LocalDateTime afterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentLocalDateTime).afterOrEqualTo(presentLocalDateTime + fewSeconds).satisfied)
    }

    @Test
    fun `'ZonedDateTime afterOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<ZonedDateTime?>(null).afterOrEqualTo(presentZonedDateTime).satisfied)
    }

    @Test
    fun `'ZonedDateTime afterOrEqualTo' succeeds when the value is after or equal to the provided one`() {
        assertTrue(
            Validatable(presentZonedDateTime).afterOrEqualTo(presentZonedDateTime - fewSeconds).satisfied,
            "The constraint is satisfied when the value is after the provided one"
        )
        assertTrue(Validatable(presentZonedDateTime).afterOrEqualTo(presentZonedDateTime).satisfied, "The constraint is satisfied when the value is equal to the provided one")
    }

    @Test
    fun `'ZonedDateTime afterOrEqualTo' fails when the value is before the provided one`() {
        assertFalse(Validatable(presentZonedDateTime).afterOrEqualTo(presentZonedDateTime + fewSeconds).satisfied)
    }
    //endregion
}
