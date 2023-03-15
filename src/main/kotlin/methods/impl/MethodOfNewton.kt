package methods.impl

import SolveEquation
import methods.Method
import utils.Equation
import kotlin.math.abs


object MethodOfNewton : Method {


    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        val function = equation.getFunction()
        checkNumberOfRoots(function, isolation)
        checkConvergence(function, isolation)
        checkNumberOfRoots(derive(function), isolation)
        var x = getFirstApproximation(function, isolation)
        var x0 = x
        var cntOfIterations = 0;
        val d = derive(function)

        if (
            abs(function(x)) > accuracy &&
            abs(x - x0) > accuracy &&
            abs(function(x) / d(x)) > accuracy
        ) {
            SolveEquation.printResult(x, function(x), 0)
        } else {

            do {
                x0 = x
                cntOfIterations++
                x = x0 - function(x0) / d(x)
            } while (
                abs(function(x)) > accuracy &&
                abs(x - x0) > accuracy &&
                abs(function(x) / d(x)) > accuracy
            )
            SolveEquation.printResult(x, function(x), cntOfIterations)
            showGraph(function, isolation)
        }
    }



    private fun getFirstApproximation(function: (Double) -> Double, isolation: Pair<Double, Double>): Double {
        val secondD = derive(derive(function))
        val (a, b) = isolation
        return if (function(a) * secondD(a) > 0) a else b
    }
}