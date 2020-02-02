package org.zhiwei.jetpack.kt.base.old

import android.util.Log

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月20日 9:22
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
class KotlinOld {

	//todo kotlin都是封装类型，不能自动的类型转换,lazy懒加载也可以
	val lazyStr: String by lazy {
		println("这条语句，只会在第一次加载时候调用，再次调用这个变量的时候，就不会打印了")
		"懒加载的返回值"
	}


	var age: Int = 0 //变量的声明，kotlin不需要 ; 分号来结束语句.但是一行若有多个语句，可以用 ; 分割
	val PI: Float = 3.1415926f// var 声明变量 val 声明常量。格式为 var/val name:Type = init ,:Type 也可以省略
	//这里演示 一行多条语句，分号分割，但是IDE格式化后，就不会在一行了。
	//    var b: Byte = 0x08;    var st: Short = 0x16;var i: Int = 0x32;var l: Long = 64L; var f: Float = 32.0f;var d:Double=64.0;
	var b: Byte = 0x08
	var st: Short = 0x16
	var i: Int = 0x32
	var l: Long = 64L
	var f: Float = 32.0f
	var d: Double = 64.0
	val str: String = "zifuchuan"
	var cc: Char = '9'//不能直接写2 需要单引号，不同于java。只能是单个字符

	//可用下划线分割长的数字
	var millon: Int = 1_242_143_253


	//fun 关键字，定义函数
	fun onCreate() {

		//多参数
		getSum(1, 2, 3, 4, 9)
		//匿名函数定义
		sumLambda(1, 32)

		//
		equalIt()
		//
		circle()
		//
		labelTest()
		//
		println(lazyStr)
		println(lazyStr)
	}

	/* 注释，类似于java的注释，不过这个多行注释，内部可以嵌套单行注释//，而java的不行
		//定义一个函数，返回值为空 格式：
		//fun functionName():returnType 返回类型为空 Unit。类似于java中的void,Unit可省略。其他不行。
	*/
	fun doNothing(): Unit {
		println("do Nothing()")
	}

	// 带参数，返回值的函数 可以简写为  fun getSum(a: Int, b: Int)=a+b
	fun getSum(a: Int, b: Int): Int {
		return a + b
	}

	//vararg 关键字，表示 可变参数，即可接收多个同类参数
	fun getSum(vararg a: Int) {
		//可变参数，类似于数组
		println("KotlinActivity.getSum : " + a[0])
	}

	//匿名函数的定义，lambda形式
	val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

	var abc = "变量值引用$i,返回值引用${sumLambda(1, 32)}"

	//Kotlin的null判断，? 问号表示可能为null，会抛出异常。!!则表示 断定非 null
	fun parseInt(s: String): Int? {
		return s.toInt()
	}

	fun typeIs() {
		if (abc is String) {
			println("类型一致")
		}
		if (i !is Int) {
			println("类型不一致")
		}
	}

	// .. 符号表示范围 range且是 [ ]左右都闭合的区间
	fun conditation() {
		//i++
		for (i in 1..4) {
			println(i)
		}
		//这样不会输出，想要i-- 需要down to
		for (i in 4..0) {
			println(i)
		}
		//step
		for (i in 1..10 step 2) {
			println(i)
		}
		//i--
		for (i in 8 downTo 3 step 2) {
			println(i)
		}
		//左闭右开 [ ) 使用until
		for (i in 1 until 7) {
			println(i)
		}
		//if else
		var abc = 0
		if (1 < 2) abc = 1 else abc = 3

		if (1 in 0..3) abc = 100

		//when 类似于switch case
		var x = 1009
		when (x) {
			0 -> abc = 0//类似于case
			2 -> abc = 3
			in 10..100 -> abc = 2300
			is Int -> println("int")
			!is Int -> println("! Int")

			else -> abc = 200//类似于default
		}
	}

	/**
	 * == 对比 两个对象的值 ， === 对比两个对象的地址
	 */
	fun equalIt() {
		/*
		 *  shl(bits) - 左位移（Java中的<<)
			shr(bits) - 右位移（Java中的>>）
			ushr(bits) - 无符号右位移(Java中的>>>)
			and(bits) - &
			or(bits) - |
			xor(bits) - ^
			inv() - reverse
		 *
		 */
		//todo kotlin中数组对象不可变，内容可变
		val abc: Array<Int> = arrayOf(0, 2, 4)
		val efg = Array(3) { i -> i * 2 }//得到的数组，是{0,2,4}
		for (i in efg) {
			Log.i("test", "i: " + i)
		}

		val a = "aaa"
		val cc: CharArray = charArrayOf('a', 'a', 'a')
		val b = String(cc)

		if (a == b) {
			println("两个对象的值一样")
		}

		if (a === b) {
			println("两个对象一样")
		}
		//todo string 可以用""" 三引号包裹长字符，可用 | 作为边界
		val longStr = """
            |第一行，
            |第二行，
            |第三行
        """.trimIndent()
			.trimMargin()//trim空白
	}

	fun circle() {

		val array = arrayOf(1, 23, 32)

		for (item in array) println(item)

		for (j in array.indices) println(array[j])

		for ((index, value) in array.withIndex()) {

			print("item $index is $value")
		}

//        var x: Int = 3
//
//        while (x in 1..30) {
//
//        }
//
//        do {
//            println()
//        } while (x == 3)
	}

	fun labelTest() {
		//return break continue 可以配合label标签

		lab1@ for (s in 0..9) {
			println(s)
			lab2@ for (m in 3..5) {
				println(m)
				if (m == 4 && s == 4) break@lab1
			}
		}
		val aaa = arrayOf(21, 332, 3, 23, 5, 25, 23)
		aaa.forEach {
			//it代表便利的index对应的对象值
			println("return@forEach 之前 $it")
			if (it == 23) return@forEach//匿名标签，这里return@forEach，就类似于普通for循环中的continue作用，如果是return，不带标签，就return整个函数了。所以这里只是结束单次循环，可以验证注意下面的输出
			println("return@forEach 之后 $it")
		}
	}
	//todo open 可继承的class的修饰符，abstract 抽象，则不需要open也可以。内部类inner 关键字 final 默认都是final的。private internal，protected，public； object是关键字，表示对象。companion代码块，类似于静态

	companion object {
		private val TAG: String = this::class.java.simpleName//Tag
	}

}