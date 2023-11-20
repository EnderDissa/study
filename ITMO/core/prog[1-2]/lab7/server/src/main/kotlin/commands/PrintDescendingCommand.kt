package commands

import common.CommandID
import common.entities.Movie
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class PrintDescendingCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is printing all the elements descending way"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "print_descending"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        val movies = movieManager.getMovieQueue()
        val sortedOscars = movies.stream()
            .map(Movie::getOscarsCount)
            .sorted(Comparator.comparingLong { (it ?: 0) * -1 })

        return UniqueCommandResponse(ResponseCode.OK, messageC = "Descending sorted movies",
            commandIDC = CommandID.PRINT_DESCENDING, hashSetLong =  sortedOscars.toList())
    }
}