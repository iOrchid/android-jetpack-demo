package org.zhiwei.kotlin.concepts

/**
 * kotlin语法基础之 接口
 */
class SyntaxInterface {

    //0. 接口的定义使用interface关键字,没有构造函数， 可以有函数声明，且函数可以有默认的实现
    interface Shape {

        val ss: String //接口的属性，是抽象的，不能初始化赋值,实现类会赋值
        val ddd: String //要么是提供一个getter访问
            get() = "ddd"
        var ee: String
            set(value) {
//                field=value+""//在接口的属性上，就不同用field设置setter了
            }
            get() = "eee"

        fun draw()//接口的函数

        fun foo() {
            //可以有默认实现的
        }
    }

    //1. 接口可以继承其他接口，也可以给基类接口的抽象函数一个默认实现，那么后续的子类实现该接口的话，可选择不必实现已经有默认实现的函数
    interface SuperInterface {
        fun foo()
    }

    interface CommonInterface : SuperInterface {
        fun boo()
        fun zoo()

        override fun foo() {
            println("默认实现了foo，")
        }
    }

    class SubClass : CommonInterface {
        override fun boo() {

        }

        override fun zoo() {

        }
        //对于foo函数，就可以选择实现或不实现

    }

    //2. 多接口实现的时候，可能会出现函数名冲突，需要实现类显式的指明，参见[SyntaxInheritance]中line 62行，类似。

    //3. SAM单一抽象接口，只有一个函数定义的接口，在kotlin中就可以简化为lambda方式
    private fun testSam() {
        //kotlin中函数内可以定义局部函数
        fun aSam(su: SuperInterface) {
            su.foo()
        }
        //非Sam形式
        aSam(object : SuperInterface {
            override fun foo() {

            }
        })
        val fo = object : SuperInterface {
            override fun foo() {

            }
        }
        fo.foo()
    }

    //可以定义简短的SAM接口,只能是SAM式的才可以如此
    fun interface SamInterface {
        fun abc(name: String)
    }

    private val sam = SamInterface { name ->
        println(name)
    }

    fun testSam2() {
        fun hhh(ine: SamInterface) {

        }
        sam.abc("Kotlin测试Sam")
        hhh(sam)
    }
}