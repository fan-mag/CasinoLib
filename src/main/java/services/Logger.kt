package services

import helpers.BodyBuilder
import io.restassured.RestAssured.given

object Logger {
    lateinit var URL: String

    fun log(service: String? = null, message: String) {
        given()
                .baseUri(URL)
                .body(BodyBuilder.loggerBody(service, message))
                .post()
                .then()
                .assertThat()
                .statusCode(200)
    }


}
