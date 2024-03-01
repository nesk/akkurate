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

Sometimes, you need to apply a constraint depending on the result of a previous one, like Twitter has to do in its
settings when a user wants to update its username.

When Twitter launched, you could pick a one-character username. However, this is no longer possible; you're now forced
to use at least five characters.

Now imagine being Twitter's product owner for today's settings page. Someone tries to change its username to “a”. This
is less than five characters, and it already exists. So, what errors do you want to display?

1. Two errors? One about character count, and another one about an already taken username?
2. Or only one error about character count?

The answer is the second one:

![A single input contains the value "a" while an error message is displayed below. The error states "Your username must be longer than 4 characters."](twitter-too-short.png "Twitter's form to change a username")
{width="597" height="156"}

![A single input contains the value "johndoe" while an error message is displayed below. The error states "That username has been taken. Please choose another."](twitter-taken.png "Twitter's form to change a username")
{width="597" height="156"}

Since new users can't register with handles shorter than 5 characters anyway, Twitter chose to skip the database check
when the username is too short. Avoiding an unnecessary database query and restraining error spamming for the user.

This is a typical use case for conditional constraints. When a constraint is applied, you can read its `satisfied`
property to check if the constraint is satisfied or not.

```kotlin
interface UserDao {
    fun existsByUsername(username: String): Boolean
}

// For the sake of the example, let's assume we've got the following
// global variable, acting as a DAO (Data Access Object).
val userDao = object : UserDao {
    override fun existsByUsername(username: String): Boolean = TODO()
}

@Validate
data class UserUpdate(val username: String)

Validator<UserUpdate> {
    // Retrieve the satisfied status of the constraint.
    val (isValidUsername) = username.hasLengthGreaterThanOrEqualTo(5)

    // Run the database check only if the last constraint succeeded.
    if (isValidUsername) {
        username.constrain {
            !userDao.existsByUsername(it)
        } otherwise { "This username is already taken" }
    }
}
```

> The `satisfied` property can be read by referencing its name: \
> `val constraintOk = (prop.isNotEmpty()).satisfied` \
> \
> or by using [destructuring](%destructuring_url%): \
> `val (constraintOk) = prop.isNotEmpty()` \
> \
> We recommend using the destructuring form, since its easier to read.

{style="note"}

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

## Transformation

Before validating some data, you might need to normalize it.

Imagine you add support for hashtags on the `Book` class:

```kotlin
@Validate
data class Book(val title: String, val hashtags: Set<String>)

val validateBook = Validator<Book> {
    title.isNotEmpty()
    hashtags.each { isNotEmpty() }
}
```

However, your users might prefix a hashtag with a `#`, which means the book will be considered valid if a hashtag
contains this single character:

```kotlin
// This validation will succeed despite the book containing
// a hashtag with a single `#` character.
validateBook(
    Book(
        title = "The Lord of the Rings",
        hashtags = setOf("heroic", "#fantasy", "#")
    )
)
```

To solve this, the solution would be to trim this character before validation, this can be done with
the [`map`](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.validatables/map.html) function:

```kotlin
val validateBook = Validator<Book> {
    title.isNotEmpty()
    hashtags.each {
        map { it.trimStart('#') }.isNotEmpty()
    }
}
```
