package dev.nesk.akkurate.examples.ktor.server.plugins

import dev.nesk.akkurate.annotations.Validate
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

lateinit var bookDao: BookDao

fun Application.configureDatabases() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )
    bookDao = BookDao(database)
}

@Validate
@Serializable
data class Book(
    val isbn: String = "",
    val title: String = "",
)

object Books : Table() {
    val isbn = char("isbn", length = 13)
    val title = varchar("title", length = 50)

    override val primaryKey = PrimaryKey(isbn)
}

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

    suspend fun existsWithIsbn(isbn: String): Boolean = dbQuery {
        Books.select { Books.isbn eq isbn }.singleOrNull() != null
    }
}
