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
    fun `calling 'register' with a satisfied constraint leaves the collection empty`() {
        // Arrange
        val constraint = Constraint(true, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertTrue(registry.toSet().isEmpty(), "The collection is empty")
    }

    @Test
    fun `calling 'register' with an unsatisfied constraint adds it to the collection`() {
        // Arrange
        val constraint = Constraint(false, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single(), "The single item is identical to the registered constraint")
    }

    @Test
    fun `calling 'register' with a constraint violation adds it to the collection`() {
        // Arrange
        val constraint = Constraint(false, Validatable(null))
        val registry = ConstraintRegistry(Configuration())
        // Act
        registry.register(constraint)
        // Assert
        assertSame(constraint, registry.toSet().single(), "The single item is identical to the registered constraint")
    }

    @Test
    fun `calling 'register' multiples times with constraints adds them to the collection if they're unsatisfied`() {
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
    fun `'runWithConstraintRegistry' returns a validation success if all the constraint where satisfied`() {
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
    fun `'runWithConstraintRegistry' returns a validation failure if any constraint was unsatisfied`() {
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
    fun `definining the root path in the configuration will prepend all the paths in the returned constraint violations`() {
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
    fun `definining the default message in the configuration will replace empty messages in constraints`() {
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
    fun `calling 'checkFirstViolationConfiguration' with failOnFirstViolation=true skips all the upcoming constraints after the first constraint violation`() {
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
    fun `calling 'checkFirstViolationConfiguration' with failOnFirstViolation=false executes all the constraints before returning a failure`() {
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
