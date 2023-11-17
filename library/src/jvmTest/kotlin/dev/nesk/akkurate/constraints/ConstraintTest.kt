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
import dev.nesk.akkurate.validatables.Validatable
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class ConstraintTest {
    private fun ConstraintBuilder(satisfied: Boolean) = Constraint(satisfied, Validatable(null))

    @Test
    fun `the default message is empty`() {
        assertEquals("", ConstraintBuilder(false).message)
    }

    @Test
    fun `the first destructuring component returns the value of the 'satisfied' property`() {
        val satisfied = listOf(true, false).random()
        assertEquals(satisfied, ConstraintBuilder(satisfied).component1())
    }

    @Test
    fun `'ConstraintViolation' conversion reuses the message and the path`() {
        // Arrange
        val builder = ConstraintBuilder(false) otherwise { "foo" } withPath { absolute("bar") }
        // Act
        val violation = builder.toConstraintViolation("default", listOf())
        // Assert
        assertEquals("foo", violation.message)
        assertEquals(listOf("bar"), violation.path)
    }

    @Test
    fun `'ConstraintViolation' conversion replaces empty messages with the default message`() {
        // Arrange
        val builder = ConstraintBuilder(false)
        // Act
        val violation = builder.toConstraintViolation("default", listOf())
        // Assert
        assertEquals("default", violation.message)
    }

    @Test
    fun `'ConstraintViolation' conversion prepends the path with the root path`() {
        // Arrange
        val builder = ConstraintBuilder(false) withPath { absolute("foo") }
        // Act
        val violation = builder.toConstraintViolation("default", listOf("root"))
        // Assert
        assertEquals(listOf("root", "foo"), violation.path)
    }

    @Test
    fun `'ConstraintViolation' conversion fails with a satisfied constraint`() {
        assertThrows<IllegalArgumentException> {
            ConstraintBuilder(true).toConstraintViolation("default", listOf())
        }
    }

    @Test
    fun `calling 'otherwise' with a lambda updates the message if the constraint is not satisfied`() {
        val constraint = ConstraintBuilder(false) otherwise { "foo" }
        assertEquals("foo", constraint.message)
    }

    @Test
    fun `calling 'otherwise' with a lambda does not update the message if the constraint is satisfied`() {
        val constraint = ConstraintBuilder(true) otherwise { "foo" }
        assertEquals("", constraint.message)
    }

    @Test
    fun `calling 'withPath' with a lambda updates the path if the constraint is not satisfied`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(false, validatable)
        // Act
        val path = constraint.withPath { appended("bar") }.path
        // Assert
        assertEquals(listOf("foo", "bar"), constraint.path)
    }

    @Test
    fun `calling 'withPath' with a lambda does not update the path if the constraint is satisfied`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        val constraint = Constraint(true, validatable)
        // Act
        val path = constraint.withPath { relative("bar") }.path
        // Assert
        assertEquals(listOf("foo"), constraint.path)
    }

    @Test
    fun `calling 'constrain' with a truthy lambda creates a satisfied constraint and doesn't register it`() {
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
    fun `calling 'constrain' with a falsy lambda creates and registers an unsatisfied constraint with the validatable path`() {
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
    fun `calling 'constrain' with failOnFirstViolation=true throws a FirstViolationException before the execution of the next constraint after an unsatisfied one`() {
        // Arrange
        val constraintRegistry = ConstraintRegistry(Configuration { failOnFirstViolation = true })
        val validatable = Validatable(null, constraintRegistry)
        var secondConstraintHasExecuted = false
        // Act
        validatable.constrain { false } otherwise { "message 1" }
        val exception = assertThrows<FirstViolationException> {
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
    fun `calling 'constrainIfNotNull' acts as 'constrain' when the wrapped value is not null`() {
        // Arrange
        val validatable = Validatable("foo" as String?)
        // Act
        val constraint = validatable.constrainIfNotNull { false }
        // Assert
        assertFalse(constraint.satisfied, "The constraint is unsatisfied")
        assertEquals(validatable.path(), constraint.path, "The constraint path is the same as the validatable")
    }

    @Test
    fun `calling 'constrainIfNotNull' returns a satisfied constraint when the wrapped value is null`() {
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
    fun `'equals' returns true when all the values are the same`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'satisfied')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) otherwise { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'validatable path')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo")) otherwise { "baz" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'message')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'satisfied')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(true, Validatable("foo", "bar")) otherwise { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'validatable path')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo")) otherwise { "baz" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'message')`() {
        val original = Constraint(false, Validatable("foo", "bar")) otherwise { "baz" }
        val other = Constraint(false, Validatable("foo", "bar")) otherwise { "" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
