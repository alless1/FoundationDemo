package com.alless.kotlindemo

/**
 * Created by chengjie on 2019/4/22
 * Description:
 */
/*kotlin*/
const val MAX_INDEX = 36

fun <T> joinToString(collection: Collection<T>,
                     separator: String = ",",
                     prefix: String = "",
                     postfix: String = ""): String {
    val result = StringBuffer()
    result.append(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

fun String.lastChar(): Char = this.get(this.length - 1)

val String.lastChar: Char
    get() = get(length - 1)