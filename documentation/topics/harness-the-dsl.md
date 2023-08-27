# Harness the DSL

%product% provides a <tooltip term="DSL">DSL</tooltip> to help you write clear and concise code. With it, you can
validate your data in a more declarative manner, improving readability and maintainability.

## Access properties

To access a property, write its name:

```kotlin
@Validate
data class Book(val title: String)

Validator<Book> {
    title.isNotEmpty()
}
```

> Remember, before accessing the properties inside the `Validator`, to trigger code generation by building your
> project.

{style="note"}

To access a deep property, write the path to it, like ordinary Kotlin code:

```kotlin
@Validate
data class Book(val author: Author)

@Validate
data class Author(val user: User)

@Validate
data class User(val emailAddress: String)

Validator<Book> {
    author.user.emailAddress.isNotEmpty()
}
```

## Avoid path repetition

When applying multiple constraints to a single property, it can become cumbersome to write its path each time to
constrain it. You can avoid repetition by writing the path and invoking it with a lambda:

```kotlin
Validator<Book> { // this: Validatable<Book>
    author.user.emailAddress { // this: Validatable<String>
        isNotEmpty()
        isContaining("@")
    }
}
```

## Apply the same constraints to multiple properties

When you need to validate multiple properties in the same manner, you can use _validatable compounds_ to write the
constraints only once.

To showcase this, let's add new properties to the `User` class:

```kotlin
@Validate
data class User(
    val emailAddress: String,

    // Extend the `User` class with new properties
    val firstName: String,
    val middleName: String,
    val lastName: String
)
```

Now you can either apply the same constraints to each property, or use a validatable compound:

<compare
    type="top-bottom"
    first-title="Without validatable compounds"
    second-title="With a validatable compound">

```kotlin
Validator<User> {
    firstName.isNotEmpty()
    firstName.hasLengthLowerThanOrEqualTo(50)
    middleName.isNotEmpty()
    middleName.hasLengthLowerThanOrEqualTo(50)
    lastName.isNotEmpty()
    lastName.hasLengthLowerThanOrEqualTo(50)
}
```

```kotlin
Validator<User> {
    (firstName and middleName and lastName) {
        isNotEmpty()
        hasLengthLowerThanOrEqualTo(50)
    }
}
```

</compare>

> Due to Kotlin's precedence, the parentheses around the property names are mandatory when composing a validatable
> compound.

## Use nullable types

When manipulating nullable types, there is no need to handle nullability during path traversal. Let's demonstrate this
by making the author nullable inside `Book`:

```kotlin
@Validate
data class Book(val author: Author?)
```

When accessing `author`, it returns a `Validatable<Author?>` type:

```kotlin
Validator<Book> {
    author // Validatable<Author?>
}
```

Even if `author` is nullable, it is possible to not bother and access its child properties without any nullability
management. However, _the nullability will propagate to the children:_

<compare
    type="top-bottom"
    first-title="Without nullables"
    second-title="With a single nullable, propagating to its children">

```kotlin
@Validate
data class Book(val author: Author)

Validator<Book> {
    author // Validatable<Author>
    author.user // Validatable<User>
    author.user.emailAddress // Validatable<String>
}
```

```kotlin
@Validate
data class Book(val author: Author?)

Validator<Book> {
    author // Validatable<Author?>
    author.user // Validatable<User?>
    author.user.emailAddress // Validatable<String?>
}
```

</compare>

This behavior allows applying constraints on a deep property without having to care about nullability. If the property
is null, _the constraint will always succeed:_

```kotlin
Validator<Book> {
    // This constraint will always succeed when
    // the value of the property is `null`.
    author.user.emailAddress.isNotEmpty()
}
```

If you want to enforce your users to provide a value for a property, use the `isNotNull` constraint:

```kotlin
Validator<Book> {
    author.user.emailAddress {
        isNotNull() // Fails if the value is null.
        isNotEmpty() // When not null, checks for emptiness.
    }
}
```

## Unwrapping the value

Sometimes it might be necessary to access the original value of the property. When calling `unwrap()` on a
`Validatable<T>`, it returns the `T` value that was wrapped:

```kotlin
@Validate
data class Book(val title: String)

val validateBook = Validator<Book> {
    println(title)
    println(title.unwrap())
}

validateBook(Book(title = "The Lord of the Rings"))

// prints: Validatable(unwrap=The Lord of the Rings, path=[title])
// prints: The Lord of the Rings
```

You can also use destructuring:

```kotlin
val validateBook = Validator<Book> {
    val (unwrappedTitle) = title
    println(unwrappedTitle)
}

validateBook(Book(title = "The Lord of the Rings"))

// prints: The Lord of the Rings
```
