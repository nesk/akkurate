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

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Book> { book ->
            when (val result = validateBook(bookDao, book)) {
                is dev.nesk.akkurate.ValidationResult.Success -> ValidationResult.Valid
                is dev.nesk.akkurate.ValidationResult.Failure -> {
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
            constrain { dao.findByIsbn(it) == null } otherwise {
                "This ISBN is already registered"
            }
        }
    }

    title {
        isNotBlank()
        hasLengthLowerThanOrEqualTo(50)
    }
}
