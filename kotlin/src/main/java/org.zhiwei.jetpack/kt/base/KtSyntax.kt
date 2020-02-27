package org.zhiwei.jetpack.kt.base

import android.view.View
import android.widget.TextView
import java.util.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月20日 10:35
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 基础语法的补充演示
 * 1、Kotlin的导包和java基本类似，格式就是package package.name ,如果包名中存在kotlin的关键字，则需要``反引号，比如`in`.zhiwei.jetpack，in就是kotlin的关键字
 * 2、类型智能转换，不同于java，kotlin中的数据类型的转换，都需要显示的操作，可以用is 判断类型，用 as 转换。或者基础类型的可以有toInt,toDouble等
 * 3、Kotlin中最高类型是Any，类似于Java中的Object，所有对象类都是继承自Any
 * 4、Kotlin的代码注释和java类似，//单行，/**/多行，/** 文档注释 */，但是比Java更强大的是，多行/文档注释中，可以嵌套单行或者多行注释，而Java的不行。
 * 5、可使用字符串模板，而不用太多的+来拼接字符串
 * 6、可null类型 Any?之类的，使用时候，要么?.，要么!!强制
 * 7、lambda函数表达式的定义，1、在kotlin中函数内可以定义函数，2、函数可以简写为lambda，3、函数可以作为另一个函数的参数
 * 8、条件/循环语句if、when、while、for，kotlin中没有java版三目运算符?:那种，而是用if..else..代替。（kotlin中有?:，不是三木运算符，而是判空以及兜底操作符）
 * 9、@label标签注释，便于控制循环和return函数的指向
 * 10、数组的声明和初始化，常用两种方式，arrayof，或者Array<>()，
 * 11、对于Int和Long，有位运算操作。kotlin中存在一元、二元操作符，也有中位操作符，如 to
 */
class KtSyntax {

    //<editor-folder desc="1、类型转换">

    //基本数据类型，简单演示几个，其他类似
    private var aNum = 20//默认Int类型
    private var bNum = 92.3//默认Double
    private var cNum = 30f//Float值，就需要f/F的标记
    private var dStr = "this is a string !"
    private val ePi = "3.14"

    private lateinit var ssValue: Any//在使用前要记得初始化，也可以做初始化检测 this::ssValue.isInitialized 来判断

    private fun testConvert() {
        //1、使用数据类型的扩展函数，toDouble，toFloat等转换
        if (aNum is Int)
            aNum.toDouble()
        //2、使用as，不能转换，只能将未知类型，作为 某个类型操作，而且，必须是某个类型，否则就NPL空指针异常了。
        bNum as Double //as Double是可以的，因为默认就是Double，如果as Float，就会提示
        //如果as 不确定转换是否成功，可以用as?，这样转换后的结果，是可null的类型，在kotlin中，可null的类型，就需要你在使用的时候判空了，
        ePi.toDouble()//string转toDouble,如果string不是数字内容，机会NPL，可以使用toDoubleOrNull，会得到一个可null的类型，就能及时判空
        //3、智能推导转换
        if (ssValue is String) {
            //if的条件判断后，在此代码块内，就认为是string了 使用as 转化为string标记
            (ssValue as String).substring(8)
        }
        //演示 !is
        if (ssValue !is String) {
            ssValue.hashCode()
        }
    }

    //</editor-folder>

    //<editor-folder desc="2、数组、字符串模板、null检查、lambda、区间">

    //1、字符串模板，类似shell或python语言中，也就是占位符的赶脚，不需要的可以转义
    val stemp = "this is a string template $ePi 使用\$符号就能引用到其他变量或者函数"
    //2、null检查，在topLevel中就说到，对于任何kotlin的类型，都有一个可null的对应，比如Int，有Int?，String有String?,Any有Any?
    //todo 不同于java，在kotlin中，如果声明和定义的是可null的，那么在使用的时候，就需要判空，或者强制使用，而声明定义不带?的那种，就不能接收null，否则崩溃
    private var nStr: String? = null

