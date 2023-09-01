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

package dev.nesk.akkurate._test

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.assertTrue

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 *
 * This implementation is specific for [Validatable]. It checks if the wrapped value and the path, are equal.
 * Instead of relying on [Validatable.equals], which only compares the wrapped value.
 */
fun <T> assertEquals(expected: Validatable<T>, actual: Validatable<T>, message: String? = null) {
    if (expected isNotEqualTo actual) {
        assertTrue(false, messagePrefix(message) + "Expected <$expected>, actual <$actual>.")
    }
}

/**
 * Asserts that the [expected] iterable is *structurally* equal to the [actual] iterable,
 * i.e. contains the same number of the same elements in the same order, with an optional [message].
 *
 * The elements are compared for equality with a special implementation for [Validatable].
 * It checks if the wrapped value and the path, are equal. Instead of relying on [Validatable.equals],
 * which only compares the wrapped value.
 */
fun <T> assertContentEquals(expected: Iterable<Validatable<T>>, actual: Iterable<Validatable<T>>, message: String? = null) {
    if (expected === actual) return

    var index = 0
    val expectedIt = expected.iterator()
    val actualIt = actual.iterator()

    while (expectedIt.hasNext() && actualIt.hasNext()) {
        val expectedElement = expectedIt.next()
        val actualElement = actualIt.next()

        if (expectedElement isNotEqualTo actualElement) {
            assertTrue(false, messagePrefix(message) + "Iterable elements differ at index $index. Expected element <$expectedElement>, actual element <${actualElement}>.")
        }

        index++
    }

    if (expectedIt.hasNext()) {
        check(!actualIt.hasNext())

        assertTrue(false, messagePrefix(message) + "Iterable lengths differ. Expected length is bigger than $index, actual length is $index.")
    }

    if (actualIt.hasNext()) {
        check(!expectedIt.hasNext())

        assertTrue(false, messagePrefix(message) + "Iterable lengths differ. Expected length is $index, actual length is bigger than $index.")
    }
}

/**
 * Returns `true` when the two [Validatable] are different, either by their wrapped value or by their path.
 */
private infix fun <T> Validatable<T>.isNotEqualTo(other: Validatable<T>): Boolean {
    if (this === other) return false
    return this.unwrap() != other.unwrap() || this.path() != other.path()
}

private fun messagePrefix(message: String?) = if (message == null) "" else "$message. "

