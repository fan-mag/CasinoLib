package CasinoLib.services

import CasinoLib.exceptions.UserAlreadyExistsException
import CasinoLib.exceptions.UserNotAuthorizedException
import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.exceptions.WrongApikeyProvidedException
import CasinoLib.helpers.BodyBuilder
import CasinoLib.model.Privilege
import CasinoLib.model.User
import com.google.gson.Gson
import io.restassured.RestAssured.given
import io.restassured.http.ContentType

object Auth {
    lateinit var URL: String

    fun createUser(login: String, password: String): String {
        val response = given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .body(BodyBuilder.createUserBody(login, password))
                .`when`()
                .post()
        when (response.statusCode) {
            422 -> throw UserAlreadyExistsException()
            201 -> {
                val user = Gson().fromJson(response.body.asString(), User::class.java)
                return if (user.apikey != null) user.apikey!!
                else throw UnknownError("Can't find user api key in createUser")
            }
            else -> throw UnknownError("Response code ${response.statusCode} can not be handled")
        }
    }

    fun getUserKey(login: String, password: String): String {
        val response = given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .body(BodyBuilder.getUserBody(login, password))
                .`when`()
                .put()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            401 -> throw UserNotAuthorizedException()
            200 -> {
                val user = Gson().fromJson(response.body.asString(), User::class.java)
                return if (user.apikey != null) user.apikey!!
                else throw UnknownError("Can't find user api key in getUserKey")
            }
            else -> throw UnknownError("Response code ${response.statusCode} can not be handled")
        }
    }

    fun getUserPrivilege(apikey: String): Privilege {
        val response = given()
                .baseUri(URL)
                .header("apikey", apikey)
                .`when`()
                .get()
        when (response.statusCode) {
            404 -> throw WrongApikeyProvidedException()
            200 -> {
                return Gson().fromJson(response.body.asString(), Privilege::class.java)
                        ?: throw UnknownError("null privilege")
            }
            else -> throw UnknownError("Response code ${response.statusCode} can not be handled")
        }
    }

    fun deleteUser(apikey: String, login: String, password: String): Boolean {
        val response = given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .header("apikey", apikey)
                .body(BodyBuilder.deleteUserBody(login, password))
                .`when`()
                .delete()
        when (response.statusCode) {
            404 -> return false
            202 -> return true
            else -> throw UnknownError("Response code ${response.statusCode} can not be handled")

        }
    }

    fun getUserLogin(apikey: String) : String {
        val response = given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .header("apikey", apikey)
                .`when`()
                .patch()
        when (response.statusCode) {
            404 -> throw UserNotFoundException()
            200 -> return Gson().fromJson(response.body.asString(), User::class.java).login!!
            else -> throw UnknownError("Response code ${response.statusCode} can not be handled")
        }
    }
}