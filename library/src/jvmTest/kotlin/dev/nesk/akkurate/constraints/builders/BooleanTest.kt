package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanTest {
    @Test
    fun `'isTrue' succeeds when the value is true`() {
        assertTrue(Validatable(true).isTrue().satisfied)
    }

    @Test
    fun `'isTrue' fails when the value is false or null`() {
        assertFalse(Validatable(false).isTrue().satisfied, "The constraint is not satisfied when the value is false")
        assertFalse(Validatable(null).isTrue().satisfied, "The constraint is not satisfied when the value is null")
    }

    @Test
    fun `'isNotTrue' succeeds when the value is false or null`() {
        assertTrue(Validatable(false).isNotTrue().satisfied, "The constraint is satisfied when the value is false")
        assertTrue(Validatable(null).isNotTrue().satisfied, "The constraint is satisfied when the value is null")
    }

    @Test
    fun `'isNotTrue' fails when the value is true`() {
        assertFalse(Validatable(true).isNotTrue().satisfied)
    }

    @Test
    fun `'isFalse' succeeds when the value is false`() {
        assertTrue(Validatable(false).isFalse().satisfied)
    }

    @Test
    fun `'isFalse' fails when the value is true or null`() {
        assertFalse(Validatable(true).isFalse().satisfied, "The constraint is not satisfied when the value is true")
        assertFalse(Validatable(null).isFalse().satisfied, "The constraint is not satisfied when the value is null")
    }

    @Test
    fun `'isNotFalse' succeeds when the value is true or null`() {
        assertTrue(Validatable(true).isNotFalse().satisfied, "The constraint is satisfied when the value is true")
        assertTrue(Validatable(null).isNotFalse().satisfied, "The constraint is satisfied when the value is null")
    }

    @Test
    fun `'isNotFalse' fails when the value is false`() {
        assertFalse(Validatable(false).isNotFalse().satisfied)
    }
}
