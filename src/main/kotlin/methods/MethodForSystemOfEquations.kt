package methods

import utils.SystemOfEquations

interface MethodForSystemOfEquations {

    private val dx: Double
        get() = 0.0001

    private val dy: Double
        get() = 0.0001

    fun solve(equations: SystemOfEquations, approximation: Pair<Double, Double>, accuracy: Double);

    fun deriveX(f: (Double, Double) -> Double):  (Double, Double) -> Double{
        return {x: Double, y:Double -> (f(x + dx, y) - f(x, y)) / dx}
    }

    fun deriveY(f: (Double, Double) -> Double):  (Double, Double) -> Double{
        return {x: Double, y:Double -> (f(x, y + dy) - f(x, y)) / dy}
    }




}