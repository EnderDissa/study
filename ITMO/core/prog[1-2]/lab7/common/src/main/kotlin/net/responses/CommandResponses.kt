package common.net.responses

import common.CommandID
import common.entities.Movie
import common.entities.User
import kotlinx.serialization.Serializable


@Serializable
data class UniqueCommandResponse(private val responseCodeC: ResponseCode,
                                 private val messageC: String? = null,
                                 private val exceptionDataC: String? = null,
                                 val commandIDC: CommandID,
                                 val movie: Movie? = null,
                                 val hashSetMovie: List<Movie>? = null,
                                 val hashSetLong: List<Long?>? = null) : Response(responseCodeC, messageC, exceptionDataC, commandIDC)
@Serializable
class AddResponse(private val responseCodeC: ResponseCode,
                  private val messageC: String?,
                  private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.ADD)


class AddIfMaxResponse(private val responseCodeC: ResponseCode,
                       private val messageC: String?,
                       private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.ADDIFMAX)


class AddIfMinResponse(private val responseCodeC: ResponseCode,
                       private val messageC: String?,
                       private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.ADDIFMIN)


class ExecuteScriptResponse(private val responseCodeC: ResponseCode,
                            private val messageC: String?,
                            private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.EXECUTE_SCRIPT)


class HelpResponse(private val responseCodeC: ResponseCode,
                   private val messageC: String?,
                   private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.HELP)


class InfoResponse(private val responseCodeC: ResponseCode,
                   private val messageC: String?,
                   private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.INFO)


class MaxScreenwriterResponse(private val responseCodeC: ResponseCode,
                              private val messageC: String?,
                              private val exceptionDataC: String?,

                              val movie: Movie?): Response(responseCodeC, messageC, exceptionDataC, CommandID.MAX_SCREENWRITER)


class PrintAscendingResponse(private val responseCodeC: ResponseCode,
                             private val messageC: String?,
                             private val exceptionDataC: String?,

                             val hashSet: List<Movie>?): Response(responseCodeC, messageC, exceptionDataC, CommandID.PRINT_ASCENDING)


class PrintDescendingResponse(private val responseCodeC: ResponseCode,
                              private val messageC: String?,
                              private val exceptionDataC: String?,

                              val hashSet: List<Long?>?
): Response(responseCodeC, messageC, exceptionDataC, CommandID.PRINT_DESCENDING)


class RemoveByIdResponse(private val responseCodeC: ResponseCode,
                         private val messageC: String?,
                         private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.REMOVE_BY_ID)


class RemoveLowerResponse(private val responseCodeC: ResponseCode,
                          private val messageC: String?,
                          private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.REMOVE_LOWER)


class ShowResponse(private val responseCodeC: ResponseCode,
                   private val messageC: String?,
                   private val exceptionDataC: String?,
                   val hashSet: List<Movie>?): Response(responseCodeC, messageC, exceptionDataC, CommandID.SHOW)


class UpdateByIdResponse(private val responseCodeC: ResponseCode,
                         private val messageC: String?,
                         private val exceptionDataC: String?): Response(responseCodeC, messageC, exceptionDataC, CommandID.UPDATE_BY_ID)