package accounting

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

class GetBalanceTest {
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
    fun `get balance by user`() {
        assertEquals(Account.getBalance(apikey).amount, initAmount)
    }

    @Test
    fun `get user balance by operator`() {
        assertEquals(Account.getBalance(Const.operatorApikey, user.login!!).amount, initAmount)
    }

    @Test
    fun `get NotExisted user balance by operator`() {
        assertFailsWith(UserNotFoundException::class) {
            Account.getBalance(Const.operatorApikey, RandomString.generate(12)).amount
        }
    }

    @Test
    fun `get balance with unexisted apikey`() {
        assertFailsWith(UserNotFoundException::class) {
            Account.getBalance(RandomString.generate(15))
        }
    }

}