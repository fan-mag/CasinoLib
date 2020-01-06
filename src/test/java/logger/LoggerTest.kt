package logger

import CasinoLib.services.CasinoLibrary
import CasinoLib.services.Logger
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class LoggerTest {

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)
    }

    @Test
    fun testWithService() {
        Logger.log(message = "test with service", service = "test service")
    }

    @Test
    fun testWithDefaultService() {
        Logger.log(message = "test without service")
    }
}