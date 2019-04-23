package com.alless.kotlindemo

import com.alless.test.Student
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)
        //println(mix(Color2.RED, Color2.RED))


/*        val list = listOf<Int>(3, 2, 5, 8, 6)
        println(joinToString(list,";","{","}"))
        println(joinToString(list,separator = ";",prefix = "{",postfix = "}"))
        println(joinToString(list))
        println(joinToString(list,postfix = "end"))*/

        println("Kotlin".lastChar())
        println("Kotlin".lastChar)

    }

    fun mix(c1: Color2, c2: Color2) =
            when (setOf<Color2>(c1, c2)) {
                setOf(Color2.YELLOW, Color2.RED) -> "orange"
                else -> throw Exception("dirty color")
            }

    interface Expr
    class Num(val value: Int) : Expr
    class Sum(val left: Expr, val right: Expr) : Expr

    fun eval(e: Expr): Int =
            if (e is Num) {
                e.value
            } else if (e is Sum) {
                eval(e.left) + eval(e.right)
            } else {
                throw  IllegalAccessException("Unknown exception")
            }

    fun eval2(e: Expr): Int =
            when (e) {
                is Num -> e.value
                is Sum -> eval2(e.left) + eval2(e.right)
                else -> throw  IllegalAccessException("Unknown exception")
            }

    val onToTen = 1..10;

    val binaryReps = TreeMap<Char, String>()
    fun test2() {
/*        for (i in 1..100) {

        }*/
        for (c in 'A'..'F') {
            Integer.toBinaryString(c.toInt())
        }
        val (key, value) = 1.to("one")
        val (number, name) = 1 to "one"
    }

    infix fun Any.to(other: Any) = Pair(this, other)


}

//抽象类默认是open的
abstract class Animated {
    //抽象方法默认是open的
    abstract fun animate()

    open fun stopAnimating() {

    }

    fun animateTwice() {

    }
}

