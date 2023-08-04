package dev.nesk.akkurate.examples.walkthrough.context

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.examples.walkthrough.context.validation.accessors.id

// Contextual validation allows you to pass a context (a value) to your
// validator, which you will have access too on each validation.

// Here, our context is a `BookRepository`, with a method to check if a
// book already exists in storage.

class BookRepository {
    fun existsById(id: Int): Boolean = true // fake implementation
}

// We want to validate `BookCreation` and ensure the ID is not already taken.

@Validate
data class BookCreation(val id: Int, val title: String)

// To use a context with your validator, pass the context type as the first
// generic parameter, then the value type.

val validateBookCreation = Validator<BookRepository, BookCreation> { repository ->

    // Now, when validating, you can access your context. Which allows, here,
    // to fail the validation if the book already exists in storage.

    id.constrain { !repository.existsById(it) } otherwise { "This book ID already exists" }

}

fun main() {

    val bookRepository = BookRepository()
    val bookCreation = BookCreation(id = 1, title = "The Lord of the Rings")

    // To provide a context when validating, pass it as the first parameter, then the value to validate.
    validateBookCreation(bookRepository, bookCreation)

    // You can also curry the function to reuse the same context across multiple validations.
    val validateBookWithRepository = validateBookCreation(bookRepository)
    validateBookWithRepository(bookCreation)

}
