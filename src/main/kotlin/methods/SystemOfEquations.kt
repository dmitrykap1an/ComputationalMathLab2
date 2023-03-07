package methods

import java.util.function.DoubleFunction

data class SystemOfEquations(
    private val exp: String,
    private val function1: DoubleFunction<Double>,
    private val function2: DoubleFunction<Double>,
    private val listOfRoots: Pair<List<Double>, List<Double>>
){
    fun getExp() = exp

    fun getFunction1() = function1

    fun getFunction2() = function2

    fun getListOfRoots() = listOfRoots
}