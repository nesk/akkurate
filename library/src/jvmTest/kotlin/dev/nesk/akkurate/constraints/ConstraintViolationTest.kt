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

import kotlin.test.*

class ConstraintViolationTest {
    @Test
    fun `'component1' returns the message`() {
        assertEquals("foo", ConstraintViolation("foo", listOf("bar")).component1())
    }

    @Test
    fun `'component2' returns the path`() {
        assertEquals(listOf("bar"), ConstraintViolation("foo", listOf("bar")).component2())
    }

    //region Tests for `equals()` and `hashCode()`

    @Test
    fun `'equals' returns true when all the values are the same`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf("bar"))
        assertTrue(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'path')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf(""))
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'message')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("", listOf("bar"))
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf("bar"))
        assertEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'path')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("foo", listOf(""))
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'message')`() {
        val original = ConstraintViolation("foo", listOf("bar"))
        val other = ConstraintViolation("", listOf("bar"))
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    //endregion
}
