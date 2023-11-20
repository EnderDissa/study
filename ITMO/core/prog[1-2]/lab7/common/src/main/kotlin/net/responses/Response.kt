package common.net.responses

import common.CommandID
import kotlinx.serialization.Serializable

@Serializable
enum class ResponseCode {
    OK,
    FAIL
}

@Serializable
open class Response(val responseCode: ResponseCode,
                    val message: String?,
                    val exceptionData: String?,
                    val commandID: CommandID)