package commands

import common.CommandID
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class PrintAscendingCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is printing all the elements ascending way"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "print_ascending"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        val movies = movieManager.getMovieQueue()
        val sortedMovies = movies.stream()
            .sorted(Comparator.comparingLong { it.getOscarsCount() ?: 0 })

        return UniqueCommandResponse(ResponseCode.OK, messageC = "Ascending sorted movies",
            commandIDC = CommandID.PRINT_ASCENDING, hashSetMovie =  sortedMovies.toList())
    }
}