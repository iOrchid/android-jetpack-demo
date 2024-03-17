package org.zhiwei.kotlin.concepts

import kotlin.properties.Delegates

/**
 * Kotlin语法基础之 继承
 */
open class SyntaxInheritance {

    //region 0. Kotlin中所有类都默认继承自Any（可null类型则对应Any?),都有equals，hashcode，toString函数；
    //1. 默认类是final的，若需要可被继承，则显示标记为open,继承类，使用:冒号

    open class SuperClass {
        constructor(name: String)
        constructor(name: String, age: Int) : this(name)

        //声明函数可被重写
        open fun foo() {}

    }

    class SubClass : SuperClass("") {
        //继承类，则必须指定基类的某个构造函数
        //如果子类有多个构造函数，也可对应去调用基类的多个构造函数
        override fun foo() {
            super.foo()
        }
    }

    open class SubClass2 : SuperClass("") {
        //继承类，则必须指定基类的某个构造函数
        //如果子类有多个构造函数，也可对应去调用基类的多个构造函数
        //如果子类还是可被继承的，其也可以根据需要，将基类的open函数设置final，后面的继承就不能override了
        final override fun foo() {
            super.foo()
        }

        open var name: String = ""
    }

    class SubSub : SubClass2() {
        //无法override foo函数
        override var name: String = "可以复写属性字段"
    }

    //3. todo 注意，子类初始化对象的流程是：1，先初始化基类（依次主构造函数，属性或init）而后是子类自身的属性或init块
    //所以设计可被继承类的时候，要注意内部open字段和函数的使用，其数值会因子类而不同，并不是自己初始化的那样。
    // 3.1 子类内部调用基类的函数/属性，可使用super.xxx；内部类如果使用外部类的基类，可以super@OuterClassName

    //4. 如果继承与实现接口同时有一个相同的函数名，子类就必须指明如何具体实现
    interface Foo {
        //接口定义，默认也是open的，
        fun foo() {}//接口的函数，默认即使open的,可以有默认实现
    }

    open class Boo {
        open fun foo() {}
    }

    //只有接口Foo的foo()函数有默认实现的时候，下面，才必须override，而且根据需要可以指定调用基类的foo
    class Sub3 : Boo(), Foo {
        override fun foo() {
//            super.foo()//有冲突了，需要指明
            super<Boo>.foo()//这样就可以了
            super<Foo>.foo()
        }

    }
    //endregion

    //region 1. 委托,可视为替代继承的较好方式
    private interface IShape {
        val name: String
        fun draw()
        fun ppp()
    }

    private class Rectangle : IShape {
        override val name: String
            get() = "矩形的name啊"

        override fun draw() {
            println("万能的draw啊 ，$name")
        }

        override fun ppp() {
            println("咋地了，Rect的PPP啊")
        }

    }

    //此时如果新建的class类，不想继承来实现draw，就可以委托;
    // todo 委托条件是，1，继承接口，又不想实现；2，接收一个已经实现了的其他对象，并委托；
    private class LazyShape(sp: IShape) : IShape by sp {
        override val name: String
            get() = "懒人的name"

        override fun ppp() {
            println("LazyShape还是我自己ppp吧")
        }
    }

    //如果LazyShape也愿意实现接口的函数，那么是可以的，且创建LazyShape的对象调用的时候，优先自身的draw
    private fun testShape() {
        val rec = Rectangle()
        val ls = LazyShape(rec)
        ls.draw() //todo draw调用的name只能是Rectangle的，而不是ls的
        ls.ppp()//会调用LazyShape自身的
    }


    //1.2 属性委托，by lazy要求必须是val的
    val sss: String by lazy {
        println("第一lazy调用时候，初始化sss")
        "这就是个系统库提供的标准委托属性的实现"
    }

    //属性值 观察者的委托
    var zzz: String by Delegates.observable("还没有赋值") { property, oldValue, newValue ->
        println("每次赋值之后，都会调用 $oldValue --> $newValue")
    }

    //委托给另一个属性，可以是topLevel的，可以是自身或者其他对象的 其他属性/扩展属性，使用::双引号
    //使用场景：在废弃一些属性时候，可以转变给新的属性
    private class IntClass(val num: Int)

    private class DemoClass(var dNO: Int, ic: IntClass, map: Map<String, Any>) {
        var numberOne: Int by this::dNO//委托给自身的属性,注意⚠️var或val要保持两者的一致
        val numberTwo by ic::num //委托给其他对象的其他属性
        val num: Int by ::topInt//委托给topLevel的属性

        //todo 也可以委托给map，则要求传入的map需要有对应字段
        val name: String by map
        val age: Int by map
    }

    // 局部委托属性，就是在函数内作用域局部变量的委托
    private fun testLocal(afun: () -> String) {
        val aLazyStr by lazy(afun)//因为afun是String类型的结果，所以可以如此委托
        //只有在使用到aLazyStr时候，才会赋值
    }


    //endregion

}

private val topInt: Int = 999//topLevel的Int，上面用