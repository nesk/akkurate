# Getting started

This article will show you how to install %product% and write your first validation code.

## Installation

%product% is shipped with two dependencies; one is the library, the other one is a compiler plugin based on
[KSP](https://kotlinlang.org/docs/ksp-overview.html). Follow the installation instructions below, according to your
project structure.

<procedure title="Install in a single-platform project">

<step>
Add KSP to your plugin list; make sure to <a href="https://github.com/google/ksp/releases">use the appropriate 
version</a>, depending on the Kotlin version you're using.
<br/>
<code-block lang="kotlin">
plugins {
    kotlin("jvm") version "1.9.10"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}
</code-block>
</step>

<step>
Add the dependencies and register the compiler plugin through KSP.
<br/>
<code-block lang="kotlin">
dependencies {
    implementation("dev.nesk.akkurate:akkurate-core:%version%")
    implementation("dev.nesk.akkurate:akkurate-ksp-plugin:%version%")
    ksp("dev.nesk.akkurate:akkurate-ksp-plugin:%version%")
}
</code-block>
</step>

</procedure>

<procedure title="Install in a multiplatform project">

<tip>
<p>Coming soon!</p>
</tip>

</procedure>

## Basic usage

%product% requires two elements to validate an object:

- the `@Validate` annotation, to mark each class you need to validate;
- and the `Validator` interface, to define validation rules for your classes.

Here we want to validate a `Book` class, with its title and release date. Let's create it and add the `@Validate`
annotation to it:

```kotlin
@Validate
data class Book(val title: String, val releaseDate: LocalDateTime)
```

This annotation marks the class for the compiler processor, so it can generate _validatable accessors_ for it. Now,
build the project with `./gradlew build` (or <ui-path> Build | Build Project</ui-path> in IntelliJ IDEA) to trigger code
generation. We will explain this whole concept later.

To apply some constraints, you need to instantiate a `Validator` with a lambda, let's start with an empty one:

```kotlin
val validateBook = Validator<Book> {
    // this: Validatable<Book>
}
```

The lambda receiver is a `Validatable<Book>`, a generic class wrapping each value you access during validation. Its job
is to track the path of each value, provide a <tooltip term="DSL">DSL</tooltip> to ease validation, and act as a
register for the constraints you apply to the value.

Now, let's constrain the title and the release date; the former must contain characters, and the latter cannot be more
than one year later after the current date:

```kotlin
val validateBook = Validator<Book> {
    title.isNotEmpty()

    val oneYearLater = LocalDateTime.now().plusYears(1)
    releaseDate.isBeforeOrEqualTo(oneYearLater)
}
```

You might have noticed that `title` and `releaseDate` aren't properties of the `Validatable<T>` class, but it works
anyway. This is why we had to annotate our data class with `@Validate` and trigger the generation of _validatable
accessors_; those act as a bridge between the `Validatable<T>` class and the underlying value.

Now let's validate a book with our new validator:

```kotlin
val invalidBook = Book(
    title = "",
    releaseDate = LocalDateTime.now().plusYears(3)
)

when (val result = validateBook(invalidBook)) {
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

<include from="apply-constraints.md" element-id="validation-result-sealed-class"/>

Here, the validation failed, so we got the following lines in the output stream:

```text
The book is invalid, here are the errors:
  - [title]: Must not be empty
  - [releaseDate]: Must be before or equal to "2024-08-11T18:56:04.0"
```

Here is the whole code of this example, feel free to play with it in your editor:

```kotlin
package dev.nesk.akkurate.demo

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.annotations.Validate
import dev.nesk.akkurate.constraints.builders.*
import dev.nesk.akkurate.demo.validation.accessors.*
import java.time.LocalDateTime

@Validate
data class Book(val title: String, val releaseDate: LocalDateTime)

val validateBook = Validator<Book> {
    title.isNotEmpty()

    val oneYearLater = LocalDateTime.now().plusYears(1)
    releaseDate.isBeforeOrEqualTo(oneYearLater)
}

fun main() {
    val invalidBook = Book(
        title = "",
        releaseDate = LocalDateTime.now().plusYears(3)
    )

    when (val result = validateBook(invalidBook)) {
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
}

```

{collapsible="true" default-state="collapsed" collapsed-title="BookValidation.kt"}
