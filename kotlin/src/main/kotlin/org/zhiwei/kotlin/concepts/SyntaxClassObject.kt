package org.zhiwei.kotlin.concepts

/**
 * Kotlin语法基础之 类与对象
 */
class SyntaxClassObject {
    // 0. Kotlin中导入包 每个kt文件都有package包定义（极个别场景，kt文件不属于package包时候，则无显式的包名）
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

}

//类型别名
typealias AAA = String