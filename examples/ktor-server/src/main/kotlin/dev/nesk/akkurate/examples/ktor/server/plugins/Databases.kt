/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        Books.selectAll()
            .where { Books.isbn eq isbn }
            .singleOrNull() != null
    }
}
