package dev.nesk.akkurate

import kotlin.test.*

class ValidationErrorsTest {
    @Test
    fun `the contents are the same than the set provided to the constructor`() {
        // Arrange
        val originalSet = setOf(
            ValidationErrors.Error("message1", listOf("root", "child1")),
            ValidationErrors.Error("message2", listOf("root", "child2")),
        )
        // Act
        val errors = ValidationErrors(originalSet)
        // Assert
        assertContentEquals(originalSet, errors.toList())
    }

    @Test
    fun `the grouped messages contain the same data and in the same order`() {
        // Arrange
        val originalSet = setOf(
            ValidationErrors.Error("message1", listOf("root", "child1")),
            ValidationErrors.Error("message2", listOf("root", "child2")),
            ValidationErrors.Error("message3", listOf("root", "child1")),
        )
        val expectedMap = mapOf(
            listOf("root", "child1") to setOf(
                ValidationErrors.Error("message1", listOf("root", "child1")),
                ValidationErrors.Error("message3", listOf("root", "child1")),
            ),
            listOf("root", "child2") to setOf(ValidationErrors.Error("message2", listOf("root", "child2"))),
        )
        // Act
        val errors = ValidationErrors(originalSet).byPath
        // Assert
        assertEquals(expectedMap, errors, "The data is the same but grouped by path")
        assertContentEquals(expectedMap.values, errors.values, "In each group, the messages keep the same order as the original collection")
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when the messages are the same`() {
        val original = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        val other = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when the messages differ`() {
        val original = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        val other = ValidationErrors(emptySet())
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when the messages are the same`() {
        val original = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        val other = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when the messages differ`() {
        val original = ValidationErrors(setOf(ValidationErrors.Error("foo", listOf("bar"))))
        val other = ValidationErrors(emptySet())
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion

    class ErrorTest {
        @Test
        fun `'component1' returns the message`() {
            assertEquals("foo", ValidationErrors.Error("foo", listOf("bar")).component1())
        }

        @Test
        fun `'component2' returns the path`() {
            assertEquals(listOf("bar"), ValidationErrors.Error("foo", listOf("bar")).component2())
        }

        //region Tests for `equals()` and `hashCode()`

        @Test
        fun `'equals' returns true when all the values are the same`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("foo", listOf("bar"))
            assertTrue(original.equals(other))
        }

        @Test
        fun `'equals' returns false when at least one of the values differ (variant 'path')`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("foo", listOf(""))
            assertFalse(original.equals(other))
        }

        @Test
        fun `'equals' returns false when at least one of the values differ (variant 'message')`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("", listOf("bar"))
            assertFalse(original.equals(other))
        }

        @Test
        fun `'hashCode' returns the same hash when all the values are the same`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("foo", listOf("bar"))
            assertEquals(original.hashCode(), other.hashCode())
        }

        @Test
        fun `'hashCode' returns different hashes when at least one of the values differ (variant 'path')`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("foo", listOf(""))
            assertNotEquals(original.hashCode(), other.hashCode())
        }

        @Test
        fun `'hashCode' returns different hashes when at least one of the values differ (variant 'message')`() {
            val original = ValidationErrors.Error("foo", listOf("bar"))
            val other = ValidationErrors.Error("", listOf("bar"))
            assertNotEquals(original.hashCode(), other.hashCode())
        }

        //endregion
    }
}
