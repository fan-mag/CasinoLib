package accounting

import CasinoLib.model.User
import Const
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class GetBalanceTest {
    lateinit var user: User

    @BeforeClass
    fun prepareFixtures() {
        Const.init()
    }

    @Test
    fun getBalanceTest() {

    }
}