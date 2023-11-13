package dev.nesk.akkurate.examples.ktor.server

import dev.nesk.akkurate.examples.ktor.server.plugins.configureDatabases
import dev.nesk.akkurate.examples.ktor.server.plugins.configureRouting
import dev.nesk.akkurate.examples.ktor.server.plugins.configureSerialization
import dev.nesk.akkurate.examples.ktor.server.plugins.configureValidation
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()
    configureValidation()
}
