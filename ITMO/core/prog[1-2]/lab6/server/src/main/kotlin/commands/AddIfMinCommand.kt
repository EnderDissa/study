package commands

import common.CommandID
import common.entities.Movie
import common.entities.MovieManager
import common.net.requests.*
import common.net.responses.*
import java.lang.Exception

class AddIfMinCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is adding element, if it's value less then minimum"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "add_if_min"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        return try {
            val minCount = movieManager.getMovieQueue().stream()
                .map(Movie::getOscarsCount)
                .mapToLong { it ?: -1 }
                .min()
                .orElse(Long.MAX_VALUE)

            request.movie!!.setNewId(movieManager.giveId())

            if ((request.movie!!.getOscarsCount() ?: -1) < minCount) {
                movieManager.addMovie(request.movie!!)
                UniqueCommandResponse(ResponseCode.OK, messageC = "Movie added to collection with id = ${request.movie!!.getId()}",
                    commandIDC = CommandID.ADDIFMIN)
            } else {
                UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = "Movie oscars count isn't min. " +
                        "Min value is $minCount", commandIDC = CommandID.ADDIFMIN)
            }
        } catch (e: Exception) {
            UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = e.toString(), commandIDC = CommandID.ADDIFMIN)
        }
    }
}