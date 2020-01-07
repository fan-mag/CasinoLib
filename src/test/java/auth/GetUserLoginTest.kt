package auth

import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.model.User
import CasinoLib.services.Auth
import CasinoLib.services.CasinoLibrary
import generators.RandomString
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetUserLoginTest {
    lateinit var user: User

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)

        user = User(login = RandomString.generate(12), password = RandomString.generate(15))

        user.apikey = Auth.createUser(user.login!!, user.password!!)
    }


    @Test
    fun `get User Login by api key`() {
        val login = Auth.getUserLogin(user.apikey!!)
        assertEquals(user.login, login)
    }

    @Test
    fun `get User Login with Not Existing api key`() {
        assertFailsWith(UserNotFoundException::class) {
            Auth.getUserLogin(RandomString.generate(15))
        }

    }
}