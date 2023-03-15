package methods.impl

import SolveEquation
import methods.Method
import utils.Equation
import kotlin.math.abs

object MethodOfHalfDivision: Method {

    private var cntOfIterations = 0
    private var x = Double.MAX_VALUE

    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        val f = equation.getFunction()
        checkNumberOfRoots(f, isolation)
        val answer = selectInterval(f, isolation, accuracy)
        val root = answer.first
        SolveEquation.printResult(root, f(root), cntOfIterations)
        showGraph(f, isolation)
    }


    private fun selectInterval(f: (Double) -> Double, isolation: Pair<Double, Double>, accuracy: Double): Pair<Double, Double>{
        return if(abs(isolation.first - isolation.second) <= accuracy || abs(f(x)) <= accuracy) isolation
        else {
            cntOfIterations++;
            x = (isolation.first + isolation.second) / 2
            if(f(x) * f(isolation.first) <= 0) selectInterval(f, isolation.first to x, accuracy)
            else selectInterval(f, x to isolation.second, accuracy)
        }
    }

}