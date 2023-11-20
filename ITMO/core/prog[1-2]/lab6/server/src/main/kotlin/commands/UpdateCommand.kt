package commands

import common.CommandID
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class UpdateCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is updating element from collection"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "update"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        try {
            return if (movieManager.removeElementById(request.value!!)) {
                request.movie!!.setNewId(request.value!!)
                movieManager.addMovie(request.movie!!)
                UniqueCommandResponse(
                    ResponseCode.OK, messageC = "Element was updated",
                    commandIDC = CommandID.UPDATE_BY_ID)
            } else UniqueCommandResponse(
                ResponseCode.FAIL, exceptionDataC = "Element wasn't updated",
                commandIDC = CommandID.UPDATE_BY_ID)
        } catch (e: Exception) {
            return UniqueCommandResponse(
                ResponseCode.FAIL, exceptionDataC = e.toString(),
                commandIDC = CommandID.UPDATE_BY_ID
            )
        }
    }
}