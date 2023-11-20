package client.net

import common.net.requests.*
import common.net.responses.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.*
import java.nio.channels.DatagramChannel
import java.util.Arrays
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.protobuf.*
import java.math.BigInteger

@OptIn(ExperimentalSerializationApi::class)
class UDPClient(address: InetAddress, port: Int) {
    private val PACKET_SIZE = 1024
    private val DATA_SIZE = PACKET_SIZE - 1
    private var client: DatagramChannel? = null
    private val address: InetSocketAddress
    private val logger: Logger = LoggerFactory.getLogger(UDPClient::class.java)


    init {
        this.address = InetSocketAddress(address, port)
    }
    fun SetConnection(){
        client = DatagramChannel.open().bind(null).connect(address)
        client!!.configureBlocking(false)
        logger.info("client started and ready to listen command, address=$address")


    }

    fun sendAndReceiveCommand(request: UniqueCommandRequest): UniqueCommandResponse {
        val dataToSend= ProtoBuf.encodeToByteArray(request)
        sendData(dataToSend)
        logger.info("Data sended to $address")
        return ProtoBuf.decodeFromByteArray<UniqueCommandResponse>(receiveData())
    }

    private fun sendData(data: ByteArray) {

        val ret = Array(Math.ceil(data.size / DATA_SIZE.toDouble()).toInt()) {
            ByteArray(
                DATA_SIZE
            )
        }
        var start = 0
        for (i in ret.indices) {
            if (i == 0) {
                val dataSize = ByteBuffer.allocate(4)
                dataSize.putInt(data.size)
                ret[i] = dataSize.array() + Arrays.copyOfRange(data, start, start + DATA_SIZE - 4)
                start += DATA_SIZE - 4
                continue
            }
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE)
            start += DATA_SIZE
        }
        for (i in ret.indices) {
            val chunk = ret[i]
            if (i == ret.size - 1) {
                val lastChunk = chunk + byteArrayOf(1)
                client!!.send(ByteBuffer.wrap(lastChunk), address)
            } else {
                val answer = chunk + byteArrayOf(0)
                client!!.send(ByteBuffer.wrap(answer), address)
            }
        }
    }


    private fun receiveData(): ByteArray {
        var received = false
        var result = ByteArray(0)
        while (!received) {
            val data = receiveDataFromServer(PACKET_SIZE)

            if (data[data.size - 1].toInt() == 1) {
                received = true
            }
            result += Arrays.copyOf(data, data.size - 1)
        }
        val size = BigInteger(1, result.copyOf(4)).toInt()
        logger.info("Data recieved from $address")
        return result.copyOfRange(4, 4 + size)
    }

    private fun receiveDataFromServer(bufferSize: Int): ByteArray {
        val buffer = ByteBuffer.allocate(bufferSize)
        var address: SocketAddress? = null
        while (address == null) {
            address = client!!.receive(buffer)
        }
        return buffer.array()
    }
    /*
    private fun sendAndReceiveData(data: ByteArray): ByteArray {

        sendData(data)
        return receiveData()
    }
    */

}