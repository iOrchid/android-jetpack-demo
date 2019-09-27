package org.zhiwei.jetpack.kt.common

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月20日 10:33
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 进阶语法的演示操作类，主要有扩展函数/属性、伴生对象扩展/作用域扩展，密封类/copy函数、泛型/约束/型变/星号引用、枚举常量、对象表达式/委托，
 * 1、扩展函数，可以topLevel/类中，而扩展函数是静态解析的，不是动态加载
 * 2、扩展函数若于成员函数重名，优先调用成员函数
 * 3、扩展空对象，this可以代指被扩展类的对象
 * 4、扩展属性，可以定义在topLevel中，类中，但是不能在函数内，而且没有初始化值，只能显示的getter,而且扩展属性只能val，所以没有setter
 * 5、扩展函数针对于类，类的伴生对象也可以
 * 6、扩展函数作用域，类似于普通的导包，如果定义在一个kt文件的topLevel中，在外部使用就导入该kt的包就行。
 * 7、在一个类中，定义另一个类的扩展函数，则在扩展函数内，就会有多个隐式的对象引用，即引用当前类和被扩展类的引用
 * 8、扩展函数，在定义类及其子类中，可以被覆盖重写，但是针对于具体被扩展类，扩展函数是静态加载的。
 * 9、数据类可以实现其他接口或继承其他类，copy函数便于快速创建数据对象，系统提供了Pair、Triple等标准数据类
 * 10、密封类，用于限定数据的类型，理解为特殊的数据类，枚举类型的扩展，相对更为灵活。其声明和子类继承，都必须在topLevel中，布恩那个在其它类中
 */
class CommonKt {

    //<editor-folder desc="1、扩展函数、扩展属性">

    //1、扩展函数就是对已有的类，添加新方法，但是不修改原有类。todo 可以写在topLevel级别，可以写在某个类内，也可以有权限声明，根据业务场景需要。
    fun String.getStudentName(i: Int): String {
        //这个函数，就是对String类添加了一个getStudentName的函数，看下面main函数的演示
        return "小明$i"
    }

    //2、扩展函数是静态解析，而非动态加载,也就是说，如果C类扩展了一个函数foo()，C的子类也扩展foo(),那么调用谁就是谁，而不是运行时解析
    open class C {
        //C类
        fun doo() {
            println("C内部成员doo")
        }
    }

    class D : C()//D继承C

    fun C.foo() {//对C扩展foo
        println("C.foo")
    }

    fun C.doo() {//C扩展doo函数，同名于自身内部函数
        println("C.doo")
    }

    fun D.foo() {//对D扩展foo
        println("D.foo")
    }

    fun printFoo(c: C) {
        c.foo()
    }

    //3、如果被扩展类，已经有了一个函数，而又扩展了同名函数，则调用时候，优先使用类自身的成员函数

    //4、扩展空对象，被扩展类若为空也可以调用，如扩展Any?可null对象一个函数，调用时候，函数内可以用this代指该对象
    fun Any?.getSuper(): String {
        if (this == null) return "对象是null"//this代指调用方/被代理类的对象
        //上面判空后，下面就是非空了
        return "Any是没有super的，哈哈哈"
    }

    //5、扩展属性,可以在类，kt文件内，不能函数内，不能有默认值，只能显示的getter，只能是val，如果var，写上setter的话，编译没问题，运行崩溃
    val String.middle: Int
        get() = this.length / 2//扩展string的获取长度一半的数值，


    //6、扩展函数的隐式引用
    private class CA {
        fun bar() {
            println("CA.bar")
        }
    }

    private class CB {
        fun baz() {
            println("CB.baz")
        }

        //在CB中对CA扩展函数，则含有CB，CA的对象引用
        fun CA.bb() {
            //下面这两个，可以简写位bar(),baz(),而不用this显式引用
            this.bar()//这就是引用的CA
            this@CB.baz()//这就是引用的CB
        }

    }


    //7、

    private open class EA
    private class SubEA : EA()

    //对上述两个类进行扩展函数定义
    private open class ODemo {
        open fun EA.foo() {
            println("ODemo中扩展的EA.foo")
        }

        open fun SubEA.foo() {
            println("ODemo 中 SubEA的扩展foo")
        }

        fun print(ea: EA) {
            ea.foo()//调用扩展函数
        }
    }

    //在子类中，覆盖已有的扩展函数
    private class SubDemo : ODemo() {
        override fun EA.foo() {
            println("===ODemo中扩展的EA.foo ===")
        }

        override fun SubEA.foo() {
            println("--- SubDemo 中 SubEA的扩展foo ---")
        }

    }


    //函数演示
    fun testExt() {
        val school = "TsingHua"
        //todo 看这里，可以使用上面的扩展函数啦
        println(school.getStudentName(2))
        println(school.getStudentAge())
        //调用printFoo,虽然传递的参数是D的对象，但是输出的结果，是C.foo，因为扩展函数是对于类，静态扩展解析的。
        printFoo(D())
        //成员函数优先
        C().doo()
        //null对象嗲用
        val t = null
        println(t.getSuper())
        //string的长度一半
        println("abddgjljdglkajg".middle)
        //
        ODemo().print(EA())//因为扩展函数对于被扩展类都是静态加载的，所以由于print函数接收的是EA，所以输出结果就是“ODemo中扩展的EA.foo”
        SubDemo().print(EA())//在定义扩展函数的类/子类中，允许覆写扩展函数，调用时候，是动态的。所以输出 “SubDemo中扩展的EA.foo”
        ODemo().print(SubEA())//这里即使你传递的是SubEA，但是输出的还是 “ODemo中扩展的EA.foo”，因为print的参数是EA


    }

    //</editor-folder>


    //<editor-folder desc="2、密封类/copy函数">

    //1、数据类可以实现其他接口，以及继承类,网上教程说是不能继承类，其实新版本kotlin可以的，
    private open class GGG

    private data class CC(var a: Int) : GGG()

    //2、密封类，算是一个特别的数据类。不能open，也不要interface，abstract。用作限定数据类型，相比枚举类型，更为灵活。
    // 枚举是一个类，多个数值，而密封类，是一个类，可以有很多种子类，打得属于一个类型就行。作为类型限制.
    //todo 参照文件底部Expr声明,这样就能限定参数是expr类型，相比于枚举类型，较为灵活一些
    private fun textExpr(e: Expr) {
        when (e) {
            is Const -> {
                //这里就不些具体代码了，
            }
            is NotNumber -> {
//如果能确定所有类型，其实else可以不写
            }
            is BigNumber -> {
//密封类就主要用于替换枚举，用于一些要求宽泛的限定数据类型的地方
            }
            else -> {
                //
            }
        }
    }

    private fun testCz() {
        //数据类的copy并修改值
        val c1 = CC(2)
        val c2 = c1.copy(a = 3)
    }

    //</editor-folder>


}

//这个是 toplevel级别的扩展函数，并加了权限修饰
private fun String.getStudentAge(): Int {
    return 22
}

//密封类，必须生命在kt文件根节点，不要放在某个类内部，实现子类可以放在该文件的任意位置，哪怕其他class内
sealed class Expr//限定某个函数的参数只能是expr的类型

//下面就是属于expr类型的不同数据，
data class Const(var name: Double) : Expr()

data class BigNumber(val bb: Double) : Expr()
private data class CommonNum(var a: Int) : Expr()
object NotNumber : Expr()