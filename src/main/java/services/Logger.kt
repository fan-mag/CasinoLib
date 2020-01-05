package services

import helpers.BodyBuilder
import io.restassured.RestAssured.given
import java.util.concurrent.Executors

class Logger(val service: String?, val message: String) : Runnable {

    fun log() {
        threadQueue.submit(Thread(this))
    }

    override fun run() {
        given()
                .baseUri(URL)
                .body(BodyBuilder.loggerBody(service, message))
                .post()
                .then()
                .assertThat()
                .statusCode(200)
    }

    companion object {
        lateinit var URL: String
        val threadQueue = Executors.newFixedThreadPool(1)
        fun log(service: String? = null, message: String) {
            Logger(service, message).log()
        }
    }
}
