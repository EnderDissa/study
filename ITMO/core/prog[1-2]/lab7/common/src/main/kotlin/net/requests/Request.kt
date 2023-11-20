package common.net.requests

import common.CommandID
import kotlinx.serialization.Serializable

@Serializable
open class Request(val commandID: CommandID)