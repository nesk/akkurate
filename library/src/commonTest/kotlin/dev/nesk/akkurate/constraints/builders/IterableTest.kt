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

import dev.nesk.akkurate.test.Validatable
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

class IterableTest {
    companion object {
        private val VALUE = listOf('f', 'o', 'o')
        private val VALIDATABLE = Validatable(VALUE)
        private val NULL_VALIDATABLE = Validatable<List<*>?>(null)
    }

    //region isContaining

    @Test
    fun __isContaining__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isContaining(' ').satisfied)
    }

    @Test
    fun __isContaining__succeeds_when_the_value_contains_the_provided_element() {
        assertTrue(VALIDATABLE.isContaining('o').satisfied)
    }

    @Test
    fun __isContaining__fails_when_the_value_does_not_contain_the_provided_element() {
        assertFalse(VALIDATABLE.isContaining('a').satisfied)
    }

    //endregion

    //region isNotContaining

    @Test
    fun __isNotContaining__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.isNotContaining(' ').satisfied)
    }

    @Test
    fun __isNotContaining__succeeds_when_the_value_does_not_contain_the_provided_element() {
        assertTrue(VALIDATABLE.isNotContaining('a').satisfied)
    }

    @Test
    fun __isNotContaining__fails_when_the_value_contains_the_provided_element() {
        assertFalse(VALIDATABLE.isNotContaining('o').satisfied)
    }

    //endregion

    //region hasNoDuplicates

    @Test
    fun __hasNoDuplicates__succeeds_when_the_value_is_null() {
        assertTrue(NULL_VALIDATABLE.hasNoDuplicates().satisfied)
    }

    @Test
    fun __hasNoDuplicates__succeeds_when_the_value_contains_unique_elements() {
        assertTrue(Validatable(VALUE.toSet().toTypedArray()).hasNoDuplicates().satisfied)
    }

    @Test
    fun __hasNoDuplicates__fails_when_the_value_contains_duplicated_elements() {
        assertFalse(VALIDATABLE.hasNoDuplicates().satisfied)
    }

    //endregion
}
