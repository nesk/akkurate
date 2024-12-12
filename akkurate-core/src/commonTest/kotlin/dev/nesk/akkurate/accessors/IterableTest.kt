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
import dev.nesk.akkurate.test.Validatable
import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.*

class IterableTest {
    @Test
    fun __iterator__returns_an__Iterable__wrapping_each_value_of_the_original__Iterable__with_an_indexed_path() {
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
    fun __iterator__returns_a_lazy__Iterable__and_values_are_processed_on_the_fly() {
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
    fun __iterator__returns_an_empty_iterator_when_the_iterable_is_null() {
        // Arrange
        val value: Iterable<Any>? = null
        // Act
        val iterator = Validatable(value).iterator()
        // Assert
        assertFalse(iterator.hasNext(), "The iterator has no next value")
        assertFailsWith<NoSuchElementException>("The iterator cannot be iterated") { iterator.next() }
    }

    //region first

    @Test
    fun __first__returns_a_wrapped_null_when_the_parent_is_null() {
        assertNull(Validatable<Iterable<Any>?>(null).first().unwrap())
    }

    @Test
    fun __first__returns_a_wrapped_null_when_the_collection_is_empty() {
        assertNull(Validatable(emptyList<Any>()).first().unwrap())
    }

    @Test
    fun __first__returns_the_first_value_wrapped_in_Validatable() {
        val validatable = Validatable(listOf("foo", "bar")).first()
        assertEquals("foo", validatable.unwrap())
        assertContentEquals(listOf("first"), validatable.path())
    }

    //endregion

    //region last

    @Test
    fun __last__returns_a_wrapped_null_when_the_parent_is_null() {
        assertNull(Validatable<Iterable<Any>?>(null).last().unwrap())
    }

    @Test
    fun __last__returns_a_wrapped_null_when_the_collection_is_empty() {
        assertNull(Validatable(emptyList<Any>()).last().unwrap())
    }

    @Test
    fun __last__returns_the_last_value_wrapped_in_Validatable() {
        val validatable = Validatable(listOf("foo", "bar")).last()
        assertEquals("bar", validatable.unwrap())
        assertContentEquals(listOf("last"), validatable.path())
    }

    //endregion


    @Test
    fun calling__each__will_execute_the_block_for_each_value_of_the_iterable_and_wrapped_in_Validatable_and_passed_as_the_receiver() {
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
    fun __each__never_executes_the_block_when_the_iterable_is_null() {
        // Arrange
        val validatables: Validatable<Iterable<Any>?> = Validatable(null)
        // Act
        var hasBeenExecuted = false
        validatables.each { hasBeenExecuted = true }
        // Assert
        assertFalse(hasBeenExecuted)
    }
}
