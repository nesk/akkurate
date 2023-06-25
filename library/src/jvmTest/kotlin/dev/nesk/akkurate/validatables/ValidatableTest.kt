package dev.nesk.akkurate.validatables

import dev.nesk.akkurate.constraints.Constraint
import kotlin.reflect.KProperty1
import kotlin.test.*

class ValidatableTest {
    private fun <T> Validatable(wrappedValue: T, parent: Validatable<*>) = Validatable(wrappedValue, null, parent)

    @Test
    fun `the default path is empty`() {
        assertEquals(emptyList(), Validatable("foo").path())
    }

    @Test
    fun `the path segment is used as the root if no parent is provided`() {
        assertEquals(listOf("root"), Validatable("foo", "root").path())
    }

    @Test
    fun `without a path segment, the validatable uses the same path as its parent`() {
        val parent = Validatable("foo", "parent")
        val child = Validatable("foo", parent = parent)
        assertEquals(listOf("parent"), child.path())
    }

    @Test
    fun `the path segment gets appended to the empty parent path`() {
        val parent = Validatable("foo")
        val child = Validatable("foo", "child", parent)
        assertEquals(listOf("child"), child.path())
    }

    @Test
    fun `the path segment gets appended to the parent path`() {
        val parent = Validatable("foo", "parent")
        val child = Validatable("foo", "child", parent)
        assertEquals(listOf("parent", "child"), child.path())
    }

    @Test
    fun `the validatable can be unwrapped with its original value`() {
        val foo = object {}
        assertSame(foo, Validatable(foo).unwrap())
    }

    @Test
    fun `calling 'registerConstraint' with a satisfied constraint leaves the constraints collection empty`() {
        // Arrange
        val constraint = Constraint(true, Validatable(null))
        val validatable = Validatable("foo")
        // Act
        validatable.registerConstraint(constraint)
        // Assert
        assertTrue(validatable.constraints.isEmpty(), "The constraints collection is empty")
    }

    @Test
    fun `calling 'registerConstraint' with an unsatisfied constraint adds it to the constraints collection`() {
        // Arrange
        val constraint = Constraint(false, Validatable(null))
        val validatable = Validatable("foo")
        // Act
        validatable.registerConstraint(constraint)
        // Assert
        assertEquals(1, validatable.constraints.size, "The constraints collection contains only one item")
        assertEquals(constraint, validatable.constraints.first(), "The constraints collection item is equal to the registered constraint")
    }

    @Test
    fun `registered constraints are always stored in the root validatable`() {
        // Arrange
        val level0 = Validatable("foo")
        val level1 = Validatable("bar", "bar", level0)
        val level2 = Validatable("baz", "baz", level1)
        val constraint0 = Constraint(false, level0)
        val constraint1 = Constraint(false, level1)
        val constraint2 = Constraint(false, level2)
        // Act
        level0.registerConstraint(constraint0)
        level1.registerConstraint(constraint1)
        level2.registerConstraint(constraint2)
        // Assert
        assertEquals(3, level0.constraints.size, "The root validatable contains 3 constraints")
        assertContains(level0.constraints, constraint0, "The root validatable contains its own constraint")
        assertContains(level0.constraints, constraint1, "The root validatable contains the constraint from its direct children")
        assertContains(level0.constraints, constraint2, "The root validatable contains the constraint from its indirect children")
    }

    @Test
    fun `invoking the validatable executes the block immediately`() {
        // Arrange
        var hasExecuted = false
        val validatable = Validatable("foo")
        // Act
        validatable { hasExecuted = true }
        // Assert
        assertTrue(hasExecuted, "The lambda has been executed")
    }

    @Test
    fun `calling 'validatableOf' returns a property wrapped in a Validatable`() {
        // Arrange
        val parent = Validatable("foo", "string")
        // Act
        val child = parent.validatableOf(String::length)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun `calling 'validatableOf' with a nullable receiver returns a nullable property wrapped in a Validatable (variant with non-null value)`() {
        // Arrange
        val parent = Validatable("foo" as String?, "string")
        // Act
        // FIXME: The cast is a workaround for https://youtrack.jetbrains.com/issue/KT-59493, it can be removed with KT v1.9.20
        val child = parent.validatableOf(String::length as KProperty1)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun `calling 'validatableOf' with a nullable receiver returns a nullable property wrapped in a Validatable (variant with null value)`() {
        // Arrange
        val parent = Validatable(null as String?, "string")
        // Act
        // FIXME: The cast is a workaround for https://youtrack.jetbrains.com/issue/KT-59493, it can be removed with KT v1.9.20
        val child = parent.validatableOf(String::length as KProperty1)
        // Assert
        assertNull(child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun `calling 'validatableOf' returns a getter function wrapped in a Validatable`() {
        // Arrange
        val parent = Validatable("foo", "string")
        // Act
        val child = parent.validatableOf(String::count)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "count"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun `calling 'validatableOf' with a nullable receiver returns a nullable getter function wrapped in a Validatable (variant with non-null value)`() {
        // Arrange
        val parent = Validatable("foo" as String?, "string")
        // Act
        val child = parent.validatableOf(String::count)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "count"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun `calling 'validatableOf' with a nullable receiver returns a nullable getter function wrapped in a Validatable (variant with null value)`() {
        // Arrange
        val parent = Validatable(null as String?, "string")
        // Act
        val child = parent.validatableOf(String::count)
        // Assert
        assertNull(child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "count"), child.path(), "The child validatable extends the parent path with the property name")
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when the wrapped values are the same`() {
        val validatable1 = Validatable("foo", "bar")
        val validatable2 = Validatable("foo", "baz", validatable1)
        assertTrue(validatable1.equals(validatable2))
    }

    @Test
    fun `'equals' returns false when the wrapped values are different`() {
        val validatable1 = Validatable("foo")
        val validatable2 = Validatable("bar")
        assertFalse(validatable1.equals(validatable2))
    }

    @Test
    fun `'hashCode' returns the same hash when the wrapped values are the same`() {
        val validatable1 = Validatable("foo", "bar")
        val validatable2 = Validatable("foo", "baz", validatable1)
        assertEquals(validatable1.hashCode(), validatable2.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when the wrapped values differ`() {
        val validatable1 = Validatable("foo")
        val validatable2 = Validatable("bar")
        assertNotEquals(validatable1.hashCode(), validatable2.hashCode())
    }

    //endregion
}
