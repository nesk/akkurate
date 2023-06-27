package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.*

class ConstraintTest {
    private fun Constraint(satisfied: Boolean) = Constraint(satisfied, Validatable(null))

    @Test
    fun `the default message is null`() {
        assertNull(Constraint(false).message)
    }

    @Test
    fun `the first destructuring component returns the value of the 'satisfied' property`() {
        val satisfied = listOf(true, false).random()
        assertEquals(satisfied, Constraint(satisfied).component1())
    }

    @Test
    fun `calling 'explain' with a lambda updates the message if the constraint is not satisfied`() {
        val constraint = Constraint(false) explain { "foo" }
        assertEquals("foo", constraint.message)
    }

    @Test
    fun `calling 'explain' with a lambda does not update the message if the constraint is satisfied`() {
        val constraint = Constraint(true) explain { "foo" }
        assertNull(constraint.message)
    }

    @Test
    fun `calling 'withPath' with a lambda updates the path if the constraint is not satisfied`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(false, validatable)
        // Act
        val path = constraint.withPath { appended("bar") }.path
        // Assert
        assertEquals(listOf("foo", "bar"), constraint.path)
    }

    @Test
    fun `calling 'withPath' with a lambda does not update the path if the constraint is satisfied`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(true, validatable)
        // Act
        val path = constraint.withPath { relative("bar") }.path
        // Assert
        assertEquals(listOf("foo"), constraint.path)
    }

    @Test
    fun `calling 'constrain' with a falsy lambda creates and registers an unsatisfied constraint with the validatable path`() {
        // Arrange
        val validatable = Validatable("foo", "foo")
        // Act
        val constraint = validatable.constrain { false }
        // Assert
        assertFalse(constraint.satisfied, "The constraint is unsatisfied")
        assertEquals(validatable.path(), constraint.path, "The constraint path is the same as the validatable")
        assertEquals(1, validatable.constraints.size)
        assertEquals(constraint, validatable.constraints.first())
    }

    @Test
    fun `calling 'constrainIfNotNull' acts as 'constrain' when the wrapped value is not null`() {
        // Arrange
        val validatable = Validatable("foo" as String?)
        // Act
        val constraint = validatable.constrainIfNotNull { false }
        // Assert
        assertFalse(constraint.satisfied, "The constraint is unsatisfied")
        assertEquals(validatable.path(), constraint.path, "The constraint path is the same as the validatable")
    }

    @Test
    fun `calling 'constrainIfNotNull' returns a satisfied constraint when the wrapped value is null`() {
        // Arrange
        val validatable = Validatable(null as String?)
        // Act
        val constraint = validatable.constrainIfNotNull { false }
        // Assert
        assertTrue(constraint.satisfied, "The constraint is satisfied")
        assertEquals(validatable.path(), constraint.path, "The constraint path is the same as the validatable")
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when all the values are the same`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'satisfied')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) explain { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'validatable path')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", null)) explain { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'message')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) explain { "" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'satisfied')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) explain { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'validatable path')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", null)) explain { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'message')`() {
        val original = Constraint(false, Validatable("foo", "bar")) explain { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) explain { "" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
