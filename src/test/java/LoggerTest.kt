import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import services.Logger

class LoggerTest {

    @BeforeMethod
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        Logger.init(propertiesPath)
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