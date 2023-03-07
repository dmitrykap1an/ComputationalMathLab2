
import methods.MethodOfHalfDivision
import methods.MethodOfNewton
import methods.MethodOfSimpleIterations
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import kotlin.math.max
import kotlin.math.min

object CLI {
    private val mapOfEquations = mapOf(
        1 to Equation("x^2 + 2x + 1 = 0", fun(x: Double) = x * x + 2 * x + 1, listOf()),
        2 to Equation("x^3 - x + 4 = 0", fun(x: Double) = x * x * x - x + 4, listOf())
    )
    private val mapOfMethods = mapOf(
        1 to "Метод половинного деления",
        2 to "Метод Ньютона",
        3 to "Метод простых итераций"
    )

    private val mapOfSystemEquations = mapOf(
        1 to SystemOfEquations(
            "",
            fun(x: Double) = x * x + 2 * x + 1,
            fun(x: Double) = x * x + 2 * x + 1,
            listOf<Double>() to listOf()
        )
    )

    private lateinit var input: () -> String
    private val br = BufferedReader(FileReader("src/files/task3/task3.txt"))
    private val bw = BufferedWriter(FileWriter("src/files/task3/task3-result.txt"))
    private var visible = true
    private var q: Double? = null


    fun doMethod(){
        askInputOption()
        when(askKindOfEquation()){
            1 -> solveEquation()

            else -> solveSystemOfEquations()
        }
    }

    private fun solveEquation(){
        val indexOfMethod = askMethod()
        val equation = askEquation()
        val isolation = askRootIsolation()
        val accuracy = askAccuracy()

        when(indexOfMethod){
            1 -> MethodOfHalfDivision.solve(equation, isolation, accuracy)
            2 -> MethodOfNewton.solve(equation, isolation, accuracy)
            3 -> {
                q = askLipschitzCoefficient()
                MethodOfSimpleIterations.solve(equation, isolation, accuracy)
            }
        }

    }

    private fun solveSystemOfEquations(){
        val equation = askSystemOfEquations()
    }

    private fun askKindOfEquation(): Int{
        while (true){
            ask("1) Решить нелинейное уравнение\n")
            ask("2) Решить систему нелинейных уравнений\n")
            ask("Выберете подходящий для вас пункт: ")
            val index = input().toIntOrNull()
            if(index is Int && index in 1..2){
                return index
            }
        }

    }

    private fun askInputOption() {
        print("Прочитать данные из файла? Д/н ")
        val str = readln()
        input = when (str.lowercase()) {
            "д", "\n", "l" -> {
                visible = false
                { br.readLine() }
            }

            else -> {
                visible = true
                { readln() }
            }
        }
    }

   fun printResult(x: Double, value: Double, cntOfIterations: Int) {
        print("Записать результат работы программы в файл? Д/н ")
        val str = readln()
         when (str.lowercase()) {
             "д", "\n", "l" -> {
                bw.write("Корень уравнения: $x\nЗначение функции в корне: $value\nЧисло итераций: $cntOfIterations")
                bw.flush()
            }

            else -> {
                println("Корень уравнения: $x\nЗначение функции в корне: $value\nЧисло итераций: $cntOfIterations")
            }
        }
    }

    private fun askLipschitzCoefficient(): Double{
        while(true) {
            ask("Введите коэффициент Липшица: ")
            val q = input().toDoubleOrNull()
            if(q is Double && q > 0.0 && q < 1.0) return q;
            println("Коэффициент Липшица должен быть представлен числом и быть в промежутке (0, 1)")
        }

    }

    private fun askSystemOfEquations(): SystemOfEquations {
        mapOfSystemEquations.forEach {
            ask("${it.key}) ${it.value.getExp()}\n")
        }
        ask("Напишите номер системы уравнений, ответ к которой вы хотите увидеть: ")

        return mapOfSystemEquations[askToChoose(mapOfSystemEquations.size)]!!
    }



    private fun askEquation(): Equation {
        mapOfEquations.forEach {
            ask("${it.key}) ${it.value.getExp()}\n")
        }
        ask("Напишите номер уравнения, ответ к которому вы хотите увидеть: ")

        return mapOfEquations[askToChoose(mapOfEquations.size)]!!
    }

    private fun askMethod(): Int {
        mapOfMethods.forEach {
            ask("${it.key}) ${it.value}\n")
        }
        ask("Напишите номер метода, с помощью которого найти решение уравнения: ")
        return askToChoose(mapOfMethods.size)
    }

    private fun askToChoose(length: Int): Int {
        var number: Int?
        while (true) {
            number = input().toIntOrNull();
            if (number is Int && number in 1..5) return number;
            else {
                ask("Выберете число из промежутка от 1 до $length: ")
            }
        }
    }

    private fun askAccuracy(): Double{
        ask("Введите точность: ")
        return input().toDouble()
    }

    private fun askRootIsolation(): Pair<Double, Double>{
        while (true){
            try{
                ask("Введите через пробел изоляции корня: ")
                val (a, b) = input().split(" ").map { it.toDoubleOrNull() }
                if(a is Double && b is Double) return Pair(min(a, b), max(a, b))
            }
            catch (e: IndexOutOfBoundsException){
                ask("Введите через пробел два числа!\n")
            }

        }
    }

    private fun ask(text: String){
        if(visible) print(text)
    }

    fun getQ() = q!!
}

