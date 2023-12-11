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

package dev.nesk.akkurate.examples.ktor.server.plugins

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.hasLengthLowerThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isMatching
import dev.nesk.akkurate.constraints.builders.isNotBlank
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.ktor.server.plugins.validation.accessors.isbn
import dev.nesk.akkurate.examples.ktor.server.plugins.validation.accessors.title
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import dev.nesk.akkurate.ValidationResult.Failure as AkkurateFailure
import dev.nesk.akkurate.ValidationResult.Success as AkkurateSuccess

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Book> { book ->
            when (val result = validateBook(bookDao, book)) {
                is AkkurateSuccess -> ValidationResult.Valid
                is AkkurateFailure -> {
                    val reasons = result.violations.map {
                        "${it.path.joinToString(".")}: ${it.message}"
                    }
                    ValidationResult.Invalid(reasons)
                }
            }
        }
    }
}

val validateBook = Validator.suspendable<BookDao, Book> { dao ->
    isbn {
        val (isValidIsbn) = isMatching(Regex("""\d{13}""")) otherwise {
            "Must be a valid ISBN (13 digits)"
        }

        if (isValidIsbn) {
            constrain { !dao.existsWithIsbn(it) } otherwise {
                "This ISBN is already registered"
            }
        }
    }

    title {
        isNotBlank()
        hasLengthLowerThanOrEqualTo(50)
    }
}
