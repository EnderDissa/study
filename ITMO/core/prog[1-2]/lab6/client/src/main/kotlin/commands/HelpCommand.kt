package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
class HelpCommand(val client: UDPClient): Command() {
    override fun getName() =  "help"
    override fun execute(argument: String?): Response {
        if (argument != null) throw CommandArgumentException("Method help don't support arguments")
        val response = client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.HELP))
        return response
    }
}