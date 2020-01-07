package CasinoLib.services

import java.io.FileReader
import java.util.*

object CasinoLibrary {

    fun init(propertiesPath: String) {
        val properties = Properties()
        properties.load(FileReader(propertiesPath))
        Logger.URL = properties.getProperty("logger.url")
        Logger.BUFFER_SIZE = properties.getProperty("logger.buffer_size", "10").toInt()
        Auth.URL = properties.getProperty("auth.url")
        Account.URL = properties.getProperty("accnt.url")

    }
}