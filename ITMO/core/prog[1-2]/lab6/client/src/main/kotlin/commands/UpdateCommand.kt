package client.commands


import client.builders.MovieBuilder
import client.net.UDPClient
import common.net.requests.*
import common.*
import common.net.responses.Response

class UpdateCommand(val client: UDPClient): Command() {
    override fun getName() = "update"
    override fun execute(argument: String?): Response {
        if (argument == null) throw CommandArgumentException("Method remove_by_id don't support zero arguments")

        val id = argument.toLong()
        val movie = MovieBuilder.build()
        return client.sendAndReceiveCommand(UniqueCommandRequest(commandIDc = CommandID.UPDATE_BY_ID, value = id, movie = movie))
    }
}