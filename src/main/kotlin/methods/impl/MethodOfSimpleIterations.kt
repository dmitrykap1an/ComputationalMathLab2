package methods.impl

import SolveEquation
import methods.Method
import utils.Equation
import kotlin.math.abs
import kotlin.math.max
import kotlin.system.exitProcess

object MethodOfSimpleIterations: Method {

   private var q = SolveEquation.getQ()
    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        val function = equation.getFunction()
        checkNumberOfRoots(function, isolation)
        val lambda = getLambda(function, isolation)
        val a = isolation.first
        val phi = { x: Double -> x + lambda * function(x)}
        var x0 = a;
        var x = x0
        var cntOfIterations = 0
        do{
            cntOfIterations++
            x0 = x
            x = phi(x0)
        } while (checkEnd(q, x, x0, accuracy))
        if(x.isNaN() || x.isInfinite()){
            println("на промежутке [${isolation.first}, ${isolation.second}] метод расходится")
            exitProcess(130)
        }
        SolveEquation.printResult(x, function(x), cntOfIterations)
        showGraph(function, isolation)

    }


    private fun checkEnd(q: Double, x: Double, x0: Double, accuracy: Double): Boolean{
           return abs(x - x0) > accuracy
    }


    private fun getLambda(function: (Double) -> Double, isolation: Pair<Double, Double>): Double{
        val d = derive(function)
        val (a, b) = isolation
        return -1/max(abs(d(a)), abs(d(b)))
    }

   override fun checkConvergence(f: (Double) -> Double, isolation: Pair<Double, Double>) {
        var max = 0.0
        val d = derive(f)
        val (a, b) = isolation
        val step = (b - a)/10
        var min = a
        while(min <= b){
            max  = max(max, abs(d(min)))
            min+=step
        }
        if(max > q) {
            println("Не выполняется достаточное условие сходимости")
            exitProcess(1)
        }
    }
}