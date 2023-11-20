package commands

import common.CommandID
import common.entities.Movie
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse
import kotlin.Comparator

class MaxScreenwriterCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is printing all the elements, which value of screenwriter is maximum"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "max_by_screenwriter"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        val maxMovie: Movie? = movieManager.getMovieQueue().stream()
            .max(Comparator.comparingInt { it.getScreenwriter().getHeight() })
            .orElse(null)


        return if (maxMovie != null) UniqueCommandResponse(ResponseCode.OK,
            messageC = "Found maxscreenwriter height movie",
            commandIDC = CommandID.MAX_SCREENWRITER, movie = maxMovie)
        else UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = "There's is no movie with maxscreenwriter",
            commandIDC = CommandID.MAX_SCREENWRITER)
    }
}