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

import dev.nesk.akkurate.ValidationResult.Failure
import dev.nesk.akkurate.ValidationResult.Success
import dev.nesk.akkurate.constraints.ConstraintViolationSet
import dev.nesk.akkurate.constraints.GenericConstraintViolationSet
import dev.nesk.akkurate.validatables.DefaultMetadataType

/**
 * The result of a validation. Can be [a successful outcome][Success] with the validated value, or [a failure][Failure] with a violation list.
 */
public sealed interface ValidationResult<out E, out T> {
    /**
     * Throws an [Exception] if the result is a failure.
     */
    public fun orThrow()

    public fun orThrow(toDefaultMetadata: (E) -> DefaultMetadataType)

    /**
     * A successful outcome to the validation, with [the validated value][value].
     */
    public class Success<T> internal constructor(
        /**
         * The subject of the validation result.
         */
        public val value: T,
    ) : ValidationResult<Nothing, T> {
        /**
         * Returns the [value].
         */
        public operator fun component1(): T = value

        override fun orThrow(): Unit = Unit
        override fun orThrow(toDefaultMetadata: (Nothing) -> DefaultMetadataType): Unit = Unit

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
    /**
     * A failed outcome to the validation, with a violation list describing what went wrong.
     */
    public class Failure<E> internal constructor(
        /**
         * A list of failed constraint violations.
         */
        public val violations: GenericConstraintViolationSet<E>,
    ) : ValidationResult<E, Nothing> {
        /**
         * Returns the [violations].
         */
        public operator fun component1(): GenericConstraintViolationSet<E> = violations

        override fun orThrow(): Nothing = orThrow { emptyMap() }
        override fun orThrow(toDefaultMetadata: (E) -> DefaultMetadataType): Nothing =
            throw Exception(
                ConstraintViolationSet(violations.map { it.copy(metadata = toDefaultMetadata(it.metadata)) }.toSet())
            )


        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Failure<E>

            return violations == other.violations
        }

        override fun hashCode(): Int = violations.hashCode()
        override fun toString(): String = "ValidationResult.Failure(violations=$violations)"

    }

    /**
     * A failed outcome to the validation, which can be thrown.
     */
    public class Exception internal constructor(
        /**
         * A list of failed constraint violations.
         */
        public val violations: ConstraintViolationSet,
    ) : RuntimeException() {
        override fun toString(): String = "ValidationResult.Exception(violations=$violations)"
    }
}
