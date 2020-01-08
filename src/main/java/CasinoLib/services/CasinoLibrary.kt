package CasinoLib.services

import java.io.FileReader
import java.util.*

object CasinoLibrary {
    var isInitializedAlready = false

    fun init(propertiesPath: String) {
        if (!isInitializedAlready) {
            isInitializedAlready = true
            val properties = Properties()
            properties.load(FileReader(propertiesPath))
            Logger.URL = properties.getProperty("logger.url")
            Logger.BUFFER_SIZE = properties.getProperty("logger.buffer_size", "10").toInt()
            Logger.watcher = Logger.Watcher(timeout = properties.getProperty("logger.buffer_timeout", "2000").toInt(),
                    pollInterval = properties.getProperty("logger.buffer_pollinterval", "250").toLong())
            Thread(Logger.watcher).start()
            Auth.URL = properties.getProperty("auth.url")
            Account.URL = properties.getProperty("accnt.url")
        }
    }
}