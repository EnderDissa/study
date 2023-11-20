package common.net.requests

import common.CommandID
import common.entities.Movie
import common.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UniqueCommandRequest(val movie: Movie? = null,
                                val value: Long? = null,
                                val commandIDc: CommandID,
                                val user: User? = null) : Request(commandIDc)

@Serializable
class AddRequest(val movie: Movie): Request(CommandID.ADD)

@Serializable
class AddIfMaxRequest(val movie: Movie): Request(CommandID.ADDIFMAX)

@Serializable
class AddIfMinRequest(val movie: Movie): Request(CommandID.ADDIFMIN)

@Serializable
class ExecuteScriptRequest(val scriptName: String): Request(CommandID.EXECUTE_SCRIPT)

@Serializable
class HelpRequest(): Request(CommandID.HELP)

@Serializable
class InfoRequest(): Request(CommandID.INFO)

@Serializable
class MaxScreenwriterRequest(): Request(CommandID.MAX_SCREENWRITER)

@Serializable
class PrintAscendingRequest(): Request(CommandID.PRINT_ASCENDING)

@Serializable
class PrintDescendingRequest(): Request(CommandID.PRINT_DESCENDING)

@Serializable
class RemoveByIdRequest(val id: Long): Request(CommandID.REMOVE_BY_ID)

@Serializable
class RemoveLowerRequest(val oscarsCount: Long): Request(CommandID.REMOVE_LOWER)

@Serializable
class ShowRequest(): Request(CommandID.SHOW)

@Serializable
class UpdateByIdRequest(val id: Long, val movie:Movie): Request(CommandID.UPDATE_BY_ID)