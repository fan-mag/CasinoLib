package services

import com.google.gson.Gson
import exceptions.UserAlreadyExistsException
import exceptions.UserNotAuthorizedException
import exceptions.UserNotFoundException
import helpers.BodyBuilder
import io.restassured.RestAssured.given
import model.User

object Auth {
    lateinit var URL: String

    fun createUser(login: String, password: String): String {
        val response = given()
                .baseUri(URL)
                .body(BodyBuilder.createUserBody(login, password))
                .`when`()
                .post()
        when (response.statusCode())
        {
            422 -> throw UserAlreadyExistsException()
            201 -> {
                val user = Gson().fromJson(response.body.asString(), User::class.java)
                return if (user.apikey != null) user.apikey!!
                else throw UnknownError("Не могу найти ApiKey в createUser")
            }
            else -> throw UnknownError("Полученный код ${response.statusCode()} не обрабатывается")
        }
    }

    fun getUserKey(login: String, password: String): String {
        val response = given()
                .baseUri(URL)
                .body(BodyBuilder.getUserBody(login, password))
                .`when`()
                .put()
        when (response.statusCode())
        {
            404 -> throw UserNotFoundException()
            401 -> throw UserNotAuthorizedException()
            200 -> {
                val user = Gson().fromJson(response.body.asString(), User::class.java)
                return if (user.apikey != null) user.apikey!!
                else throw UnknownError("Не могу найти ApiKey в getUserKey")
            }
            else -> throw UnknownError("Полученный код ${response.statusCode()} не обрабатывается")
        }
    }

    fun getUserBalance(): Int {
        return 0
    }

    fun deleteUser() {

    }


}