import java.io.FileReader
import java.util.*

object Const {
    lateinit var operatorApikey: String

    fun init() {
        val prop = Properties()
        prop.load(FileReader("src\\test\\resources\\local.properties"))
        operatorApikey = prop.getProperty("operator.key")
    }
}