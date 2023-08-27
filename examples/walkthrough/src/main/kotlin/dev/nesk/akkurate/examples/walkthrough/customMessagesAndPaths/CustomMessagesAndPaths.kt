package dev.nesk.akkurate.examples.walkthrough.customMessagesAndPaths

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.accessors.length
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.isGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isNotEmpty
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.constraints.withPath
import dev.nesk.akkurate.examples.walkthrough.customMessagesAndPaths.validation.accessors.description
import dev.nesk.akkurate.examples.walkthrough.customMessagesAndPaths.validation.accessors.title

@Validate
data class Book(val title: String, val description: String)

val validateBook = Validator<Book> {

    // For each constraint, you can use the `otherwise` function to provide a custom message.
    title.isNotEmpty() otherwise { "The title cannot be empty" }

    // You can also redefine the path used by the constraint, this can be useful when you apply
    // a constraint on a deep property, but you want the constraint violation to show the path
    // of a parent property.

    // Without customization, the path will be "description.length" because we apply a constraint directly on the length property.
    description.length.isGreaterThanOrEqualTo(20)

    // Here we override the natural path with "description".
    description.length.isGreaterThanOrEqualTo(20) otherwise { "At least 20 chars" } withPath { absolute("description") }

    // Of course, this is just an example, we already got you covered with builtin constraints like `hasLengthGreaterThanOrEqualTo`.

}

fun main() {

    // Prints the following lines:
    //   title: The title cannot be empty
    //   description.length: Must be greater than or equal to 20
    //   description: At least 20 chars

    val invalidBook = Book(title = "", description = "todo")
    val result = validateBook(invalidBook)
    if (result is ValidationResult.Failure) {
        for (violation in result.violations) {
            println("${violation.path.joinToString(".")}: ${violation.message}")
        }
    }

}

