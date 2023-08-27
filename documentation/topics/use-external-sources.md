# Use external sources

Data validation can sometimes rely on external sources, a database, an HTTP API, a file, among other things. %product%
comes with a mechanic of contextual validation to allow using those external sources during validation.

## Contextual validation

[Remember the code example about Twitter for conditional constraints?](complex-structures.md#conditional-constraints) We
were using the following global variable to query our database:

```kotlin
val userDao = object {
    fun existsByHandle(handle: String) = true // fake implementation
}
```

However, instead of global variable, there is a high chance you store your DAO instance inside a container and inject it
wherever you need.

Contextual validation allows you to pass a context to your validator, in addition to the validated object. To use a
context with a validator, instead of instantiating `Validator<ValueType>`, we
instantiate `Validator<ContextType, ValueType>`.

Here's a validator checking if the validated string is contained within the contextual list:

```kotlin
val validateContains = Validator<List<String>, String> { list ->
    // list: List<String>
    constrain { list.contains(it) }
}
```

The context type is passed first, then the value type. The context will then be passed as the first parameter of the
lambda.

To run the validation, call your validator with the context first, and the value next:

```kotlin
val names = listOf("john", "sarah", "alex")

validateContains(names, "sarah") // success
```

> We're not supporting
> [Kotlin's context receivers](https://github.com/Kotlin/KEEP/blob/310b1f798edd5313dbc48b2f54a234ffee5d6314/proposals/context-receivers.md)
> yet. Because they're still experimental, the implementation and the API aren't stable, and they don't
> provide as much flexibility as our custom implementation.

{style="note"}

### Keep the same context across multiple validations

You don't need to provide the context for each validation. Especially when you want to provide the context higher in the
code hierarchy and let some more specific code provide the object to validate.

You can solve this by [currying](https://en.wikipedia.org/wiki/Currying) your validator:

```kotlin
val validateContainsName = validateContains(names)

validateContainsName("sarah") // success
validateContainsName("trudy") // failure
```

### Use multiple contextual objects

When you need to pass multiple objects as a context, create a data class to wrap them:

```kotlin
interface BookDao
interface UserDao

data class BookUserContext(val bookDao: BookDao, val userDao: UserDao)

Validator<BookUserContext, Any> { context ->
    context.bookDao // BookDao 
    context.userDao // UserDao 
}
```

Use [destructuring](https://kotlinlang.org/docs/destructuring-declarations.html#destructuring-in-lambdas) to write more
concise code:

```kotlin
Validator<BookUserContext, Any> { (bookDao, userDao) ->
    bookDao // BookDao 
    userDao // UserDao 
}
```

## Suspendable validation

If your external source is asynchronous, like an HTTP API, you have to make your validator suspendable by calling
`Validator.suspendable`:

```kotlin
interface BookHttpApi {
    suspend fun existsById(id: Int)
}

@Validate
data class Book(val id: Int)

val validateBook = Validator.suspendable<BookHttpApi, Book> { api ->
    constrain { !api.existsById(it.id) } otherwise {
        "This book already exists"
    }
}
```

The generated validator can only be called from a suspendable function:

```kotlin
suspend fun main() {
    validateBook(bookHttpApiInstance, Book(id = 1))
}
```
