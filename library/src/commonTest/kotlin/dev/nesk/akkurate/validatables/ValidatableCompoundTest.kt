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

import dev.nesk.akkurate._test.Validatable
import dev.nesk.akkurate._test.assertContentEquals
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidatableCompoundTest {
    @Test
    fun a_validatable_compound_is_created_when_using_the__and__function_between_two_validatables() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        // Act
        val compound = foo and bar
        // Assert
        assertContentEquals(
            setOf(foo, bar),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun a_new_validatable_compound_is_created_when_using_the__and__function_between_a_compound_and_a_validatable() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        // Act
        val compound = (foo and bar) and baz
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun a_new_validatable_compound_is_created_when_using_the__and__function_between_a_validatable_and_a_compound() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        // Act
        val compound = foo and (bar and baz)
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun a_new_validatable_compound_is_created_when_using_the__and__function_between_two_compounds() {
        // Arrange
        val foo = Validatable("foo", "foo")
        val bar = Validatable("bar", "bar")
        val baz = Validatable("baz", "baz")
        val qux = Validatable("qux", "qux")
        // Act
        val compound = (foo and bar) and (baz and qux)
        // Assert
        assertContentEquals(
            setOf(foo, bar, baz, qux),
            compound.validatables.asIterable(),
            "The compound contains the involved validatables in the same order",
        )
    }

    @Test
    fun invoking_a_validatable_compound_with_a_lambda_executes_that_lambda_for_each_validatable_in_the_compound_order() {
        // Arrange
        val validatables = listOf(
            Validatable("foo", "foo"),
            Validatable("bar", "bar"),
        )
        val compound = ValidatableCompound(validatables)
        // Act
        val receivedValidatables = buildList {
            compound { add(this) }
        }
        // Assert
        assertContentEquals(validatables, receivedValidatables, "The lambda has been called for each validatable in the compound order")
    }

    @Test
    fun a_validatable_compound_containing_two_validatables_with_the_same_value_will_call_the_lambda_twice_and_not_just_once_due_to__equals__implementation() {
        // Arrange
        val validatables = listOf(
            Validatable("", "foo"),
            Validatable("", "bar"),
        )
        val compound = ValidatableCompound(validatables)
        // Act
        val receivedValidatables = buildList {
            compound { add(this) }
        }
        // Assert
        assertEquals(2, receivedValidatables.size, "The lambda has been called twice")
    }
}
