package client.commands

import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
class InfoCommand(val client: UDPClient): Command() {
    override fun getName() =  "info"
    override fun execute(argument: String?): Response {
        if (argument != null) throw CommandArgumentException("Method info don't support arguments")
        val response = client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.INFO))
        return response

    }
}