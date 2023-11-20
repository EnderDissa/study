package client

import client.net.UDPClient
import client.commands.*
import commands.CommandManager
import client.run.*
import java.net.*
import common.entities.LogStatus
fun main() {
    val commandManager = CommandManager()
    
    val PORT=22837

    val client = UDPClient(InetAddress.getLocalHost(), PORT)
    
    commandManager.addCommand(AddCommand(client))
    commandManager.addCommand(AddIfMaxCommand(client))
    commandManager.addCommand(AddIfMinCommand(client))
    commandManager.addCommand(RemoveByIdCommand(client))
    commandManager.addCommand(ExitCommand(client))
    commandManager.addCommand(HelpCommand(client))
    commandManager.addCommand(InfoCommand(client))
    commandManager.addCommand(MaxScreenwriterCommand(client))
    commandManager.addCommand(PrintAscendingCommand(client))
    commandManager.addCommand(PrintDescendingCommand(client))
    commandManager.addCommand(RemoveLowerCommand(client))
    commandManager.addCommand(ShowCommand(client))
    commandManager.addCommand(UpdateCommand(client))
    commandManager.addCommand(LoginCommand(client))
    commandManager.addCommand((RegisterCommand(client)))
    val runManager = RunManager(commandManager,client)

    var logStatus=LogStatus.NEEDLOGIN
    println("Hello!\nIf you want to register, type command 'register', else 'login'")
    client.SetConnection()
    runManager.run(commandManager)
}