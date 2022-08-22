package socket.mbos.io.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import socket.mbos.io.routes.connectionRouting

fun Application.configureRouting() {
    routing {
        connectionRouting()
    }
}
