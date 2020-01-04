package services

import com.google.gson.GsonBuilder
import io.restassured.RestAssured.given
import model.Event

object Logger {
    lateinit var URL: String

    fun log(service: String? = null, message: String) {
        given()
                .baseUri(URL)
                .body(setBody(service, message))
                .post()
                .then()
                .assertThat()
                .statusCode(200)
    }

    fun setBody(service: String?, message: String): String {
        val event = Event(service = service, message = message)
        val body = GsonBuilder().setPrettyPrinting().create().toJson(event)
        return body
    }
}
