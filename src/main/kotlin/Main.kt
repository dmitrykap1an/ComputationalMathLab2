
import kotlin.math.abs

/**
 * Лабораторная работа 2 по Вычислительной математике
 *
 * Программная часть
 * @author: Каплан Дмитрий Денисович
 * <p>Вариант 12
 * <p>Программа решает уравнения методами:
 * <p>1)Половинного деления
 * <p>3)Ньютона
 * <p>5)Метод простой итерации
 * <p>И систему нелинейный уравнений методом Ньютона
 */

fun main() {
    CLI.doMethod()


    //computationalImplementation()
}


fun computationalImplementation(){
    fun newX(a: Double, b: Double) = (a + b) / 2
    val a = -0.01265
    val b = 0.0252
    val x = newX(a, b)
    println("new x is $x")
    fun temp(x: Double) = x*x*x - 4.5*x*x - 9.21 * x - 0.383
    println("f(a) is ${temp(a)}")
    println("f(b) is ${temp(b)}")
    println("f(x) is ${temp(x)}")
    println("|a - b| is ${abs(a - b)}")
}