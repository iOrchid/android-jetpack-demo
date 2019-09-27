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
}