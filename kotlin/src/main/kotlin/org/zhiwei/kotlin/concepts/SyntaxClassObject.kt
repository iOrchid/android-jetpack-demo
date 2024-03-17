package org.zhiwei.kotlin.concepts

import android.view.View
import android.view.View.OnClickListener

/**
 * Kotlin语法基础之 类与对象
 */
class SyntaxClassObject {

    //region 0. Kotlin中导入包 每个kt文件都有package包定义（极个别场景，kt文件不属于package包时候，则无显式的包名）
    //导入其他包的类，使用import xxx.xxx.xxx.ClassName ;如果有不同包的相同类名同时存在，可以将其中一个或者 两个都设置别名
    //todo 使用 import xxx.xxx.xxx.AClass as AAA 格式；也可以使用类型别名，typealias(必须在topLevel层声明：即在kt文件根层级，而不是属于某个类或者函数）

    //region 1. 类 class关键字修饰；一般命名风格字母开头，大写驼峰风格；（数字，字母，下划线之类的避开语言的关键字）
    class LabelClassEmpty //没有类的具体实现代码内部，则就简化如此；此时是有默认无参数的构造函数；

    //1.1 kotlin中类的构造函数有主构造函数，默认在class声明处，内部可以有多个其他构造函数，但是都必须调用到主构造函数（依次传递到也算）
    //主构造函数的constructor关键字如果是public的权限时可省略;此示例该主构造函数是internal的权限，则不可省略;
    // 主构造内的name如果添加val/var则该参数就成了类的属性字段，可以在init块中使用，或者其他属性声明处使用。
    public class LabelClassFull internal constructor(private val name: String) {

        private val innerName =
            "内部名字".also { println("》》初始化innerName $it ,主构造name $name") }

        //这就是一个次构造函数，必须调用主构造函数
        constructor(name: String, age: Int) : this(name) {
            println("初始化constructor二参数构建函数 $name ,$age 主构造name $name")
        }

        //类内有一个init的初始化代码块，
        init {
            println("---- 初始化init代码块 主构造name $name")
        }

        private val innerAge = 99.also { println(",,,,,初始化innerAge $it") }

        //又一个构造函数，通过次级构造函数，传递调用到主构造函数的。
        constructor(name: String, age: Int, desc: String) : this(name, age)

        //todo 切记，任何时候初始化类为对象的时候，都是先初始化主构造函数，再依次按照类内部属性/代码块的顺序依次初始化；
        //todo 即使在创建对象的时候，使用的是次级构造函数，也是如此，且次级构造函数反而会在主构造，属性/init块 完成之后，再执行；不论该次级构造函数写在属性或者init块之前还是之后；
    }

    //要么就是不区分主次构造函数，那么调用哪个就是哪个；此时可理解为主构造函数为空，其余逻辑依旧如上
    class Pet {
        //如此写法，则会限制性init块，在执行下面的构造函数
        init {
            println("pet类的init代码块")
        }

        constructor(name: String) {
            println("单参数pet类的构造函数 $name")
        }

        constructor(name: String, age: Int) {
            println("多参数pet类的构造函数 $name ,$age")
        }
    }

    val rabbit = Pet("大白兔🐰")//Kotlin创建对象，不要new关键字

    //一个类内部可以包含：init块，构造函数，嵌套类，内部类，属性，函数，对象的声明等；
    //1.2 抽象类 abstract修饰，可以有abstract的函数（反之则必须）；
    // kotlin的类，函数默认都是final的，想要标记普通可继承或者可重写，就需要open标记字段；而abstract也不需要open修饰
    companion object {
        //伴生对象，每个class都有一个，可以理解为附加于该类的静态的属性对象，内部可用于定义一些函数或者属性字段，属于类，而不是该类生成的对象；
        const val THIS_IS_A_CLASS_NAME = "这个属性字段属于类，使用类名.调用，而不是对象"
    }

    //endregion

    //region 2. 数据类
    private data class DataClass(
        val name: String,//2.1 数据类的格式 data class 类名(var/val 属性名:属性类型 [= 默认值],...)
        var age: Int,//至少包含一个属性声明
    ) {
        //2.2 其余情况和普通类差不多,可以有属性，次级构造函数，函数声明,伴生对象等，可继承，可实现接口;
        // 但自身是final的不可修改。不可被继承；不能是内部类inner的，不能是密封类sealed的；
        // 就是数据类data class 必须有主构造函数且至少有一个属性在内声明；
        //Kotlin会优化data class ，添加copy函数，toString改写。与Java交互如果需要无参构造函数，则要求所有主构造函数内属性必须有默认值；


        var desc: String = "描述"

        constructor(age: Int) : this("", age)

        fun foo() {}

        companion object {

        }

    }

