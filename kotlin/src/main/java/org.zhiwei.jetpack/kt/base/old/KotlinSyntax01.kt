package org.zhiwei.jetpack.kt.base.old

//package org.zhiwei.jetpack.kt.base
//
///**
// * Kotlin基础语法（一），变量/常量/注释，基础数据类型
// * Author: zhiwei.
// * Github: https://github.com/zhiwei1990
// * Date: 2019/3/17,19:25.
// * You never know what you can do until you try !
// */
//
////<editor-folder desc="kotlin 变量/常量的 类中定义">
//
///*
//Kotlin中的单行、多行、文档注释与Java的注释无区别。
//唯一不同的是在kotlin的注释中，多行/文档 注释内，可以且套多行注释。
///*
//这个就是多行注释中的多行注释
// */
// * 要是在Java中就会报错了。但是他们都能在多行注释中嵌套单行注释
// */
//class KotlinSyntax01 {
//
//	protected val permValue = 3
//
//	//类属性的常量/变量的声明及初始化，大体和顶级toplevel中的声明，差不多。
//	var cA: Int = 10
//	var cB = 11
//	val cC = 3.1415926
//	lateinit var cS: String//不可空
//	private var cE: String? = null//声明可空，则需要?
//	//kotlin中class、var、function等默认修饰符不写，就是public，另有 protected、internal、private，类似于Java
//	//使用by lazy延迟初始化，则必须是val修饰，不可变，引用类型，基础类型不能延迟初始化。涉及到栈 堆
//	val list: Array<String> by lazy { arrayOf("cdd", "ddd", "dddd", "ddwecd") }
//
//	//init 为kotlin中class文件实例化必然调用的函数，不论构造函数有几个，都会调用init的
//	init {
//		cE = "null string"
//	}
//
//	//伴生对象，每个类都会有自己个一个伴生对象，不论它实例化多少个对象，这个object对当前类可理解为单例，静态
//	companion object {
//		const val PI = 3.1415926
//	}
//}
//
////</editor-folder>
//
//
////<editor-folder desc="kotlin 变量/常量的 Top Level中定义,也就是kt文件根结点定义">
//
//
////以下的变量/常量声明，是在kt文件的顶级节点下，根据public，private等修饰权限，对于整个module是有效的。所以
////这里的topLevel中定义的public的变量，在其他kotlin文件中，顶级位置，就不能再次定义同名变量，会冲突。
//var a: Int = 7//变量声明变量的标准格式 var name：type = xxx 其中xxx表示直接赋值或者实例化对象
//var b = 6//自动推到数据类型,如 6 推断为Int，而 6.0则推断为Double
//
////kotlin中一切皆对象形式，没有Java中的那种基础数据类型。变量的声明必须初始化，要么null，要么延迟初始化，而且null的对象和非空对象，声明也不一样。
//var s1: String? = null
//var s2: String? = "abc"//类型后加个?表示这个变量可以为null，见36行
//var s3: String = "abc"//上两个还可以s1=null，s2=null，但是s3是不可以的，因为类型是String，而不是String?
//lateinit var d: String//如果想要延迟初始化，可以用lateinit关键字修饰,但是?与lateinit不能共用
//val str: String by lazy { "sss val str" }//lazy 为高阶函数，延迟初始化，by连接词，但是不能与lateinit共用，且必须是val类型。
//val ee: Int = 900//val表示不可变量
//
//fun Test() {
//	d = "dddf"//延迟初始化的
////        ee = 3//不可变量，常量，都是不能修改的。
////        s3 = null//这里s3=null的话，编译就会报错
//	//局部变量，不可变量的声明
//	val sd = "ss 20010"
//	var ssa = 10080
//
//}
//
////Java中不允许在类外定义常量，变量之类的，但是kotlin中可以。Java中一个.java文件只能又一个public的类，但是kotlin中可以有多个。
//val lenggth: Int = 100//val 表示不可变量，可以为顶级，类成员，局部不变量。
//const val PI: Double =
//	3.1415926//const 修饰的val，表示常量，必须在top level或者companion object中声明，不能作为普通的类成员常量。其中单例类可以理解为伴生类就是自身的一个kotlin的类
////by lazy 延迟实例化，一个对象只会执行依次初始化
//val ccc: String by lazy { "fdf" }
//
////object  可以理解为一个单例类
//object Single {
//	const val sP = 3.1415926
//	fun ttSingle() {
//		println("hh single")
//	}
//}
////</editor-folder>