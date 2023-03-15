package utils

data class Equation(
    private val exp: String,
    private val function: (Double) -> Double,
){
    fun getExp() = exp

    fun getFunction() = function

}
