package commands

import common.CommandID
import common.net.requests.UniqueCommandRequest
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse

class HelpCommand(private val commandManager: CommandManager): Command() {
    /**
     * Get information about command abstract method
     *
     * @return information about command [String]
     * @author Markov Maxim 2023
     */
    override fun getDescription() = "Command is printing commands description in console"

    /**
     * Get name of command abstract method
     *
     * @return name of command [String]
     * @author Markov Maxim 2023
     */
    override fun getName() =  "help"

    /**
     * Execute command abstract method.
     *
     * @param request if it is needed [String]
     * @return none
     * @author Markov Maxim 2023
     */
    override fun execute(request: UniqueCommandRequest): UniqueCommandResponse {
        val outString = StringBuilder()

        commandManager.getCommands().values.forEach { outString.append("${it.getName()} - ${it.getDescription()}\n\n")}
        return UniqueCommandResponse(ResponseCode.OK, messageC = outString.toString(), commandIDC = CommandID.HELP)
    }
}