    fun testNull() {
        //这里使用nStr时候，由于其是String?的可null类型，就需要判空，或者强制使用
        nStr?.substring(9)
        //这样可以链式操作，只要前方有一个null，就不会继续下去，直接返回null结果
        nStr?.replace('a', 'b')?.substring(9)
        //也可以强制使用，决定非空 使用!!符号
        nStr!!.toLowerCase(Locale.getDefault())
    }

    //3、lambda，在Kotlin中更多的函数式编程代码，高阶函数，lambda就更是常见
    val lNum: (Int, Int) -> Int = { x, y -> x + y }

    fun testLd() {
        //调用上面的lNum，其实也就是调用一个加和的函数，上面就是一个lambda的定义
        println(lNum(2, 3))
        //函数内的定义函数
        fun inFun(vararg ss: String) {
            println(ss.toString())
        }
        //调用上面的函数内的函数，可变参数，
        println(inFun("d1", "d2", "d3"))
        //4、区间，在kotlin中可函数区间的概念
        val rNum = 0..3//表示区间，[0,3]闭区间，都可以取到边界值的
        println(rNum.toString())
    }

    //4、数组，在Kotlin中数组默认实final的，声明时候需要注意，是可变，还是不可变的;
    //1),不同于Java，在Kotlin中 数组/列表 不限定必须是某一个类型，元素可以任何类型
    private var arrayA = arrayOf(1, 'a', false, "String", kotlin.math.PI, 0.9f)
    private var listA = listOf(1, 0.3, 'c', "SSS", kotlin.math.PI)
    //2)、初始化的方式有两种：
    private var arr1 = Array<Int>(5) { i -> 3 * i }
    private var arr2 = arrayOf(1, 2, 3, 4, 5)//或者emptyArray(),arrayOfNulls（）来初始化
    //可变的列表与不可变的，它们的操作不一样的，
    var listMut = mutableListOf<Int>()
    var listFinal = listOf<Int>()


    //</editor-folder>

    //<editor-folder desc="3、条件语句if、循环for、while、do..while，以及特殊的when">

    //1、if条件语句，以及if..else..，没有?..:..，有?:也是判空用的
    private fun testIf() {
        var a = 3
        if (a in 1..10) {
            println("$a 在 1..10的区间内")
        }
        //替换三目运算
        var b = if (a in 1..5) "哈哈" else "嘿嘿"
        //这个是判空，若空就return了，return后也可以跟一个语句，由于这个testIf是返回为Unit的，所以return的语句，也只能是Unit的。
        nStr ?: return
        nStr ?: return print("return的语句，是返回空的语句就行")
        //if...else if ...else 就是多重条件
        if (a < 0) {
            //
        } else if (a == 0) {
            //
        } else {
            //
        }
    }

    //区间，或者 until，都是从小到大，如果第一个数大于后面的数，是输不出来的
    private fun testFor() {
        //取值[1,10]
        for (i in 1..10) {
            println(i)
        }
        //until 取值[1,10),左闭右开
        for (i in 1 until 10) {
            println(i)
        }
        // 10..1 ,或者10 until 1都是没有输出的
        for (i in 10 until 1) {
            println(i)
        }
        //其实如上的for条件，都是默认每次循环+1的，可以指定+的步骤
        for (i in 1..10 step 2) {
            println(i)
        }
        //循环递减，这里指定step 2 可以不写，默认是1
        for (i in 10 downTo 1 step 2) {
            println(i)
        }

    }

    //while 循环,简单模拟语法，所以代码语句条件不一定就成立合理
    private fun testWhile() {
        var a = 3
        do {
            println("hhh")
            a++
        } while (a < 9)

        while (a < 7) {
            println("a < 7")
            a++
        }
        //不论是while还是for循环，都要避免死循环。而且括号内抽象来说，接收的就是一个条件语句，任何语句都行
        while (a in 0..3) {
            println(a)
            a++
        }
        //虽然这里面也能写step，但是无效
        while (a in 1 until 5) {
            println(a)
            //a=a+2的简写
            a += 2
        }

    }

