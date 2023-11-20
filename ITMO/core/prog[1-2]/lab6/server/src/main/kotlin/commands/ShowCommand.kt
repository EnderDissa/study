package commands

import common.CommandID
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class ShowCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is showing description of all elements in collection in console"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "show"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        return UniqueCommandResponse(ResponseCode.OK, messageC = "Movie set", commandIDC = CommandID.SHOW,
            hashSetMovie = movieManager.getMovieQueue().toList())
    }
}