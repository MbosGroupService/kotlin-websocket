package socket.mbos.io

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import socket.mbos.io.plugins.*

fun main() {
    embeddedServer(Netty, port = 7970, host = "localhost") {
        configureSerialization()
        configureSockets()
        configureHTTP()
        configureRouting()
    }.start(wait = true)
}
