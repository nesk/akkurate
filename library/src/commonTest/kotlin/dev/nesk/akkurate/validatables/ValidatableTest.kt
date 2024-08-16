/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nesk.akkurate.validatables

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate._test.Validatable
import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.ConstraintRegistry
import dev.nesk.akkurate.constraints.ConstraintViolation
import kotlin.reflect.KProperty1
import kotlin.test.*

class ValidatableTest {
    @Test
    fun `the path of a root validatable is empty`() {
        assertEquals(emptyList(), Validatable("foo").path())
    }

    @Test
    fun `the path segment gets appended to the parent path`() {
        val parent = Validatable(null)
        val child1 = Validatable(null, "foo", parent)
        val child2 = Validatable(null, "bar", child1)
        assertEquals(listOf("foo", "bar"), child2.path())
    }

    @Test
    fun `the validatable can be unwrapped with its original value`() {
        val foo = object {}
        assertSame(foo, Validatable(foo).unwrap())
    }

    @Test
    fun `the first destructuring component unwraps with the original value`() {
        val foo = object {}
        assertSame(foo, Validatable(foo).component1())
    }

    @Test
    fun `calling 'register' with a constraint stores it in the registry`() {
        // Arrange
        val registry = ConstraintRegistry(Configuration())
        val validatable = Validatable(null, registry)
        val constraint = Constraint(false, validatable)
        // Act
        validatable.registerConstraint(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single())
    }

    @Test
    fun `calling 'register' with a constraint violation stores it in the registry`() {
        // Arrange
        val registry = ConstraintRegistry(Configuration())
        val validatable = Validatable(null, registry)
        val constraint = ConstraintViolation("", emptyList())
        // Act
        validatable.registerConstraint(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single())
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

