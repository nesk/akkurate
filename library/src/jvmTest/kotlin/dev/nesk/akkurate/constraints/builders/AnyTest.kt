package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AnyTest {
    @Test
    fun `'isNull' succeeds when the value is null`() {
        assertTrue(Validatable(null).isNull().satisfied)
    }

    @Test
    fun `'isNull' fails when the value is not null`() {
        assertFalse(Validatable("foo").isNull().satisfied)
    }

    @Test
    fun `'isNotNull' succeeds when the value is not null`() {
        assertTrue(Validatable("foo").isNotNull().satisfied)
    }

    @Test
    fun `'isNotNull' fails when the value is null`() {
        assertFalse(Validatable(null).isNotNull().satisfied)
    }

    @Test
    fun `'isEqualTo' succeeds when the provided value is equal to the validated one`() {
        assertTrue(Validatable("foo").isEqualTo("foo").satisfied)
    }

    @Test
    fun `'isEqualTo' fails when the provided value is different from the validated one`() {
        assertFalse(Validatable("foo").isEqualTo("bar").satisfied)
    }

    @Test
    fun `'isEqualTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertTrue(Validatable("foo").isEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'isEqualTo' fails when the value of the provided validatable is different from the validated one`() {
        assertFalse(Validatable("foo").isEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the provided value is different from the validated one`() {
        assertTrue(Validatable("foo").isNotEqualTo("bar").satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the provided value is equal to the validated one`() {
        assertFalse(Validatable("foo").isNotEqualTo("foo").satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the value of the provided validatable is different from the validated one`() {
        assertTrue(Validatable("foo").isNotEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertFalse(Validatable("foo").isNotEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'isIdenticalTo' succeeds when the provided value is the same as the validated one`() {
        val value = object {}
        assertTrue(Validatable(value).isIdenticalTo(value).satisfied)
    }

    @Test
    fun `'isIdenticalTo' fails when the provided value is not the same as the validated one`() {
        assertFalse(Validatable(object {}).isIdenticalTo(object {}).satisfied)
    }

    @Test
    fun `'isNotIdenticalTo' succeeds when the provided value is not the same as the validated one`() {
        assertTrue(Validatable(object {}).isNotIdenticalTo(object {}).satisfied)
    }

    @Test
    fun `'isNotIdenticalTo' fails when the provided value is the same as the validated one`() {
        val value = object {}
        assertFalse(Validatable(value).isNotIdenticalTo(value).satisfied)
    }
}
