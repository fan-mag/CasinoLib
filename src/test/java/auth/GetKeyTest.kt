package auth

import CasinoLib.exceptions.UserNotAuthorizedException
import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.model.User
import CasinoLib.services.Auth
import CasinoLib.services.CasinoLibrary
import generators.RandomString
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.test.assertFailsWith

class GetKeyTest {
    lateinit var user: User

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)

        user = User(login = RandomString.generate(12), password = RandomString.generate(15))

        user.apikey = Auth.createUser(user.login!!, user.password!!)
    }


    @Test
    fun getKeyWithCorrect() {

        val apikey = Auth.getUserKey(user.login!!, user.password!!)

        Assert.assertEquals(user.apikey, apikey)

    }

    @Test
    fun getKeyIncorrectPassword() {
        assertFailsWith(UserNotAuthorizedException::class) {
            Auth.getUserKey(user.login!!, RandomString.generate(15))
        }
    }

    @Test
    fun getKeyNotExistingUser() {
        assertFailsWith(UserNotFoundException::class) {
            Auth.getUserKey(RandomString.generate(12), RandomString.generate(15))
        }
    }
}