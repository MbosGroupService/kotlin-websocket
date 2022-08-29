package socket.mbos.io.sockets

import com.google.gson.Gson
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import socket.mbos.io.util.Logger
import socket.mbos.io.util.generateId

private val gson = Gson()

fun Route.loggingSocket() {

    webSocket("/logs") {

        val id = generateId()
        try  {

            println("connected to logs: $id")

            Logger.addListener(id) {
                send(gson.toJson(it))
            }

            for(frame in incoming) { }

            Logger.removeListener(id)
        }
        catch (e: ClosedReceiveChannelException) {
            Logger.removeListener(id)
        }
        catch (e: Throwable) {
            Logger.removeListener(id)
        }
    }
}