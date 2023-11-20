package commands

import common.CommandID
import common.entities.Movie
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse
import java.lang.Exception

class AddIfMaxCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is adding element, if it's value more then maximum"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "add_if_max"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        return try {
            val maxCount = movieManager.getMovieQueue().stream()
                .map(Movie::getOscarsCount)
                .mapToLong { it ?: -1 }
                .max()
                .orElse(-1)

            request.movie!!.setNewId(movieManager.giveId())

            if ((request.movie!!.getOscarsCount() ?: -1) > maxCount) {
                movieManager.addMovie(request.movie!!)
                UniqueCommandResponse(ResponseCode.OK, messageC = "Movie added to collection with id = ${request.movie!!.getId()}",
                    commandIDC = CommandID.ADDIFMAX)
            } else {
                UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = "Movie oscars count isn't max. " +
                        "Max value is $maxCount", commandIDC = CommandID.ADDIFMAX)
            }
        } catch (e: Exception) {
            UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = e.toString(), commandIDC = CommandID.ADDIFMAX)
        }
    }
}