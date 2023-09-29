# Use external sources

Data validation can sometimes rely on external sources, a database, an HTTP API, even a file. %product% comes with a
mechanic of contextual validation to allow using those external sources during validation.

## Contextual validation

[Remember the code example about Twitter for conditional constraints?](complex-structures.md#conditional-constraints) We
were using the following global variable to query our database:

```kotlin
val userDao = object : UserDao {
    override fun existsByUsername(username: String): Boolean = TODO()
}
```

However, instead of using a global variable, you're probably storing your DAO instance inside a container and injecting
it wherever you need.

%product% allows you to pass a context to your validator in addition to the validated object. This is done by
using `Validator<ContextType, ValueType>` to instantiate the validator.

Let's reuse [our Twitter example](complex-structures.md#conditional-constraints) and adapt it with this new feature:

```kotlin
// We remove the global variable and only keep the interface.
interface UserDao {
    fun existsByUsername(username: String): Boolean
}

@Validate
data class UserUpdate(val username: String)

val validate = Validator<UserDao, UserUpdate> { userDao ->
    //     The context is passed as a parameter ^^^^^^^

    val (isValidUsername) = username.hasLengthGreaterThanOrEqualTo(5)

    if (isValidUsername) {
        username.constrain {
            // Use the context wherever you want.
            !userDao.existsByUsername(it)
        } otherwise { "This username is already taken" }
    }
}
```

The _context type_ is passed first, then the value type; while the _context value_ is available as the first parameter
of the lambda.

Now, when calling our validator, we have to provide an instance of `UserDao` before the value to validate:

```kotlin
val someUserDao: UserDao = TODO()
val someUserUpdate: UserUpdate = TODO()

validate(someUserDao, someUserUpdate)
```

> We don't support
> [Kotlin's context receivers](https://github.com/Kotlin/KEEP/blob/310b1f798edd5313dbc48b2f54a234ffee5d6314/proposals/context-receivers.md)
> as they're still experimental (the implementation and API aren't stable). Also, they don't provide as much flexibility
> as our custom implementation.

{style="note"}

### Keep the same context across multiple validations

You don't need to provide the context for each validation. Especially when you want to provide the context higher in the
code hierarchy and let some more specific code provide the object to validate.

You can solve this by [currying](https://en.wikipedia.org/wiki/Currying) your validator:

```kotlin
val validateWithSomeUserDao = validate(someUserDao)
val userUpdate1: UserUpdate = TODO()
val userUpdate2: UserUpdate = TODO()

validateWithSomeUserDao(userUpdate1)
validateWithSomeUserDao(userUpdate2)
```

### Use multiple contextual objects

When you need to pass multiple objects as a context, create a data class to wrap them:

```kotlin
interface TweetDao

data class Context(val userDao: UserDao, val tweetDao: TweetDao)

Validator<Context, Any> { context ->
    context.userDao // UserDao 
    context.tweetDao // TweetDao 
}
```

Use [destructuring](https://kotlinlang.org/docs/destructuring-declarations.html#destructuring-in-lambdas) to write more
concise code:

```kotlin
Validator<Context, Any> { (userDao, tweetDao) ->
    userDao // UserDao 
    tweetDao // TweetDao 
}
```

## Suspendable validation

If your external source is asynchronous, like an HTTP API, you have to make your validator suspendable by calling
`Validator.suspendable`.

Let's reuse [the contextual validation example based on Twitter](#contextual-validation) and replace the `UserDao`
interface by `UserApi`:

```kotlin
interface UserApi {
    suspend fun existsByUsername(username: String): Boolean
}
```

To be able to call the suspendable method `existsByUsername`, we have to make our validator suspendable:

```kotlin
val validate = Validator.suspendable<UserApi, UserUpdate> { api ->
    username.constrain {
        !api.existsByUsername(it)
    } otherwise { "This username is already taken" }
}
```

Note that suspendable validators can only be called from suspendable functions:

```kotlin
suspend fun main() {
    validate(someUserApi, someUserUpdate)
}
```
