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

package dev.nesk.akkurate.arrow

import arrow.core.Either
import arrow.core.NonEmptySet
import arrow.core.raise.either
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.ConstraintViolation
import dev.nesk.akkurate.constraints.builders.isIdenticalTo
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertSame

class ArrowKtTest {
    interface SomeSealedClass {
        data object Valid : SomeSealedClass
        data object Invalid : SomeSealedClass
    }

    companion object {
        val validate = Validator<SomeSealedClass> {
            isIdenticalTo(SomeSealedClass.Valid)
        }
    }

    @Test
    fun __toEither__returns_the_validated_value_in_case_of_success() {
        val validationResult = validate(SomeSealedClass.Valid)
        val validationEither = validationResult.toEither()

        assertIs<ValidationResult.Success<SomeSealedClass.Valid>>(validationResult)
        assertIs<Either.Right<SomeSealedClass.Valid>>(validationEither, "The `Either` instance is a `Right` type")
        assertSame(
            validationResult.value,
            validationEither.value,
            "The `Either.Right` value is the same than the one in the `ValidationResult`"
        )
    }

    @Test
    fun __toEither__returns_the_violations_constraints_in_case_of_failure() {
        val validationResult = validate(SomeSealedClass.Invalid)
        val validationEither = validationResult.toEither()

        assertIs<ValidationResult.Failure>(validationResult)
        assertIs<Either.Left<NonEmptySet<ConstraintViolation>>>(validationEither, "The `Either` instance is a `Left` type")
        assertSame(
            validationResult.violations.single(),
            validationEither.value.single(),
            "The `Either.Left` contains the same constraint violation than the one in the `ValidationResult`"
        )
    }

    @Test
    fun bind_returns_the_validated_value_in_case_of_success() {
        val validationResult = validate(SomeSealedClass.Valid)
        val validationEither = either { bind(validationResult) }

        assertIs<ValidationResult.Success<SomeSealedClass.Valid>>(validationResult)
        assertIs<Either.Right<SomeSealedClass.Valid>>(validationEither, "The `Either` instance is a `Right` type")
        assertSame(
            validationResult.value,
            validationEither.value,
            "The `Either.Right` value is the same than the one in the `ValidationResult`"
        )
    }

    @Test
    fun bind_returns_the_violations_constraints_in_case_of_failure() {
        val validationResult = validate(SomeSealedClass.Invalid)
        val validationEither = either { bind(validationResult) }

        assertIs<ValidationResult.Failure>(validationResult)
        assertIs<Either.Left<NonEmptySet<ConstraintViolation>>>(validationEither, "The `Either` instance is a `Left` type")
        assertSame(
            validationResult.violations.single(),
            validationEither.value.single(),
            "The `Either.Left` contains the same constraint violation than the one in the `ValidationResult`"
        )
    }
}
