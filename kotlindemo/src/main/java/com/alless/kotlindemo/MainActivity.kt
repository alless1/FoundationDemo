package com.alless.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun main(args: Array<String>) {
        val test1 = "hello,world"   //变量
        var test2 = 555             //常量

        println(test1)
    }

    var a: Int = 11
    var b = 22      //可省略类型

    //代码块体
    fun max1(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    //表达式体
    fun max2(a: Int, b: Int): Int = if (a > b) a else b

    //表达式体（省略返回值）
    fun max3(a: Int, b: Int) = if (a > b) a else b




}
