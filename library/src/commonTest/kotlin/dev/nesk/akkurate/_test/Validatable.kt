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

package dev.nesk.akkurate._test

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate.constraints.ConstraintRegistry
import dev.nesk.akkurate.validatables.Validatable

// A list of helpers to easily instantiate Validatable for testing purposes.

/**
 * Instantiates a root [Validatable] with [a value][wrappedValue], a default [ConstraintRegistry] is automatically provided.
 */
fun <T> Validatable(wrappedValue: T) = Validatable(wrappedValue, ConstraintRegistry(Configuration()))

/**
 * Instantiates a child [Validatable] with [a value][wrappedValue] and [a path segment][pathSegment], a default parent is automatically provided.
 */
fun <T> Validatable(wrappedValue: T, pathSegment: String): Validatable<T> {
    val parent = Validatable(wrappedValue, ConstraintRegistry(Configuration()))
    return Validatable(wrappedValue, pathSegment, parent)
}
