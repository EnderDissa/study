package client.commands


import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
import common.entities.LogStatus
import common.entities.User

class HelpCommand(val client: UDPClient): Command() {
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }
    override fun getName() =  "help"
    override fun execute(argument: String?,user: User?): Response {
        if (argument != null) throw CommandArgumentException("Method help don't support arguments")
        val user=client.getUser()
        val response = client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.HELP, user = user))
        return response
    }
}