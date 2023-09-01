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

class ConstraintViolationSetTest {
    @Test
    fun `the contents are the same than the set provided to the constructor`() {
        // Arrange
        val originalSet = setOf(
            ConstraintViolation("message1", listOf("root", "child1")),
            ConstraintViolation("message2", listOf("root", "child2")),
        )
        // Act
        val errors = ConstraintViolationSet(originalSet)
        // Assert
        assertContentEquals(originalSet, errors.toList())
    }

    @Test
    fun `the grouped messages contain the same data and in the same order`() {
        // Arrange
        val originalSet = setOf(
            ConstraintViolation("message1", listOf("root", "child1")),
            ConstraintViolation("message2", listOf("root", "child2")),
            ConstraintViolation("message3", listOf("root", "child1")),
        )
        val expectedMap = mapOf(
            listOf("root", "child1") to setOf(
                ConstraintViolation("message1", listOf("root", "child1")),
                ConstraintViolation("message3", listOf("root", "child1")),
            ),
            listOf("root", "child2") to setOf(ConstraintViolation("message2", listOf("root", "child2"))),
        )
        // Act
        val errors = ConstraintViolationSet(originalSet).byPath
        // Assert
        assertEquals(expectedMap, errors, "The data is the same but grouped by path")
        assertContentEquals(expectedMap.values, errors.values, "In each group, the messages keep the same order as the original collection")
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when the messages are the same`() {
        val original = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        val other = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when the messages differ`() {
        val original = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        val other = ConstraintViolationSet(emptySet())
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when the messages are the same`() {
        val original = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        val other = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when the messages differ`() {
        val original = ConstraintViolationSet(setOf(ConstraintViolation("foo", listOf("bar"))))
        val other = ConstraintViolationSet(emptySet())
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
