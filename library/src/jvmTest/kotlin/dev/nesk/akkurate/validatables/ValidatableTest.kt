package dev.nesk.akkurate.validatables

import kotlin.reflect.KProperty1
import kotlin.test.*

class ValidatableTest {
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
}
