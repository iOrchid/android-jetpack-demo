package org.zhiwei.jetpack.kt.base.old

//package org.zhiwei.jetpack.kt.base
//
///**
// * Kotlin语法基础（五），函数，方法的定义，使用
// * Author: zhiwei.
// * Github: https://github.com/zhiwei1990
// * Date: 2019-05-04,20:28.
// * You never know what you can do until you try !
// */
//class KotlinSyntax05 {
//
//	fun testFun() {
//		//普通调用
//		ttUnit()
//		println("hh")
//		//成员函数的调用方式
//		KotlinSyntax05().ttUnit()
//		//调用在KotlinSyntax01中的top level的定义函数
//		Test()
//		//单例调用
//		Single.ttSingle()
//		//调用带默认值的
//		textFP(2, 'd', '%', "str")
//		textFP2(8, d = "str")//不传递c参数的值，那么它就默认了,而传递的参数顺序和形参不一致，就需要明白指出来 形参=value
//		//调用可变参数的
//		testVa(3)
//		testVa(3, 1, 4)
//		//
//		testFR(3)
//	}
//
//	// 带参数的函数定义,可以设置默认值，包含默认参数的函数，子类继承覆写函数的时候，子类不能修改默认值的
//	private fun textFP(a: Int, b: Char, c: Char = '#', d: String): String {
//		return "$a $b $c $d"
//	}
//
//	//如果都有默认值，那么调用函数也可以不传任何值，使用默认，
//	private fun textFP2(b: Int, c: Char = '#', d: String): String {
//		return "$a $b $c $d"
//	}
//
//	//简写带有返回值的
//	private fun testFR(a: Int) = a + 9
//
//	private fun testVa(vararg a: Int) {
//		println(a)
//	}
//
//	//返回类型过为Unit，可以省略不写，
//	private fun ttUnit(): Unit {
//		//返回类型为Unit，可以省略掉return
//		return Unit//可以省略掉Unit，甚至整个return语句
//	}
//}