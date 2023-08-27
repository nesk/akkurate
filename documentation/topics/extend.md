# Extend %product%

Although %product% focuses on providing all the necessary constraints to validate day-to-day Kotlin objects, you might
need to write your own constraints for custom business validation.

> Remember, if you're missing a constraint, [report it on our GitHub repository.](%github_product_url%/issues)

## Inline constraints

Sometimes you might need a constraint for only one specific case across the whole codebase; this is where _inline
constraints_ shine. To create one, use `constrain` inside your validator:

```kotlin
@Validate
data class Book(val title: String)

Validator<Book> {
    title.constrain { it.split(" ").size > 2 } otherwise {
        "The title must contain more than two words"
    }
}
```

The `constrain` function accepts a lambda, which must return a `Boolean`; if `true`, the constraint is satisfied;
otherwise a violation is raised.

> As any built-in constraint, this function returns a `Constraint` object, which can be customized with `otherwise` and
> `withPath`.

{style="note"}

## Named constraints

If the same constraint is used multiple times, stop repeating yourself and extract it to an extension function.

Let's reuse our previous example, extract the constraint to an extension function with a `Validatable<String>`
receiver, and name it `hasWordCountGreaterThanTwo`:

```kotlin
Validator<Book> {
    title.hasWordCountGreaterThanTwo() otherwise {
        "The title must contain more than two words"
    }
}

private fun Validatable<String>.hasWordCountGreaterThanTwo() =
    constrain { it.split(" ").size > 2 }
```

However, named constraints can do more than inline ones. First, we can add a parameter to count a variable number of
words:

```kotlin
Validator<Book> {
    title.hasWordCountGreaterThan(2) otherwise {
        "The title must contain more than two words"
    }
}

private fun Validatable<String>.hasWordCountGreaterThan(count: Int) =
    constrain { it.split(" ").size > count }
```

You can also add a default message to explain what happened, in case the developer using your constraint doesn't provide
one:

```kotlin
private fun Validatable<String>.hasWordCountGreaterThan(count: Int) =
    constrain { it.split(" ").size > count } otherwise {
        "Must contain more than $count words"
    }
```

Finally, it might be useful to support nullable values like in %product%'s built-in constraints. The
`constrainIfNotNull` function can be used to create constraints that are always satisfied when the value is `null`
(like described by [the Vacuous Truth principle](https://en.wikipedia.org/wiki/Vacuous_truth#In_computer_programming)):

```kotlin
private fun Validatable<String?>.hasWordCountGreaterThan(count: Int) =
    constrainIfNotNull { it.split(" ").size > count } otherwise {
        "Must contain more than $count words"
    }
```
