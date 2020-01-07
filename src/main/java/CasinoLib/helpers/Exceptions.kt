package CasinoLib.helpers

import CasinoLib.services.Logger

object Exceptions {
    fun handle(exception: Exception, service: String) {
        if (exception.message != null) {
            exception.printStackTrace()
            Logger.log(service = service, message = exception.message!!)
        } else {
            exception.printStackTrace()
            Logger.log(service = service, message = "Exception without any message")
        }
    }
}