package dev.nesk.akkurate.examples.walkthrough.unwrap

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.lengthLowerThan
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
    title.lengthLowerThan(10) otherwise { "This title is too long: \"$unwrappedTitle\"" }
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
