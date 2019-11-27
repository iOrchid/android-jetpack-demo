package org.zhiwei.jetpack.kt.advanced

import kotlin.reflect.KProperty

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月15日 17:30
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Kotlin高级语法函数，如 委托、协程、高阶函数，反射、
 * 1、类委托，by 关键字 a、声明接口或抽象类；b、实现接口/抽象类，得到实现类real；c、委托类，实现接口/抽象类，但是并不实际实现函数，而是持有一个接口实现类对象的引用，委托它来操作函数。
 * 2、属性委托 by 关键字 a、定义委托属性管理类，并做好getValue setValue函数的声明，需要operator关键词，以及方法签名的类型是需要委托的类型。2、在使用类中，定义委托属性。
 * 标准库中提供lazy，只用于val属性的委托，接收lambda表达式。
 *
 * 3、高阶函数 Kotlin中常见的高阶函数，有also、let、apply、repeat、lazy、takeUnless、takeIf、with、run、runCatching
 *
 * 4、Kotlin的协程 coroutine 简言之就是 替代回调 简化异步
 */
object AdvancedKt {

    //<editor-folder desc="1、类委托、属性委托">

    //todo 1、定义接口或者抽象类
    private interface IDelegate {
        fun showInfo()
    }

    //2、接口的实现类
    private class RealDelegateImpl(val info: String) : IDelegate {

        override fun showInfo() {
            println(info)
        }

    }

    //3、委托类,也是实现接口，但是不具体实现函数，交由持有的对象类代理
    private class Derived(delegate: IDelegate) : IDelegate by delegate

    //演示类代理效果
    fun testDelegate() {
        val real = RealDelegateImpl("具体的IDelegate接口的实现类，RealDelegateImpl")
        val der = Derived(real)
        //这里调用到showInfo，实际上就是委托给了real的实现。
        der.showInfo()
    }

    // todo 属性委托，指在一个类中的属性，并不是直接在类中定义，而是委托给某个类，实现统一管理。by 之后就是委托表达式。属性有val/var，val的只需要有getValue委托，var的需要有setValue，getValue。
    // todo thisRef表示委托类的对象引用,property代指被委托的属性

    //1、先定义，可以委托的属性的管理类，这里委托了一个string类型的属性,并做了相应的setValue和getValue

    private class DelegateStr {

