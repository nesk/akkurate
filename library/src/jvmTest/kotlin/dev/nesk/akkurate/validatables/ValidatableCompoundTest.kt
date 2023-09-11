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

import dev.nesk.akkurate._test.assertContentEquals
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidatableCompoundTest {
    @Test
    fun `a validatable compound is created when using the 'and' function between two validatables`() {
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
    fun `a new validatable compound is created when using the 'and' function between a compound and a validatable`() {
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
    fun `a new validatable compound is created when using the 'and' function between a validatable and a compound`() {
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
    fun `a new validatable compound is created when using the 'and' function between two compounds`() {
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
    fun `invoking a validatable compound with a lambda executes that lambda for each validatable in the compound order`() {
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
    fun `a validatable compound containing two validatables with the same value will call the lambda twice, not just once due to 'equals' implementation`() {
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
