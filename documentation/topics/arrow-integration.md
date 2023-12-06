# Arrow

[Arrow](https://arrow-kt.io/) is a functional programming library, for which %product% provides an integration to
convert its validation results
to [Arrow's typed errors.](https://arrow-kt.io/learn/typed-errors/working-with-typed-errors/)

This article will show you what conversions are possible, and how they can help you bridge the gap between %product% and
Arrow.

![A code example of the Arrow integration used to showcase %product% on social networks.](social-arrow.png)
{width="700" border-effect="rounded"}

## Installation

Before using %product% with Arrow, you need to add the following dependency to your Gradle script:

<procedure title="Install in a single-platform project" id="single-platform-installation">

<code-block lang="kotlin">
dependencies {
    implementation("dev.nesk.akkurate:akkurate-arrow:%version%")
}
</code-block>

</procedure>

<procedure title="Install in a multiplatform project">

<tip>
<p>Coming soon! <a href="%roadmap_url%">See the roadmap for more informations.</a></p>
</tip>

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

Sometimes you're already inside a Raise computation when validating your data. It might be tempting to convert the
validation result to an `Either` and call `bind()` on it:

```kotlin
either {
    validateBook(validBook).toEither().bind()
}
```

However, %product% allows you to bind your validation without any intermediate conversion:

```kotlin
either {
    bind(validateBook(validBook))
}
```
