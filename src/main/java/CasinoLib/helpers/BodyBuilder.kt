package CasinoLib.helpers

import CasinoLib.model.Amount
import CasinoLib.model.Event
import CasinoLib.model.User
import com.google.gson.GsonBuilder

object BodyBuilder {

    fun loggerBody(events: List<Event>): String {
        return GsonBuilder().create().toJson(events)
    }

    fun createUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().create().toJson(user)
    }

    fun getUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().create().toJson(user)
    }

    fun deleteUserBody(login: String, password: String): String {
        val user = User(login = login, password = password)
        return GsonBuilder().create().toJson(user)
    }

    fun amountBody(login: String, amount: Long): String {
        val amountEntity = Amount(login = login, amount = amount)
        return GsonBuilder().create().toJson(amountEntity)
    }
}