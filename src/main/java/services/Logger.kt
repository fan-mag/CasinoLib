package services

import io.restassured.RestAssured.given
import java.io.FileReader
import java.util.*

class Logger {
    companion object {
        lateinit var URL: String

        fun init(propertiesPath: String) {
            val properties = Properties()
            properties.load(FileReader(propertiesPath))
            URL = properties.getProperty("logger.url")
        }

        fun log(service: String? = null, message: String) {
            given()
                    .baseUri(URL)
                    .body(setBody(service, message))
                    .post()
                    .then()
                    .assertThat()
                    .statusCode(200)
        }

        fun setBody(service: String?, message: String): String
        {
            return if (service == null) """
                    {
                        "message":"$message"
                    }
                """.trimIndent()
            else """
                    {
                        "service":"$service",
                        "message":"$message"
                    }
                """.trimIndent()
        }
    }
}