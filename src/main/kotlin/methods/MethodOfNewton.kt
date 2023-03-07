package methods

import CLI
import Equation
import java.util.function.DoubleFunction
import kotlin.math.abs


object MethodOfNewton : Method {


    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        val function = equation.getFunction()
        checkNewtonConvergence(function, isolation)

        var x = getFirstApproximation(function, isolation)
        var x0 = x
        var cntOfIterations = 0;
        val d = derive(function)

        if (
            abs(function.apply(x)) > accuracy &&
            abs(x - x0) > accuracy &&
            abs(function.apply(x) / d.apply(x)) > accuracy
        ) {
            CLI.printResult(x, function.apply(x), 0)
        } else {

            do {
                x0 = x
                cntOfIterations++
                x = x0 - function.apply(x0) / d.apply(x)
            } while (
                abs(function.apply(x)) > accuracy &&
                abs(x - x0) > accuracy &&
                abs(function.apply(x) / d.apply(x)) > accuracy
            )
            CLI.printResult(x, function.apply(x), cntOfIterations)
            showGraph(function, isolation)
        }
    }

    private fun checkNewtonConvergence(function: DoubleFunction<Double>, isolation: Pair<Double, Double>) {
        checkConvergence(function, isolation)
        val firstD = derive(function)
        val secondD = derive(firstD)
        val (a, b) = isolation
        if (!(firstD.apply(a) * firstD.apply(b) > 0 && secondD.apply(a) * secondD.apply(b) > 0)) {
            println("Не выполняется достаточное условие единственности корня на отрезке [$a,$b]")
        }
    }


    private fun getFirstApproximation(function: DoubleFunction<Double>, isolation: Pair<Double, Double>): Double {
        val secondD = derive(derive(function))
        val (a, b) = isolation
        return if (function.apply(a) * secondD.apply(a) > 0) a else b
    }
}