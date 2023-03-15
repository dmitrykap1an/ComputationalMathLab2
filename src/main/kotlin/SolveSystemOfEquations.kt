
import CLI.createFileAndWriteResult
import methods.impl.MethodOfNewtonForSystemOfEquations
import utils.SystemOfEquations

object SolveSystemOfEquations {
    private val mapOfSystemEquations = mapOf(
        1 to SystemOfEquations(
            "x^2 + y^2 = 4\n   y = 3x^2",
            fun(x: Double, y: Double) = x * x + y * y - 4,
            fun(x: Double, y: Double) = y - 3 * x * x,
            0.783212564406379 to 1.840265763132049
        )
    )


    fun solve(){
        val equation = askSystemOfEquations()
        val approximation = CLI.askRootIsolationOrFirstApproximation("Введите начальное приближение: ")
        val accuracy = CLI.askAccuracy()
        MethodOfNewtonForSystemOfEquations.solve(equation, approximation, accuracy)
    }


    private fun askSystemOfEquations(): SystemOfEquations {
        mapOfSystemEquations.forEach {
            CLI.ask("${it.key}) ${it.value.getExp()}\n")
        }
        CLI.ask("Напишите номер системы уравнений, ответ к которой вы хотите увидеть: ")

        return mapOfSystemEquations[CLI.askToChoose(mapOfSystemEquations.size)]!!
    }

    fun printResult(result: String, vectorOfRoots: Pair<Double, Double>,errorVector: Pair<Double, Double>, cntOfIterations: Int) {
        print("Записать результат работы программы в файл? Д/н ")
        val str = readln()
        when (str.lowercase()) {
            "д", "\n", "l" -> {
                val bw  = createFileAndWriteResult()
                bw.write(result)
                bw.newLine()
                bw.write(getVector("вектор неизвестных",vectorOfRoots ))
                bw.newLine()
                bw.write("решение было найдено за $cntOfIterations шага(ов)")
                bw.newLine()
                bw.write(getVector("вектор погрешностей", errorVector))
                bw.flush()
            }

            else -> {
                println(result)
                println(getVector("вектор неизвестных",vectorOfRoots ))
                println("решение было найдено за $cntOfIterations шагов")
                println(getVector("вектор погрешностей", errorVector))
            }
        }
    }

    private fun getVector(text: String, pair: Pair<Double, Double>): String{
        val sb = StringBuilder()
        sb.append("$text: ").append("( ").append(pair.first).append(", ").append(pair.second).append(" )")
        return sb.toString()
    }
}