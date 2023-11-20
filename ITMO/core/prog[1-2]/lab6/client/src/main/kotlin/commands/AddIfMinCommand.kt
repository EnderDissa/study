package client.commands


import client.builders.MovieBuilder
import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
class AddIfMinCommand(val client: UDPClient): Command() {
    override fun getName() = "add_if_min"
    override fun execute(argument: String?): Response {
        if (argument != null) throw CommandArgumentException("Method add_if_min don't support arguments")
        val movie = MovieBuilder.build()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.ADDIFMIN, movie = movie))

    }
}