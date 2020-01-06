package CasinoLib.helpers

import CasinoLib.model.Event
import CasinoLib.model.User
import com.google.gson.GsonBuilder

object BodyBuilder {

    fun loggerBody(service: String?, message: String): String {
        val event = Event(service = service, message = message)
        return GsonBuilder().setPrettyPrinting().create().toJson(event)
    }

    fun createUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().setPrettyPrinting().create().toJson(user)
    }

    fun getUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().setPrettyPrinting().create().toJson(user)
    }

    fun deleteUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().setPrettyPrinting().create().toJson(user)
    }
}