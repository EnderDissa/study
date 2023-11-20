package client.commands


import client.builders.MovieBuilder
import client.net.UDPClient
import common.net.requests.*
import common.net.responses.*
import common.*
import common.entities.LogStatus
import common.entities.User

class AddIfMinCommand(val client: UDPClient): Command() {
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }
    override fun getName() = "add_if_min"
    override fun execute(argument: String?,user: User?): Response {
        if (argument != null) throw CommandArgumentException("Method add_if_min don't support arguments")
        val movie = MovieBuilder.build()
        val user=client.getUser()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.ADDIFMIN, movie = movie, user = user))

    }
}