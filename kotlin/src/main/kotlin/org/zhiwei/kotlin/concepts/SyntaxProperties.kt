package org.zhiwei.kotlin.concepts

/**
 * Kotlin语法基础之 属性
 */
class SyntaxProperties {
    //0. 属性定义 使用var 定义变量，val 定义不可变量；属性可以定义在kt文件根节点下，即不属于任何类之内；此时称为topLevel的属性
    //属性定义的格式 val/var 属性名：属性类型 = 属性值 ，在函数内或者代码块内的局部变量，可以延迟初始化；

    //1. 不同于Java，属性初始化没有new关键字，也不需要写getter，setter （kotlin代码给java调用时，会有getter/setter生成）
    val pname: String = "pName"
    var pAge: Int = 18

    //可以显式的手动设置想要的setter和getter，里面可以调用其他属性值或者计算或者函数
    var desc: String = "" //作为类的属性定义，必须初始化。field代表的是幕后属性字段
        get() {
            return "这个是自定义get的赋值$field"
        }
        set(value) {
            field = value + "附加一点点"
        }

    //2. 编译期常量，也即是kotlin里常用的const val的声明；
    companion object {
        //编译期常量声明是const val 开始，必须在kt文件根节点或object类内或类的companion object内；
        //且赋值应该是String或基本数据类型，不会变的那种。而且不能有自定义的getter
        const val TEST_ONE = "test_one"
    }

    //3. 延迟初始化 lateinit关键字 ,对于类的属性，不能是主构造函数内的属性。
    // 不能有自定义getter/setter的。必须是var可变的，且类型非空的。
    // 可以是topLevel的属性或类的属性，但是不能是函数的局部变量。使用前必须初始化，否则抛异常，
    lateinit var latePName: String

    fun testP() {
        //延迟初始化的属性 可以用对象名::属性名.isInitialized判断是否初始化
        if (this::latePName.isInitialized) {
            //
        }
    }
}
