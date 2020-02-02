package org.zhiwei.jetpack.kt

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun testKt() {
        println("Byte范围: ${Byte.MIN_VALUE}~${Byte.MAX_VALUE}")
        println("Short范围: ${Short.MIN_VALUE}~${Short.MAX_VALUE}")
        println("Int范围: ${Int.MIN_VALUE}~${Int.MAX_VALUE}")
        println("Long范围: ${Long.MIN_VALUE}~${Long.MAX_VALUE}")
        println("Float范围: ${Float.MIN_VALUE}~${Float.MAX_VALUE}")
        println("Double范围: ${Double.MIN_VALUE}~${Double.MAX_VALUE}")
    }

    @Test
    fun testKtClazz() {
//        val tt = KtClazz4("名字")
//		val (name, age, sex, desc) = User("小明", 22, 1, "小明是个男的，大学生一枚")
        for (i in 10 until 1) {
            println(i)
        }
//        println(tt.toString())
    }

    @Test
    fun testWhile() {
        var a = 2
        while (a in 1 until 5) {
            println(a)
            a += 2
        }
    }

    var String.middle: Int
        get() = this.length / 2
        set(value) {
            println("v:$value")
        }


    @Test
    fun testEx() {
        open class C {
            fun doo() {
                println("DDD")
            }
        }//C类

        class D : C()//D继承C

        fun C.foo() {//对C扩展foo
            println("C.foo")
        }

        fun C.doo() {
            println("C.doo")
        }

        fun D.foo() {//对D扩展foo
            println("D.foo")
        }

        fun printFoo(c: D) {
            c.foo()
        }

        printFoo(D())
        C().doo()
        val s = "abcedfajkgja"
        s.middle = 999//

//        println("${s.middle}")
    }

    private open class GGG
    private data class CC(var a: Int) : GGG()

    @Test
    fun testCZ() {

        //数据类的copy并修改值
//        val c1 = CC(2)
//        val c2 = CC(a = 2)
////
////        println(c2)
//
//        println(c1 == c2)//都是"a=2"，所以true
//        println(c1 === c2)//并不是同一个对象实例，所以 false

        val d = Derived("agdd", "jjj")
        println(d)
    }

    private open class Base(val name: String) {

        init {
            println("Base的Init代码块")
        }

        open val size: Int =
            name.length.also { println("Base的成员属性，不是在构造函数内的: $it") }
    }

    private class Derived(
        name: String,
        val lastName: String
    ) : Base(name.capitalize().also { println("传给 Base 构造函数内的成员属性参数: $it") }) {

        init {
            println("Derived 的 init 代码块")
        }

        override val size: Int =
            (super.size + lastName.length).also { println("Derived 覆写 base的属性的执行 : $it") }
    }

    private val numbers: MutableSet<Int> = mutableSetOf(1, 2, 3, 4)//为了演示操作，使用mutableSet
    private fun testSet() {
        numbers.remove(2)
        numbers.add(5)
        for (number in numbers) {
            println(number)
        }
    }

    @Test
    fun testKt2() {
//        testSet()
        testFor()
    }

    private fun testFor() {
        val list = arrayListOf(2, "agjg", null, 999, 'c', true, "agdklagjld")

        list.forEach {
            it ?: return@forEach
            println("it $it")
        }
    }
}