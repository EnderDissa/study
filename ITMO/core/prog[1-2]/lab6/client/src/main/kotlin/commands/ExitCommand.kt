package client.commands

import client.net.UDPClient
import common.CommandArgumentException
import common.CommandID
import common.net.requests.UniqueCommandRequest
import common.net.responses.Response
import kotlin.system.exitProcess
class ExitCommand(val client:UDPClient): Command() {
    override fun getName() =  "exit"
    override fun execute(argument: String?): Response{
        if (argument != null) throw CommandArgumentException("Method exit don't support arguments")
        client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.EXIT))
        exitProcess(0)
    }
}