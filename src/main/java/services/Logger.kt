package services

import helpers.BodyBuilder
import io.restassured.RestAssured.given

object Logger : Runnable {
    lateinit var URL: String
    @Volatile var service: String? = null
    @Volatile lateinit var message: String

    fun log(service: String? = null, message: String) {
        synchronized(this) {
            this.service = service
            this.message = message
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

}
