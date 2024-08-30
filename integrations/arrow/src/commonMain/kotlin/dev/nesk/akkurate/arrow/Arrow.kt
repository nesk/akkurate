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

import arrow.core.*
import arrow.core.raise.Raise
import arrow.core.raise.either
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.constraints.ConstraintViolation
import dev.nesk.akkurate.constraints.ConstraintViolationSet

private fun ConstraintViolationSet.toNonEmptySet(): NonEmptySet<ConstraintViolation> =
    nonEmptySetOf(this.first(), *this.drop(1).toTypedArray())

/**
 * Transforms the [ValidationResult] to [Either.Right] of [T], or [Either.Left] of [ConstraintViolation] list.
 *
 * You can convert the result of the validation to an [Either] and apply transformations to it:
 * ```
 * val validateBookName = Validator<String> { isNotBlank() }
 *
 * fun normalizeBookName(bookName: String) = validateBookName(bookName)
 *     .toEither()
 *     .fold(
 *         ifLeft = { "<error: ${it.head.message}>" },
 *         ifRight = { it.trim() },
 *     )
 * ```
 *
 * A non-blank string is trimmed because the validation was successful and went through the `ifRight` block:
 * ```
 * normalizeBookName("  The Lord of the Rings  ") // Returns: "The Lord of the Rings"
 * ```
 *
 * Whereas, a blank string fails the validation and goes through the `ifLeft` block:
 * ```
 * normalizeBookName("  ") // Returns: "<error: Must not be blank>"
 * ```
 */
public fun <T> ValidationResult<T>.toEither(): Either<NonEmptySet<ConstraintViolation>, T> = when (this) {
    is ValidationResult.Failure -> violations.toNonEmptySet().left()
    is ValidationResult.Success -> value.right()
}

/**
 * Binds the [ValidationResult] to the [Raise] computation.
 *
 * You can bind the result of the validation to a `Raise` computation:
 * ```
 * val validateBookName = Validator<String> { isNotBlank() }
 * val validateAuthorName = Validator<String> { isNotBlank() }
 *
 * fun combineBookAndAuthor(bookName: String, authorName: String) = either {
 *     val book = bind(validateBookName(bookName))
 *     val author = bind(validateAuthorName(authorName))
 *     "${book.trim()} by ${author.trim()}"
 * }
 * ```
 *
 * Non-blank strings returns [Either.Right] because the validation was successful and the [either] block could complete:
 * ```
 * combineBookAndAuthor("  The Lord of the Rings  ", "  J. R. R. Tolkien  ")
 * // Returns: Either.Right(value = "The Lord of the Rings by J. R. R. Tolkien")
 * ```
 *
 * Whereas, a single blank string fails the validation and interrupts the [either] block, returning a [Either.Left] of constraint violations:
 * ```
 * normalizeBookName("  ", "  J. R. R. Tolkien  ") // Returns: Either.Left<NonEmptySet<ConstraintViolation>>
 * ```
 *
 * @return The validated value on successful result.
 */
public fun <T> Raise<NonEmptySet<ConstraintViolation>>.bind(validationResult: ValidationResult<T>): T = when (validationResult) {
    is ValidationResult.Failure -> raise(validationResult.violations.toNonEmptySet())
    is ValidationResult.Success -> validationResult.value
}
