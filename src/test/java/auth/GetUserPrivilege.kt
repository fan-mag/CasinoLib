package auth

import CasinoLib.exceptions.WrongApikeyProvidedException
import CasinoLib.model.User
import CasinoLib.services.Auth
import CasinoLib.services.CasinoLibrary
import generators.RandomString
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.test.assertFailsWith

class GetUserPrivilege {
    lateinit var user: User
    lateinit var userApikey: String
    lateinit var user2: User
    lateinit var user3: User

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)

        user = User(login = RandomString.generate(12), password = RandomString.generate(15))
        user2 = User(login = RandomString.generate(12), password = RandomString.generate(15))
        user3 = User(login = RandomString.generate(12), password = RandomString.generate(15))

        userApikey = Auth.createUser(user.login!!, user.password!!)
    }

    @Test
    fun getMyPrivilege() {
        val privilege = Auth.getUserPrivilege(userApikey)

        Assert.assertEquals(privilege.level, 7)
        Assert.assertEquals(privilege.description, "User")
    }

    @Test
    fun getPrivilegeWrongApikey() {
        assertFailsWith(WrongApikeyProvidedException::class) {
            Auth.getUserPrivilege(RandomString.generate(15))
        }
    }

}