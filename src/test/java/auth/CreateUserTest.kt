package auth

import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import services.CasinoLibrary

class CreateUserTest {

    @BeforeClass
    fun prepareFixtures() {
        val propertiesPath = "src/test/resources/local.properties"
        CasinoLibrary.init(propertiesPath)
    }

    @Test
    fun createUserTest() {
        //Auth.createUser(login = RandomString.generate(12), password = RandomString.generate(15))
    }
}