package org.zhiwei.kotlin

import org.junit.Test
import org.zhiwei.kotlin.basic.conditionSyntax
import org.zhiwei.kotlin.basic.name
import org.zhiwei.kotlin.basic.testCollection
import org.zhiwei.kotlin.basic.testParseIntNull
import org.zhiwei.kotlin.basic.testWhen
import org.zhiwei.kotlin.concepts.SyntaxClassObject
import org.zhiwei.kotlin.concepts.SyntaxFunction

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

    @Test
    fun testLoop() {
//        listOf(1, 3, 3, 44, 35, 5, 466).forEach {
//            if (it == 44) return@forEach
//            println("输出 $it")
//        }
//
//        listOf(1,2,3,45,64,).forEach(fun(value:Int){
//            if (value==3)return //如此，则return到函数testReturnJump()的调用处了
//            println("--- 输出 $value")
//        })

        //如下，则 println("-----输出测试完成-----")无法执行，因为遍历到44时候，就return出整个testLoop函数了。

        listOf(1, 3, 3, 44, 35, 5, 466).forEach {
            if (it == 44) return
            println("》》》输出 $it")
        }
        println("-----输出测试完成-----")
    }

    @Test
    fun testClass() {
        //演示类的初始化对象时候，构造函数，属性，init代码块执行顺序
//        SyntaxClassObject.LabelClassFull("一个参数 张三")
//        SyntaxClassObject.LabelClassFull("两个参数 李四", 88)

        SyntaxClassObject.Pet("小小灰狼")
        SyntaxClassObject.Pet("大白兔", 3)
    }

    //sampleStart
    open class Base(val name: String) {

        init {
            println("基类base的init块")
        }

        open val size: Int =
            name.length.also { println("基类的size属性 $it") }
    }

    class Derived(
        name: String,
        val lastName: String,
    ) : Base(name.replaceFirstChar { it.uppercase() }
        .also { println("子类入参传入给Base基类: $it") }) {

        init {
            println("子类的init块")
        }

        override val size: Int =
            (super.size + lastName.length).also { println("子类的size属性: $it") }
    }
//sampleEnd

    @Test
    fun testInheritance() {
        println("初始化子类是小写字母的hello world")
        Derived("hello", "world")
    }

    private fun SyntaxFunction.Sub.school() {
        println("扩展Sub一个函数叫school")
    }

    private fun SyntaxFunction.Super.school() {
        println("扩展Super函数，叫school")
    }

    @Test
    fun testExt() {
        SyntaxFunction.Sub().school()//此时就可以使用扩展函数了。
        val str: SyntaxFunction.Super = SyntaxFunction.Sub()
        str.school()
    }

    private data class User(val name: String) {
        var age: Int = 10
    }

    @Test
    fun testDataClass() {
        val user = User("张三")
        val user2 = User("张三")
        user.age = 20
        user2.age = 30
        println("用户User与User2的内容toString $user ，， $user2")
        println("用户User与User2的年龄 ${user.age} ，， ${user2.age}")
        println("用户User与User2是否相等 ${user == user2}")
    }

    @Test
    fun testVs() {
        //将句子分割成单词的集合，然后过滤长度>3的单词，取出前边4个，并输出他们的长度
        //迭代器形式
        val words = "The quick brown fox jumps over the lazy dog".split(" ")

        val lengthsList = words.filter {
            println("过滤: $it")
            it.length > 3
        }.map {
            println("长: ${it.length}")
            it.length
        }.take(4)

        println("iterator的 -----   前四个长度大于3的单词的长度是 :$lengthsList")

        //序列形式
        val wordsSequence = words.asSequence()

        val lengthsSequence = wordsSequence.filter {
            println("过滤: $it")
            it.length > 3
        }.map {
            println("长: ${it.length}")
            it.length
        }.take(4)

        println("Sequence的 ----- 前四个长度大于3的单词的长度是 :${lengthsSequence.toList()}")

    }
}