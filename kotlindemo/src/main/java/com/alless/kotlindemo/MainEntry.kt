package com.alless.kotlindemo

/**
 * Created by chengjie on 2019/4/19
 * Description:
 */
object MainEntry {
    @JvmStatic
    fun main(args: Array<String>) {
        println(getColor(Color2.YELLOW))
    }
}

fun getColor(color: Color2){
    when(color){
        Color2.RED -> "red"
        Color2.GREEN -> "green"
        Color2.YELLOW -> "yellow"
    }
}
