package logger

import CasinoLib.services.CasinoLibrary
import CasinoLib.services.Logger
import org.testng.Assert.assertEquals
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
    }

    @Test
    fun testWithDefaultService() {
        Logger.log(message = "test without service")
    }

    @Test
    fun `test buffer watcher`() {

        Thread.sleep(Logger.watcher.timeout.toLong() + 500)
        assertEquals(Logger.buffer.size, 0)
        Logger.log(service = "test service", message = "Group 1 - 1")
        assertEquals(Logger.buffer.size, 1)
        Logger.log(service = "test service", message = "Group 1 - 2")
        assertEquals(Logger.buffer.size, 2)
        Logger.log(service = "test service", message = "Group 1 - 3")
        assertEquals(Logger.buffer.size, 3)
        Logger.log(service = "test service", message = "Group 1 - 4")
        assertEquals(Logger.buffer.size, 4)
        Logger.log(service = "test service", message = "Group 1 - 5")
        assertEquals(Logger.buffer.size, 0)
        Thread.sleep(500)
        assertEquals(Logger.buffer.size, 0)

        Logger.log(service = "test service", message = "Group 2 - 1")
        assertEquals(Logger.buffer.size, 1)
        Logger.log(service = "test service", message = "Group 2 - 2")
        assertEquals(Logger.buffer.size, 2)
        Logger.log(service = "test service", message = "Group 2 - 3")
        assertEquals(Logger.buffer.size, 3)
        Thread.sleep(Logger.watcher.timeout.toLong() + 500)
        assertEquals(Logger.buffer.size, 0)

        Logger.log(service = "test service", message = "Group 3 - 1")
        assertEquals(Logger.buffer.size, 1)
        Thread.sleep(Logger.watcher.timeout.toLong() - 500)
        Logger.log(service = "test service", message = "Group 3 - 2")
        assertEquals(Logger.buffer.size, 2)
        Thread.sleep(Logger.watcher.timeout.toLong() - 500)
        Logger.log(service = "test service", message = "Group 3 - 3")
        assertEquals(Logger.buffer.size, 3)
        Thread.sleep(Logger.watcher.timeout.toLong() - 500)
        Logger.log(service = "test service", message = "Group 3 - 4")
        assertEquals(Logger.buffer.size, 4)
        Thread.sleep(Logger.watcher.timeout.toLong() + 500)
        assertEquals(Logger.buffer.size, 0)

    }
}