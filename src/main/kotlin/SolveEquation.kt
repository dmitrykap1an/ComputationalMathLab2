
import CLI.ask
import CLI.askAccuracy
import CLI.askRootIsolationOrFirstApproximation
import CLI.askToChoose
import CLI.createFileAndWriteResult
import CLI.input
import methods.impl.MethodOfHalfDivision
import methods.impl.MethodOfNewton
import methods.impl.MethodOfSimpleIterations
import utils.Equation

object SolveEquation {

    private val mapOfEquations = mapOf(
        1 to Equation(
            "x^2 + 2x - 1 = 0",
            fun(x: Double) = x * x + 2 * x - 1
        ),
        2 to Equation(
            "x^3 - x + 4 = 0",
            fun(x: Double) = x * x * x - x + 4
        ),
        3 to Equation(
            "x^3 - 4.5x^2 - 9.21x - 0.383",
            fun(x: Double) = x * x * x - 4.5 * x * x - 9.21 * x - 0.383
        )
    )
    private val mapOfMethods = mapOf(
        1 to "Метод половинного деления",
        2 to "Метод Ньютона",
        3 to "Метод простых итераций"
    )
    private var q: Double? = null
    private var indexOfCurrentMethod: Int? = null

    fun solve(){
        indexOfCurrentMethod = askMethod()
        val equation = askEquation()
        val isolation = askRootIsolationOrFirstApproximation("Введите через пробел изоляции корня: ")
        val accuracy = askAccuracy()

        when (indexOfCurrentMethod) {
            1 -> MethodOfHalfDivision.solve(equation, isolation, accuracy)
            2 -> MethodOfNewton.solve(equation, isolation, accuracy)
            3 -> {
                q = askLipschitzCoefficient()
                MethodOfSimpleIterations.solve(equation, isolation, accuracy)
            }
        }
    }

    private fun askMethod(): Int {
        mapOfMethods.forEach {
            ask("${it.key}) ${it.value}\n")
        }
        ask("Напишите номер метода, с помощью которого найти решение уравнения: ")
        return askToChoose(mapOfMethods.size)
    }

    private fun askLipschitzCoefficient(): Double {
        while (true) {
            ask("Введите коэффициент Липшица: ")
            val q = input().toDoubleOrNull()
            if (q is Double && q > 0.0 && q < 1.0) return q;
            println("Коэффициент Липшица должен быть представлен числом и быть в промежутке (0, 1)")
        }

    }



    private fun askEquation(): Equation {
        mapOfEquations.forEach {
            ask("${it.key}) ${it.value.getExp()}\n")
        }
        ask("Напишите номер уравнения, ответ к которому вы хотите увидеть: ")

        return mapOfEquations[askToChoose(mapOfEquations.size)]!!
    }

    fun printResult(x: Double, value: Double, cntOfIterations: Int) {
        print("Записать результат работы программы в файл? Д/н ")
        val str = readln()
        when (str.lowercase()) {
            "д", "\n", "l" -> {
                val bw  = createFileAndWriteResult()
                bw.write("Корень уравнения: $x\nЗначение функции в корне: $value\nЧисло итераций: $cntOfIterations\nМетод решения: ${mapOfMethods[indexOfCurrentMethod]}")
                bw.flush()
            }

            else -> {
                println("Корень уравнения: $x\nЗначение функции в корне: $value\nЧисло итераций: $cntOfIterations\nМетод решения: ${mapOfMethods[indexOfCurrentMethod]}")
            }
        }
    }




    fun getQ() = q!!


}