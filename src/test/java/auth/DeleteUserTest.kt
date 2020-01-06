package auth

import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.model.User
import CasinoLib.services.Auth
import CasinoLib.services.CasinoLibrary
import generators.RandomString
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.test.assertFailsWith

class DeleteUserTest {
    lateinit var user : User
    lateinit var user2 : User
    lateinit var user3 : User

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)

        user = User(login = RandomString.generate(12), password = RandomString.generate(15))
        user2 = User(login = RandomString.generate(12), password = RandomString.generate(15))
        user3 = User(login = RandomString.generate(12), password = RandomString.generate(15))
    }

    @Test
    fun deleteSelf() {
        val apikey = Auth.createUser(user.login!!, user.password!!)

        Auth.deleteUser(apikey, user.login!!, user.password!!)
        assertFailsWith(UserNotFoundException::class) {
            Auth.getUserKey(user.login!!, user.password!!)
        }
    }

    @Test
    fun deleteOtherUser() {
        val apikey2 = Auth.createUser(user2.login!!, user2.password!!)
        Auth.createUser(user3.login!!, user3.password!!)

        Auth.deleteUser(apikey2, user3.login!!, user3.password!!)

        Auth.getUserKey(user3.login!!, user3.password!!)

        assertFailsWith(UserNotFoundException::class) {
            Auth.getUserKey(user2.login!!, user2.password!!)
        }

    }
}