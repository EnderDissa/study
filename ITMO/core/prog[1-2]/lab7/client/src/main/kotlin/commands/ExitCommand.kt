package client.commands

import client.net.UDPClient
import common.CommandArgumentException
import common.CommandID
import common.entities.LogStatus
import common.entities.User
import common.net.requests.UniqueCommandRequest
import common.net.responses.Response
import kotlin.system.exitProcess
class ExitCommand(val client:UDPClient): Command() {
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }
    override fun getName() =  "exit"
    override fun execute(argument: String?,user: User?): Response{
        if (argument != null) throw CommandArgumentException("Method exit don't support arguments")
        val user=client.getUser()
        client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.EXIT, user = user))
        exitProcess(0)
    }
}