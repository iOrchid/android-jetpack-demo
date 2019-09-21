package org.zhiwei.jetpack.kt.base

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月17日 9:48
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * TopLevel的演示，也就是说，在kotlin的语法定义中
 * 1、是可以直接将变量、函数等声明在文件的根层级，而不必一定要在某个类中（对比于Java）
 * 2、同样，在kotlin中可以在一个kt文件中，声明多个public的class/interface等，而在java中一个java文件，只能有一个public的class
 * 3、在kotlin中也有访问权限的修饰符public、internal、protected、private而与java中的public、protected、default、private并非一一对应。
 * 而且修饰符不一定可用于所有声明之处。如topLevel中，就不能用protected修饰变量或函数;权限关系会单独详细讲解
 * 4、同一个kt文件中，有多个类的时候，彼此相当于同一包内的关系
 */

//<editor-folder desc="top level中的变量/常量声明、函数定义">

/*1、数据类型，java中有
    byte(1字节)、short(2字节)、int(4字节)、long(8字节)、double(8字节)、float(4字节)、boolean(1个标记位)、char(2字节)
    其中1字节为8位
  2、kotlin中类似Byte、Short、Int、Long、Float、Double、Boolean、Char
    不同的是在kotlin中类型都是封装类型，也就没有java中基础数据类型的含义，都是可以自动拆装箱的对象类型。
  3、Byte范围: -128~127
    Short范围: -32768~32767
    Int范围: -2147483648~2147483647
    Long范围: -9223372036854775808~9223372036854775807
    Float范围: 1.4E-45~3.4028235E38
    Double范围: 4.9E-324~1.7976931348623157E308
  4、变量声明格式为 [权限修饰符] var/val valueName[:valueType] = value,其中[]内可省略，权限默认public。如 private var a:Int = 3
  5、同java一样，String并不属于数据类型，而是一个类。
  6、声明变量用var 不可变量 val ，kotlin中声明变量就要初始化（或者lazy，或者lateinit），这时候就可以省略类型，会自动推导
  7、对应于任何数据类型和class的类型，都有加?的，表示可null的类型，如Int，对应有Int?，String对应String?,User对应User?，
  不带?的声明类型就是说该value不能为null，带?的表示可以null；与Java不同，Java中没有区分，需要自己判断。

*/
var topByte: Byte = 127//范围 -128~127
var topShort: Short = 32767//这个数据的容量就是65535，也就是android上dex会遇到的方法数超出的问题原因。
var topInt: Int = 2147483647
var topLong: Long = 9223372036854775807//Long类型，可以在后面加L或l，一般建议L，便于区分
var topFloat: Float = 9999.0f//Float类型，声明赋值，就必须写上F或f，因为数字类型默认是Int或Double（根据小数点区分），所以要加上F/f，标识为Float
var topDouble: Double = 100086.0//Double类型，声明赋值的时候，必须带有小数点，不然就默认为Int了。
var topChar: Char = 's'//char类型，也是''单引号，但是Kotlin中的'a'不能像java中那样可转为ascii的数字。而且特殊字符需要转义
var topStr: String = "string value of String"//String使用""双引号，不同于python等语言可以单引号
var topString = """
    多行的，
    大串的，
    各种字符串
    也可以这么写
""".trimIndent()//kotlin中也可以使用"""三引号，用于多行大量的字符串声明，并且可以trim各种函数操作字符串的格式

val topName: String = "名字，不可修改，所以是不可变量 val 修饰"//不可变量，类似于java的final，而没有static

private const val PI =
	3.1415926//静态常量，圆周率。静态常量需要在object中，或者是toplevel声明，根据修饰符权限，作用范围。类似于java的static的final常量
var topNum = 20//省略类型标记，默认推导为Int，如果小数点的，就是默认Double，除非手动添加L/l、F/f表示Long，Float。

lateinit var topLateNum: String//这里是使用lateinit var 声明变量，可以在第一次使用该变量之前，初始化。
// 而且lateinit 和var 搭配的，不能是val。且类型不能是?的可null的，比如这里就不能是String?的
var topNullStr: String? = null//这是声明一个可null的变量

/*
权限声明的演示：
1、在topLevel中，protected是不能用的
2、public是默认修饰符，可以省略不写
3、private私有修饰符，同一类内，及其内部类。在topLevel可以理解为一个没有名字的类
 */

public var topPermPublicValue =
	"public permission"//用String类型来演示权限修饰符，public修饰符是默认的，所以可以省略不写。public范围公开
internal var topPermInternalValue = "internal permission"//模块内可访问的修饰符，权限是同一个module模块都可以
private var topPermPrivateValue =
	"private permission"//私有修饰符，作用范围为同一kt文件的同一阶层,及其内部类中。如此文件内声明class或object，都可以访问这个，
//而且kt文件就不能访问。并且，如果在一个class或object内的private变量，class/object外就不能访问。


/**
 * 函数定义:1、格式是 修饰符 fun 函数名(参数名:参数类型,):返回类型{}
 * 2、空类型Unit，类似于Java中的void，可以省略
 * 3、简单的返回可以用 =  等号简写。Kotlin中语句不需要;，除非当行多个语句，可用来分隔
 */
//无参数，空返回类型，返回可以不写
private fun getVoid(): Unit {
	//由于是返回Unit类型，return这个语句可以不写
	return Unit
}

//返回string类型
private fun getAppName(): String {
	//类似简短语句的返回，可以直接简写
	return "Android JetPack Demo"
}

//无参，返回String类型的函数，简写
internal fun getUserName() = "Android JetPackDemo"


//</editor-folder>

//简单演示在同一个kt文件中，除了上面top level的声明之外，还可以有其他public的class 或者object（kotlin中 object修饰表示一个单例类，也是一个class）
public class TopTestClass {
	//私有类,关于class的详细信息，后续进阶会有描述
}

//单例类
private object TopTestObject {
	private const val finalA = "final a"
}