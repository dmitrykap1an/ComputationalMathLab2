package methods

import CLI
import Equation
import kotlin.math.abs

object MethodOfHalfDivision: Method {

    private var cntOfIterations = 0;

    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        val function = equation.getFunction()
        val answer = selectInterval(function, isolation, accuracy)
        val root = (answer.first + answer.second) / 2
        CLI.printResult(root, function(root), cntOfIterations)
    }


    private fun selectInterval(function: (x: Double) -> Double, isolation: Pair<Double, Double>, accuracy: Double): Pair<Double, Double>{
        return if(abs(isolation.first - isolation.second) < accuracy) isolation
        else {
            cntOfIterations++;
            val x = (isolation.first + isolation.second) / 2
            if(function(x) * function(isolation.first) < 0) selectInterval(function, isolation.first to x, accuracy)
            else selectInterval(function, x to isolation.second, accuracy)
        }
    }

}