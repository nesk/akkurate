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

package dev.nesk.akkurate.test

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.validatables.Validatable

/**
 * Instantiates a [Validatable] with a value, allowing to test custom constraints.
 *
 * Usage example:
 *
 * ```
 * fun Validatable<String>.hasWordCountGreaterThan(count: Int) =
 *     constrain { it.split(" ").size > count }
 *
 * @Test
 * fun testWordCount() {
 *     assertFalse(Validatable("one two").hasWordCountGreaterThan(2).satisfied)
 *     assertTrue(Validatable("one two three").hasWordCountGreaterThan(2).satisfied)
 * }
 * ```
 */
public fun <T> Validatable(value: T): Validatable<T> {
    lateinit var validatable: Validatable<T>
    val validator = Validator<T> {
        validatable = this
    }
    validator(value)
    return validatable
}
