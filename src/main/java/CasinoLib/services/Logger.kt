package CasinoLib.services

import CasinoLib.helpers.BodyBuilder
import CasinoLib.model.Event
import io.restassured.RestAssured.given
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Logger(val service: String?, val message: String) : Runnable {

    fun log() {
        buffer.add(Event(service = service, message = message))
        watcher.queueHasMembers = true
        if (buffer.size >= BUFFER_SIZE) {
            push()
        }
    }

    private fun push() {
        watcher.queueHasMembers = false
        body = BodyBuilder.loggerBody(buffer)
        buffer.clear()
        threadQueue.submit(Thread(this))
    }

    override fun run() {
        given()
                .baseUri(URL)
                .body(body)
                .post()
                .then()
                .assertThat()
                .statusCode(200)
    }

    class Watcher(val timeout: Int, val pollInterval: Long) : Runnable {
        var queueHasMembers = false
            set(value) {
                startTime = Date().time
                field = value
            }
        private var startTime = Date().time

        override fun run() {
            while (true) {
                if (queueHasMembers) {
                    val currentTime = Date().time
                    if (currentTime - startTime > timeout) {
                        Logger("Library", "Force push queue").push()
                    }
                }
                Thread.sleep(pollInterval)
            }
        }
    }

    companion object {
        lateinit var URL: String
        lateinit var watcher: Watcher
        var BUFFER_SIZE: Int = 10
        lateinit var body: String
        val buffer: CopyOnWriteArrayList<Event> = CopyOnWriteArrayList()
        val threadQueue: ExecutorService = Executors.newFixedThreadPool(1)

        fun log(service: String? = null, message: String) {
            Logger(service, message).log()
        }


    }
}
