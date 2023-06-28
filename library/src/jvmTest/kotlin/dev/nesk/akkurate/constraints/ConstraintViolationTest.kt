package dev.nesk.akkurate.constraints

import kotlin.test.*

class ConstraintViolationTest {
    @Test
    fun `'component1' returns the message`() {
        assertEquals("foo", ConstraintViolation("foo", listOf("bar")).component1())
    }

    @Test
    fun `'component2' returns the path`() {
        assertEquals(listOf("bar"), ConstraintViolation("foo", listOf("bar")).component2())
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when all the values are the same`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf("bar"))
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'path')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf(""))
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'message')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("", listOf("bar"))
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf("bar"))
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'path')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf(""))
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'message')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("", listOf("bar"))
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
