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

package dev.nesk.akkurate.examples.walkthrough.suspendable

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.walkthrough.suspendable.validation.accessors.id

@Validate
data class BookCreation(val id: Int, val title: String)

// Let's reuse the example for contextual validation, where we used a `BookRepository`
// with an `existsById` method. But this time, let's make this method suspendable.

class BookRepository {
    suspend fun existsById(id: Int): Boolean = true // fake implementation
}

// Working with suspendable functions is just like working with normal functions,
// with a slight alteration when creating the validator. Instead of calling `Validator`,
// we now have to call `Validator.suspendable`, and that's it! Now you can use any
// suspendable function inside your validator, anywhere.

val validateBookCreation = Validator.suspendable<BookRepository, BookCreation> { repository ->

    // If you open this example inside IntelliJ, you will see an icon in the left
    // gutter, which indicates a "Suspend function call".

    id.constrain { !repository.existsById(it) } otherwise { "This book ID already exists in our database" }

}

// Since your validator now calls some suspendable functions, the validator itself is now
// suspendable, so you must call it from a coroutine and make that `main` function suspendable.

suspend fun main() {

    val bookRepository = BookRepository()
    val bookCreation = BookCreation(1, "The Lord of the Rings")

    // Validate your data just like you would do with a non-suspendable validator.
    validateBookCreation(bookRepository, bookCreation)

}
