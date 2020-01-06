package CasinoLib.services

import CasinoLib.exceptions.ForbiddenException
import CasinoLib.exceptions.NotEnoughMoney
import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.helpers.BodyBuilder
import CasinoLib.model.Amount
import com.google.gson.Gson
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object Account {
    lateinit var URL: String

    fun getBalance(apikey: String, login: String): Amount {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .queryParam("target", login)
                .`when`()
                .get()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            403 -> throw ForbiddenException()
            200 -> return Gson().fromJson(response.body.asString(), Amount::class.java)
            else -> throw UnknownError("Response code ${response.statusCode()} can not be handled")
        }
    }

    fun getBalance(apikey: String): Amount {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .`when`()
                .get()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            200 -> return Gson().fromJson(response.body.asString(), Amount::class.java)
            else -> throw UnknownError("Response code ${response.statusCode()} can not be handled")
        }
    }

    fun addBalance(apikey: String, login: String, amount: Long): Amount {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .contentType(ContentType.JSON)
                .body(BodyBuilder.amountBody(login, amount))
                .`when`()
                .put()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            403 -> throw ForbiddenException()
            200 -> return Gson().fromJson(response.body.asString(), Amount::class.java)
            else -> throw UnknownError("Response code ${response.statusCode()} can not be handled")
        }
    }

    fun subBalance(apikey: String, login: String, amount: Long): Amount {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .contentType(ContentType.JSON)
                .body(BodyBuilder.amountBody(login, -amount))
                .`when`()
                .put()
        when (response.statusCode) {
            422 -> throw NotEnoughMoney()
            404 -> throw UserNotFoundException()
            403 -> throw ForbiddenException()
            200 -> return Gson().fromJson(response.body.asString(), Amount::class.java)
            else -> throw UnknownError("Response code ${response.statusCode()} can not be handled")
        }
    }

    fun setBalance(apikey: String, login: String, amount: Long): Amount {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .contentType(ContentType.JSON)
                .body(BodyBuilder.amountBody(login, amount))
                .`when`()
                .post()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            403 -> throw ForbiddenException()
            200 -> return Gson().fromJson(response.body.asString(), Amount::class.java)
            else -> throw UnknownError("Response code ${response.statusCode()} can not be handled")
        }
    }


}