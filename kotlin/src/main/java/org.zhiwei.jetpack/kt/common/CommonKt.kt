package org.zhiwei.jetpack.kt.common

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer

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
 * 进阶语法的演示操作类，主要有扩展函数/属性、伴生对象扩展/作用域扩展，密封类/copy函数、泛型/约束/型变/星号引用、枚举常量、对象表达式/委托，别名/内联类
 * 1、扩展函数，可以topLevel/类中，而扩展函数是静态解析的，不是动态加载
 * 2、扩展函数若于成员函数重名，优先调用成员函数
 * 3、扩展空对象，this可以代指被扩展类的对象
 * 4、扩展属性，可以定义在topLevel中，类中，但是不能在函数内，而且没有初始化值，只能显示的getter,而且扩展属性只能val，所以没有setter
 * 5、扩展函数针对于类，类的伴生对象也可以
 * 6、扩展函数作用域，类似于普通的导包，如果定义在一个kt文件的topLevel中，在外部使用就导入该kt的包就行。
 * 7、在一个类中，定义另一个类的扩展函数，则在扩展函数内，就会有多个隐式的对象引用，即引用当前类和被扩展类的引用
 * 8、扩展函数，在定义类及其子类中，可以被覆盖重写，但是针对于具体被扩展类，扩展函数是静态加载的。
 * 9、数据类可以实现其他接口或继承其他类，copy函数便于快速创建数据对象，系统提供了Pair、Triple等标准数据类
 * 10、密封类，用于限定数据的类型，理解为特殊的数据类，枚举类型的扩展，相对更为灵活。其声明和子类继承，都必须在同一个文件内，且在topLevel或密封类自身的类代码块中
 * 11、泛型，类似于java的泛型，kotlin中泛型可用于类、接口和函数，可用于上界约束，并且可以多个上界同时作用，使用where关键字
 * 12、todo 泛型的协变，型变，以及星号投影的深入学习
 * 13、枚举，自身就有的属性是name，ordinal（在枚举类中的序号）,也可以有自己构造参数，添加额外属性字段.不能继承其它类,可以实现接口，但是每个枚举对象都必须实现接口函数
 * 14、对象表达式，匿名对象中声明的变量属性只作用于内部范围，不论是不是public的，外部都无法访问到。object可以用于定义单例（作为对象修饰的时候，不同于表达式，访问方式二号作用域也不同
 * 不能访问外部类的任何东西，外部类对象也不能引用它，只能用外部类.引用。），伴生对象companion修饰的object，可以有名字，可以没有，它不受签名括号内的限制。
 * todo 在演示代码中，函数/类的定义，较多使用private，为的是避免其他地方写代码出现引用混乱和命名重复
 * 15、别名定义，可以设定访问权限，便于代码约定，实质不改变底层虚拟机的类名指向。
 * 16、内联类，inline 关键词，1.3版本才有，实验性的特性，类似于内联函数，用于优化内存开销与占用。
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
    // 枚举是一个类，多个数值，而密封类，是一个类，可以有很多种子类，大的属于一个类型就行。作为类型限制.
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

    //<editor-folder desc="3、泛型相关">

    //1、kotlin的泛型类似于java的，可用于类、接口和函数
    private class Tc01<T>(t: T) {
        //这里就是在class的类上，配置泛型T，接收不同的类型，则value就是不同类型的
        var value = t
    }

    //2、函数的泛型声明
    private fun <T> funT(t: T): Tc01<T> {
        //这里声明一个函数，接收T类型，返回一个tc01的对象
        return Tc01(t)
    }

    //3、泛型约束，约束上界。就是业务中常见的，允许接受某个参数，必须是某种类型的子类。（根据业务，可以考虑枚举，密封类，泛型）
    private fun <T : TextView> getTv(t: TextView): String {
        //这里演示，函数接收参数位textView的，或者其子类，并返回text字符串
        return t.text.toString()
    }

    //4、多约束，在业务中存在要求某个参数是某个类或子类，同时还要其是某个接口的实现类，如此就存在了多个上界约束条件,在kotlin中使用where
    private interface FunT {
        fun getName() = "default name"
    }

    private fun <T> testMultiT(t1: T, t2: T): String where T : TextView, T : FunT {
        //这里演示的就是，参数同时满足多个上界条件约束
        return "t1，t2 是TextView的子类对象，${t1.text}，t1 t2是FunT的实现类的对象，${t2.getName()}"
    }

    //todo 还需深入理解！！ 5、型变,kotlin中没有通配符，而是有 声明处型变、类型投影来作用。in 逆变，out 协变
    private class Tc02<out A>(val a: A) {
        //这里定义一个class，接收参数A类型，使用out修饰，则对应A类型就只能作为出参和返回类型。类似的 in 关键字修饰，就作为入参
        fun foo(): A {
            return a
        }
    }

    private class Tc03<in B>(b: B) {
        fun boo() {
            println("in 标记，作为入参限制，所以B不能为返回类型")
        }
    }

    //6、星号投影，针对于in，out的限定泛型使用，对于一个函数可能有多个参数的未知类型约束，可用*投射表示，根据位置来表示in或out的限定
    //todo 还需要深入学习理解！！

    private fun testT() {
        //1、测试泛型类，不同的T类型，它的value就是不同类型的数据。<>尖括号内有时候能自动推断的，可以省略不写
        val t1 = Tc01<Int>(4)
        println(t1.value is Int)
        val 她 = Tc01<String>("她，目前来说，不少编程语言的变量和函数声明可以使用中文名称，但是不保证一定没问题，所以呢，保持规范，还是标准命名规范")
        println(她.value)
        她.value is String

        //2、测试泛型 函数的使用
        println(funT(1))
        funT<Int>(2)//如果能自动推断类型，可以省略尖括号
        println(funT("string"))
        //3、泛型约束,约束上界，这里演示都是textView的子类
        val context: Context? = null
        val btn = Button(context)
        val et = EditText(context)
        getTv<Button>(btn)
        getTv<EditText>(et)
        //3、型变,如同java一样，kotlin中，List<TextView>和List<Button>它们不是一个类型的，也不是子类于扩展，
        val a1 = Tc02<String>("this is a String value class")
        var a2 = Tc02<Any>(333)
        a2 = a1//todo 称之为协变（Any 应用于String，高类型用于子类）
        println(a2.foo())//这样的话，输出的结果就是“this is ...”这个string，而不是333
        //
        var b1 = Tc03<String>("bbb")
        var b2 = Tc03<Any>(666)
//        b2 = b1//这里就不能这么写了，因为in修饰型变限制为入参泛型
        b1 = b2//但是b1可以=b2，todo 称之为 逆变（string的应用于Any，子类逆向用于基类）

    }

    //</editor-folder>

    //<editor-folder desc="4、枚举，对象表达式">

    //1、枚举，类似于java中，每个枚举都是一个对象实例.默认情况下枚举对象的名称，就是自己的value，可以在都构造函数中添加参数初始值(不能在其他地方声明)
    private enum class E1 {
        E1001, E1002, E1003
    }

    //带有自定义参数和默认值的枚举
    private enum class E2(val num: Int = 0) {
        E2000(),//上面写了默认值，这里可以不修改参数
        E2001(100),
        E2002(200),
        E2003(300)
    }

    //2、可以实现接口
    private enum class E3 : DialogInterface.OnClickListener {
        E3001,
        E3002;

        override fun onClick(p0: DialogInterface?, p1: Int) {
            //do something
        }
    }

    //如果枚举包含自定义参数的话，每个枚举对象都要单独实现接口函数
    private enum class E4(age: Int) : DialogInterface.OnClickListener {
        E4001(22) {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                //do something
            }
        },
        E4002(33) {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                //do something
            }
        }
    }

    //3、可以有自己的匿名类，以及函数,以及在对象中重写函数
    private enum class E5 {
        //枚举对象就要实现枚举类的抽象函数，也可以覆写存在的其他函数（如果是open的话，因为kotlin的class和函数都是默认final的）
        E5001 {
            override fun e5Boo() {

            }

            override fun e5Foo() {}

        };

        open fun e5Foo() {}
        abstract fun e5Boo()
    }

    private fun testEnum() {
        //E1的枚举对象，value就是自身名称,
        E1.E1001
        E2.E2001.num//添加的属性
        //
        E2.valueOf("E2002")//根据枚举对象的名称，来获取该枚举对象
        E2.values()//获得一个array数组，枚举类的所有对象值
        enumValueOf<E2>("E2001")//扩展函数的写法
        enumValues<E2>()
    }


    //4、对象表达式，object实现匿名类

    private open class AnC(age: Int) {
        open val mAge = age
    }

    open class AnCC
    private interface AnI {
        fun ioo()
    }

    //5、匿名对象可用作类内和私有作用域,这里就创建一个匿名类的对象，赋值给anFoo函数的返回值
    private fun anFoo() = object {
        val xxx = "xxxxxxx"
    }

    //因为AnC是private，所以对象出来也必须是private
    private val anC = object : AnC(33) {

    }
    val anCC = object : AnCC() {}

    fun anBoo() = object {
        val yyy = "yyyyyy"
    }

    //6、object可以用于定义一个对象类，也就是单例类，不能饮用commonKt的其他变量和函数，如果使用它，就只能用CommonKt.SingleObj.testSingle操作（这里为了演示使用了private，限制了好多操作）
    private object SingleObj {
        fun testSingle() {}
    }

    //其是这两个变量引用的都是同一个对象
    private val s1 = SingleObj
    private val s2 = SingleObj

    private fun testAnony() {
        val ctx: Context? = null
        val btn = Button(ctx)
        //1、对象表达式之，匿名对象
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

            }
        })
        //2、对象用于继承类或者接口,这里的ci就是一个对象，object表示的匿名对象实现了接口和类的继承
        val ci: AnC = object : AnC(33), AnI {
            val name = "jj"
            override fun ioo() {
                //do something

            }
        }
