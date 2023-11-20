package commands

import common.CommandID
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.*

/**
 * info command representation class
 */
class InfoCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is printing collection description in console"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() =  "info"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        return UniqueCommandResponse(ResponseCode.OK, messageC = "Class: " + movieManager.getCollectionClass() + "\n" +
                "Creation date: " + movieManager.getCreationDate() + "\n" +
                "Number of elements: " + movieManager.getCollectionNumberOfElements() + "\n",
                commandIDC = CommandID.INFO)
    }
}