    //when 类似于java中的switch case，但是相比较更为强大
    private fun testWhen() {
        //1、代替if
        var a = 3
        //因为boolean只有两个取值，所以下面分支就true和false，可以不写else,或者省略ture或false的一个也行。
        when (a > 9) {
            //注意这里的写法，属于函数式简写，若只有一条语句，{}可以省略
            true -> {
                println("true")
            }
            false -> println("false ")
            else -> println("nothing")
        }

        //2、类似switch case时候，但是更灵活，更强大，因为它的case语句可以"任意表达"
        var b: Any = "xxx"
        when (b) {
            9 -> println("9")
            is Number -> println("is Number")
            !is Int -> println("not is int")
            "ss" -> println("ss")
            'c' -> println("is a char")
            true -> println("true")
            a != 3 -> println("可以是和b没有任何关系的case预计")
            else -> println("兜底的，else语句")
        }
        //可以没有括号条件句
        when {
            a > 3 -> println("a>3")
            else -> println("else")
        }

    }

    //</editor-folder>

    //<editor-folder desc="4、break、continue、return以及@label">
    //类似于java语言，break中断，continue，继续，return返回函数，@标签标记
    private fun testCtrl() {
        //输出结果是1，2，
        for (i in 1..10) {
            if (i == 3) break//根据条件，中断循环，其实也就是提前结束
            println("i=$i")
        }
        //输出结果是1，2，3，4，6，7，8，9
        for (i in 1..9) {
            if (i == 5) continue//满足条件时，结束本次循环，继续下一次，在此之后的语句，这次循环就不会执行了，
            println(i)
        }
        //输出结果是
        for (i in 1..7) {
            if (i == 4) return//满足条件，就return出整个函数testCtrl
            println(i)
        }
        println("这个输出语句，不会执行，因为上面的for，其中有个return条件")
    }

    //label标签
    private fun testLabel() {

        for1@ for (i in 1..10) {
            for2@ for (j in 0..7) {
                if (i == 5 && j == 5) break@for1//根据条件，退出循环，指定的某个循环，这里退出最外层的for
            }
        }

        kotlin.run {
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@run//直接return最外层的函数，这里有lambda，也可以return@forEach
            }
        }

        //使用@标签，可以在具体业务中，处理需要return或者break的层级，避免它使用默认的层级，而不符合业务要求。

        return@testLabel//默认即使return最近一层的函数
    }

    //</editor-folder>

    //<editor-folder desc="5、位运算、嵌套类/内部类/匿名类，注解">
    //1、位运算，针对于int和long类型的数据
    fun testBit() {
        var i3 = 3
        var l8 = 8
        //类似于Java的位运算，只不过kotlin中用函数表示。
        i3.shl(2)//左移2位，Java中 <<
        l8.shr(3)//右移3位，Java中 >>
        i3.ushr(4)//无符号右移4位，Java中 >>>
        i3.and(2)//与 运算 Java中&
        l8.or(3)//或运算 Java中 |
        l8.xor(3)//异或运算 Java中 ^
        i3.inv()//取反
    }

    //如此写法，在Java中可能是内部类，但是在Kotlin中这只是嵌套类，不能引用到外部类的参数
    private class KA {
        fun testK() {
            //arr1//引用不到外面的arr1，arr2等
        }
    }

    //带有inner的类，才是内部类，能够引用外部类的函数和变量
    private inner class KInterA {
        fun testIn() {
            arr1//可以引用到外部类的成员与函数
        }
    }

    fun testClazz() {
        //匿名内部类，也就是接口对象的形式
        var v: View = TextView(null)
        v.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                println("这就是匿名内部类的写法，只不过，有时候，可以被lambda简写")
            }
        })
    }


    //</editor-folder>


}