package commands

import common.CommandID
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse
import managers.DataBaseManager

class UserRegisterCommand(private val dataBaseManager: DataBaseManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is adding new user"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() =  "register"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest) = try {
            dataBaseManager.addUser(request.user!!)

            UniqueCommandResponse(
                ResponseCode.OK, messageC = "User was successfully added",
                commandIDC = CommandID.REGISTER
            )
        } catch (e: Exception) {
            UniqueCommandResponse(
                ResponseCode.FAIL, exceptionDataC = "User wasn't added",
                commandIDC = CommandID.REGISTER
            )
        }
}