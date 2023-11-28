# Server-side validation with Ktor

<tldr>

**Code example:** [ktor-server](%github_product_url%/tree/main/examples/ktor-server)

**Time to complete:** 20 minutes

</tldr>

This tutorial provides a sampling of how %product% helps you write server-side validation with Ktor. We're going to
create an HTTP API to manage the books contained within a library; its role is to ensure each book has a valid and
unique <tooltip term="ISBN">ISBN</tooltip>, as well as a valid title.

> [Read our “Getting Started” guide](getting-started.md) if you're looking to write your first validation code with
> Akkurate.

{style="note"}

## Setting up the project

You can download a generated Ktor
project [by following this link.](https://start.ktor.io#/settings?name=akkurateWithKtor&website=akkuratewithktor.example.com&artifact=com.example.akkuratewithktor.akkuratewithktor&kotlinVersion=1.9.20&ktorVersion=2.3.5&buildSystem=GRADLE_KTS&engine=NETTY&configurationIn=HOCON&plugins=routing%2Ccontent-negotiation%2Ckotlinx-serialization%2Cstatus-pages%2Cexposed&addSampleCode=false)
Then click <ui-path>Add plugins | Generate project</ui-path> and, once the project is downloaded, open it in IntelliJ.

> The following plugins are already preconfigured:
> - **Content Negotiation & kotlinx.serialization** to handle JSON payloads;
> - **Exposed** to easily read/write to the database;
> - **Routing** to handle the requests;
> - **Status Pages** to return a specific response when an exception is thrown.

## Defining and persisting the data model

A book is composed of an [ISBN](https://en.wikipedia.org/wiki/ISBN) and a title. ISBN stands for _International Standard
Book Number_, a unique identifier assigned to each book.

To represent the book in the application, we will use the following data class:

```kotlin
@Serializable
data class Book(
    val isbn: String = "",
    val title: String = "",
)
```

The `@Serializable` annotation is necessary because this class will be used to transmit JSON data over HTTP.

To store the `Book` class, we will use [Exposed](https://github.com/JetBrains/Exposed) and follow what's recommended in
[Database persistence with Exposed](https://ktor.io/docs/interactive-website-add-persistence.html); allowing us to
easily query our database and keep this tutorial as simple as possible.

First, we need to define our database schema to store all the properties of our books:

```kotlin
object Books : Table() {
    val isbn = char("isbn", length = 13)
    val title = varchar("title", length = 50)

    override val primaryKey = PrimaryKey(isbn)
}
```

We're making the `isbn` the primary key, since it's already a unique identifier. It's also a `char(13)` type instead of
a `varchar` because an ISBN is always composed of 13 characters.

Next, we have to create our <tooltip term="DAO">DAO</tooltip>, which will provide two essential methods:

- `create()` to store a new book in the database,
- `list()` to retrieve all the books stored in our database.

```kotlin
class BookDao(database: Database) {
    init {
        transaction(database) {
            SchemaUtils.create(Books)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    private fun ResultRow.toBook() = Book(
        isbn = this[Books.isbn],
        title = this[Books.title],
    )

    suspend fun create(book: Book): Unit = dbQuery {
        Books.insert {
            it[isbn] = book.isbn
            it[title] = book.title
        }
    }

    suspend fun list(): List<Book> = dbQuery {
        Books.selectAll().map { it.toBook() }
    }
}
```

Finally, we need to instantiate our DAO with a database connection when the application starts up. Open the <path>
Databases.kt</path> file, create a top level variable `lateinit var bookDao: BookDao`, and define it inside
the `configureDatabases` function:

```kotlin
lateinit var bookDao: BookDao

fun Application.configureDatabases() {
    val database = Database.connect(/* ... */)
    bookDao = BookDao(database)
}
```

> This tutorial stores the DAO inside a global variable for simplicity. However, this is not recommended for a
> production
> application. You should instead use a Dependency Injection framework like [Koin](https://insert-koin.io/)
> or [Kodein-DI](https://kosi-libs.org/kodein/7.19/index.html).

{style="warning"}

## Handling the requests

We will need two routes for our HTTP API; `POST /books` to register a new book to the database, and `GET /books` to list
all the books in the database.

Open the <path>Routing.kt</path> file and copy the following code in the `configureRouting` function:

```kotlin
routing {
    post("/books") {
        val book = call.receive<Book>()
        bookDao.create(book)
        call.respond(HttpStatusCode.Created)
    }

    get("/books") {
        val books = bookDao.list()
        call.respond(HttpStatusCode.OK, books)
    }
}
```

The `POST /books` route deserializes the payload, stores it in the database, and returns a 201 HTTP status code.
The `GET /books` route fetches all the books and serializes them into the response.

## What can we improve?

Our API is done, you can run it either with IntelliJ or with the `./gradlew run` command.

Create a new book with `isbn=123` and `title` being empty:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --data '{"isbn": "123", "title": ""}' \
    --header 'Content-Type: application/json'
```

```text
HTTP/1.1 201 Created
```

</compare>

Now list the books:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v
```

```text
HTTP/1.1 200 OK
[
  {
    "isbn": "123          ",
    "title": ""
  }
]
```

</compare>

We can see two issues in this response; the ISBN is now filled up to the 13 required characters, and we shouldn't allow
empty titles.

Try to run the first query again:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --data '{"isbn": "123", "title": ""}' \
    --header 'Content-Type: application/json'
```

```text
HTTP/1.1 500 Internal Server Error
```

</compare>

Now we have an internal error because we're trying to insert a second book with the same ISBN as the first one. This is
impossible due to the ISBN being the primary key of the table.

Finally, try to create a book with a title over 50 characters:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --data '{"isbn": "1234", "title": "this a really long title and it will not fit our database column"}' \
    --header 'Content-Type: application/json'
```

```text
HTTP/1.1 500 Internal Server Error
```

</compare>

Once again, we see another internal error, because our title is composed of 64 characters meanwhile our database column
can contain a maximum of 50 characters.

## Validating the requests

All these issues can be fixed by validating the requests. We will use %product% coupled
to [Ktor request validation](https://ktor.io/docs/request-validation.html).

### Enhancing the DAO

Before writing any validation code, we need to add the following method to our `BookDao` class to allow searching a book
by its ISBN:

```kotlin
suspend fun existsWithIsbn(isbn: String): Boolean = dbQuery {
    Books.select { Books.isbn eq isbn }.singleOrNull() != null
}
```

That way, we can check if a book exists by running `bookDao.existsWithIsbn(isbn)`.

### Writing validation constraints

First, we must install %product% dependencies:

<include from="getting-started.md" element-id="single-platform-installation" />

Then mark the `Book` class with the `@Validate` annotation:

```kotlin
@Validate
@Serializable
data class Book(/* ... */)
```

Just like in the [Getting Started guide](getting-started.md), we create a `Validator` instance and add our constraints
to it:

```kotlin
val validateBook = Validator.suspendable<BookDao, Book> { dao ->
    isbn {
        val (isValidIsbn) = isMatching(Regex("""\d{13}""")) otherwise {
            "Must be a valid ISBN (13 digits)"
        }

        if (isValidIsbn) {
            constrain { !dao.existsWithIsbn(it) } otherwise {
                "This ISBN is already registered"
            }
        }
    }

    title {
        isNotBlank()
        hasLengthLowerThanOrEqualTo(50)
    }
}
```

> Remember to trigger code generation to access the properties inside the `Validator`, you can do this by building
> your project.

{style="note"}

There are multiple things to explain here:

- We use [a suspendable validator](use-external-sources.md#suspendable-validation)
  with [a context](use-external-sources.md#contextual-validation). Those allow our validator to call
  the `BookDao.existsWithIsbn` method, to ensure a book isn't already registered in our database.
- The call to `existsWithIsbn` is done within [an inline constraint](extend.md#inline-constraints)
  and [only if the ISBN is valid,](complex-structures.md#conditional-constraints) to avoid a useless query to the
  database.
- We ensure the title is not blank but also that it isn't longer than 50 characters, otherwise the database will reject
  it with an exception.

## Wiring to Ktor validation

%product% runs the validation and returns a result, but it needs to provide the latter to Ktor in order to generate a
response. This requires [the Request Validation plugin](https://ktor.io/docs/request-validation.html):

```kotlin
implementation("io.ktor:ktor-server-request-validation:$ktor_version")
```

This plugin allows configuring a validation function for a specific class; it will be executed on each deserialization.
A validation result must be returned, we can generate it from %product%'s own result:

```kotlin
import dev.nesk.akkurate.ValidationResult.Failure as AkkurateFailure
import dev.nesk.akkurate.ValidationResult.Success as AkkurateSuccess

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<Book> { book ->
            when (val result = validateBook(bookDao, book)) {
                is AkkurateSuccess -> ValidationResult.Valid
                is AkkurateFailure -> {
                    val reasons = result.violations.map {
                        "${it.path.joinToString(".")}: ${it.message}"
                    }
                    ValidationResult.Invalid(reasons)
                }
            }
        }
    }
}
```

Notice how we execute our `validateBook` function with the provided book, then we map the result to Ktor's result.

We also have to call our `configureValidation` function on application start, this is done in the <path>
Application.kt</path> file:

```kotlin
fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()
    configureValidation() // Register the validation handlers
}
```

When the validation fails, the plugin throws a `RequestValidationException`. To handle this exception and return a
proper response, we use [the Status Pages plugin](https://ktor.io/docs/status-pages.html).

Open the <path>Routing.kt</path> file, navigate to the `configureRouting` function, then the `install(StatusPages) {}`
lambda, and add the following code:

```kotlin
exception<RequestValidationException> { call, cause ->
    call.respond(HttpStatusCode.UnprocessableEntity, cause.reasons)
}
```

When the `RequestValidationException` is thrown, the Status Page plugin catches it and returns a response with a 422
HTTP status code, along with a JSON array of validation messages.

## Conformance checking

It's time to test our API once again.

Create a new book with invalid values:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --data '{"isbn": "123", "title": ""}' \
    --header 'Content-Type: application/json'
```

```text
HTTP/1.1 422 Unprocessable Entity
[
  "isbn: Must be a valid ISBN (13 digits)",
  "title: Must not be blank"
]
```

</compare>

This time, our validation took care of rejecting the request.

Now try to create a book with valid values:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --data '{"isbn": "1234567891234", "title": "The Lord of the Rings"}' \
    --header 'Content-Type: application/json'
```

```text
HTTP/1.1 201 Created
```

</compare>

The request is considered valid and we received a 201 HTTP status code.

What if we try to create the same book a second time?

```text
HTTP/1.1 422 Unprocessable Entity
[
  "isbn: This ISBN is already registered"
]
```

As expected, we can't register the same ISBN twice.

Our API is now fully validated, which means security is improved, and the users can understand why the request failed.
