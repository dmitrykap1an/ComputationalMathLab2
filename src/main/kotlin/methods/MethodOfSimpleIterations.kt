package methods

import CLI
import Equation
import java.util.function.DoubleFunction
import kotlin.math.abs
import kotlin.math.max
import kotlin.system.exitProcess

object MethodOfSimpleIterations: Method {

   private var q = CLI.getQ()
    override fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double) {
        q = CLI.getQ()
        val function = equation.getFunction()
        //checkConvergence(function, isolation)
        val lambda = getLambda(function, isolation)
        val (a, b) = isolation
        val phi = DoubleFunction{x -> x + lambda * function.apply(x)}
        var x0 = a;
        var x = x0
        var cntOfIterations = 0
        do{
            cntOfIterations++
            x0 = x
            x = phi.apply(x0)
        } while (checkEnd(q, x, x0, accuracy))

        CLI.printResult(x, function.apply(x), cntOfIterations)
        showGraph(function, isolation)

    }


    private fun checkEnd(q: Double, x: Double, x0: Double, accuracy: Double): Boolean{
       if(q <= 0.5){
           return abs(x - x0) > accuracy
       }
        return abs(x - x0) > (1 - q) / q * accuracy
    }


    private fun getLambda(function: DoubleFunction<Double>, isolation: Pair<Double, Double>): Double{
        val d = derive(function)
        val (a, b) = isolation
        return -1/max(abs(d.apply(a)), abs(d.apply(b)))
    }

   override fun checkConvergence(function: DoubleFunction<Double>, isolation: Pair<Double, Double>) {
        var max = 0.0
        val d = derive(function)
        val (a, b) = isolation
        val step = (b - a)/10
        var min = a
        while(min <= b){
            max  = max(max, abs(d.apply(min)))
            min+=step
        }
        if(max > q) {
            println("Не выполняется достаточное условие сходимости")
            exitProcess(1)
        }
    }
}