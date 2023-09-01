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

package dev.nesk.akkurate.examples.walkthrough.throwable

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.isNotEmpty
import dev.nesk.akkurate.examples.walkthrough.throwable.validation.accessors.title

// Instead of handling the result returned by the `validateBook` function,
// you can call `orThrow` to throw an exception if the validation failed.

fun main() {

    // Success, does not throw
    val validBook = Book("The Lord of the Rings")
    validateBook(validBook).orThrow()

    // Failure, an exception is thrown
    try {
        val invalidBook = Book("")
        validateBook(invalidBook).orThrow()
    } catch (exception: ValidationResult.Exception) {
        // Prints the following lines:
        //   title: Cannot be empty
        for (violation in exception.violations) {
            println("${violation.path.joinToString(".")}: ${violation.message}")
        }
    }

}

@Validate
data class Book(val title: String)

val validateBook = Validator<Book> {
    title.isNotEmpty()
}
