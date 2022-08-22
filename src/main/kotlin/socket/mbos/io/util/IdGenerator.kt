package socket.mbos.io.util

fun generateId(): String {
    return java.util.UUID.randomUUID().toString()
}