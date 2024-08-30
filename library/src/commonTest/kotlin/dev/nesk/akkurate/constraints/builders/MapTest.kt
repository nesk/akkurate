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
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!! THE FOLLOWING CODE MUST BE SYNCED ACROSS MULTIPLE FILES: Array.kt, Iterable.kt, Collection.kt, Map.kt !!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * The validation API is nearly the same across `Array`, `Collection`, `Iterable` and `Map` types but, due to
 * missing union types in Kotlin, we must duplicate a lot of code between these types.
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
    fun __isEmpty__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isEmpty().satisfied)
    }

    @Test
    fun __isEmpty__succeeds_when_the_value_is_empty() {
        assertTrue(EMPTY_VALIDATABLE.isEmpty().satisfied)
    }

    @Test
    fun __isEmpty__fails_when_the_value_is_not_empty() {
        assertFalse(VALIDATABLE.isEmpty().satisfied)
    }

    //endregion

    //region isNotEmpty

    @Test
    fun __isNotEmpty__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isNotEmpty().satisfied)
    }

    @Test
    fun __isNotEmpty__succeeds_when_the_value_is_not_empty() {
        assertTrue(VALIDATABLE.isNotEmpty().satisfied)
    }

    @Test
    fun __isNotEmpty__fails_when_the_value_is_empty() {
        assertFalse(EMPTY_VALIDATABLE.isNotEmpty().satisfied)
    }

    //endregion

    //region hasSizeEqualTo

    @Test
    fun __hasSizeEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeEqualTo__succeeds_when_the_size_is_equal_to_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeEqualTo(EXACT_SIZE).satisfied)
    }

    @Test
    fun __hasSizeEqualTo__fails_when_the_size_is_different_than_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.hasSizeEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
    }

    //endregion

    //region hasSizeNotEqualTo

    @Test
    fun __hasSizeNotEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeNotEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeNotEqualTo__succeeds_when_the_size_is_different_than_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeNotEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is satisfied when the size is greater than the provided one")
        assertTrue(VALIDATABLE.hasSizeNotEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is satisfied when the size is lower than the provided one")
    }

    @Test
    fun __hasSizeNotEqualTo__fails_when_the_size_is_equal_to_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeNotEqualTo(EXACT_SIZE).satisfied)
    }

    //endregion

    //region hasSizeLowerThan

    @Test
    fun __hasSizeLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeLowerThan(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeLowerThan__succeeds_when_the_size_is_lower_than_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeLowerThan(SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun __hasSizeLowerThan__fails_when_the_size_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeLowerThan(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.hasSizeLowerThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region hasSizeLowerThanOrEqualTo

    @Test
    fun __hasSizeLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeLowerThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeLowerThanOrEqualTo__succeeds_when_the_size_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeLowerThanOrEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is satisfied when the size is lower than the provided one")
        assertTrue(VALIDATABLE.hasSizeLowerThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun __hasSizeLowerThanOrEqualTo__fails_when_the_size_is_greater_than_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeLowerThanOrEqualTo(SIZE_MINUS_ONE).satisfied)
    }

    //endregion

    //region hasSizeGreaterThan

    @Test
    fun __hasSizeGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeGreaterThan(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeGreaterThan__succeeds_when_the_size_is_greater_than_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeGreaterThan(SIZE_MINUS_ONE).satisfied)
    }

    @Test
    fun __hasSizeGreaterThan__fails_when_the_size_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeGreaterThan(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
        assertFalse(VALIDATABLE.hasSizeGreaterThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region hasSizeGreaterThanOrEqualTo

    @Test
    fun __hasSizeGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeGreaterThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeGreaterThanOrEqualTo__succeeds_when_the_size_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(VALIDATABLE.hasSizeGreaterThanOrEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is satisfied when the size is greater than the provided one")
        assertTrue(VALIDATABLE.hasSizeGreaterThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun __hasSizeGreaterThanOrEqualTo__fails_when_the_size_is_lower_than_the_provided_one() {
        assertFalse(VALIDATABLE.hasSizeGreaterThanOrEqualTo(SIZE_PLUS_ONE).satisfied)
    }

    //endregion

    //region hasSizeBetween

    @Test
    fun __hasSizeBetween__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasSizeBetween(NULL_SIZE..NULL_SIZE).satisfied)
    }

    @Test
    fun __hasSizeBetween__succeeds_when_the_size_is_between_the_provided_range() {
        assertTrue(VALIDATABLE.hasSizeBetween(SIZE_MINUS_ONE..SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun __hasSizeBetween__fails_when_the_size_is_outside_the_provided_range() {
        @Suppress("EmptyRange")
        assertFalse(VALIDATABLE.hasSizeBetween(1..0).satisfied)
    }

    //endregion


    //region isContainingKey

    @Test
    fun __isContainingKey__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isContainingKey(Int.MAX_VALUE).satisfied)
    }

    @Test
    fun __isContainingKey__succeeds_when_the_value_contains_the_provided_item() {
        assertTrue(VALIDATABLE.isContainingKey(1).satisfied)
    }

    @Test
    fun __isContainingKey__fails_when_the_value_does_not_contain_the_provided_item() {
        assertFalse(VALIDATABLE.isContainingKey(-1).satisfied)
    }

    //endregion

    //region isNotContainingKey

    @Test
    fun __isNotContainingKey__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isNotContainingKey(Int.MAX_VALUE).satisfied)
    }

    @Test
    fun __isNotContainingKey__succeeds_when_the_value_does_not_contain_the_provided_item() {
        assertTrue(VALIDATABLE.isNotContainingKey(-1).satisfied)
    }

    @Test
    fun __isNotContainingKey__fails_when_the_value_contains_the_provided_item() {
        assertFalse(VALIDATABLE.isNotContainingKey(1).satisfied)
    }

    //endregion

    //region isContainingValue

    @Test
    fun __isContainingValue__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isContainingValue(' ').satisfied)
    }

    @Test
    fun __isContainingValue__succeeds_when_the_value_contains_the_provided_item() {
        assertTrue(VALIDATABLE.isContainingValue('o').satisfied)
    }

    @Test
    fun __isContainingValue__fails_when_the_value_does_not_contain_the_provided_item() {
        assertFalse(VALIDATABLE.isContainingValue('a').satisfied)
    }

    //endregion

    //region isNotContainingValue

    @Test
    fun __isNotContainingValue__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isNotContainingValue(' ').satisfied)
    }

    @Test
    fun __isNotContainingValue__succeeds_when_the_value_does_not_contain_the_provided_item() {
        assertTrue(VALIDATABLE.isNotContainingValue('a').satisfied)
    }

    @Test
    fun __isNotContainingValue__fails_when_the_value_contains_the_provided_item() {
        assertFalse(VALIDATABLE.isNotContainingValue('o').satisfied)
    }

    //endregion
}

