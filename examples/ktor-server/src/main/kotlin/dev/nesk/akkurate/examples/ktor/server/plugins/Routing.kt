package dev.nesk.akkurate.examples.ktor.server.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.UnprocessableEntity, cause.reasons)
        }
    }

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
}
