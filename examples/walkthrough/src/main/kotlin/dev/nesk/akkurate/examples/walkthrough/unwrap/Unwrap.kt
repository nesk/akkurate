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

package dev.nesk.akkurate.examples.walkthrough.unwrap

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.hasLengthLowerThan
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.walkthrough.unwrap.validation.accessors.title

// When accessing a property, you get a `Validatable` wrapping the original value.
// But you can easily access the original value if you need it.

val validateBook = Validator<Book> {

    // You can retrieve the original value by unwrapping the validatable with `unwrap`.
    title.unwrap()

    // You can also unwrap by using destructuring.
    val (unwrappedTitle) = title

    // The unwrapped value can be used however you want, including in constraint messages.
    title.hasLengthLowerThan(10) otherwise { "This title is too long: \"$unwrappedTitle\"" }
}

fun main() {

    // Failure, prints the following lines:
    //   title: This title is too long: "The Lord of the Rings"
    val invalidBook = Book("The Lord of the Rings")
    val result = validateBook(invalidBook)
    if (result is ValidationResult.Failure) {
        for (violation in result.violations) {
            println("${violation.path.joinToString(".")}: ${violation.message}")
        }
    }

}

@Validate
data class Book(val title: String)
