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

class AnyTest {

    //region isNull

    @Test
    fun isNull__succeeds_when_the_value_is_null() {
        assertTrue(Validatable(null).isNull().satisfied)
    }

    @Test
    fun __isNull__fails_when_the_value_is_not_null() {
        assertFalse(Validatable("foo").isNull().satisfied)
    }

    //endregion

    //region isNotNull

    @Test
    fun __isNotNull__succeeds_when_the_value_is_not_null() {
        assertTrue(Validatable("foo").isNotNull().satisfied)
    }

    @Test
    fun __isNotNull__fails_when_the_value_is_null() {
        assertFalse(Validatable(null).isNotNull().satisfied)
    }

    //endregion

    //region isEqualTo

    @Test
    fun __isEqualTo__succeeds_when_the_provided_value_is_equal_to_the_validated_one() {
        assertTrue(Validatable("foo").isEqualTo("foo").satisfied)
    }

    @Test
    fun __isEqualTo__fails_when_the_provided_value_is_different_from_the_validated_one() {
        assertFalse(Validatable("foo").isEqualTo("bar").satisfied)
    }

    @Test
    fun __isEqualTo__succeeds_when_the_value_of_the_provided_validatable_is_equal_to_the_value_of_the_validated_one() {
        assertTrue(Validatable("foo").isEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun __isEqualTo__fails_when_the_value_of_the_provided_validatable_is_different_from_the_value_of_the_validated_one() {
        assertFalse(Validatable("foo").isEqualTo(Validatable("bar")).satisfied)
    }

    //endregion

    //region isNotEqualTo

    @Test
    fun __isNotEqualTo__succeeds_when_the_provided_value_is_different_from_the_validated_one() {
        assertTrue(Validatable("foo").isNotEqualTo("bar").satisfied)
    }

    @Test
    fun __isNotEqualTo__succeeds_when_the_provided_value_is_equal_to_the_validated_one() {
        assertFalse(Validatable("foo").isNotEqualTo("foo").satisfied)
    }

    @Test
    fun __isNotEqualTo__succeeds_when_the_value_of_the_provided_validatable_is_different_from_the_validated_one() {
        assertTrue(Validatable("foo").isNotEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun __isNotEqualTo__succeeds_when_the_value_of_the_provided_validatable_is_equal_to_the_validated_one() {
        assertFalse(Validatable("foo").isNotEqualTo(Validatable("foo")).satisfied)
    }

    //endregion

    //region isIdenticalTo

    @Test
    fun __isIdenticalTo__succeeds_when_the_provided_value_is_the_same_as_the_validated_one() {
        val value = object {}
        assertTrue(Validatable(value).isIdenticalTo(value).satisfied)
    }

    @Test
    fun __isIdenticalTo__fails_when_the_provided_value_is_not_the_same_as_the_validated_one() {
        assertFalse(Validatable(object {}).isIdenticalTo(object {}).satisfied)
    }

    @Test
    fun __isNotIdenticalTo__succeeds_when_the_provided_value_is_not_the_same_as_the_validated_one() {
        assertTrue(Validatable(object {}).isNotIdenticalTo(object {}).satisfied)
    }

    @Test
    fun __isNotIdenticalTo__fails_when_the_provided_value_is_the_same_as_the_validated_one() {
        val value = object {}
        assertFalse(Validatable(value).isNotIdenticalTo(value).satisfied)
    }

    //endregion

    //region isInstanceOf

    @Test
    fun __isInstanceOf__succeeds_when_the_value_is_of_the_provided_type() {
        assertTrue(Validatable<Any>("foo").isInstanceOf<String>().satisfied)
    }

    @Test
    fun __isInstanceOf__fails_when_the_value_is_not_of_the_provided_type() {
        assertFalse(Validatable<Any>("foo").isInstanceOf<Int>().satisfied)
    }

    //endregion

    //region isNotInstanceOf

    @Test
    fun __isNotInstanceOf__succeeds_when_the_value_is_of_the_provided_type() {
        assertTrue(Validatable<Any>("foo").isNotInstanceOf<Int>().satisfied)
    }

    @Test
    fun __isNotInstanceOf__fails_when_the_value_is_not_of_the_provided_type() {
        assertFalse(Validatable<Any>("foo").isNotInstanceOf<String>().satisfied)
    }

    //endregion
}
