package net

import commands.CommandManager
import common.net.requests.*
import common.net.responses.UniqueCommandResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.net.SocketAddress

abstract class UDP(var address: InetSocketAddress, val commandManager: CommandManager) {
    protected val logger: Logger = LoggerFactory.getLogger(UDP::class.java)
    private var runFlag = true

    fun getAddr() = address

    abstract fun receive(): Pair<ByteArray, SocketAddress?>

    abstract fun send(data: ByteArray, address: SocketAddress)

    abstract fun connect(address: SocketAddress)

    abstract fun disconnect()

    abstract fun close()

    fun stop() {
        runFlag = !runFlag
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun run() {
        logger.info("Server started, address=$address")

        while (runFlag) {
            var data: Pair<ByteArray, SocketAddress?>

            try {
                data = receive()
                if (data.second == null) {
                    logger.error("Receiving data error")
                    disconnect()
                    continue
                }
            } catch (e: Exception) {
                logger.error("Receiving data error ${e.toString()}", e)
                disconnect()
                continue
            }

            try {
                connect(data.second!!)
                logger.info("Server connected to client address=${data.second}")
            } catch (e: Exception) {
                logger.error("Client connection error $e", e)
            }

            val request: UniqueCommandRequest

            try {
                request = ProtoBuf.decodeFromByteArray(data.first)
                logger.info("Request $request handle")
            } catch (e: Exception) {
                logger.error("Unnable to serialize request $e", e)
                disconnect()
                continue
            }

            val response: UniqueCommandResponse

            try {
                response = commandManager.handle(request)
                logger.info("Created response ${response.commandIDC}")
            } catch (e: Exception) {
                logger.error("Command error $e", e)
                continue
            }

            val dataToSend = ProtoBuf.encodeToByteArray(response)
            //logger.info("Response: $response")

            try {
                send(dataToSend, data.second!!)
                logger.info("Data sent to client: ${data.second}")
            } catch (e: java.lang.Exception) {
                logger.error("I/O Error : $e", e)
            }

            disconnect()
            logger.info("Server disconnected")
        }

        close()
    }
}