package methods

import utils.Equation
import org.jetbrains.letsPlot.geom.geomArea
import org.jetbrains.letsPlot.letsPlot
import java.util.function.DoubleFunction
import kotlin.math.abs
import kotlin.system.exitProcess

interface Method {
   private val dx: Double
      get() = 0.000001
   fun solve(equation: Equation, isolation: Pair<Double, Double>, accuracy: Double, );

   fun derive(f: (Double) -> Double): (Double) -> Double{
      return { x: Double -> (f(x + dx) - f(x)) / dx}
   }


   fun checkConvergence(f: (Double) -> Double , isolation: Pair<Double, Double>){
      val d = derive(f)
      val (a, b) = isolation
      if(!(f(isolation.first) * f(isolation.second) <= 0 &&
                 d(isolation.first) * d(isolation.second) > 0)) {
         println("Не выполняется достаточное условие единственности корня на отрезке [$a,$b]")
         exitProcess(1)
      }
   }

   fun checkNumberOfRoots(f: (Double) -> Double, isolation: Pair<Double, Double>) {
      var (a, b) = isolation
      val step = (b - a) / 100
      var cnt = 0
      val d = derive(f)
      var sign = d(a) >= 0
      while (a <= b) {
         if ((d(a) >= 0) != sign) {
            cnt++
            sign = !sign
         }
         a+=step
      }

      when (cnt) {
         0 -> println("На отрезке существует 1 корень")
         1 -> {
            println("На отрезке не существует корней")
            exitProcess(1)
         }

         else -> {
            println("На отрезке больше 1го корня")
            exitProcess(1)
         }
   }
   }


   fun showGraph(function: DoubleFunction<Double>, isolation: Pair<Double, Double>){
      val cntOfRepeat = 500;
      val (a, b) = isolation
      val min = a - abs(b - a)
      val max = b + 3 * abs(b - a)
      val listX = generateX(min, max, cntOfRepeat)
      val listY = generateY(listX, function)
      val data = mapOf(listX to listY)
      val plot = letsPlot(data, ) +
              geomArea(fill = "white"){; x = listX; y = listY;}
      plot.show()
   }


   private fun generateX(min: Double, max: Double, cntOfRepeat: Int): List<Double>{
      val list = mutableListOf<Double>()
      val step = (max - min) / cntOfRepeat
      var value = min
      while(value <= max){
         list.add(value)
         value+=step;
      }

      return list;
   }

   private fun generateY(list: List<Double> , function: DoubleFunction<Double>): List<Double>{
      val listY = mutableListOf<Double>()
      list.forEach {
         listY.add(function.apply(it))
      }

      return listY;
   }
}