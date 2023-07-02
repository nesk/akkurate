package dev.nesk.akkurate.validatables

import dev.nesk.akkurate._test.assertContentEquals
import kotlin.test.Test

class CompoundValidatableTest {
    @Test
    fun `a compound validatable is created when using the 'and' function between two validatables`() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        // Act
        val compound = foo and bar
        // Assert
        assertContentEquals(
            setOf(foo, bar),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun `a new compound validatable is created when using the 'and' function between a compound and a validatable`() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        // Act
        val compound = (foo and bar) and baz
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun `a new compound validatable is created when using the 'and' function between a validatable and a compound`() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        // Act
        val compound = foo and (bar and baz)
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun `a new compound validatable is created when using the 'and' function between two compounds`() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        val qux = Validatable("qux", "qux")
        // Act
        val compound = (foo and bar) and (baz and qux)
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz, qux),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun `invoking a compound validatable with a lambda executes that lambda for each validatable in the compound order`() {
        // Arrange
        val validatables = setOf(
            Validatable("foo", "foo"),
            Validatable("bar", "bar"),
        )
        val compound = CompoundValidatable(validatables)
        // Act
        val receivedValidatables = buildList {
            compound { add(this) }
        }
        // Assert
        assertContentEquals(validatables, receivedValidatables, "The lambda has been called for each validatable in the compound order")
    }
}
