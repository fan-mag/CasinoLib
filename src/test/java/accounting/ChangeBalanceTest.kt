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

class ChangeBalanceTest {
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
        Account.setBalance(Const.operatorApikey, user.login!!, initAmount)
    }

    @Test
    fun `change User Balance`() {
        val addBalance = Random.nextLong(50, 1000)
        Account.addBalance(Const.operatorApikey, user.login!!, addBalance)
        assertEquals(Account.getBalance(apikey).amount, initAmount + addBalance)

        val subBalance = Random.nextLong(10, 900)
        Account.subBalance(Const.operatorApikey, user.login!!, subBalance)
        assertEquals(Account.getBalance(apikey).amount, initAmount + addBalance - subBalance)

        val subHugeBalance = Random.nextLong(100000, 999999)
        assertFailsWith(NotEnoughMoney::class) {
            Account.subBalance(Const.operatorApikey, user.login!!, subHugeBalance)
        }
    }

    @Test
    fun `change by User`() {
        assertFailsWith(ForbiddenException::class) {
            Account.addBalance(apikey, user.login!!, Random.nextLong(1, 100))
        }
    }

    @Test
    fun `change balance of non-existing user`() {
        assertFailsWith(UserNotFoundException::class) {
            Account.addBalance(Const.operatorApikey, RandomString.generate(12), Random.nextLong(1, 100))
        }
    }
}