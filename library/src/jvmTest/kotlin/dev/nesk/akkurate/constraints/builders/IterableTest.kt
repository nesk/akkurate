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

class IterableTest {
    companion object {
        private val VALUE = listOf('f', 'o', 'o')
        private val VALIDATABLE = Validatable(VALUE)
        private val NULL_VALIDATABLE = Validatable<List<*>?>(null)
    }

    //region isContaining

    @Test
    fun `'isContaining' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isContaining(' ').satisfied)
    }

    @Test
    fun `'isContaining' succeeds when the value contains the provided element`() {
        assertTrue(VALIDATABLE.isContaining('o').satisfied)
    }

    @Test
    fun `'isContaining' fails when the value does not contain the provided element`() {
        assertFalse(VALIDATABLE.isContaining('a').satisfied)
    }

    //endregion

    //region isNotContaining

    @Test
    fun `'isNotContaining' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.isNotContaining(' ').satisfied)
    }

    @Test
    fun `'isNotContaining' succeeds when the value does not contain the provided element`() {
        assertTrue(VALIDATABLE.isNotContaining('a').satisfied)
    }

    @Test
    fun `'isNotContaining' fails when the value contains the provided element`() {
        assertFalse(VALIDATABLE.isNotContaining('o').satisfied)
    }

    //endregion
}
