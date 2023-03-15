package utils

data class SystemOfEquations(
    private val exp: String,
    private val function1: (Double, Double) -> Double,
    private val function2: (Double, Double) -> Double,
    private val roots: Pair<Double, Double>
){
    fun getExp() = exp

    fun getFunction1() = function1

    fun getFunction2() = function2

    fun getRoots() = roots

}