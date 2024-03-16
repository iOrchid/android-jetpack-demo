package org.zhiwei.kotlin.concepts

/**
 * kotlin语法基础之 函数相关
 */
class SyntaxFunction {

    //region 0.扩展函数
    open class Super
    class Sub : Super()

    //扩展函数，定义访问作用域，扩展者的，以及函数具体实现
    private fun Sub.school() {
        println("扩展Sub一个函数叫school")
    }

    private fun Super.school() {
        println("扩展Super函数，叫school")
    }

    //扩展函数，可理解为静态工具类，并不会修改被扩展类；所以
    fun testExt() {
        Sub().school()//此时就可以使用扩展函数了。调用sub的扩展
        val str: Super = Sub()
        str.school()//调用super的扩展，虽然str是sub的对象，但是声明的是Super类，所以叫静态扩展。
    }
    //todo 如果扩展函数与内部已经有的函数完全一致，则调用自有的。扩展失效。

    //1. 扩展属性，类似于扩展函数，但是扩展属性是没有初始化的，可以手动setter，getter
    private var Super.sss
        get() = "Sp额sss"
        set(value) {
            //因为是扩展属性，对于Super类就没有这个字段，所以setter只能用来做别的事
            println("可以给其他值运算等 $value")
        }
    //也可以给类的伴生对象companion object扩展；
    // 可以在需要的地方扩展定义，其作用域根据需要选择修饰符；
    //endregion 也可以在open类内定义open的扩展函数，给子类也能实现override，不过如此做法，场景业务不多


    //region 1.函数定义，lambda，内联函数，操作符重载
    fun empty(): Unit {
        println("无参无返回值的普通函数")
    }

    fun params(a: Int, b: String, c: Char = 'A'): Boolean {
        println("这是一个有参数，且有默认参数$c 设置的函数定义，返回值布尔类型，")
        return a > b.length
    }

    open class SuperFun {
        open fun foo(a: Int = 3) {}
    }

    class SubFun : SuperFun() {
        //如果基类函数的参数有默认值，则实现类override时候，不能写默认值
        override fun foo(a: Int) {
            super.foo(a)
        }
    }

    private fun paramsFun(funParam: (num: Int, str: String) -> Boolean) {
        println("这里形参是一个匿名函数表达式，指明了参数的名字，也可以简写(Int,String)->Boolean")
    }

    //多个参数 vararg必须是放在函数定义的最后一个参数位置
    private fun manyParams(a: Int, vararg bb: String) {}

    infix fun String.sdcard(a: Int) {
        println("中缀函数的定义，必须是扩展函数；有且仅有一个参数；不能是vararg的，也不能有默认值")
    }


    //尾递归函数,替代一些循环，解决堆栈溢出的风险
    val eps = 1E-10 // "good enough", could be 10^-15
    tailrec fun findFixPoint(x: Double = 1.0) {
        if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))
    }

    private fun testFun() {
        //调用函数，
        empty()
        params(3, "ss")//因为c参数有默认值，可以不传
        params(b = "dd", a = 99, c = 'D')//可以根据形参名称，传入，而不用顾及顺序
        //调用表达式的函数，如果最后一个参数是表达式，可以括号内，也可以括号外，
        paramsFun({ num, str -> str.length > num })
        paramsFun { num, str -> str.length > num }
        manyParams(3, "ad", "dsd")
        //中缀函数使用
        "abd".sdcard(66)
        "abd" sdcard 66
        findFixPoint(3.0)
    }

    //lambda函数表达式 ,表达式用做函数的参数，比定义接口更方便；
    val lab = { a: Int -> a > 90 }

    //函数定义为变量表达，匿名函数
    val ss = fun(s: String): Int { return s.length }

    //多重表达式，返回类型还是表达式形式的
    val lablab = { b: String -> { c: Int -> c > 100 } }

    //lambda表达式中，单个参数可以不写参数名，默认是it，不用的话，使用_下划线处理
    // 系统提供了apply，also，run，with，let等扩展函数，作用区别就是返回值和语义不同


    private fun testLambda() {
        //调用lambda可以invoke，也可以直接()入参
        lab.invoke(3)
        ss("北包包")
        lablab.invoke("哈哈哈")
        //系统提供的函数，可以使用::调用，invoke
        List<String>::size.invoke(listOf("abd", "cdsd"))

    }


    //endregion

    //高阶函数lambda 每个函数都是一个对象，所以无形中也会增加性能消耗，必要时候减少函数创建，使用inline内联
    fun abd(): () -> Unit {
        return { println("kongkong") }
    }

    //inline用于函数内联，调用者其实就是贴进来一段代码，而不是函数；
    //noinline，只能修饰inline函数的参数，让这个参数不参与内联；
    //corssinline，因为break continue在inline函数中不可用，涉及到return返回的问题，该修饰符使得inline函数的参数不能使用内部的return
    inline fun ddd(noinline ab: () -> Unit, crossinline ee: () -> Double, c: String) {

    }

    private fun ddff() {
        ddd(abd(), { 2.0 }, "")
    }


    //还可以有内联属性,内联属性不能有setter的field
    inline var ssssss: String
        get() {
            return "TODO()"
        }
        set(value) {
            println("咋办，啥用")
        }

}