package auth

import exceptions.UserAlreadyExistsException
import generators.RandomString
import model.User
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import services.Auth
import services.CasinoLibrary
import kotlin.test.assertFailsWith

class CreateUserTest {
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
    fun createUserTest() {
        val apikey = Auth.createUser(user.login!!, user.password!!)

        val apikeyUser = Auth.getUserKey(user.login!!, user.password!!)

        Assert.assertEquals(apikey, apikeyUser)

    }

    @Test
    fun creatingUserThatExists() {
        Auth.createUser(user2.login!!, user2.password!!)

        assertFailsWith(UserAlreadyExistsException::class){
            Auth.createUser(user2.login!!, user2.password!!)
        }
    }
}