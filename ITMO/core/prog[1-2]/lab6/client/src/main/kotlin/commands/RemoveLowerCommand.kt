package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
class RemoveLowerCommand(val client: UDPClient): Command() {
    override fun getName() = "remove_lower"
    override fun execute(argument: String?): Response {
        if (argument == null) throw CommandArgumentException("Method remove_by_id don't support zero arguments")

        val id = argument.toLong()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.REMOVE_LOWER, value = id))
    }
}