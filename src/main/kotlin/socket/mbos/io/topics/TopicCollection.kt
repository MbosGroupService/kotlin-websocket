package socket.mbos.io.topics

import java.util.*
import kotlin.collections.HashMap

object TopicCollection {

    private val topics = Collections.synchronizedMap(HashMap<String, Topic>())

    fun findTopic(name: String): Topic {
        return if (topics.containsKey(name)) {
            topics[name]!!
        }
        else {
            val newTopic = Topic(name)
            topics[name] = newTopic
            newTopic
        }
    }

    fun allConnections(): List<TopicConnection> {
        val connections = mutableListOf<TopicConnection>()

        topics.forEach { (_, topic) ->
            connections.addAll(topic.getAll())
        }

        return connections
    }
}