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

package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.ConstraintViolation
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.constraints.withPath
import dev.nesk.akkurate.validatables.validatableOf
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class ValidatorTest {
    private class Context
    private class Value(val name: String = "value")

    private class Third
    private class Second(val third: Third)
    private class First(val second: Second)

    @Test
    fun `a validation with satisfied constraints returns a successful result with the corresponding value`() {
        // Arrange
        val value = Value()
        val validate = Validator<Value> {
            constrain { true }
        }
        // Act
        val result = validate(value)
        // Assert
        assertIs<ValidationResult.Success<Value>>(result, "The result is a success")
        assertSame(result.value, value, "The value of the result is the same as the input")
    }

    @Test
    fun `a validation with unsatisfied constraints returns a failed result with the corresponding violations`() {
        // Arrange
        val validate = Validator<Nothing?> {
            constrain { false } otherwise { "Bad value" } withPath { absolute("path", "to", "value") }
        }
        val expectedViolations = setOf(ConstraintViolation("Bad value", listOf("path", "to", "value")))
        // Act
        val result = validate(null)
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(expectedViolations, result.violations, "The result contains the corresponding violations")
    }

    @Test
    fun `a contextual validation with satisfied constraints returns a successful result`() {
        // Arrange
        val validate = Validator<Context, Nothing?> {
            constrain { true }
        }
        // Act
        val result = validate(Context(), null)
        // Assert
        assertIs<ValidationResult.Success<*>>(result)
    }

    @Test
    fun `a contextual validation with unsatisfied constraints returns a failed result with the corresponding violations`() {
        // Arrange
        val validate = Validator<Context, Nothing?> {
            constrain { false } otherwise { "Bad value" } withPath { absolute("path", "to", "value") }
        }
        val expectedViolations = setOf(ConstraintViolation("Bad value", listOf("path", "to", "value")))
        // Act
        val result = validate(Context(), null)
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(expectedViolations, result.violations, "The result contains the corresponding violations")
    }

    @Test
    fun `an async validation with satisfied constraints returns a successful result`() = runTest {
        // Arrange
        val validate = Validator.suspendable<Nothing?> {
            constrain { true }
        }
        // Act
        val result = validate(null)
        // Assert
        assertIs<ValidationResult.Success<*>>(result)
    }

    @Test
    fun `an async validation with unsatisfied constraints returns a failed result with the corresponding violations`() = runTest {
        // Arrange
        val validate = Validator.suspendable<Nothing?> {
            constrain { false } otherwise { "Bad value" } withPath { absolute("path", "to", "value") }
        }
        val expectedViolations = setOf(ConstraintViolation("Bad value", listOf("path", "to", "value")))
        // Act
        val result = validate(null)
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(expectedViolations, result.violations, "The result contains the corresponding violations")
    }

    @Test
    fun `an async contextual validation with satisfied constraints returns a successful result`() = runTest {
        // Arrange
        val validate = Validator.suspendable<Context, Nothing?> {
            constrain { true }
        }
        // Act
        val result = validate(Context(), null)
        // Assert
        assertIs<ValidationResult.Success<*>>(result)
    }

    @Test
    fun `an async contextual validation with unsatisfied constraints returns a failed result with the corresponding violations`() = runTest {
        // Arrange
        val validate = Validator<Context, Nothing?> {
            constrain { false } otherwise { "Bad value" } withPath { absolute("path", "to", "value") }
        }
        val expectedViolations = setOf(ConstraintViolation("Bad value", listOf("path", "to", "value")))
        // Act
        val result = validate(Context(), null)
        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(expectedViolations, result.violations, "The result contains the corresponding violations")
    }

    @Test
    fun `definining the root path in the configuration will prepend all the paths in the returned constraint violations`() {
        val config = Configuration { rootPath("foo", "bar") }
        val validate = Validator<Value>(config) {
            validatableOf(Value::name).constrain { false }
        }
        val result = validate(Value())
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(listOf("foo", "bar", "name"), result.violations.single().path)
    }

    @Test
    fun `definining the default message in the configuration will replace empty messages in constraints`() {
        val config = Configuration { defaultViolationMessage = "default" }
        val validate = Validator<Nothing?>(config) {
            constrain { false }
        }
        val result = validate(null)
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals("default", result.violations.single().message)
    }

    @Test
    fun `definining failOnFirstViolation=true in the configuration skips all the upcoming constraints after the first constraint violation`() {
        // Arrange
        val config = Configuration { failOnFirstViolation = true }
        val validate = Validator<Nothing?>(config) {
            constrain { false } otherwise { "first message" }
            constrain { false } otherwise { "second message" }
        }

        // Act
        val result = validate(null)

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals("first message", result.violations.single().message)
    }

    @Test
    fun `definining failOnFirstViolation=false in the configuration executes all the constraints before returning a failure`() {
        // Arrange
        val config = Configuration { failOnFirstViolation = false }
        val validate = Validator<Nothing?>(config) {
            constrain { false } otherwise { "first message" }
            constrain { false } otherwise { "second message" }
        }

        // Act
        val result = validate(null)

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertEquals(2, result.violations.size)
        assertContentEquals(listOf("first message", "second message"), result.violations.map { it.message })
    }

    @Test
    fun `a composite validation reports all the contraint violations, including the nested ones`() {
        // Arrange
        val validate3 = Validator<Third> {
            constrain { false } otherwise { "failure3" }
        }
        val validate2 = Validator<Second> {
            validatableOf(Second::third).validateWith(validate3)
            constrain { false } otherwise { "failure2" }
        }
        val validate1 = Validator<First> {
            constrain { false } otherwise { "failure1" }
            validatableOf(First::second).validateWith(validate2)
        }

        val expectedViolations = setOf(
            ConstraintViolation("failure1", listOf()),
            ConstraintViolation("failure3", listOf("second", "third")),
            ConstraintViolation("failure2", listOf("second")),
        )

        // Act
        val result = validate1(First(Second(Third())))

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertContentEquals(expectedViolations, result.violations.toList(), "All the constraints violations are reported")
    }

    @Test
    fun `a contextual composite validation reports all the contraint violations, including the nested ones`() {
        // Arrange
        val validate3 = Validator<Context, Third> {
            constrain { false } otherwise { "failure3" }
        }
        val validate2 = Validator<Context, Second> { context ->
            validatableOf(Second::third).validateWith(validate3, context)
            constrain { false } otherwise { "failure2" }
        }
        val validate1 = Validator<Context, First> { context ->
            constrain { false } otherwise { "failure1" }
            validatableOf(First::second).validateWith(validate2, context)
        }

        val expectedViolations = setOf(
            ConstraintViolation("failure1", listOf()),
            ConstraintViolation("failure3", listOf("second", "third")),
            ConstraintViolation("failure2", listOf("second")),
        )

        // Act
        val result = validate1(Context(), First(Second(Third())))

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertContentEquals(expectedViolations, result.violations.toList(), "All the constraints violations are reported")
    }

    @Test
    fun `an async composite validation reports all the contraint violations, including the nested ones`() = runTest {
        // Arrange
        val validate3 = Validator.suspendable<Third> {
            constrain { false } otherwise { "failure3" }
        }
        val validate2 = Validator.suspendable<Second> {
            validatableOf(Second::third).validateWith(validate3)
            constrain { false } otherwise { "failure2" }
        }
        val validate1 = Validator.suspendable<First> {
            constrain { false } otherwise { "failure1" }
            validatableOf(First::second).validateWith(validate2)
        }

        val expectedViolations = setOf(
            ConstraintViolation("failure1", listOf()),
            ConstraintViolation("failure3", listOf("second", "third")),
            ConstraintViolation("failure2", listOf("second")),
        )

        // Act
        val result = validate1(First(Second(Third())))

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertContentEquals(expectedViolations, result.violations.toList(), "All the constraints violations are reported")
    }

    @Test
    fun `an async contextual composite validation reports all the contraint violations, including the nested ones`() = runTest {
        // Arrange
        val validate3 = Validator.suspendable<Context, Third> {
            constrain { false } otherwise { "failure3" }
        }
        val validate2 = Validator.suspendable<Context, Second> { context ->
            validatableOf(Second::third).validateWith(validate3, context)
            constrain { false } otherwise { "failure2" }
        }
        val validate1 = Validator.suspendable<Context, First> { context ->
            constrain { false } otherwise { "failure1" }
            validatableOf(First::second).validateWith(validate2, context)
        }

        val expectedViolations = setOf(
            ConstraintViolation("failure1", listOf()),
            ConstraintViolation("failure3", listOf("second", "third")),
            ConstraintViolation("failure2", listOf("second")),
        )

        // Act
        val result = validate1(Context(), First(Second(Third())))

        // Assert
        assertIs<ValidationResult.Failure>(result, "The result is a failure")
        assertContentEquals(expectedViolations, result.violations.toList(), "All the constraints violations are reported")
    }
}
