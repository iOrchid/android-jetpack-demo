package org.zhiwei.jetpack.kt.base

import java.util.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月20日 10:35
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 基础语法的补充演示
 * 1、Kotlin的导包和java基本类似，格式就是package package.name ,如果包名中存在kotlin的关键字，则需要``反引号，比如`in`.zhiwei.jetpack，in就是kotlin的关键字
 * 2、类型智能转换，不同于java，kotlin中的数据类型的转换，都需要显示的操作，可以用is 判断类型，用 as 转换。或者基础类型的可以有toInt,toDouble等
 * 3、Kotlin中最高类型是Any，类似于Java中的Object，所有对象类都是继承自Any
 * 4、Kotlin的代码注释和java类似，//单行，/**/多行，/** 文档注释 */，但是比Java更强大的是，多行/文档注释中，可以嵌套单行或者多行注释，而Java的不行。
 * 5、可使用字符串模板，而不用太多的+来拼接字符串
 * 6、可null类型 Any?之类的，使用时候，要么?.，要么!!强制
 * 7、lambda函数表达式的定义，1、在kotlin中函数内可以定义函数，2、函数可以简写为lambda，3、函数可以作为另一个函数的参数
 * 8、条件/循环语句if、when、while、for，kotlin中没有java版三目运算符?:那种，而是用if..else..代替。（kotlin中有?:，不是三木运算符，而是判空以及兜底操作符）
 */
class KtSyntax {

	//<editor-folder desc="1、类型转换">

	//基本数据类型，简单演示几个，其他类似
	private var aNum = 20//默认Int类型
	private var bNum = 92.3//默认Double
	private var cNum = 30f//Float值，就需要f/F的标记
	private var dStr = "this is a string !"
	private val ePi = "3.14"

	private lateinit var ssValue: Any

	private fun testConvert() {
		//1、使用数据类型的扩展函数，toDouble，toFloat等转换
		if (aNum is Int)
			aNum.toDouble()
		//2、使用as，不能转换，只能将未知类型，作为 某个类型操作，而且，必须是某个类型，否则就NPL空指针异常了。
		bNum as Double //as Double是可以的，因为默认就是Double，如果as Float，就会提示
		//如果as 不确定转换是否成功，可以用as?，这样转换后的结果，是可null的类型，在kotlin中，可null的类型，就需要你在使用的时候判空了，
		ePi.toDouble()//string转toDouble,如果string不是数字内容，机会NPL，可以使用toDoubleOrNull，会得到一个可null的类型，就能及时判空
		//3、智能推导转换
		if (ssValue is String) {
			//if的条件判断后，在此代码块内，就认为是string了 使用as 转化为string标记
			(ssValue as String).substring(8)
		}
		//演示 !is
		if (ssValue !is String) {
			ssValue.hashCode()
		}
	}

	//</editor-folder>

	//<editor-folder desc="2、字符串模板、null检查、lambda、区间">

	//1、字符串模板，类似shell或python语言中，也就是占位符的赶脚，不需要的可以转义
	val stemp = "this is a string template $ePi 使用\$符号就能引用到其他变量或者函数"
	//2、null检查，在topLevel中就说到，对于任何kotlin的类型，都有一个可null的对应，比如Int，有Int?，String有String?,Any有Any?
	//todo 不同于java，在kotlin中，如果声明和定义的是可null的，那么在使用的时候，就需要判空，或者强制使用，而声明定义不带?的那种，就不能接收null，否则崩溃
	private var nStr: String? = null

	fun testNull() {
		//这里使用nStr时候，由于其是String?的可null类型，就需要判空，或者强制使用
		nStr?.substring(9)
		//这样可以链式操作，只要前方有一个null，就不会继续下去，直接返回null结果
		nStr?.replace('a', 'b')?.substring(9)
		//也可以强制使用，决定非空 使用!!符号
		nStr!!.toLowerCase(Locale.getDefault())
	}

	//3、lambda，在Kotlin中更多的函数式编程代码，高阶函数，lambda就更是常见
	val lNum: (Int, Int) -> Int = { x, y -> x + y }

	fun testLd() {
		//调用上面的lNum，其实也就是调用一个加和的函数，上面就是一个lambda的定义
		println(lNum(2, 3))
		//函数内的定义函数
		fun inFun(vararg ss: String) {
			println(ss.toString())
		}
		//调用上面的函数内的函数，可变参数，
		println(inFun("d1", "d2", "d3"))
		//4、区间，在kotlin中可函数区间的概念
		val rNum = 0..3//表示区间，[0,3]闭区间，都可以取到边界值的
		println(rNum.toString())
	}


	//</editor-folder>


	//<editor-folder desc="3、条件语句if、循环for、while、do..while，以及特殊的when">

	//1、if条件语句，以及if..else..，没有?..:..，有?:也是判空用的
	private fun testIf() {
		var a = 3
		if (a in 1..10) {
			println("$a 在 1..10的区间内")
		}
		//替换三目运算
		var b = if (a in 1..5) "哈哈" else "嘿嘿"
		//这个是判空，若空就return了，return后也可以跟一个语句，由于这个testIf是返回为Unit的，所以return的语句，也只能是Unit的。
		nStr ?: return
		nStr ?: return print("return的语句，是返回空的语句就行")
	}

	//区间，或者 until，都是从小到大，如果第一个数大于后面的数，是输不出来的
	private fun testFor() {
		//取值[1,10]
		for (i in 1..10) {
			println(i)
		}
		//until 取值[1,10),左闭右开
		for (i in 1 until 10) {
			println(i)
		}
		// 10..1 ,或者10 until 1都是没有输出的
		for (i in 10 until 1) {
			println(i)
		}
		//其实如上的for条件，都是默认每次循环+1的，可以指定+的步骤
		for (i in 1..10 step 2) {
			println(i)
		}
		//循环递减，这里指定step 2 可以不写，默认是1
		for (i in 10 downTo 1 step 2) {
			println(i)
		}

	}


	//</editor-folder>

}