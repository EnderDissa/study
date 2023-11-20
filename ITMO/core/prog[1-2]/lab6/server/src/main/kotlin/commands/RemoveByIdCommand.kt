package commands

import common.CommandID
import common.entities.MovieManager
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class RemoveByIdCommand(private val movieManager: MovieManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is deleting element from collection"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() = "remove_by_id"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        return try {
            if (movieManager.removeElementById(request.value!!))
                UniqueCommandResponse(ResponseCode.OK,
                    messageC = "Element with id = ${request.value!!} was removed",
                    commandIDC = CommandID.REMOVE_BY_ID
                )
            else UniqueCommandResponse(ResponseCode.FAIL,
                exceptionDataC = "Element with id = ${request.value!!} wasn't removed",
                commandIDC = CommandID.REMOVE_BY_ID
            )
        } catch (e: Exception) {
            UniqueCommandResponse(ResponseCode.FAIL, exceptionDataC = e.toString(),
                commandIDC = CommandID.REMOVE_BY_ID)
        }
    }
}