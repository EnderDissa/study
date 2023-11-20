package client.run

import client.commands.Command
import client.console.ConsoleManager
import client.net.UDPClient
import client.commands.CommandManager
import common.CommandArgumentException
import common.net.responses.ResponseCode
import common.net.responses.UniqueCommandResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.PortUnreachableException

/**
 * Progrum runtime representative class
 */
class RunManager(private val commandManager: CommandManager, private val client: UDPClient) {
    /**
     * run method
     *
     * @author Denis Berman
     */
    fun run(commandManager: CommandManager) {
        var secs=5
        val logger: Logger = LoggerFactory.getLogger(UDPClient::class.java)
        while (ConsoleManager.hasNext()) {
            val line = ConsoleManager.getNextLine()

            val tokens = line.split(" ")

            val command = commandManager.getCommands()[tokens[0]]
            while(!false){
                try {
                    val executionResponse = if (tokens.size > 1) commandExecute(command!!, tokens[1])
                    else commandExecute(command!!, null)

                    println(executionResponse)
                } catch (e: PortUnreachableException) {
                    logger.error("Server is busy, next try in "+secs.toString()+" secs!")
                    Thread.sleep(secs.toLong()*1000)
                    secs+=5
                    continue}
                catch(e: NullPointerException){
                    logger.error("Don't know this command!")
                }
                catch(e: CommandArgumentException){
                    logger.error(e.message!!)
                }
                break
            }
        }
    }
//    fun runLine(line: String){
//        val tokens = line.split(" ")
//
//        if (tokens.isEmpty()) {
//            println("Something went wrong:(")
//        }
//        val command = commandManager.getCommands()[tokens[0]]
//
//        val executionCode = if (tokens.size > 1) commandExecution(command, tokens[1])
//        else commandExecution(command, null)
//
//        if (executionCode == ExecutionCode.EXCEPTION) println("Something went wrong:((")
//        else if (executionCode == ExecutionCode.NO_COMMAND) println("There's no command $tokens")
//
//    }
    /**
     * command execution method
     *
     * @argument command to execute [Command]
     * @author Markov Maxim 2023
     */
    private fun commandExecute(command: Command, argument: String?): String? {
        val response = command.execute(argument) as UniqueCommandResponse
        val responseCode = response.responseCode
        val message = response.message
        val exceptionData = response.exceptionData
        val commandID = response.commandID
        val hashSetMovie = response.hashSetMovie
        val hashSetLong = response.hashSetLong
        val movie=response.movie

        var rez: String?
        rez = message

        if (responseCode == ResponseCode.OK) {

                if (hashSetMovie!=null){
                    rez+="\n"
                    for (item in hashSetMovie) {
                        rez += item
                        rez += "\n"
                    }
                }

                if (hashSetLong!=null){
                    rez+="\n"
                    for (item in hashSetLong) {
                        rez += item
                        rez += "\n"
                    }
                }
                if (movie!=null){
                    rez+="\n"+movie.toString()
                }
            }
        else {
        if (exceptionData == null) {
            rez = responseCode.toString()
        }
        else {
            rez = exceptionData.toString()
        }
        }
        return rez
    }
}