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
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate._test.Validatable
import kotlin.test.*

class ConstraintRegistryTest {
    @Test
    fun calling__register__with_a_satisfied_constraint_leaves_the_collection_empty() {
        // Arrange
        val constraint = Constraint(true, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertTrue(registry.toSet().isEmpty(), "The collection is empty")
    }

    @Test
    fun calling__register__with_an_unsatisfied_constraint_adds_it_to_the_collection() {
        // Arrange
        val constraint = Constraint(false, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single(), "The single item is identical to the registered constraint")
    }

    @Test
    fun calling__register__with_a_constraint_violation_adds_it_to_the_collection() {
        // Arrange
        val constraint = Constraint(false, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single(), "The single item is identical to the registered constraint")
    }

    @Test
    fun calling__register__multiples_times_with_constraints_adds_them_to_the_collection_if_they_are_unsatisfied() {
        // Arrange
        val constraint1 = Constraint(false, Validatable(null, "path1"))
        val constraint2 = Constraint(true, Validatable(null, "path2"))
        val constraint3 = Constraint(false, Validatable(null, "path3"))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint1)
        registry.register(constraint2)
        registry.register(constraint3)
        // Assert
        assertContentEquals(listOf(constraint1, constraint3), registry.toSet(), "The collection contains the unsatisfied constraints in the same order")
    }

    @Test
    fun __runWithConstraintRegistry__returns_a_validation_success_if_all_the_constraint_where_satisfied() {
        // Arrange
        val value = object {}
        val constraint1 = Constraint(true, Validatable(null, "path1"))
        val constraint2 = Constraint(true, Validatable(null, "path2"))
        val constraint3 = Constraint(true, Validatable(null, "path3"))
        // Act
        val result = runWithConstraintRegistry(value, Configuration()) {
            it.register(constraint1)
            it.register(constraint2)
            it.register(constraint3)
        }
        // Assert
        assertIs<ValidationResult.Success<*>>(result)
        assertSame(value, result.value)
    }

    @Test
    fun __runWithConstraintRegistry__returns_a_validation_failure_if_any_constraint_was_unsatisfied() {
        // Arrange
        val value = object {}
        val constraint1 = Constraint(true, Validatable(null, "path1")) otherwise { "message 1" }
        val constraint2 = Constraint(false, Validatable(null, "path2")) otherwise { "message 2" }
        val constraint3 = Constraint(false, Validatable(null, "path3")) otherwise { "message 3" }
        // Act
        val result = runWithConstraintRegistry(value, Configuration()) {
            it.register(constraint1)
            it.register(constraint2)
            it.register(constraint3)
        }
        // Assert
        assertIs<ValidationResult.Failure>(result)
        assertContentEquals(listOf("message 2", "message 3"), result.violations.map { it.message })
    }

    @Test
    fun definining_the_root_path_in_the_configuration_will_prepend_all_the_paths_in_the_returned_constraint_violations() {
        // Arrange
        val config = Configuration { rootPath("foo", "bar") }
        val constraint = Constraint(false, Validatable(null, "baz"))
        // Act
        val result = runWithConstraintRegistry(null, config) { it.register(constraint) }
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(listOf("foo", "bar", "baz"), result.violations.single().path)
    }

    @Test
    fun definining_the_default_message_in_the_configuration_will_replace_empty_messages_in_constraints() {
        // Arrange
        val config = Configuration { defaultViolationMessage = "default" }
        val constraint = Constraint(false, Validatable(null))
        // Act
        val result = runWithConstraintRegistry(null, config) { it.register(constraint) }
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals("default", result.violations.single().message)
    }

    @Test
    fun calling__checkFirstViolationConfiguration__with_failOnFirstViolation_set_to_true_skips_all_the_upcoming_constraints_after_the_first_constraint_violation() {
        // Arrange
        val config = Configuration { failOnFirstViolation = true }
        val constraint1 = Constraint(false, Validatable(null)) otherwise { "first message" }
        val constraint2 = Constraint(false, Validatable(null)) otherwise { "second message" }
        // Act
        val result = runWithConstraintRegistry(null, config) {
            it.register(constraint1)
            it.checkFirstViolationConfiguration()
            it.register(constraint2)
        }
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals("first message", result.violations.single().message)
    }

    @Test
    fun calling__checkFirstViolationConfiguration__with_failOnFirstViolation_set_to_false_executes_all_the_constraints_before_returning_a_failure() {
        // Arrange
        val config = Configuration { failOnFirstViolation = false }
        val constraint1 = Constraint(false, Validatable(null)) otherwise { "first message" }
        val constraint2 = Constraint(false, Validatable(null)) otherwise { "second message" }
        // Act
        val result = runWithConstraintRegistry(null, config) {
            it.register(constraint1)
            it.checkFirstViolationConfiguration()
            it.register(constraint2)
        }
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(2, result.violations.size)
        assertContentEquals(listOf("first message", "second message"), result.violations.map { it.message })
    }
}
