package CasinoLib.exceptions

import CasinoLib.services.Logger

open class BaseException(message: String): RuntimeException(message) {

    init {
        Logger.log("Library", message)
    }

}