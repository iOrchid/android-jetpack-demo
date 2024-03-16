package org.zhiwei.kotlin

import org.junit.Test
import org.zhiwei.kotlin.basic.conditionSyntax
import org.zhiwei.kotlin.basic.name
import org.zhiwei.kotlin.basic.testCollection
import org.zhiwei.kotlin.basic.testParseIntNull
import org.zhiwei.kotlin.basic.testWhen

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    /**
     * 单元测试，验证一下语法基础
     */
    @Test
    fun testSyntax() {
        val strTemplate =
            "这是一个字符串string，里面要引用变量$name 所以就叫字符串模版，\n 注意这里会换行，因为有转义符号 '$' 符号引变量也可用{}包裹。这个string声明就没有写类型，因为可推导出来"
        val stringLongText =
            """这样使用三个双引号的字符串，$name 可以在内部使用"双引号"，或者其他符号\n 不会被转义"""
        print(strTemplate)
        print(stringLongText)
        conditionSyntax(99)
        testWhen("你好啊")
        testWhen(999)
        testCollection()
        testParseIntNull()
    }


    @Test
    fun testTypes() {
        val aa1 = arrayOf(1, 2, 3)
        val aa2 = arrayOf(1, 2, 3)

        println("计算结果 ${aa1 == aa2}")
        println("计算结果 ${aa1.contentEquals(aa2)}")

    }
}