        private var _value: String? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef,通过属性委托，${property.name} 得到了自定义的委托值 $_value"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            this._value = value
            println("$thisRef, 属性 ${property.name} 在这里赋值 $value")
        }

    }

    private class DelegateInt {

        operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return 999
        }
    }

    //2、使用属性委托，
    private class ProClass {
        //委托string
        var strOne: String by DelegateStr()
        //委托int，这里属性是val的，必须有getValue，setValue可以没有，因为也用不着。
        val n99: Int by DelegateInt()
        //使用系统提供的标准库，有一些内置的委托类定义，lazy就是其中之一，只用于val类型属性。
        val ss: String by lazy { "" }
    }


    //演示
    fun testProperty() {
        val p = ProClass()
        p.strOne = "哈哈哈哈，p丫丫"
        println(p.strOne)
        println(p.n99)
    }


    //</editor-folder>

    //<editor-folder desc="2、高阶函数">

    /**
     * Kotlin中常见的高阶函数，有also、let、apply、repeat、lazy、takeUnless、takeIf、with、run、runCatching
     * 1、also 函数代码块内 it 代指调用者，可执行针对 调用者的 多种函数操作，代码块外，依旧是该引用对象。
     * 2、let 函数块内也是 it 代指调用者，可执行多种代码块，但是返回最后一个语句的结果，作为let块的返回值
     * 3、apply 函数块内是 this 代指调用者 内部可执行多个调用者的函数，返回的依旧是自身。
     * 4、repeat 函数内也就是个for循环，重复执行某种操作
     * 5、lazy 函数实现懒加载，内部持有_value 私有和公有value，只有在初次调用初始化时候，才赋值。适用于低频，大变量的实例化
     * 6、takeIf 函数块内，满足的话，返回该调用者，否则 null
     * 7、takeUnless 函数块内 如果不满足，就返回调用者。满足，就null
     * 8、with 函数块内操作 this 引用接收者，然后最后一个结果返回给代码块。有点类似let,run
     * 9、run 函数内操作this 接收者，返回后一个代码结果。会异常。类似的runCatching，会兜住异常。
     *
     * 10、反射，Kotlin属于Jvm语言，支持Java的反射，
     */
    private val ssr = "ssr"


    // 函数可以 直接 引用另一个函数定义
    private fun aFun() = ssr.asSequence()

    fun testAdFun() {
        //also
        val also = ssr.also {
            "also ssr = $ssr"
            33
        }
        println(also)

        //let
        val let = ssr.let {
            "let ssr + $ssr rrs"
            99
        }
        println(let)
        //apply
        val apply = ssr.apply {
            length
            indexOf('s')
            99
        }
        println(apply)
        //repeat函数，重复执行的动作，有点类似循环遍历
        repeat(3) { i -> println("第$i 次 repeat") }

        //takeIf
        val takeIf = ssr.takeIf {
            it.length > 2
            2 > 0
        }
        //takeUnless
        ssr.takeUnless {
            it.length > 2
        }

        //with
        val with = with(ssr) {
            length > 2
            'd'
            "jjjjj"
            999
        }
        //run
        val run = ssr.run {
            length > 2
            'd'
            "jjjjj"
            999
        }
        //runCatching
        ssr.runCatching {
            length > 2
            'd'
            null
            999
        }

        //todo 调用函数引用,实质调用的就是ssr.asSequence
        aFun()
        ::aFun//有点类似java的静态引用，

    }

    //演示基础高阶函数lambda用法
    private fun test(a: Int, b: Int): Int {
        return a + b
    }

    //函数作为参数，随意定了个规则，没有实际意义的，仅仅为了演示
    private fun testL(a: Int, b: (a1: Int, b1: Int) -> Int): Int {

        return b.invoke(a, a + 2)
    }


    private fun sumAB(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    fun testAdFun2() {
        //调用方式1
        test(2, sumAB(22, 12))
        //
        testL(29) { a, b -> a + b }
    }


    //</editor-folder>


    //<editor-folder desc="3、反射">
    //1、Java中的反射

    private val abStr = "this is abc String"
    fun testJavaReflect() {
        //1、获取class对象的三种方式
        val forName = Class.forName("String")
        val javaClass = String.javaClass
        val objJavaClass = abStr.javaClass

        //2、获取构造函数 5种方式
        val constructors = forName.constructors//获取public的所有构造函数，
        val declaredConstructors =
            forName.declaredConstructors//指定参数列表类型的，构造函数，可以public，protected，private
        val enclosingConstructor =
            forName.enclosingConstructor//如果类生命在其它类的构造函数中，返回该类所在的构造函数，如存在则返回，不存在则null
        val constructor = forName.getConstructor(Char.javaClass)//获得public的 指定参数类型的构造函数
        val declaredConstructor = forName.getDeclaredConstructor()//获取类自身生命的构造函数

        //3、获取类的成员变量,4种方式
        javaClass.declaredFields
        javaClass.fields
        javaClass.getDeclaredField("get")
        javaClass.getField("length")

        //4、成员函数的获取，5种方式
        objJavaClass.methods
        objJavaClass.getMethod("get")
        objJavaClass.getDeclaredMethod("subString")
        objJavaClass.enclosingMethod
        objJavaClass.declaredMethods

    }

    private val ssKt = "ss kt "
    fun testKtRef() {
        //1、获取class对象 2种方式
        val javaClass = ssKt.javaClass
        val java = String::class.java
        //kotlin的
        {
            val kClass = String::class
            val kotlin = ssKt.javaClass.kotlin
            kotlin
        }
        //2、获取构造函数
        javaClass.constructors
        //3、其他函数，有获取成员变量、成员属性、私有变量，是否枚举，枚举常量、是否抽象类等等。
        javaClass.modifiers
        javaClass.isMemberClass
        javaClass.signers
    }

    //</editor-folder>

    //<editor-folder desc="4、协程">

    /*
     * 1、协程，又称纤程，在go、python、kotlin中有的概念，理解为一种轻量的线程。用以替换callback回调的谜之缩进，使用表达式，优化代码和异步处理。
     * 2、挂起函数 suspend修饰，存在suspend和resume两种操作
     * 3、指定协程的运行所在线程，需要Dispatchers来分发调度，分为Dispatchers.Main/IO/Default，分别用于主线程、IO密集操作、CPU密集操作等类型，通过withContext指定。
     * 4、每个线程有一个调用栈，suspend函数会被复制到其他地方（开分支），完成操作后，resume复制回调用栈。
     * 5、协程需要一个CoroutineScope，也就是生命周期管理，接收CoroutineContext参数。用以控制协程任务
     * //launch 启动协程，返回job，可用于取消协程，异常则抛；
     * //async 启动带有返回结果的协程，Deferred.await()获取结果。内部异常不抛，只有在await时候才会感知到异常。
     * //withContext 启动协程，入参CoroutineContext来改变协程的运行上下环境。
     * 6、结构化并发，即A协程中启动B协程，且B协程任务，在A完成之前就结束。如此，构成 结构化并发。
     * //协程追踪，如果丢失操作管控，就造成协程泄露，被泄露的协程，resume时候会消耗内存、cpu、磁盘、网络等资源。所以通过CoroutineScope控制追踪，有cancel函数。
     * //系统提供viewModelScope和UIScope。coroutineScope会在任意一个协程发生异常后取消所有的子协程的运行，而supervisorScope并不会取消其他的子协程。
     * 7、GlobalScope创建协程，launch、async、withContext，和额外的runBlocking，
     * 8、runBlocking和launch类似，只是它的delay会阻塞线程。async阻塞的是协程，而不是线程。
     */
    fun testCor() {
        //演示模拟
//        CoroutineKt.testCon()
//        CoroutineKt2.testCor2()

//        CoroutineKt3.testDisCtx()

//        CoroutineKt4.testKt4()

        Pip.testPip()

    }
    //</editor-folder>

}