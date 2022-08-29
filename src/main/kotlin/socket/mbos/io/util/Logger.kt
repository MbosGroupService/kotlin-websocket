package socket.mbos.io.util

import java.util.Date

typealias Listener = suspend (info: LogInfo) -> Unit

data class LogInfo(val date: Date, val message: String) {
    override fun toString(): String {
        return  "$date -> $message"
    }
}

object Logger {

    private val listeners = hashMapOf<String, Listener>()

    fun addListener(id: String, listener: Listener): String {
        listeners[id] = listener
        return id
    }

    fun removeListener(id: String) {
        listeners.remove(id)
    }

    suspend fun info(message: String) {
        val date = Date()
        val info = LogInfo(date, message)

        println("[INFO] $date:  $info")

        listeners.forEach { (id, listener) ->
            listener(info)
        }
    }

    suspend fun error(message: String) {
        val date = Date()
        val info = LogInfo(date, message)

        println("[ERROR] $date:  $info")

        listeners.forEach { (id, listener) ->
            listener(info)
        }
    }
}