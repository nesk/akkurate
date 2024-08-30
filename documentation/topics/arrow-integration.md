# Arrow

[Arrow](https://arrow-kt.io/) is a functional programming library, for which %product% provides an integration to
convert its validation results
to [Arrow's typed errors.](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/)

This article will show you what conversions are possible and how they can help you bridge the gap between %product% and
Arrow.

![A code example of the Arrow integration used to showcase %product% on social networks.](social-arrow.png)
{width="700" border-effect="rounded"}

## Installation

Before using %product% with Arrow, you need to add the following dependency to your Gradle script:

<procedure title="Install %product%'s support library for Arrow">

<code-block lang="kotlin">
implementation("dev.nesk.akkurate:akkurate-arrow:%version%")
</code-block>

</procedure>

## Convert to the Either type

By calling [toEither](%api_reference_url%/akkurate-arrow/dev.nesk.akkurate.arrow/to-either.html), you can convert the
validation result to an `Either`.

Take the following validator:

```kotlin
@Validate
data class Book(val title: String)

val validateBook = Validator<Book> {
    title.isNotEmpty()
}
```

You obtain a validation result by calling the `validateBook()` function:

```kotlin
val validBook = Book(title = "The Lord of the Rings")

val result: ValidationResult<Book> = validateBook(validBook)
```

Then, you can convert it by calling the `toEither()` extension function:

```kotlin
val eitherResult: Either<NonEmptySet<ConstraintViolation>, Book> =
    result.toEither()
```

On a successful validation, you can read the validated value; on a failed one, you can list all the constraint
violations:

```kotlin
when (eitherResult) {
    is Either.Left -> {
        println("The book is invalid: ${eitherResult.value}")
    }

    is Either.Right -> {
        println("The book is valid: ${eitherResult.value}")
    }
}
```

However, converting the validation result to an `Either` type doesn't bring much if you only use `when` expressions.
Arrow is a _functional_ library after all, so you should probably take advantage of it:

```kotlin
val message = eitherResult
    .map { "The book is valid: $it" }
    .getOrElse { "The book is invalid: $it" }

println(message)
```

## Bind to Raise computation

When working with Arrow's functional programming paradigms, the bind function is crucial in handling validations
seamlessly, especially when dealing with multiple `Either` types. This is particularly useful when you're already inside
a Raise computation.

Let's consider an example where we have multiple data classes to validate:

```kotlin
@Validate
data class Book(val title: String)

@Validate
data class Author(val name: String)

val validateBook = Validator<Book> {
    title.isNotEmpty()
}

val validateAuthor = Validator<Author> {
    name.isNotEmpty()
}
```

In a typical scenario, you might want to validate a `Book` and an `Author` within the same context. Without bind, you
would convert each validation result to an `Either` and handle them separately, which can be cumbersome:

```kotlin
// Validate the data and convert the results to Either
val bookResult = validateBook(Book("The Lord of the Rings")).toEither()
val authorResult = validateAuthor(Author("J.R.R. Tolkien")).toEither()

// Handle the validation results separately
val book = when (bookResult) {
    is Either.Left -> throw IllegalArgumentException("Invalid book")
    is Either.Right -> bookResult.value
}
val author = when (authorResult) {
    is Either.Left -> throw IllegalArgumentException("Invalid author")
    is Either.Right -> authorResult.value
}

// Further computation with the validated data
println("Validated book: $book")
println("Validated author: $author")
```

However, using `bind` allows you to compute over the happy path by automatically handling errors and focusing on
successful outcomes. When a validation fails, `bind` short-circuits the computation, eliminating the need for explicit
error checking and handling within the `either` block.

```kotlin
either {
    // Directly bind the validation results
    val book = bind(validateBook(Book("The Lord of the Rings")))
    val author = bind(validateAuthor(Author("J.R.R. Tolkien")))

    // Further computation with the validated data
    println("Validated book: $book")
    println("Validated author: $author")
}
```
