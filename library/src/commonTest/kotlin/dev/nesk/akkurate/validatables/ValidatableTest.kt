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
import dev.nesk.akkurate.test.Validatable
import kotlin.test.*

class ValidatableTest {
    @Test
    fun the_path_of_a_root_validatable_is_empty() {
        assertEquals(emptyList(), Validatable("foo").path())
    }

    @Test
    fun the_path_segment_gets_appended_to_the_parent_path() {
        val parent = Validatable(null)
        val child1 = Validatable(null, "foo", parent)
        val child2 = Validatable(null, "bar", child1)
        assertEquals(listOf("foo", "bar"), child2.path())
    }

    @Test
    fun the_validatable_can_be_unwrapped_with_its_original_value() {
        val foo = object {}
        assertSame(foo, Validatable(foo).unwrap())
    }

    @Test
    fun the_first_destructuring_component_unwraps_with_the_original_value() {
        val foo = object {}
        assertSame(foo, Validatable(foo).component1())
    }

    @Test
    fun calling__register__with_a_constraint_stores_it_in_the_registry() {
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
    fun calling__register__with_a_constraint_violation_stores_it_in_the_registry() {
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
    fun invoking_the_validatable_executes_the_block_immediately() {
        // Arrange
        var hasExecuted = false
        val validatable = Validatable("foo")
        // Act
        validatable { hasExecuted = true }
        // Assert
        assertTrue(hasExecuted, "The lambda has been executed")
    }

    @Test
    fun calling__validatableOf__returns_a_property_wrapped_in_a_Validatable() {
        // Arrange
        val parent = Validatable("foo", "string")
        // Act
        val child = parent.validatableOf(String::length)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun calling__validatableOf__with_a_nullable_receiver_returns_a_nullable_property_wrapped_in_a_Validatable____variant_with_non_null_value() {
        // Arrange
        val parent = Validatable("foo" as String?, "string")
        // Act
        val child: Validatable<Int?> = parent.validatableOf(String::length)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun calling__validatableOf__with_a_nullable_receiver_returns_a_nullable_property_wrapped_in_a_Validatable____variant_with_null_value() {
        // Arrange
        val parent = Validatable(null as String?, "string")
        // Act
        val child = parent.validatableOf(String::length)
        // Assert
        assertNull(child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "length"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun calling__validatableOf__returns_a_getter_function_wrapped_in_a_Validatable() {
        // Arrange
        val parent = Validatable("foo", "string")
        // Act
        val child = parent.validatableOf(String::count)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "count"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun calling__validatableOf__with_a_nullable_receiver_returns_a_nullable_getter_function_wrapped_in_a_Validatable____variant_with_non_null_value() {
        // Arrange
        val parent = Validatable("foo" as String?, "string")
        // Act
        val child = parent.validatableOf(String::count)
        // Assert
        assertEquals(3, child.unwrap(), "The child validatable wraps the value of the property")
        assertEquals(listOf("string", "count"), child.path(), "The child validatable extends the parent path with the property name")
    }

    @Test
    fun calling__validatableOf__with_a_nullable_receiver_returns_a_nullable_getter_function_wrapped_in_a_Validatable____variant_with_null_value() {
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
    fun __equals__returns_true_when_the_wrapped_values_are_the_same() {
        val validatable1 = Validatable("foo", "bar")
        val validatable2 = Validatable("foo", "baz", validatable1)
        assertTrue(validatable1.equals(validatable2))
    }

    @Test
    fun __equals__returns_false_when_the_wrapped_values_are_different() {
        val validatable1 = Validatable("foo")
        val validatable2 = Validatable("bar")
        assertFalse(validatable1.equals(validatable2))
    }

    @Test
    fun __hashCode__returns_the_same_hash_when_the_wrapped_values_are_the_same() {
        val validatable1 = Validatable("foo", "bar")
        val validatable2 = Validatable("foo", "baz", validatable1)
        assertEquals(validatable1.hashCode(), validatable2.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_the_wrapped_values_differ() {
        val validatable1 = Validatable("foo")
        val validatable2 = Validatable("bar")
        assertNotEquals(validatable1.hashCode(), validatable2.hashCode())
    }

    //endregion
}

