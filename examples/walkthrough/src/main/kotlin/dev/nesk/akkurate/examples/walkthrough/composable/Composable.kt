package dev.nesk.akkurate.examples.walkthrough.composable

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.accessors.each
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.lengthBetween
import dev.nesk.akkurate.constraints.builders.notEmpty
import dev.nesk.akkurate.examples.walkthrough.composable.validation.accessors.books
import dev.nesk.akkurate.examples.walkthrough.composable.validation.accessors.name
import dev.nesk.akkurate.examples.walkthrough.composable.validation.accessors.title
import dev.nesk.akkurate.validateWith

// With composable validation, you can create a validator for a specific class and reuse it inside another validator.
// Here we create a validator for each of our classes: `Book` and `Library`

@Validate
data class Book(val title: String)

@Validate
data class Library(val name: String, val books: Set<Book>)

val validateBook = Validator<Book> {
    title.lengthBetween(5..100)
}

val validateLibrary = Validator<Library> {

    // Let's validate the `Library` properties.
    name.notEmpty()

    // However, we do not have to repeat ourselves to validate the books contained within
    // the library, we can just reuse `validateBook` for each book.
    books.each {
        validateWith(validateBook)
    }

}

fun main() {

    // You can validate a single book with its own validator.
    val book = Book("test")
    validateBook(book)

    // Or validate a library, which will also validate the books contained within.
    val library = Library("", setOf(book))
    validateLibrary(library)

}
