package CasinoLib.services

import java.io.FileReader
import java.util.*

object CasinoLibrary {

    fun init(propertiesPath: String) {
        val properties = Properties()
        properties.load(FileReader(propertiesPath))
        Logger.URL = properties.getProperty("logger.url")
        Auth.URL = properties.getProperty("auth.url")
    }
}