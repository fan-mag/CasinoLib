package CasinoLib.services

import CasinoLib.helpers.BodyBuilder
import CasinoLib.model.Event
import io.restassured.RestAssured.given
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Logger(val service: String?, val message: String) : Runnable {

    fun log() {
        buffer.add(Event(service = service, message = message))
        if (buffer.size == BUFFER_SIZE) {
            body = BodyBuilder.loggerBody(buffer)
            buffer.clear()
            threadQueue.submit(Thread(this))
        }
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

    companion object {
        lateinit var URL: String
        var BUFFER_SIZE: Int = 10
        lateinit var body: String
        val buffer: ArrayList<Event> = ArrayList()
        val threadQueue: ExecutorService = Executors.newFixedThreadPool(1)

        fun log(service: String? = null, message: String) {
            Logger(service, message).log()
        }
    }
}
