package dev.nesk.akkurate

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertContentEquals

class PathTest {
    @Test
    fun `generating an absolute path doesn't reuse the validatable path`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).absolute("bar", "baz")
        // Assert
        assertContentEquals(listOf("bar", "baz"), path)
    }

    @Test
    fun `generating a relative path appends the provided segments to the path of the parent validatable`() {
        // Arrange
        val parent = Validatable(null, "foo")
        val child = Validatable(null, "bar", parent)
        // Act
        val path = PathBuilder(child).relative("baz")
        // Assert
        assertContentEquals(listOf("foo", "baz"), path)
    }

    @Test
    fun `generating a relative path without a parent is the same as an absolute path`() {
        // Arrange
        val orphan = Validatable(null, "foo")
        // Act
        val path = PathBuilder(orphan).relative("bar")
        // Assert
        assertContentEquals(listOf("bar"), path)
    }

    @Test
    fun `generating an appended path returns the validatable path appended with the provided segments`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).appended("bar")
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }

    @Test
    fun `calling 'path' on a validatable returns a builder associated to this validatable`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = validatable.path { appended("bar") }
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }
}
