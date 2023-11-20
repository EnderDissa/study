import commands.*
import common.CommandID
import common.entities.MovieManager
import net.UDPServerDatagram
import java.net.InetAddress
import kotlinx.coroutines.*

    @OptIn(DelicateCoroutinesApi::class)
    fun main() = runBlocking {

        val movieManager = MovieManager()

        movieManager.setFileName("collection.csv")
        movieManager.load()
        Runtime.getRuntime().addShutdownHook(Thread(movieManager::save))


        val commandManager = CommandManager()
        commandManager.addCommand(AddCommand(movieManager), CommandID.ADD)
        commandManager.addCommand(AddIfMaxCommand(movieManager), CommandID.ADDIFMAX)
        commandManager.addCommand(AddIfMinCommand(movieManager), CommandID.ADDIFMIN)
        commandManager.addCommand(HelpCommand(commandManager), CommandID.HELP)
        commandManager.addCommand(InfoCommand(movieManager), CommandID.INFO)
        commandManager.addCommand(MaxScreenwriterCommand(movieManager), CommandID.MAX_SCREENWRITER)
        commandManager.addCommand(PrintAscendingCommand(movieManager), CommandID.PRINT_ASCENDING)
        commandManager.addCommand(PrintDescendingCommand(movieManager), CommandID.PRINT_DESCENDING)
        commandManager.addCommand(RemoveByIdCommand(movieManager), CommandID.REMOVE_BY_ID)
        commandManager.addCommand(RemoveLowerCommand(movieManager), CommandID.REMOVE_LOWER)
        commandManager.addCommand(ShowCommand(movieManager), CommandID.SHOW)
        commandManager.addCommand(UpdateCommand(movieManager), CommandID.UPDATE_BY_ID)

        val server = UDPServerDatagram(InetAddress.getLocalHost(), commandManager, 22837)
//    val thread = launch {
//        val controller = ServerController(movieManager, server)
//        controller.run()
//
//    }
        val controller = ServerController(movieManager, server)
        var checker = false
        GlobalScope.launch { server.run() }


        while (!checker) {
            val input = readlnOrNull()

            if (input != null) {
                runBlocking { checker = controller.run(input) }
            }
        }
        // thread.join()
    }