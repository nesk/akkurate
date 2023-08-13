package dev.nesk.akkurate.examples.walkthrough.dsl

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.lengthGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.lengthLowerThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.notEmpty
import dev.nesk.akkurate.examples.walkthrough.dsl.validation.accessors.author
import dev.nesk.akkurate.examples.walkthrough.dsl.validation.accessors.firstName
import dev.nesk.akkurate.examples.walkthrough.dsl.validation.accessors.identity
import dev.nesk.akkurate.examples.walkthrough.dsl.validation.accessors.lastName
import dev.nesk.akkurate.validatables.and

// The library DSL is here to help you write validation code just like you would
// write classic code. But you also get various features to avoid repetition.

val validateBook = Validator<Book> {

    // To access a deeply nested property, you can write your code just like you would outside the
    // validator. The difference is, instead of retrieving the original value of each property, you
    // get a `Validatable` wrapping it, which allows you to apply constraints.
    author.identity.firstName.notEmpty()

    // The library DSL allows you to avoid repetition of long paths, you can nest your
    // code if you want to validate multiple properties under a specific path.

    author.identity {
        firstName.lengthGreaterThanOrEqualTo(2)
        lastName.lengthGreaterThanOrEqualTo(3)

        // Through the DSL you can also apply the same constraints for multiple properties.
        // Here, both `firstName` and `lastName` will be constrained by a maximum length.

        (firstName and lastName) {
            lengthLowerThanOrEqualTo(50)
        }
    }

}

@Validate
data class AuthorIdentity(val firstName: String, val lastName: String)

@Validate
data class Author(val identity: AuthorIdentity)

@Validate
data class Book(val author: Author)
