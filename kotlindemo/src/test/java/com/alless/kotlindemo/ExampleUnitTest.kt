package com.alless.kotlindemo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)
        println(mix(Color2.RED, Color2.RED))
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
}
