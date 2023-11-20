package commands

import common.net.requests.UniqueCommandRequest
import common.net.responses.UniqueCommandResponse

/**
 * Execution command representative interface
 */
abstract class Command {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    abstract fun getDescription(): String

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    abstract fun getName(): String


    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    abstract fun execute(request: UniqueCommandRequest): UniqueCommandResponse
}