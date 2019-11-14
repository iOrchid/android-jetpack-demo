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

}