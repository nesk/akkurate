# Complex structures

Validating complex structures can quickly become a challenge. %product% has been designed from scratch for this task and
provides you everything you need for any complexity levels.

## Structured programming

Despite using a declarative approach for validation, %product% doesn't enforce you to use it and even encourages you to
switch to imperative code when needed.

### Loops

When validating arrays, maps or iterables, you can use a normal loop to iterate over each item:

```kotlin
@Validate
data class Library(val books: Set<Book>)

@Validate
data class Book(val title: String)

Validator<Library> {
    for (book in books) {
        book.title.isNotEmpty()
    }
}
```

Since iterating over a collection to validate its items is a common task, %product% also provides the `each` helper:

```kotlin
Validator<Library> {
    books.each { // this: Validatable<Book>
        title.isNotEmpty()
    }
}
```

### Conditions

You can use conditions to apply constraints only when needed. Let's say our `Library` has a maximum capacity:

```kotlin
@Validate
data class Library(val books: Set<Book>, val maximumCapacity: Int)
```

The maximum capacity is infinite when it's equal to 0, so we want to constrain the size of the book collection only if
the maximum capacity is at least 1. To achieve this, we can unwrap the value, add a condition, and create a constraint
only if its positive.

```kotlin
Validator<Library> {
    val (max) = maximumCapacity
    if (max > 0) {
        books.hasSizeLowerThanOrEqualTo(max) otherwise {
            "Too many books"
        }
    }
}
```

#### Conditional constraints

Sometimes, you need to apply a constraint depending on the result of a previous one.

Let's take Twitter as an example. When it launched, you could register with a one-character handle. However, this is no
longer possible; you're now forced to use at least five characters.

Now imagine being Twitter's product owner for today's registration page. Someone tries to register with the “a” handle.
This is less than five characters, and it already exists. So, what errors do you want to display?

* Two errors? One about character count, and another one about an already taken handle?
* Or only one error about character count?

Since new users can't register with handles shorter than 5 characters anyway, you might want to skip the database check
when the handle is too short.

This is a typical case for conditional constraints. When a constraint is applied, you can read its `satisfied`
property to check if the constraint is satisfied or not. You can also use destructuring, _which is easier to read._

```kotlin
// For the sake of the example, let's assume we've got the following
// global variable, acting as a DAO (Data Access Object).
val userDao = object {
    fun existsByHandle(handle: String) = true // fake implementation
}

@Validate
data class TwitterRegistration(val handle: String)

Validator<TwitterRegistration> {
    // Retrieve the status of the constraint.
    val (isValidHandle) = handle.hasLengthGreaterThanOrEqualTo(5)

    // Run the database check only if the last constraint succeeded.
    if (isValidHandle) {
        handle.constrain {
            !userDao.existsByHandle(it)
        } otherwise { "This handle is already taken" }
    }
}
```

## Composition

It is common to validate the same properties in different places. To avoid applying the same constraints multiple times,
you can use composition to reuse a validator inside another one.

Here we have a `Person` class used by two different properties: `author` and `reviewers`. To avoid code repetition, we
create a validator for the `Person` class and then call it from `Validator<Book>`.

```kotlin
@Validate
data class Book(val author: Person, val reviewers: Set<Person>)

@Validate
data class Person(val fullName: String)

val validatePerson = Validator<Person> {
    fullName.isNotEmpty()
}

val validateBook = Validator<Book> {
    author.validateWith(validatePerson)
    reviewers.each { validateWith(validatePerson) }
}
```

## Comparing values

You might sometimes need to check if two values are equal:

```kotlin
Validator<Library> {
    val library = this
    for (book in books) {
        constrain {
            book.height.unwrap() == library.shelfHeight.unwrap()
        } otherwise { "Book height must be equal to shelf height" }
    }
}
```

However, writing `.unwrap()` everywhere can quickly become cumbersome. This is why `Validatable<T>` implements
`equals` and `hashCode` as pass-through methods to the underlying value. Which means you can remove the calls to
`unwrap` to check for equality:

<compare type="top-bottom" first-title="With explicit usage of unwrap()" second-title="Without unwrap()">

```kotlin
book.height.unwrap() == library.shelfHeight.unwrap()
```

```kotlin
book.height == library.shelfHeight
```

</compare>
