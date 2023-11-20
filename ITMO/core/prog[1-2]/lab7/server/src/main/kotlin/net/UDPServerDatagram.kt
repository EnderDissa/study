package net

import commands.CommandManager
import managers.DataBaseManager
import java.math.BigInteger
import java.net.*
import java.nio.ByteBuffer
import java.util.*
import kotlin.math.ceil

class UDPServerDatagram(address: InetAddress, commandManager: CommandManager, port: Int, dataBaseManager: DataBaseManager):
    UDP(InetSocketAddress(address, port), commandManager, dataBaseManager) {
    private val PACKET_SIZE
        get() = 1024
    private val DATA_SIZE
        get() = PACKET_SIZE - 1

    private val datagramSocket: DatagramSocket = DatagramSocket(getAddr())

    init {
        datagramSocket.reuseAddress = true
    }

    override fun receive(): Pair<ByteArray, SocketAddress?> {
        var receiveFlag = false
        var result = ByteArray(0)
        var socketAddress: SocketAddress? = null

        while (!receiveFlag) {
            val data = ByteArray(PACKET_SIZE)
            val datagramPacket = DatagramPacket(data, PACKET_SIZE)

            datagramSocket.receive(datagramPacket)
            socketAddress = InetSocketAddress(datagramPacket.address, datagramPacket.port)

            if (data[PACKET_SIZE - 1].toInt() == 1) {
                receiveFlag = true
                logger.info("Data received from ${datagramPacket.address}")
            }
            result += data.copyOf(data.size - 1)
        }
        val size = BigInteger(1, result.copyOf(4)).toInt()
        return Pair(result.copyOfRange(4, 4 + size), socketAddress)
    }

    override fun send(data: ByteArray, address: SocketAddress) {
        val dataSend = Array(ceil((data.size + 4) / DATA_SIZE.toDouble()).toInt()) {ByteArray(DATA_SIZE)}

        var start = 0
        for (i in dataSend.indices) {
            if (i == 0) {
                val dataSize = ByteBuffer.allocate(4)
                dataSize.putInt(data.size)
                dataSend[i] = dataSize.array() + Arrays.copyOfRange(data, start, start + DATA_SIZE - 4)
                start += DATA_SIZE - 4
                continue
            }
            dataSend[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE)
            start += DATA_SIZE
        }

        for (i in dataSend.indices) {
            if (i != dataSend.size - 1) {
                val datagramPacket = DatagramPacket(dataSend[i] + byteArrayOf(0), PACKET_SIZE, address)
                datagramSocket.send(datagramPacket)
            } else {
                val datagramPacket = DatagramPacket(dataSend[i] + byteArrayOf(1), PACKET_SIZE, address)
                datagramSocket.send(datagramPacket)
            }
        }

        logger.info("Send ended")
    }

    override fun connect(address: SocketAddress) {
        datagramSocket.connect(address)
    }

    override fun disconnect() {
        logger.info("Socket disconnected")
        datagramSocket.disconnect()
    }

    override fun close() {
        logger.info("Socket closed")
        datagramSocket.close()
    }
}