package dev.nesk.akkurate.examples.walkthrough.throwable

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.notEmpty
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
    title.notEmpty()
}
