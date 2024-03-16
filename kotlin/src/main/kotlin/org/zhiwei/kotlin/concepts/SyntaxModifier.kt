package org.zhiwei.kotlin.concepts

/**
 * Kotlin语法基础之 权限修饰符
 */

//0. kotlin中权限修饰符有private internal protected 和public；一般如果是public则默认不写

//1. 在package内，也就是kt文件topLevel可声明 属性，函数，类，接口，对象等。
/*
    1.1     private 权限表示在作用域内私有，外部不可访问。
            internal 权限表示同一包package内可访问。
            protected 权限表示子类实现类可访问。（不可用于topLevel的声明处）
            public 权限公开。
 */

class SyntaxModifier protected constructor() {
    private val sName = ""

    /*
    1.2 在类内部，权限修饰符的作用：
    private 这个类内部（包含其所有成员或内部类里面）可见；
    protected 与 private 差不多，但也在子类中可见。
    internal 本模块内可见
    public 能见到类声明的都可见其 public 成员。
     */
    //这叫嵌套类，不同于Java，
    class NestClass {
        private val nName = ""
        fun test() {
//            sName //嵌套类不能访问到外部类的私有属性，就立即为两个独立的类，只是文件上有了交集而已。
        }
    }

    //这才叫内部类
    inner class InnerClass {
        private val inName = ""
        fun test() {
            sName //可以访问到外部类的私有属性，内部类会有外部类的引用的，所以可以。不同于嵌套类。
        }
    }

    fun testNestInner() {
//        NestClass().nName//无法访问到嵌套类的私有属性
//        InnerClass().inName//无法访问到内部类的私有属性
    }

    //继承时候，复写基类的函数，权限可以放大，不能缩小；返回值类型，则是可以缩小而不能放大

    open class Super {
        protected open fun aa() {}

        protected open fun bb(): Any {
            return 3
        }
    }

    class Sub : Super() {
        public override fun aa() {
            super.aa()
        }

        override fun bb(): Int {
            return 2
        }
    }

    //权限修饰符可以作用于 构造函数上
    //局部变量、函数和类不能有可见性修饰符。
    //internal 作用域可理解为IDE的一个module内
}