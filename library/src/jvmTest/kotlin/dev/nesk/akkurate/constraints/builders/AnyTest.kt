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

class AnyTest {
    @Test
    fun `'isNull' succeeds when the value is null`() {
        assertTrue(Validatable(null).isNull().satisfied)
    }

    @Test
    fun `'isNull' fails when the value is not null`() {
        assertFalse(Validatable("foo").isNull().satisfied)
    }

    @Test
    fun `'isNotNull' succeeds when the value is not null`() {
        assertTrue(Validatable("foo").isNotNull().satisfied)
    }

    @Test
    fun `'isNotNull' fails when the value is null`() {
        assertFalse(Validatable(null).isNotNull().satisfied)
    }

    @Test
    fun `'isEqualTo' succeeds when the provided value is equal to the validated one`() {
        assertTrue(Validatable("foo").isEqualTo("foo").satisfied)
    }

    @Test
    fun `'isEqualTo' fails when the provided value is different from the validated one`() {
        assertFalse(Validatable("foo").isEqualTo("bar").satisfied)
    }

    @Test
    fun `'isEqualTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertTrue(Validatable("foo").isEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'isEqualTo' fails when the value of the provided validatable is different from the validated one`() {
        assertFalse(Validatable("foo").isEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the provided value is different from the validated one`() {
        assertTrue(Validatable("foo").isNotEqualTo("bar").satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the provided value is equal to the validated one`() {
        assertFalse(Validatable("foo").isNotEqualTo("foo").satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the value of the provided validatable is different from the validated one`() {
        assertTrue(Validatable("foo").isNotEqualTo(Validatable("bar")).satisfied)
    }

    @Test
    fun `'isNotEqualTo' succeeds when the value of the provided validatable is equal to the validated one`() {
        assertFalse(Validatable("foo").isNotEqualTo(Validatable("foo")).satisfied)
    }

    @Test
    fun `'isIdenticalTo' succeeds when the provided value is the same as the validated one`() {
        val value = object {}
        assertTrue(Validatable(value).isIdenticalTo(value).satisfied)
    }

    @Test
    fun `'isIdenticalTo' fails when the provided value is not the same as the validated one`() {
        assertFalse(Validatable(object {}).isIdenticalTo(object {}).satisfied)
    }

    @Test
    fun `'isNotIdenticalTo' succeeds when the provided value is not the same as the validated one`() {
        assertTrue(Validatable(object {}).isNotIdenticalTo(object {}).satisfied)
    }

    @Test
    fun `'isNotIdenticalTo' fails when the provided value is the same as the validated one`() {
        val value = object {}
        assertFalse(Validatable(value).isNotIdenticalTo(value).satisfied)
    }
}
