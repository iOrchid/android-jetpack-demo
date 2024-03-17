package org.zhiwei.kotlin.concepts

import androidx.annotation.StringDef
import org.zhiwei.kotlin.concepts.SyntaxCommon.TestLab.Companion.LAB_ONE
import org.zhiwei.kotlin.concepts.SyntaxCommon.TestLab.Companion.LAB_TWO

/**
 * Kotlin语法基础之 随笔
 */
class SyntaxCommon {

    //region 0. 空安全；在Kotlin中null与非null是两个平行的类型 如String与String?
    var s: String = ""//非null，不能赋值null，编译器会报错
    var str: String? = null//可以null的类型，？问号标记

    private fun testNull() {
        str?.length//调用可能null的对象的时候，可以if判断，也可以?.的形式，这样前面null之后，后面就不会调用了，避免了Java的判null一大堆。
        if (str != null) {
            //如果你能断定非null，可以使用!!双叹号来告知编译器不要提示
            str!!.substring(3..8)
        }
//        s=null//报错，不可赋值

        str ?: return //判空返回的操作符 ?: 问号 叹号
        str as String //类型转换
        s as? Number //尝试类型转换
    }

    // endregion 根据业务场景选择定义类型是否可null。1.接口返回的可能null，2，调用java的，没有NonNull注解的可能null，解析异常的肯能null等等；


    //1. 相等性 结构相等 == 判断类对象相等与否，equals的判断。 引用相等 === 判断内存地址引用是否同一个。
    //浮点数作为基础静态数据的时候，可以正常对比。但是作为引用数据的元素时候，有一些特性；数组对比可用contentEquals对比。
    //参见 SyntaxTypes中float部分 有介绍。

    //2. this指定对象的，如果较多有歧义，可以加@标记
    val testThis = "String This".apply {
        this.length//这个this是String对象的
        this@SyntaxCommon.testNull()//这个this@SyntaxCommon指定了对象类的名字
    }

    //3. 注解
    @Target(
        AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.EXPRESSION
    )//注解作用的对象位置
    @Retention(AnnotationRetention.SOURCE)//注解作用时机
    @MustBeDocumented//是否必须文档说明
    annotation class LabIn(val name: String, val age: Int) {//注解类可以有主构造函数内的属性，必须val
        //内部不能有属性和函数
    }

    @Target(AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(LAB_ONE, LAB_TWO)//可以定义注解必须哪些值
    annotation class TestLab {
        companion object {
            const val LAB_ONE = "one"
            const val LAB_TWO = "two"
        }
    }

    //4. 解构声明，就是可以用对象的属性直接代替接收到的对象定义
    internal data class Dat(val name: String, val age: Int) {
        fun foo() {}
    }

    private fun testDat() {
        val da = Dat("张三", 99)
        //这就是对象解构声明，类似的也有函数返回的时候，用不到的参数可以_下划线处理
        val (_, age) = da.copy("李四", 23)

    }

    //5.反射
    private fun testReflect() {
        val dt = Dat("zhang", 3)
        val dtC = Dat::class//kotlin类型的class的
        val dtCJ = Dat::class.java//Java类型的class的
        println(::LAB_ONE)//属性引用方式
        println(this::testDat)//函数引用
        dtC::members.getValue(dt, dt::name)
        //访问属性
        val age = Dat::age
        age.get(dt)
        //访问函数
        val func = Dat::foo
        func.invoke(dt)
        //扩展函数
        val fun2 = Dat::school
        fun2.invoke(dt)
        //动态创建对象并调用构造函数
        val constructor = Dat::class.constructors.first()
        constructor.call("反射", 23)

    }
}

private fun SyntaxCommon.Dat.school() {

}