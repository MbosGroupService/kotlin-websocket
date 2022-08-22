package socket.mbos.io.topics

import com.google.gson.Gson
import io.ktor.websocket.*
import java.sql.Connection
import java.util.Collections

private val gson = Gson()

open class Topic(
    val name: String
) {

    private val connections = Collections.synchronizedMap(HashMap<String, DefaultWebSocketSession>())

    fun add(id: String, connection: DefaultWebSocketSession) {
        connections[id] = connection
    }

    fun remove(id: String) {
        connections.remove(id)
    }

    suspend fun <T> sendMessage(message: T) {
        connections.forEach { (id, session) ->
            session.send(gson.toJson(message))
        }
    }

    fun getAll(): List<TopicConnection> {
        return connections.map {
            TopicConnection(name, it.key)
        }
    }
}