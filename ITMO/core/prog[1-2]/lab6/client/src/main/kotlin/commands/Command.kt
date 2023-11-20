package client.commands

import common.net.requests.*
import common.net.responses.Response

/**
 * Execution command representative interface
 */
abstract class Command {
    abstract fun getName(): String

    abstract fun execute(argument: String?): Response
}