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

package dev.nesk.akkurate.accessors

import dev.nesk.akkurate._test.Validatable
import dev.nesk.akkurate._test.assertContentEquals
import dev.nesk.akkurate.validatables.Validatable
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class IterableTest {
    @Test
    fun `'iterator' returns an 'Iterable' wrapping each value of the original 'Iterable' with an indexed path`() {
        // Arrange
        val value = 1..2
        // Act
        val validatables = Iterable { Validatable(value).iterator() }.toList()
        // Assert
        assertContentEquals(
            setOf(Validatable(1, "0"), Validatable(2, "1")),
            validatables
        )
    }

    @Test
    fun `'iterator' returns a lazy 'Iterable' and values are processed on the fly`() {
        // Arrange
        var current = 0
        val value = object : Iterable<Int> {
            override operator fun iterator() = object : Iterator<Int> {
                override fun hasNext() = current < 3 // Set a limit to avoid infinite iterators
                override fun next() = ++current
            }
        }
        // Act
        val validatableIterator = Validatable(value).iterator()
        validatableIterator.next() // Run this method at least once to ensure everything works
        // Assert
        assertEquals(1, current, "The iterator is lazy, only the first element has been requested.")
    }

    @Test
    fun `'iterator' returns an empty iterator when the iterable is null`() {
        // Arrange
        val value: Iterable<Any>? = null
        // Act
        val iterator = Validatable(value).iterator()
        // Assert
        assertFalse(iterator.hasNext(), "The iterator has no next value")
        assertThrows<NoSuchElementException>("The iterator cannot be iterated") { iterator.next() }
    }

    //region first

    @Test
    fun `'first' returns a wrapped null when the parent is null`() {
        assertNull(Validatable<Iterable<Any>?>(null).first().unwrap())
    }

    @Test
    fun `'first' returns a wrapped null when the collection is empty`() {
        assertNull(Validatable(emptyList<Any>()).first().unwrap())
    }

    @Test
    fun `'first' returns the first value wrapped in Validatable`() {
        val validatable = Validatable(listOf("foo", "bar")).first()
        assertEquals("foo", validatable.unwrap())
        assertContentEquals(listOf("first"), validatable.path())
    }

    //endregion

    //region last

    @Test
    fun `'last' returns a wrapped null when the parent is null`() {
        assertNull(Validatable<Iterable<Any>?>(null).last().unwrap())
    }

    @Test
    fun `'last' returns a wrapped null when the collection is empty`() {
        assertNull(Validatable(emptyList<Any>()).last().unwrap())
    }

    @Test
    fun `'last' returns the last value wrapped in Validatable`() {
        val validatable = Validatable(listOf("foo", "bar")).last()
        assertEquals("bar", validatable.unwrap())
        assertContentEquals(listOf("last"), validatable.path())
    }

    //endregion


    @Test
    fun `calling 'each' will execute the block for each value of the iterable, wrapped in Validatable and passed as the receiver`() {
        // Arrange
        val validatables = Validatable(1..2)
        // Act
        val receivers = mutableListOf<Validatable<Int>>()
        validatables.each { receivers += this }
        // Assert
        assertContentEquals(
            setOf(Validatable(1, "0"), Validatable(2, "1")),
            receivers
        )
    }

    @Test
    fun `'each' never executes the block when the iterable is null`() {
        // Arrange
        val validatables: Validatable<Iterable<Any>?> = Validatable(null)
        // Act
        var hasBeenExecuted = false
        validatables.each { hasBeenExecuted = true }
        // Assert
        assertFalse(hasBeenExecuted)
    }
}
