
import java.io.*
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.min

object CLI {

    lateinit var input: () -> String
    private val br = BufferedReader(FileReader("src/files/task3/task3.txt"))
    private var visible = true


    fun doMethod() {
        askInputOption()
        when (askKindOfEquation()) {
            1 -> SolveEquation.solve()

            else -> SolveSystemOfEquations.solve()
        }
    }

    private fun askKindOfEquation(): Int {
        while (true) {
            ask("1) Решить нелинейное уравнение\n")
            ask("2) Решить систему нелинейных уравнений\n")
            ask("Выберете подходящий для вас пункт: ")
            val index = input().toIntOrNull()
            if (index is Int && index in 1..2) {
                return index
            }
        }

    }

    private fun askInputOption() {
        print("Прочитать данные из файла? Д/н ")
        val str = readln()
         when (str.lowercase()) {
            "д", "\n", "l" -> {
                visible = false
                input ={ br.readLine() }
            }

            else -> {
                visible = true
                input = { readln() }
            }
        }
    }


    fun askToChoose(length: Int): Int {
        var number: Int?
        while (true) {
            number = input().toIntOrNull();
            if (number is Int && number in 1..5) return number
            else {
                ask("Выберете число из промежутка от 1 до $length: ")
            }
        }
    }


   fun askRootIsolationOrFirstApproximation(text: String): Pair<Double, Double> {
        while (true) {
            try {
                ask(text)
                val (a, b) = input().split(" ").map { it.toDoubleOrNull() }
                if (a is Double && b is Double) return Pair(min(a, b), max(a, b))
            } catch (e: IndexOutOfBoundsException) {
                ask("Введите через пробел два числа!\n")
            }

        }
    }

    fun createFileAndWriteResult(): BufferedWriter {
        val date = LocalDateTime.now()
        val file = File(
            "/home/newton/IdeaProjects/Math/comp_math/lab2/src/files/results/" +
                    "${date.dayOfMonth}_${date.month}_${date.hour}:${date.minute}.${date.second}"
        )

        return BufferedWriter(FileWriter(file))
    }

   fun askAccuracy(): Double {
        ask("Введите точность: ")
        return input().toDouble()
    }

    fun ask(text: String) {
        if (visible) print(text)
    }

}