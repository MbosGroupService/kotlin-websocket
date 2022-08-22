package socket.mbos.io.util

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import io.ktor.websocket.*

suspend fun WebSocketServerSession.parameter(name: String): String? {
    val param =  call.parameters[name]
    if (param == null) {
        close(reason = CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "$name not provided."))
        return null
    }
    return param
}

fun PipelineContext<Unit, ApplicationCall>.parameter(name: String): String? {
    return call.parameters[name]
}