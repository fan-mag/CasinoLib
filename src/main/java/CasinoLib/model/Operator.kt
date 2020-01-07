package CasinoLib.model

import CasinoLib.services.Auth
import com.google.gson.Gson
import java.io.FileReader

class Operator {
    lateinit var login: String
    lateinit var password: String
    lateinit var apikey: String
    lateinit var privilege: Privilege

    fun fromJson(): Operator {
        return fromJson("src\\main\\resources\\operator.json")
    }

    fun fromJson(filePath: String): Operator {
        val operator = Gson().fromJson(FileReader(filePath), Operator::class.java)
        operator.apikey = Auth.getUserKey(operator.login, operator.password)
        operator.privilege = Auth.getUserPrivilege(operator.apikey)
        return operator
    }
}