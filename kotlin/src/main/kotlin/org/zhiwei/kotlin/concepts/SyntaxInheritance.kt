package org.zhiwei.kotlin.concepts

/**
 * Kotlin语法基础之 继承
 */
open class SyntaxInheritance {
    //0. Kotlin中所有类都默认继承自Any（可null类型则对应Any?),都有equals，hashcode，toString函数；
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
}