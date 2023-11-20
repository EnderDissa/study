package client.commands

import common.entities.LogStatus
import common.entities.User
import common.net.requests.*
import common.net.responses.Response

/**
 * Execution command representative interface
 */
abstract class Command {
    abstract fun getName(): String

    abstract fun getPerm(): LogStatus

    abstract fun execute(argument: String?,user: User?): Response
}