    private fun testDc() {
        //2.3 todo ⚠️data class的equals和toString仅包含主构造函数内的属性的判断，不包括类内部的声明；
        val dc = DataClass("张三", 20)
        dc.desc = "张三的描述001"
        val dc2 = DataClass("张三", 20)
        dc2.desc = "这个是假的张三，所以不知道说啥"
        //toString不包含desc属性
        println("两个对象 $dc ,, $dc2")
        println("判断相等是true的啊： ${dc == dc2}")
        println("事实上desc不一样的： ${dc.desc} ,,, ${dc2.desc}")

        //copy函数，便于快速创建
        val dc3 = dc.copy(name = "李四")
        //也可以有解构声明的形式
        val (name, age) = dc2.copy(name = "王二", age = 66)
        println("可以直接输出这个还没有名字的对象 王二，但是它有属性了 $name,$age")
        //系统提供了Pair，Triple等标准的数据类，便于日常使用
    }


    //endregion

    //region 3. 密封类和密封接口
    //顾名思义，密封就是要控制在一个固定区域内，sealed关键字修饰，密封类/接口 定义在同一个kt文件内，或者同一个package内（必须是同一个module的，不同module的同包名也无效）
    internal sealed class SealedClass {
        //3.1 密封类是abstract的，也没必要添加abstract修饰词;
        //
        val abd: String = ""
    }

    private sealed interface SealedInterface {
        fun siabc()
    }

    private sealed class Sc2 : SealedClass()

    private object ObSc : SealedClass() {
        fun ddd() {}
    }

    private data object DObSc : SealedClass()

    private class CommonClass : SealedClass(), SealedInterface {
        override fun siabc() {

        }

    }

    //在when表达式的时候，SealedClass类型的，when可以推断出所有实现类，所以可以不需要else

    //endregion

    //4.嵌套类，内部类，匿名内部类;
    //4.1 顾名思义，嵌套类，就是在一个类内部，写的另一个类的定义；不同于Java中如果嵌套就成了内部类；Kotlin中嵌套仅仅是文件代码的关系而已；
    //内部类，不要嵌套并且还要有inner修饰
    private inner class InnClass //内部类，有SyntaxClassObject这个类的引用。
    private class NestClass //嵌套类，不会有SyntaxClassObject这个类的引用，属于平级关系，仅文件上嵌套而已。

    //4.2 匿名内部类，用表达式的方式创建匿名类的对象，无需命名的，比如：
    private fun testAnonymous() {
        val view: View? = null
        //演示代码而已，这里object：xxx，就是创建了一个匿名类的对象，该匿名类实现了OnClickListener接口
        view?.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                //。。。。
            }
        })
    }

    //5. 枚举类,注意⚠️，所有代码为了演示方便，声明的类一般都加了private修饰符，避免其他地方重名冲突而已。具体业务根据实际选择合适的权限符

    private enum class EnClass(val age: Int) : OnClickListener {
        //5.1 枚举类，可以有主构造函数内的属性参数;可实现接口；可override函数；自身final不可open不可abstract；

        ZhangSan(3) {
            override fun onClick(v: View?) {

            }

            override fun abd() {

            }
        },

        //todo 枚举，内部包含的，都是其自身的实例对象，要理解这一点！
        Lisi(44) {
            override fun onClick(v: View?) {

            }

            override fun abd() {

            }
        },
        WangEr(22) {
            override fun onClick(v: View?) {

            }

            override fun abd() {

            }
        };//如果枚举类的声明，自身有函数，或者属性，就需要用;分号，将枚举的对象实例和下面的声明分开

        private var desc: String = ""

        fun foo() {}
        abstract fun abd()
    }

    private fun testEnum() {
        val enc = EnClass.ZhangSan
        EnClass.valueOf("ZhangSan").age
        EnClass.entries.first().name //枚举实例的名字
    }

    //6. 内联类 ,格式value class 类名(唯一的val的属性），且需要 @JvmInline
    @JvmInline
    private value class ValueClass(val name: String) {
        fun foo() {}
    }
    //也可有普通类的其他功能，如实现接口,函数定义;不能有其他属性，也无法open和abstract；
    //todo 内联类可以理解为就唯一属性的依次包装，而不是真的创建类，在使用时候会自动拆包装；有点类似类型别名的作用，但是类型别名只是别名，对比起来还是一样的；内联类则是有区分；

    private fun testValueClass() {
        val vc = ValueClass("张三")
        val aaa: AAA = "aa李四"
        fun useStr(str: String) {
            println("演示使用String入参，value class和类型别名")
        }
        useStr(vc.name)
        useStr(aaa)//AAA是个别名，实际还是String，ValueClass就是个类，但是使用的时候属性就会被内联到调用方
    }

    //7. object 关键字，修饰类对象，可以是单例对象，伴生对象或匿名对象；final的；
    private object SingleObj {
        const val HH = "这就是简单的一个单例模式了"
    }

    //类似于data class的object对象
    private data object Dobj : OnClickListener {
        var ss: String = ""
        var age: Int = 99

        fun foo() {}
        fun boo() {}
        override fun onClick(v: View?) {

        }
    }

}

//类型别名
typealias AAA = String