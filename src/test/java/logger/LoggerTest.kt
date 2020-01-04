package logger

import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import services.CasinoLibrary
import services.Logger

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