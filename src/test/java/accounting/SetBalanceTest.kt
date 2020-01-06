package accounting

import CasinoLib.exceptions.ForbiddenException
import CasinoLib.exceptions.NotEnoughMoney
import CasinoLib.exceptions.UserNotFoundException
import CasinoLib.model.User
import CasinoLib.services.Account
import CasinoLib.services.Auth
import CasinoLib.services.CasinoLibrary
import Const
import generators.RandomString
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SetBalanceTest {
    lateinit var user: User
    private var initAmount: Long = -1
    lateinit var apikey: String


    @BeforeClass
    fun prepareFixtures() {
        Const.init()
        CasinoLibrary.init("src/test/resources/local.properties")

        user = User(login = RandomString.generate(12), password = RandomString.generate(15))
        apikey = Auth.createUser(user.login!!, user.password!!)

        initAmount = Random.nextLong(1000, 10000)
    }

    @Test
    fun `set User Balance`() {
        Account.setBalance(Const.operatorApikey, user.login!!, initAmount)
        assertEquals(Account.getBalance(apikey).amount, initAmount)

        val negativeBalance = Random.nextLong(-1000, -1)
        assertFailsWith(NotEnoughMoney::class) {
            Account.setBalance(Const.operatorApikey, user.login!!, negativeBalance)
        }
    }

    @Test
    fun `set by User`() {
        assertFailsWith(ForbiddenException::class) {
            Account.setBalance(apikey, user.login!!, Random.nextLong(1, 100))
        }
    }

    @Test
    fun `set balance of non-existing user`() {
        assertFailsWith(UserNotFoundException::class) {
            Account.setBalance(Const.operatorApikey, RandomString.generate(12), Random.nextLong(1, 100))
        }
    }
}