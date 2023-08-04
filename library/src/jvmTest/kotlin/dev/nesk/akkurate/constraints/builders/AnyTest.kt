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
    fun `'equalTo' succeeds when the provided value is equal to the validated one`() {
        assertTrue(Validatable("foo").equalTo("foo").satisfied)
    }

    @Test
    fun `'equalTo' fails when the provided value is different from the validated one`() {
        assertFalse(Validatable("foo").equalTo("bar").satisfied)
    }

    @Test
    fun `'equalTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertTrue(Validatable("foo").equalTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'equalTo' fails when the value of the provided validatable is different from the validated one`() {
        assertFalse(Validatable("foo").equalTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'notEqualTo' succeeds when the provided value is different from the validated one`() {
        assertTrue(Validatable("foo").notEqualTo("bar").satisfied)
    }

    @Test
    fun `'notEqualTo' succeeds when the provided value is equal to the validated one`() {
        assertFalse(Validatable("foo").notEqualTo("foo").satisfied)
    }

    @Test
    fun `'notEqualTo' succeeds when the value of the provided validatable is different from the validated one`() {
        assertTrue(Validatable("foo").notEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'notEqualTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertFalse(Validatable("foo").notEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'identicalTo' succeeds when the provided value is the same as the validated one`() {
        val value = object {}
        assertTrue(Validatable(value).identicalTo(value).satisfied)
    }

    @Test
    fun `'identicalTo' fails when the provided value is not the same as the validated one`() {
        assertFalse(Validatable(object {}).identicalTo(object {}).satisfied)
    }

    @Test
    fun `'notIdenticalTo' succeeds when the provided value is not the same as the validated one`() {
        assertTrue(Validatable(object {}).notIdenticalTo(object {}).satisfied)
    }

    @Test
    fun `'notIdenticalTo' fails when the provided value is the same as the validated one`() {
        val value = object {}
        assertFalse(Validatable(value).notIdenticalTo(value).satisfied)
    }
}
