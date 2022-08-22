package socket.mbos.io.sockets

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import socket.mbos.io.topics.Topic
import socket.mbos.io.topics.TopicCollection
import socket.mbos.io.util.generateId
import socket.mbos.io.util.parameter
import java.util.Collections
import java.util.HashMap

fun Route.topicSocket() {

    webSocket("/{topic}") {

        val name: String = parameter("topic") ?: return@webSocket
        val topic = TopicCollection.findTopic(name)

        val connectionId = generateId()

        topic.add(connectionId, this)

        println("${topic.name}: connected $connectionId")

        try  {
            for(frame in incoming) {
                val text = (frame as Frame.Text).readText()
                println("[${topic.name}]: incoming -> $text")
            }
            println("[${topic.name}]: closed $connectionId -> ${closeReason.await()?.message}")
            topic.remove(connectionId)
        }
        catch (e: ClosedReceiveChannelException) {
            println("[${topic.name}]: disconnected $connectionId -> ${closeReason.await()}")
            topic.remove(connectionId)
        }
        catch (e: Throwable) {
            println("[${topic.name}]: disconnected $connectionId -> ${closeReason.await()}")
            topic.remove(connectionId)
        }
    }
}
