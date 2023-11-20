package client.commands

import client.builders.MovieBuilder
import client.net.UDPClient
import common.*
import common.entities.LogStatus
import common.entities.User
import common.net.requests.*
import common.net.responses.*


class AddCommand(val client: UDPClient): Command() {

    override fun getName() = "add"
    override fun getPerm(): LogStatus{
        return LogStatus.LOGGED
    }

    /**
     * Sending response method
     *
     * @param argument if it is needed [String]
     * @return none
     * @author Berman Denis
     */
    override fun execute(argument: String?,user: User?): Response {
        if (argument != null) throw CommandArgumentException("Method add don't support arguments")
        val movie= MovieBuilder.build()
        val user=client.getUser()
        val response = client.sendAndReceiveCommand(UniqueCommandRequest(movie=movie,commandIDc = CommandID.ADD, user = user))
        return response
    }
}