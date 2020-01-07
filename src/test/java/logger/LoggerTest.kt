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
        Logger.log(message = "test with service 2", service = "test service")
        Logger.log(message = "test with service 3", service = "test service")
        Logger.log(message = "test with service 4", service = "test service")
        Logger.log(message = "test with service 5", service = "test service")
        Thread.sleep(5000)
    }

    @Test
    fun testWithDefaultService() {
        //Logger.log(message = "test without service")
    }
}