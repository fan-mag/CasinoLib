package exceptions

import services.Logger

open class BaseException(message: String): RuntimeException(message) {

    init {
        Logger.log("Library", message)
    }

}