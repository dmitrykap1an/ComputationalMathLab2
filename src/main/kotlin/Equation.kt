import java.util.function.DoubleFunction

data class Equation(
    private val exp: String,
    private val function: DoubleFunction<Double>,
    private val listOfRoots: List<Double>
){
    fun getExp() = exp

    fun getFunction() = function

    fun getListOfRoots() = listOfRoots
}
