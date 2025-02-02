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

package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate._test.Validatable
import dev.nesk.akkurate.test.Validatable
import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.*

class ConstraintTest {
    private fun ConstraintBuilder(satisfied: Boolean) = Constraint(satisfied, Validatable(null))

    @Test
    fun the_default_message_is_empty() {
        assertEquals("", ConstraintBuilder(false).message)
    }

    @Test
    fun the_first_destructuring_component_returns_the_value_of_the__satisfied__property() {
        val satisfied = listOf(true, false).random()
        assertEquals(satisfied, ConstraintBuilder(satisfied).component1())
    }

    @Test
    fun __ConstraintViolation__conversion_reuses_the_message_and_the_path() {
        // Arrange
        val builder = ConstraintBuilder(false) otherwise { "foo" } withPath { absolute("bar") }
        // Act
        val violation = builder.toConstraintViolation("default", listOf())
        // Assert
        assertEquals("foo", violation.message)
        assertEquals(listOf("bar"), violation.path)
    }

    @Test
    fun __ConstraintViolation__conversion_replaces_empty_messages_with_the_default_message() {
        // Arrange
        val builder = ConstraintBuilder(false)
        // Act
        val violation = builder.toConstraintViolation("default", listOf())
        // Assert
        assertEquals("default", violation.message)
    }

    @Test
    fun __ConstraintViolation__conversion_prepends_the_path_with_the_root_path() {
        // Arrange
        val builder = ConstraintBuilder(false) withPath { absolute("foo") }
        // Act
        val violation = builder.toConstraintViolation("default", listOf("root"))
        // Assert
        assertEquals(listOf("root", "foo"), violation.path)
    }

    @Test
    fun __ConstraintViolation__conversion_fails_with_a_satisfied_constraint() {
        assertFailsWith<IllegalArgumentException> {
            ConstraintBuilder(true).toConstraintViolation("default", listOf())
        }
    }

    @Test
    fun calling__otherwise__with_a_lambda_updates_the_message_if_the_constraint_is_not_satisfied() {
        val constraint = ConstraintBuilder(false) otherwise { "foo" }
        assertEquals("foo", constraint.message)
    }

    @Test
    fun calling__otherwise__with_a_lambda_does_not_update_the_message_if_the_constraint_is_satisfied() {
        val constraint = ConstraintBuilder(true) otherwise { "foo" }
        assertEquals("", constraint.message)
    }

    @Test
    fun calling__withMetadata_updates_the_metadata() {
        val constraint = ConstraintBuilder(true) withMetadata { mapOf("foo" to "bar") }
        assertEquals(mapOf("foo" to "bar"), constraint.metadata)
    }

    @Test
    fun calling__withPath__with_a_lambda_updates_the_path_if_the_constraint_is_not_satisfied() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(false, validatable)
        // Act
        val path = constraint.withPath { appended("bar") }.path
        // Assert
        assertEquals(listOf("foo", "bar"), constraint.path)
    }

    @Test
    fun calling__withPath__with_a_lambda_does_not_update_the_path_if_the_constraint_is_satisfied() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(true, validatable)
        // Act
        val path = constraint.withPath { relative("bar") }.path
        // Assert
        assertEquals(listOf("foo"), constraint.path)
    }

    @Test
    fun calling__constrain__with_a_truthy_lambda_creates_a_satisfied_constraint_and_does_not_register_it() {
        // Arrange
        val constraintRegistry = ConstraintRegistry(Configuration())
        val parent = Validatable(null, constraintRegistry)
        val child = Validatable(null, "foo", parent)
        // Act
        val constraint = child.constrain { true }
        // Assert
        assertTrue(constraint.satisfied, "The constraint is satisfied")
        assertEquals(child.path(), constraint.path, "The constraint path is the same as the validatable")
        assertTrue(constraintRegistry.toSet().isEmpty(), "The constraint is not in the registry")
    }

    @Test
    fun calling__constrain__with_a_falsy_lambda_creates_and_registers_an_unsatisfied_constraint_with_the_validatable_path() {
        // Arrange
        val constraintRegistry = ConstraintRegistry(Configuration())
        val parent = Validatable(null, constraintRegistry)
        val child = Validatable(null, "foo", parent)
        // Act
        val constraint = child.constrain { false }
        // Assert
        assertFalse(constraint.satisfied, "The constraint is unsatisfied")
        assertEquals(child.path(), constraint.path, "The constraint path is the same as the validatable")
        assertSame(constraint, constraintRegistry.toSet().single(), "The unique constraint in the registry is the same as the one returned by the 'constraint' function")
    }

    @Test
    fun calling__constrain__with_failOnFirstViolation_set_to_true_throws_a_FirstViolationException_before_the_execution_of_the_next_constraint_after_an_unsatisfied_one() {
        // Arrange
        val constraintRegistry = ConstraintRegistry(Configuration { failOnFirstViolation = true })
        val validatable = Validatable(null, constraintRegistry)
        var secondConstraintHasExecuted = false
        // Act
        validatable.constrain { false } otherwise { "message 1" }
        val exception = assertFailsWith<FirstViolationException> {
            validatable.constrain {
                secondConstraintHasExecuted = true
                false
            } otherwise { "message 2" }
        }

        // Assert
        assertFalse(secondConstraintHasExecuted, "The second constraint has not been executed")
        assertEquals("message 1", exception.violation.message, "The exception contains a violation with 'message 1' as message")
    }

    @Test
    fun calling__constrainIfNotNull__acts_as__constrain__when_the_wrapped_value_is_not_null() {
        // Arrange
        val validatable = Validatable("foo" as String?)
        // Act
        val constraint = validatable.constrainIfNotNull { false }
        // Assert
        assertFalse(constraint.satisfied, "The constraint is unsatisfied")
        assertEquals(validatable.path(), constraint.path, "The constraint path is the same as the validatable")
    }

    @Test
    fun calling__constrainIfNotNull__returns_a_satisfied_constraint_when_the_wrapped_value_is_null() {
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
    fun __equals__returns_true_when_all_the_values_are_the_same() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        assertTrue(original.equals(other))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_satisfied() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) otherwise { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_validatable_path() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo")) otherwise { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_message() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "" }
        assertFalse(original.equals(other))
    }

    @Test
    fun __hashCode__returns_the_same_hash_when_all_the_values_are_the_same() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_satisfied() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) otherwise { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_validatable_path() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo")) otherwise { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_message() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
