package commands

import common.CommandID
import common.entities.Movie
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class RemoveLowerCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is removing all the elements, that are less"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "remove_lower"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        val oscarsCount = request.value

        val movieList = movieManager.getMovieQueue().stream()
            .filter {(it.getOscarsCount() ?: 0) < (oscarsCount ?: 0)}
            .map(Movie::getId)

        movieList.forEach{movieManager.removeElementById(it)}

        return UniqueCommandResponse(ResponseCode.OK,
            "All movies with oscars count value less than ${request.value} were removed",
            commandIDC = CommandID.REMOVE_LOWER)
    }
}