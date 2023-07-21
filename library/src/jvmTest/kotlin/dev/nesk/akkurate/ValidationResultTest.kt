package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.ConstraintViolation
import dev.nesk.akkurate.constraints.ConstraintViolationSet
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class ValidationResultTest {
    @Test
    fun `'Success' can never throw`() {
        assertDoesNotThrow {
            ValidationResult.Success.orThrow()
        }
    }

    @Test
    fun `'Failure' always throws and contains the same violations as the result`() {
        // Arrange
        val violations = ConstraintViolationSet(emptySet())
        val failure = ValidationResult.Failure(violations, null)
        // Act & Assert
        val exception = assertThrows<ValidationResult.Exception> { failure.orThrow() }
        assertSame(violations, exception.violations)
    }

    @Test
    fun `'Failure' returns the violations is-as`() {
        val violations = ConstraintViolationSet(emptySet())
        assertSame(violations, ValidationResult.Failure(violations, null).violations)
    }

    @Test
    fun `'Failure' returns the value is-as`() {
        val value = object {}
        assertSame(value, ValidationResult.Failure(ConstraintViolationSet(emptySet()), value).value)
    }

    @Test
    fun `'Failure component1' returns the violations`() {
        val failure = ValidationResult.Failure(ConstraintViolationSet(emptySet()), null)
        assertSame(failure.violations, failure.component1())
    }

    @Test
    fun `'Failure component2' returns the value`() {
        val failure = ValidationResult.Failure(ConstraintViolationSet(emptySet()), object {})
        assertSame(failure.value, failure.component2())
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when all the values are the same`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'violations')`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(emptySet()), "baz")
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'value')`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "")
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'violations')`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(emptySet()), "baz")
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'value')`() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "baz")
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))), "")
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
