package exceptions

import services.Logger

class UnknownException(message: String): BaseException(message) {
    init {
        Logger.log("Library", message)
    }
}