//        ci.name//访问不到的
        //匿名类
        anFoo().xxx
//        anBoo().yyy//访问不到,因为anBoo()多代指的对象是public的
    }


    //</editor-folder>


}

//这个是 toplevel级别的扩展函数，并加了权限修饰
private fun String.getStudentAge(): Int {
    return 22
}

//密封类，必须声明在kt文件根节点，不要放在某个类内部，实现子类可以放在toplevel，或者密封类自身内部嵌套，哪怕在内部多层class内也行
sealed class Expr//限定某个函数的参数只能是expr的类型
{
    //在密封类自身内部，可以声明子类
    class cccc : Expr() {
        //甚至在嵌套的内部，也可以，
        object ggg : Expr()
    }
}

//下面就是属于expr类型的不同数据，
data class Const(var name: Double) : Expr()

data class BigNumber(val bb: Double) : Expr()
private data class CommonNum(var a: Int) : Expr()
object NotNumber : Expr() {
    //密封类的子类中，再声明 密封类的子类，是不行的，不是内部嵌套的。
    //object ddjjjd:Expr()//这是不对的
}

//虽在同一文件，但是再其他类中，也是不可以声明密封类的子类
object dddd {
    //这是错的
    //object dddj:Expr()
}
//todo  typealias 用于定义别名，便于再调用出简写，同时对于多个同名不同包的类，可以区分。
// 比如Observable，在Rx，LifeCycle中都有，而且在同一个class文件中可能都有引用，那么在参数或者声明类的时候，就必须使用完整包名来区分。如此代码冗长，不便。所以可以在适当位置定义别名，就能简化区分。

typealias NoN = NotNumber//用NoN简写代指 NotNumber类，
typealias lObserver = Observer<View>
typealias rObserver = io.reactivex.rxjava3.core.Observer<View>
//内联类，目前还是实验特性，要求必须有且只有一个主函数的参数，作为内联参数，且val
//private inline class TinClazz(val a: Int) {}
