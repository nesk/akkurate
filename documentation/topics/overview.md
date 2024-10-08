# Overview

%product% is a validation library taking advantage of the expressive power of Kotlin. No need \
for 30+ annotations or complicated custom constraints; write your validation code in Kotlin with a beautiful declarative
API.

Designed from scratch to handle complex business logic, its role is to help you write qualitative and maintainable
validation code.

![A code example of %product% used to showcase the library on social networks.](social.png)
{width="700" border-effect="rounded"}

> %product% is under development and, despite being heavily tested, its API isn't yet stabilized; _breaking changes
> might happen on minor releases._ However, [we will always provide migration guides.](migration-guide.md)
>
> Report any issue or bug <a href="%github_product_url%/issues">in the GitHub repository.</a>

{style="warning"}

## Showcase

Here's how you can constrain a book and its list of authors.

```kotlin
// Define your classes

@Validate
data class Book(
    val title: String,
    val releaseDate: LocalDateTime,
    val authors: List<Author>,
)

@Validate
data class Author(val firstName: String, val lastName: String)

// Write your validation rules

val validateBook = Validator<Book> {
    // First the property, then the constraint, finally the message.
    title.isNotEmpty() otherwise { "Missing title" }

    releaseDate.isInPast() otherwise { "Release date must be in past" }

    authors.hasSizeBetween(1..10) otherwise { "Wrong author count" }

    authors.each { // Apply constraints to each author
        (firstName and lastName) {
            // Apply the same constraint to both properties
            isNotEmpty() otherwise { "Missing name" }
        }
    }
}

// Validate your data

when (val result = validateBook(someBook)) {
    is Success -> println("Success: ${result.value}")
    is Failure -> {
        val list = result.violations
            .joinToString("\n") { "${it.path}: ${it.message}" }
        println("Failures:\n$list")
    }
}
```

Notice how each constraint applied to a property can be read like a sentence. This code:

```kotlin
title.isNotEmpty() otherwise { "Missing title" }
```

can be read:

```text
Check if 'title' is not empty otherwise write "Missing title".
```

## Features

- [**Beautiful DSL and API.**](harness-the-dsl.md) Write crystal clear validation code and keep it <tooltip term="DRY">
  DRY</tooltip>. Use loops and conditions when needed; forget about annotation hell.
- [**Bundled with all the essential constraints.**](constraints-reference.md) Write custom constraints for your business
  logic and nothing more. The rest? It's on us!
- [**Easily and highly extendable.**](extend.md) No need to write verbose code to create custom constraints, within
  %product% they're as simple as a lambda with parameters.
- [**Contextual and asynchronous.**](use-external-sources.md) Query sync/async data sources whenever you need to, like a
  database, or a REST API. Everything can happen inside validation.
- [**Integrated with your favorite tools.**](integrations.topic) Validate your data within any popular framework, we
  take care of the integrations for you.
- [**Code once, deploy everywhere.**](getting-started.md#installation) Take advantage of Kotlin Multiplatform; write
  your validation code once, use it both for front-end and back-end usages.
- [**Testable out of the box.**](extend.md#test-your-code) Finding how to test your validation code shouldn't be one of
  your tasks. You will find all the tools you need to write good tests.

<seealso style="links">
  <category ref="external">
    <a href="%api_reference_url%">API Reference</a>
    <a href="%github_product_url%">GitHub repository</a>
    <a href="%roadmap_url%">Roadmap</a>
    <a href="https://slack-chats.kotlinlang.org/c/akkurate">#akkurate channel on Kotlin Slack</a>
  </category>
</seealso>
