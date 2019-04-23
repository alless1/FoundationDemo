package com.alless.test

import com.alless.kotlindemo.Clickable2

/**
 * Created by chengjie on 2019/4/22
 * Description:
 */

class Outer1{
    class Inner1{
    }
}

class Outer{
    //内部类用关键字inner
    inner class Inner{
        fun getOuterReference(): Outer = this@Outer
    }
}