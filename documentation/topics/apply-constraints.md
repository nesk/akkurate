# Apply constraints

Constraints are assertions that a condition is true. Applied to a `Validatable`, they allow describing a set of rules
that must be followed by the validated value; otherwise the validation will fail.

> %product% provides [built-in validation constraints.](constraints-reference.md)

## Basic usage

Constraints are extension functions of the `Validatable` class. Some can be applied to all wrapped types, some others
only to specific ones. For example, `equalTo` can be applied to all wrapped types, whereas `hasLengthLowerThan` can only
be applied to `Validatable<CharSequence>`.

To apply a constraint, call it on a property inside a `Validator`:

```kotlin
@Validate
data class Book(val title: String)

Validator<Book> {
    title.isNotEmpty()
}
```

Some constraints also accept parameters:

```kotlin
Validator<Book> {
    title.hasLengthLowerThanOrEqualTo(50)
}
```

## Read constraint violations

<snippet id="validation-result-sealed-class">

Once validation is done, it returns a `ValidationResult`, which is a sealed interface composed of two classes:

- `ValidationResult.Success`: the validation has succeeded, it contains the validated value.
- `ValidationResult.Failure`: the validation has failed, it contains a violation list. Each violation contains the path
  of the property, and the message describing why it failed.

</snippet>

Here's a usage example:

```kotlin
@Validate
data class Book(val title: String, val author: Author)

@Validate
data class Author(val fullName: String)

val validateBook = Validator<Book> {
    title.isNotEmpty()

    author.fullName {
        isNotEmpty()
        isContaining(" ") // We expect at least a space in a full name.
    }
}

val book = Book(title = "", author = Author(fullName = ""))

when (val result = validateBook(book)) {
    is ValidationResult.Success -> {
        println("The book is valid: ${result.value}")
    }

    is ValidationResult.Failure -> {
        println("The book is invalid, here are the errors:")
        for ((message, path) in result.violations) {
            println("  - $path: $message")
        }
    }
}
```

The output is the following:

```text
The book is invalid, here are the errors:
  - [title]: Must not be empty
  - [author, fullName]: Must not be empty
  - [author, fullName]: Must contain " "
```

### Group violations by path

To group the violations by their path, use `byPath`:

```kotlin
when (val result = validateBook(book)) {
    is ValidationResult.Failure -> {
        println("The book is invalid, here are the errors:")
        for ((path, violation) in result.violations.byPath) {
            println("  - $path")
            for ((message) in violation) {
                println("    - $message")
            }
        }
    }
}
```

The output is the following:

```text
The book is invalid, here are the errors:
  - [title]
    - Must not be empty
  - [author, fullName]
    - Must not be empty
    - Must contain " "
```

### Throw on failed validation

In some cases, validation failure should be treated as an exception. Instead of manually creating and throwing an
exception, one could use `orThrow`:

```kotlin
try {
    val book = Book(title = "", author = Author(fullName = ""))
    validateBook(book).orThrow()
    println("The book is valid: $book")
} catch (exception: ValidationResult.Exception) {
    println("The book is invalid, here are the errors:")
    for ((message, path) in exception.violations) {
        println("  - $path: $message")
    }
}
```

## Customization

Each constraint returns a `Constraint` object, which allows you to customize the message and the path.

### Custom message

Use `otherwise` to provide a custom message for a constraint:

```kotlin
Validator<Book> {
    title.isNotEmpty() otherwise { "Sorry, the title cannot be empty" }
}
```

Remember [the value can be unwrapped](harness-the-dsl.md#unwrapping-the-value), allowing to use it within custom
messages:

```kotlin
Validator<Book> {
    title.hasLengthLowerThanOrEqualTo(50) otherwise {
        "${title.unwrap().length} is greater than 50"
    }
}
```

### Custom path

Use `withPath` combined to `absolute` to provide a custom path for a constraint:

```kotlin
author.fullName.isNotEmpty() withPath { absolute("a", "b", "c") }
```

The constraint violation path will be `[a, b, c]`.

Other methods are available to cover your needs:

absolute
: Generates a new path absolute path. \
\
Code: `absolute("a", "b", "c")` \
Path: `[a, b, c]`

relative
: Replaces the property name by the provided components. \
\
Code: `relative("a", "b", "c")` \
Path: `[author, a, b, c]`

appended
: Appends the provided components to the current path. \
\
Code: `appended("a", "b", "c")` \
Path: `[author, fullName, a, b, c]`

What those methods essentially do is returning a `List<String>`. When in need of greater customization, you can return
the path you want based on the current path, which is passed as a parameter:

```kotlin
author.fullName.isNotEmpty() withPath { path ->
    listOf(path.first, "a", "b", "c", path.last)
}
```

The path will be `[author, a, b, c, fullName]`.

> You can use both `otherwise` and `withPath` on the same constraint. When doing so, we recommend keeping this order
> to improve readability.

## Fail on first violation

In some performance-critical environments, you might need your validator to fail as fast as possible. This can be
enabled [with the
`failOnFirstViolation` configuration option.](%api_reference_url%/akkurate-core/dev.nesk.akkurate/-configuration/fail-on-first-violation.html)

Let's reuse the example of [](apply-constraints.md#read-constraint-violations) and modify its configuration:

```kotlin
val config = Configuration {
    failOnFirstViolation = true
}

val validateBook = Validator<Book>(config) {
    // ...
}
```

The output now contains only one constraint violation, the first one:

```text
The book is invalid, here are the errors:
  - [title]: Must not be empty
```

> It is important to note that _the execution stops right after the first failed constraint,_ and the following ones are
> not executed. This is essential when you want to fail fast for performance or security reasons.

{style="note"}

<seealso style="cards">
  <category ref="related">
    <a href="constraints-reference.md" />
  </category>
</seealso>
