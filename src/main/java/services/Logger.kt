package services

import helpers.BodyBuilder
import io.restassured.RestAssured.given

class Logger(val service: String?, val message: String) : Runnable {

    fun log() {
        synchronized(this) {
            Thread(this).start()
        }
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
        fun log(service: String? = null, message: String) {
            Logger(service, message).log()
        }
    }
}
