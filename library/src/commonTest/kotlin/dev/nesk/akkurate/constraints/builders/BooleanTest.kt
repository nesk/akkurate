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

class BooleanTest {
    @Test
    fun __isTrue__succeeds_when_the_value_is_true() {
        assertTrue(Validatable(true).isTrue().satisfied)
    }

    @Test
    fun __isTrue__fails_when_the_value_is_false_or_null() {
        assertFalse(Validatable(false).isTrue().satisfied, "The constraint is not satisfied when the value is false")
        assertFalse(Validatable(null).isTrue().satisfied, "The constraint is not satisfied when the value is null")
    }

    @Test
    fun __isNotTrue__succeeds_when_the_value_is_false_or_null() {
        assertTrue(Validatable(false).isNotTrue().satisfied, "The constraint is satisfied when the value is false")
        assertTrue(Validatable(null).isNotTrue().satisfied, "The constraint is satisfied when the value is null")
    }

    @Test
    fun __isNotTrue__fails_when_the_value_is_true() {
        assertFalse(Validatable(true).isNotTrue().satisfied)
    }

    @Test
    fun __isFalse__succeeds_when_the_value_is_false() {
        assertTrue(Validatable(false).isFalse().satisfied)
    }

    @Test
    fun __isFalse__fails_when_the_value_is_true_or_null() {
        assertFalse(Validatable(true).isFalse().satisfied, "The constraint is not satisfied when the value is true")
        assertFalse(Validatable(null).isFalse().satisfied, "The constraint is not satisfied when the value is null")
    }

    @Test
    fun __isNotFalse__succeeds_when_the_value_is_true_or_null() {
        assertTrue(Validatable(true).isNotFalse().satisfied, "The constraint is satisfied when the value is true")
        assertTrue(Validatable(null).isNotFalse().satisfied, "The constraint is satisfied when the value is null")
    }

    @Test
    fun __isNotFalse__fails_when_the_value_is_false() {
        assertFalse(Validatable(false).isNotFalse().satisfied)
    }
}
