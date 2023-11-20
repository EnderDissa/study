package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
import common.entities.LogStatus
import common.entities.User

class RemoveLowerCommand(val client: UDPClient): Command() {
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }
    override fun getName() = "remove_lower"
    override fun execute(argument: String?,user: User?): Response {
        if (argument == null) throw CommandArgumentException("Method remove_by_id don't support zero arguments")
        val user=client.getUser()
        val id = argument.toLong()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.REMOVE_LOWER, value = id, user = user))
    }
}