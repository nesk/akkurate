# Server-side validation with Ktor

This tutorial provides a sampling of how %product% helps you write server-side validation with Ktor. We're going to
create an HTTP API to manage the books contained within a library; its role is to ensure each book has a valid and
unique <tooltip term="ISBN">ISBN</tooltip>, as well as a valid title.

> [Read our “Getting Started” guide](getting-started.md) if you're looking to write your first validation code with
> Akkurate.

<tldr>

**Code example:** [ktor-server](%github_product_url%/tree/main/examples/ktor-server)

**Time to complete:** 20 minutes

</tldr>

## Setting up the project

You can download a generated Ktor
project [via Ktor's Project Generator](https://start.ktor.io/settings/?name=akkuratewithktor&website=akkuratewithktor.example.com&artifact=com.example.akkuratewithktor.akkuratewithktor&kotlinVersion=2.0.21&ktorVersion=3.0.1&buildSystem=GRADLE_KTS&buildSystemArgs.version_catalog=true&engine=NETTY&configurationIn=HOCON&addSampleCode=false&plugins=routing%252Ckotlinx-serialization%252Ccontent-negotiation%252Cexposed)
and clicking <ui-path>Download</ui-path>. Once the project is downloaded, open it in IntelliJ.

> The following plugins are already preconfigured:
> - **Content Negotiation & kotlinx.serialization** to handle JSON payloads;
> - **Exposed** to easily read/write to the database;
> - **Routing** to handle the requests.
>
> Make sure **Include samples** is unchecked.

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
Databases.kt</path> file, create a top level variable `lateinit var bookDao: BookDao`, and define it inside the
`configureDatabases` function:

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

We will need two routes for our HTTP API:

- `POST /books` to store a new book in the database.
- `GET /books` to list all the books in the database.

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

The `POST /books` route deserializes the request payload, stores it in the database and returns a 201 HTTP status code.
The `GET /books` route fetches all the books and serializes them into the response.

## What can we improve?

Our API is done, you can run it either with IntelliJ or with the `./gradlew run` command.

Create a new book with `isbn=123` and `title` being empty:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --header 'Content-Type: application/json' \
    --data '{"isbn": "123", "title": ""}'
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

```json5
// HTTP/1.1 200 OK

[
  {
    "isbn": "123          ",
    "title": ""
  }
]
```

</compare>

We can see two issues in this response: the ISBN is now padded with space to reach 13 characters, and we shouldn't allow
empty titles.

Try to run the first query again:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --header 'Content-Type: application/json' \
    --data '{"isbn": "123", "title": ""}'
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
    --header 'Content-Type: application/json' \
    --data '{"isbn": "456", "title": "this a really long title and it will not fit our database column"}'
```

```text
HTTP/1.1 500 Internal Server Error
```

</compare>

Once again, we see another internal error, because our title is composed of 64 characters, meanwhile our database column
can contain a maximum of 50 characters.

## Validating the requests

All these issues can be fixed by validating the requests, first we need to set up our validator.

### Enhancing the DAO

Before writing any validation code, we need to add the following method to our `BookDao` class to allow searching a book
by its ISBN:

```kotlin
suspend fun existsWithIsbn(isbn: String): Boolean = dbQuery {
    Books.selectAll()
        .where { Books.isbn eq isbn }
        .singleOrNull() != null
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
data class Book(
    val isbn: String = "",
    val title: String = "",
)
```

{collapsible="true" default-state="collapsed" collapsed-title="@Validate @Serializable data class Book"}

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
  with [a context](use-external-sources.md#contextual-validation). Those allow our validator to call the
  `BookDao.existsWithIsbn` method, to ensure a book isn't already registered in our database.
- The call to `existsWithIsbn` is done within [an inline constraint](extend.md#inline-constraints)
  and [only if the ISBN is valid,](complex-structures.md#conditional-constraints) to avoid a useless query to the
  database.
- We ensure the title is not blank but also that it isn't longer than 50 characters, otherwise the database will reject
  it with an exception.

## Automatic validation

%product% provides [a Ktor plugin](ktor-server-integration.md) to automatically validate received payloads:

<include from="ktor-server-integration.md" element-id="install-akkurate" />

Now we must install both plugins and declare what types we want to validate on deserialization:

```kotlin
fun Application.configureValidation() {
    install(Akkurate)
    install(RequestValidation) {
        registerValidator(validateBook) { bookDao }
    }
}
```

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

When the validation fails, the server returns a default response,
based [on the RFC 9457 (Problem Details for HTTP APIs).](https://www.rfc-editor.org/rfc/rfc9457.html)

## Conformance checking

It's time to test our API once again, by creating a new book with invalid values:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --header 'Content-Type: application/json' \
    --data '{"isbn": "123", "title": ""}'
```

```json5
// HTTP/1.1 422 Unprocessable Entity

{
  "status": 422,
  "fields": [
    {
      "message": "Must be a valid ISBN (13 digits)",
      "path": "isbn"
    },
    {
      "message": "Must not be blank",
      "path": "title"
    }
  ],
  "type": "https://akkurate.dev/validation-error",
  "title": "The payload is invalid",
  "detail": "The payload has been successfully parsed, but the server is unable to accept it due to validation errors."
}
```

</compare>

This time, our validation took care of rejecting the request.

Now try to create a book with valid values:

<compare type="top-bottom" first-title="cURL request" second-title="Response">

```shell
curl 127.0.0.1:8080/books -v \
    --header 'Content-Type: application/json' \
    --data '{"isbn": "9780261103207", "title": "The Lord of the Rings"}'
```

```text
HTTP/1.1 201 Created
```

</compare>

The request is considered valid and we received a 201 HTTP status code.

What if we try to create the same book a second time?

```json5
// HTTP/1.1 422 Unprocessable Entity

{
  "status": 422,
  "fields": [
    {
      "message": "This ISBN is already registered",
      "path": "isbn"
    }
  ],
  "type": "https://akkurate.dev/validation-error",
  "title": "The payload is invalid",
  "detail": "The payload has been successfully parsed, but the server is unable to accept it due to validation errors."
}
```

As expected, we can't register the same ISBN twice.

Our API is now fully validated, which means security is improved, and the users can understand why the request failed.

<seealso style="cards">
  <category ref="related">
    <a href="ktor-server-integration.md" />
    <a href="ktor-client-integration.md" />
  </category>
</seealso>
