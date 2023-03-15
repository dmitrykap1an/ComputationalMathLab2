package methods.impl

import SolveSystemOfEquations.printResult
import methods.MethodForSystemOfEquations
import utils.Matrix
import utils.SystemOfEquations
import kotlin.math.abs

object MethodOfNewtonForSystemOfEquations : MethodForSystemOfEquations {
    override fun solve(equations: SystemOfEquations, approximation: Pair<Double, Double>, accuracy: Double) {
        var (x0, y0) = approximation
        val f = equations.getFunction1()
        val g = equations.getFunction2()
        val jacobiMatrix = getJacobiMatrix(f, g)
        var (dx, dy) = getNewApproximation(jacobiMatrix, listOf(equations.getFunction1(), equations.getFunction2()), x0, y0)
        var cntOfIterations = 1
        while(abs(dx) > accuracy || abs( dy) > accuracy){
            x0 += dx
            y0 += dy
            val newApproximation =  getNewApproximation(jacobiMatrix, listOf(equations.getFunction1(), equations.getFunction2()), x0, y0)
            cntOfIterations++
            dx = newApproximation.first
            dy = newApproximation.second
        }
        val x = x0 - dx
        val y = y0 - dy
        val result = checkAnswer(x, y, equations.getRoots(), accuracy)
        printResult(result,x to y, dx to dy, cntOfIterations)
    }


    private fun getNewApproximation(jacobiMatrix: Matrix, functions: List<(Double, Double) -> Double>, x0: Double, y0: Double):Pair<Double,Double> {
        val m = jacobiMatrix.getMatrix()
        var dx = -m[0][1](x0, y0) / m[0][0](x0, y0)
        val freeTerm = -functions[0](x0, y0) / m[0][0](x0, y0)
        val dy = (-functions[1](x0, y0)- freeTerm * m[1][0](x0, y0)) / ((m[1][0](x0, y0) * dx) + m[1][1](x0, y0))
        dx = freeTerm + dx * dy
        return dx to dy
    }

    private fun getJacobiMatrix(f: (Double, Double) -> Double, g: (Double, Double) -> Double): Matrix {
        val row1 = mutableListOf(deriveX(f), deriveY(f))
        val row2 = mutableListOf(deriveX(g), deriveY(g))
        return Matrix(mutableListOf(row1, row2))
    }

    private fun checkAnswer(x: Double, y:Double, answers: Pair<Double, Double>, accuracy: Double): String{
        return if(abs(x - answers.first) <= accuracy && abs(y - answers.second) <= accuracy){
             "Решение уравнения было найдено верно: разность между найдеными и фактическими корнями меньше погрешности"
        } else "Решение было найдено неверно: разность между найдеными и фактическими корнями больше погрешности"
    }
}
