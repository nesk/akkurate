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
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapTest {
    //region get

    @Test
    fun __get__returns_a_wrapped_null_when_the_parent_is_null() {
        assertNull(Validatable<Map<Int, Any>?>(null)[1].unwrap())
    }

    @Test
    fun __get__returns_a_wrapped_null_when_the_collection_is_empty() {
        assertNull(Validatable(emptyMap<Int, Any>())[1].unwrap())
    }

    @Test
    fun __get__returns_the_get_value_wrapped_in_Validatable() {
        val validatable = Validatable(mapOf(0 to "foo", 1 to "bar"))[1]
        assertEquals("bar", validatable.unwrap())
        assertContentEquals(listOf("1"), validatable.path())
    }

    //endregion
}
