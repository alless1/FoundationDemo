package com.alless.kotlindemo

/**
 * Created by chengjie on 2019/4/19
 * Description:
 */
class PersonBeanJava(var name: String?, var age: Int) {

}

class PersonBean {
    val name: String? = null    //只读
    var age: Int = 0            //可读可写
}

fun test() {
    when(Color2.RED){
        Color2.RED -> "red"
        Color2.GREEN -> "green"
        Color2.YELLOW -> "yellow"
    }
}

enum class Color {  //比java多一个class
    RED, GREEN, YELLOW
}

enum class Color2(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0), GREEN(0, 255, 0), YELLOW(255, 255, 0);  //kotlin唯一用到分号;的地方

    fun getValue() = (r * 256 + g) * 256 + b    //自定义一个方法
}


