package socket.mbos.io.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import socket.mbos.io.topics.TopicCollection
import socket.mbos.io.topics.TopicCollection.allConnections
import socket.mbos.io.util.parameter

fun Route.connectionRouting() {
    route("/connections") {
        get {
            val connections = allConnections()
            call.respond(HttpStatusCode.OK, connections)
            println("GET /connections: $connections")
        }
    }

    route("/event/{topic}") {
        post {
            val name = parameter("topic") ?: return@post call.respond(HttpStatusCode.BadRequest, "topic not provided")
            val body = call.receive<Any>()

            println("event to [$name]: $body")

            call.respond(HttpStatusCode.OK)

            val topic = TopicCollection.findTopic(name)
            topic.sendMessage(body)
        }
    }
}