package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
import common.entities.LogStatus
import common.entities.User

class PrintDescendingCommand(val client: UDPClient): Command() {
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }
    override fun getName() = "print_descending"
    override fun execute(argument: String?,user: User?): Response {
        if (argument != null) throw CommandArgumentException("Method print_ascending don't support arguments")
        val user=client.getUser()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.PRINT_DESCENDING, user = user))

    }
}