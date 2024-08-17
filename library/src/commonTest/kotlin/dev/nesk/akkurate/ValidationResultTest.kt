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
import dev.nesk.akkurate.constraints.ConstraintViolationSet
import kotlin.test.*

class ValidationResultTest {
    @Test
    fun __Success__can_never_throw() {
        ValidationResult.Success(null).orThrow()
    }

    @Test
    fun __Success__returns_the_value_as_is() {
        val value = object {}
        assertSame(value, ValidationResult.Success(value).value)
    }

    @Test
    fun __Success_component1__returns_the_value() {
        val success = ValidationResult.Success(object {})
        assertSame(success.value, success.component1())
    }

    @Test
    fun __Failure__always_throws_and_contains_the_same_violations_as_the_result() {
        // Arrange
        val violations = ConstraintViolationSet(emptySet())
        val failure = ValidationResult.Failure(violations)
        // Act & Assert
        val exception = assertFailsWith<ValidationResult.Exception> { failure.orThrow() }
        assertSame(violations, exception.violations)
    }

    @Test
    fun __Failure__returns_the_violations_as_is() {
        val violations = ConstraintViolationSet(emptySet())
        assertSame(violations, ValidationResult.Failure(violations).violations)
    }

    @Test
    fun __Failure_component1__returns_the_violations() {
        val failure = ValidationResult.Failure(ConstraintViolationSet(emptySet()))
        assertSame(failure.violations, failure.component1())
    }

    //region Success: tests for `equals()` and `hashCode()`

    @Test
    fun __Success_equals__returns_true_when_all_the_values_are_the_same() {
        val original = ValidationResult.Success("foo")
        val other = ValidationResult.Success("foo")
        assertTrue(original.equals(other))
    }

    @Test
    fun __Success_equals__returns_false_when_at_least_one_of_the_values_differ____variant_value() {
        val original = ValidationResult.Success("foo")
        val other = ValidationResult.Success("bar")
        assertFalse(original.equals(other))
    }

    @Test
    fun __Success_hashCode__returns_the_same_hash_when_all_the_values_are_the_same() {
        val original = ValidationResult.Success("foo")
        val other = ValidationResult.Success("foo")
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __Success_hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_value() {
        val original = ValidationResult.Success("foo")
        val other = ValidationResult.Success("bar")
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion

    //region Failure: tests for `equals()` and `hashCode()`

    @Test
    fun __Failure_equals__returns_true_when_all_the_values_are_the_same() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        assertTrue(original.equals(other))
    }

    @Test
    fun __Failure_equals__returns_false_when_at_least_one_of_the_values_differ____variant_violations() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        val other = ValidationResult.Failure(ConstraintViolationSet(emptySet()))
        assertFalse(original.equals(other))
    }

    @Test
    fun __Failure_hashCode__returns_the_same_hash_when_all_the_values_are_the_same() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        val other = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __Failure_hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_violations() {
        val original = ValidationResult.Failure(ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar")))))
        val other = ValidationResult.Failure(ConstraintViolationSet(emptySet()))
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
