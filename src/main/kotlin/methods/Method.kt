package methods

import Equation

interface Method {

   fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double);
}