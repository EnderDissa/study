package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
class PrintDescendingCommand(val client: UDPClient): Command() {
    override fun getName() = "print_descending"
    override fun execute(argument: String?): Response {
        if (argument != null) throw CommandArgumentException("Method print_ascending don't support arguments")
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.PRINT_DESCENDING))

    }
}