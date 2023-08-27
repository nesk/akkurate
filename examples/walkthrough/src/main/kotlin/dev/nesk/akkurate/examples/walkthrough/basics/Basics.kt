package dev.nesk.akkurate.examples.walkthrough.basics

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.hasLengthGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isNotEmpty
import dev.nesk.akkurate.examples.walkthrough.basics.validation.accessors.description
import dev.nesk.akkurate.examples.walkthrough.basics.validation.accessors.title

@Validate // Add the `Validate` annotation to the classes you want to validate.
data class Book(val title: String, val description: String)

// Instantiate a new validator for one of your annotated classes (here, we only have `Book`).
// The provided lambda will be called with a `Validatable<Book>` receiver.

val validateBook = Validator<Book> {

    // The `Validatable` type wraps the original value, its role is to:
    //   – Provide access to the appropriate constraints, based on the wrapped type.
    //   — Track the path of the current property being constrained.
    //   — Provide a nice DSL.

    // When constraining a property, you access it first, then apply a constraint on it.
    title.isNotEmpty() // The title must not be empty
    description.hasLengthGreaterThanOrEqualTo(20) // The description must contain at least 20 chars

}

// Let's look how to use our new validator

fun main() {

    // Success
    val validBook = Book(title = "The Lord of the Rings", description = "An epic high-fantasy novel")
    printResult(validateBook(validBook))

    // Failure, prints the following lines:
    //   title: Cannot be empty
    //   description: Must contain at least 20 characters
    val invalidBook = Book(title = "", description = "todo")
    printResult(validateBook(invalidBook))

}

fun printResult(result: ValidationResult<Book>) = when (result) {
    is ValidationResult.Success -> println("Success: ${result.value}")
    is ValidationResult.Failure -> {
        for ((message, path) in result.violations) {
            println("${path.joinToString(".")}: ${message}")
        }
    }
}
