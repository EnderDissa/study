package client.commands

import client.builders.MovieBuilder
import client.net.UDPClient
import common.*
import common.net.requests.*
import common.net.responses.*


class AddCommand(val client: UDPClient): Command() {
    override fun getName() = "add"


    /**
     * Sending response method
     *
     * @param argument if it is needed [String]
     * @return none
     * @author Berman Denis
     */
    override fun execute(argument: String?): Response {
        if (argument != null) throw CommandArgumentException("Method add don't support arguments")
        val movie= MovieBuilder.build()
        val response = client.sendAndReceiveCommand(UniqueCommandRequest(movie=movie,commandIDc = CommandID.ADD))
        return response
    }
}