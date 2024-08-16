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
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatTest {
    @Test
    fun `'Float hasFractionalCountEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<Float?>(null).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' throws an exception when the provided count is lower than 1`() {
        assertFailsWith<IllegalArgumentException> {
            Validatable(123.45f).hasFractionalCountEqualTo(0)
        }
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' succeeds when the number of digits is equal to the provided one`() {
        assertTrue(Validatable(123.45f).hasFractionalCountEqualTo(2).satisfied)
    }

    @Test
    fun `'Float hasFractionalCountEqualTo' fails when the number of digits is different than the provided one`() {
        assertFalse(Validatable(123.45f).hasFractionalCountEqualTo(5).satisfied)
    }
}
