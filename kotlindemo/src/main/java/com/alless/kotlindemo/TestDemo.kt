package com.alless.kotlindemo

/**
 * Created by chengjie on 2019/4/19
 * Description:
 */

class TestDemo{

    constructor(){
       val message:String
        if(true){
            message = "success"
        }else{
            message = "fail"
        }
        val list = arrayListOf<String>("java")
        list.add("kotlin")

        var answer = 42

    }
    fun main(args: Array<String>){
        val name = "world"
        println("hello,$name")      //$引用变量

        val a = 22
        val b = 33
        println("max is ${if(a>b) a else b}")   //${代码块}

        val personBean = PersonBean()
        personBean.age = 23
        val name2 = personBean.name

    }
}