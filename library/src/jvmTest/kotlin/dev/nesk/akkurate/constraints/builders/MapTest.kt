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

package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate._test.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!! THE FOLLOWING CODE MUST BE SYNCED ACROSS `ArrayTest`, `CollectionTest` AND `MapTest` !!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * The validation API is the same across `Array`, `Collection` and `Map` types, and so are the tests.
 * But, due to missing union types in Kotlin, we must duplicate the code for each of those tests.
 */

class MapTest {
    companion object {
        private val VALUE = mapOf(1 to 'f', 2 to 'o', 3 to 'o')
        private val EXACT_SIZE = VALUE.size
        private val SIZE_PLUS_ONE = VALUE.size + 1
        private val SIZE_MINUS_ONE = VALUE.size - 1
        private val VALIDATABLE = Validatable(VALUE)
        private val EMPTY_VALIDATABLE = Validatable(emptyMap<Int, Char>())

        private const val NULL_SIZE = Int.MAX_VALUE // no matter the size
        private val NULL_VALIDATABLE = Validatable<Map<*, *>?>(null)
    }

    //region isEmpty

    @Test
    fun `'isEmpty' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isEmpty().satisfied)
    }

    @Test
    fun `'isEmpty' succeeds when the value is empty`() {
        assertTrue(EMPTY_VALIDATABLE.isEmpty().satisfied)
    }

    @Test
    fun `'isEmpty' fails when the value is not empty`() {
        assertFalse(VALIDATABLE.isEmpty().satisfied)
    }

    //endregion

    //region isNotEmpty

    @Test
    fun `'isNotEmpty' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isNotEmpty().satisfied)
    }

    @Test
    fun `'isNotEmpty' succeeds when the value is not empty`() {
        assertTrue(VALIDATABLE.isNotEmpty().satisfied)
    }

    @Test
    fun `'isNotEmpty' fails when the value is empty`() {
        assertFalse(EMPTY_VALIDATABLE.isNotEmpty().satisfied)
    }

    //endregion

    //region hasSizeEqualTo

    @Test
    fun `'hasSizeEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeEqualTo' succeeds when the size is equal to the provided one`() {
        assertTrue(VALIDATABLE.hasSizeEqualTo(EXACT_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeEqualTo' fails when the size is different than the provided one`() {
        assertFalse(VALIDATABLE.hasSizeEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.hasSizeEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
    }

    //endregion

    //region hasSizeNotEqualTo

    @Test
    fun `'hasSizeNotEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeNotEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeNotEqualTo' succeeds when the size is different than the provided one`() {
        assertTrue(VALIDATABLE.hasSizeNotEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is satisfied when the size is greater than the provided one")
        assertTrue(VALIDATABLE.hasSizeNotEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is satisfied when the size is lower than the provided one")
    }

    @Test
    fun `'hasSizeNotEqualTo' fails when the size is equal to the provided one`() {
        assertFalse(VALIDATABLE.hasSizeNotEqualTo(EXACT_SIZE).satisfied)
    }

    //endregion

    //region hasSizeLowerThan

    @Test
    fun `'hasSizeLowerThan' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeLowerThan(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeLowerThan' succeeds when the size is lower than the provided one`() {
        assertTrue(VALIDATABLE.hasSizeLowerThan(SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun `'hasSizeLowerThan' fails when the size is greater than or equal to the provided one`() {
        assertFalse(VALIDATABLE.hasSizeLowerThan(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.hasSizeLowerThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region hasSizeLowerThanOrEqualTo

    @Test
    fun `'hasSizeLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeLowerThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeLowerThanOrEqualTo' succeeds when the size is lower than or equal to the provided one`() {
        assertTrue(VALIDATABLE.hasSizeLowerThanOrEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is satisfied when the size is lower than the provided one")
        assertTrue(VALIDATABLE.hasSizeLowerThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun `'hasSizeLowerThanOrEqualTo' fails when the size is greater than the provided one`() {
        assertFalse(VALIDATABLE.hasSizeLowerThanOrEqualTo(SIZE_MINUS_ONE).satisfied)
    }

    //endregion

    //region hasSizeGreaterThan

    @Test
    fun `'hasSizeGreaterThan' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeGreaterThan(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeGreaterThan' succeeds when the size is greater than the provided one`() {
        assertTrue(VALIDATABLE.hasSizeGreaterThan(SIZE_MINUS_ONE).satisfied)
    }

    @Test
    fun `'hasSizeGreaterThan' fails when the size is lower than or equal to the provided one`() {
        assertFalse(VALIDATABLE.hasSizeGreaterThan(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
        assertFalse(VALIDATABLE.hasSizeGreaterThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region hasSizeGreaterThanOrEqualTo

    @Test
    fun `'hasSizeGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeGreaterThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeGreaterThanOrEqualTo' succeeds when the size is greater than or equal to the provided one`() {
        assertTrue(VALIDATABLE.hasSizeGreaterThanOrEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is satisfied when the size is greater than the provided one")
        assertTrue(VALIDATABLE.hasSizeGreaterThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun `'hasSizeGreaterThanOrEqualTo' fails when the size is lower than the provided one`() {
        assertFalse(VALIDATABLE.hasSizeGreaterThanOrEqualTo(SIZE_PLUS_ONE).satisfied)
    }

    //endregion

    //region hasSizeBetween

    @Test
    fun `'hasSizeBetween' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.hasSizeBetween(NULL_SIZE..NULL_SIZE).satisfied)
    }

    @Test
    fun `'hasSizeBetween' succeeds when the size is between the provided range`() {
        assertTrue(VALIDATABLE.hasSizeBetween(SIZE_MINUS_ONE..SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun `'hasSizeBetween' fails when the size is outside the provided range`() {
        @Suppress("EmptyRange")
        assertFalse(VALIDATABLE.hasSizeBetween(1..0).satisfied)
    }

    //endregion


    //region isContainingKey

    @Test
    fun `'isContainingKey' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isContainingKey(Int.MAX_VALUE).satisfied)
    }

    @Test
    fun `'isContainingKey' succeeds when the value contains the provided item`() {
        assertTrue(VALIDATABLE.isContainingKey(1).satisfied)
    }

    @Test
    fun `'isContainingKey' fails when the value does not contain the provided item`() {
        assertFalse(VALIDATABLE.isContainingKey(-1).satisfied)
    }

    //endregion

    //region isNotContainingKey

    @Test
    fun `'isNotContainingKey' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isNotContainingKey(Int.MAX_VALUE).satisfied)
    }

    @Test
    fun `'isNotContainingKey' succeeds when the value does not contain the provided item`() {
        assertTrue(VALIDATABLE.isNotContainingKey(-1).satisfied)
    }

    @Test
    fun `'isNotContainingKey' fails when the value contains the provided item`() {
        assertFalse(VALIDATABLE.isNotContainingKey(1).satisfied)
    }

    //endregion

    //region isContainingValue

    @Test
    fun `'isContainingValue' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isContainingValue(' ').satisfied)
    }

    @Test
    fun `'isContainingValue' succeeds when the value contains the provided item`() {
        assertTrue(VALIDATABLE.isContainingValue('o').satisfied)
    }

    @Test
    fun `'isContainingValue' fails when the value does not contain the provided item`() {
        assertFalse(VALIDATABLE.isContainingValue('a').satisfied)
    }

    //endregion

    //region isNotContainingValue

    @Test
    fun `'isNotContainingValue' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isNotContainingValue(' ').satisfied)
    }

    @Test
    fun `'isNotContainingValue' succeeds when the value does not contain the provided item`() {
        assertTrue(VALIDATABLE.isNotContainingValue('a').satisfied)
    }

    @Test
    fun `'isNotContainingValue' fails when the value contains the provided item`() {
        assertFalse(VALIDATABLE.isNotContainingValue('o').satisfied)
    }

    //endregion
}

