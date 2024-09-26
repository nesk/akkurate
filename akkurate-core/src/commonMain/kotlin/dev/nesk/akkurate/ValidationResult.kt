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

package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.ConstraintViolationSet

public sealed interface ValidationResult<out T> {
    public fun orThrow()

    public class Success<T> internal constructor(public val value: T) : ValidationResult<T> {
        public operator fun component1(): T = value

        override fun orThrow(): Unit = Unit

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Success<*>

            return value == other.value
        }

        override fun hashCode(): Int = value?.hashCode() ?: 0
        override fun toString(): String = "ValidationResult.Success(value=$value)"
    }

    // This could be a data class in the future if Kotlin adds a feature to remove the `copy()` method when a constructor is internal or private.
    // https://youtrack.jetbrains.com/issue/KT-11914
    public class Failure internal constructor(public val violations: ConstraintViolationSet) : ValidationResult<Nothing> {
        public operator fun component1(): ConstraintViolationSet = violations

        override fun orThrow(): Nothing = throw Exception(violations)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Failure

            return violations == other.violations
        }

        override fun hashCode(): Int = violations.hashCode()
        override fun toString(): String = "ValidationResult.Failure(violations=$violations)"
    }

    public class Exception internal constructor(public val violations: ConstraintViolationSet) : RuntimeException() {
        override fun toString(): String = "ValidationResult.Exception(violations=$violations)"
    }
}
