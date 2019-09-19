package org.zhiwei.jetpack.kt.base.old

//package org.zhiwei.jetpack.kt.base
//
///**
// * Kotlin基础语法之三，逻辑条件控制
// * Author: zhiwei.
// * Github: https://github.com/zhiwei1990
// * Date: 2019-04-27,19:58.
// * You never know what you can do until you try !
// */
//class KotlinSyntax03 {
//	//todo 这里插一句，在Kotlin中对象的创建，不需要new关键字，直接调用构造函数即可，如在调用本类的默认构造函数
//	//就是 KotlinSyntax03() 就可以得到一个对象
//	var a = 3
//	var str = ""
//
//	fun testIf() {
//		//1、常规
//		if (a > 0) {
//			print("It's OK")
//		} else {
//			print("very bad")
//		}
//		//2、作为三目运算 Java中类似a>0?"":""的三目运算在Kotlin中用if else代替
//		str = if (a > 0) "OK" else "not OK"
//		//3、条件代码块,可以理解为加强版的 上一个 表达式，可以类似后面提到的when表达式
//		str = if (a < 9) {
//			"咋么小于9"
//		} else if (a > 10) {
//			"感觉条件不充足，不管了，反正有兜底的"
//		} else {
//			"我是兜底的"
//		}
//	}
//
//	fun testFor() {
//		//1、常规自增、自减
////        for (i in 0 until 10){//左闭右开[0,10)
////            println(i)
////        }
////        for (i in 0..10) {//取值[0,10],自增的渐变写法，类似until但是取值范围不同
////            println(i)
////        }
////        for (i in 10 downTo 0) {//左右都包含[10,0]
////            println(i)
////        }
//
//		for (i in 0 until 10 step 2) {//指定每次循环跳跃的步幅，在until，.. downTo都可以用
//			println(i)
//		}
//	}
//
//	fun testFor2() {
//		//基于索引，遍历操作
//		//1、字符串索引
////        for (i in "abdesdlagjwe"){
////            println(i)
////        }
//
//		//2、数组遍历
//		val arr = arrayOf(2, 0, "3", 'c', true)
////        for (i in arr){
////            println(i)
////        }
//		//3、数组自身属性遍历
////        for (i in arr.indices){//其中indices是IntRange类型,i就是索引
////            println(arr[i])
////        }
//		//4、withValue数组遍历
////        for ((index,value) in arr.withIndex()){//这个算是kotlin的高级语法操作，很多业务场景中方便实用
////            println("$index , $value")
////        }
//		//todo 这里就啰嗦一句了，学习更多的是靠自己主动探索，类似上面的，遇到一个新的类，就点进去看原码，看看函数调用，试试，就会学到一些
//		//数组自身的迭代器
//		val iterator = arr.iterator()
////        while (iterator.hasNext()) {
////            println(iterator.next())
////        }
//		//forEach函数，lambad表达式，默认参数形参为it，可以自定义如：iterator.forEach{xp->println(xp)}
//		iterator.forEach {
//			println(it)
//		}
//	}
//
//	fun testWhen() {
//		//Kotlin中没有了swith。。case，改用when，真心觉得更强大
//		//1、替代switch...case
//		val index = 3
//		when (index) {
//			1 -> {
//				println("不对")
//			}
//			2 -> println("差一点")//只有一行，其实不用花括号
//			3 -> {
//				println("OK")
//			}
//			else -> {
//				println("nothing")
//			}
//		}
//
//		//2、多重合并的case
//		when (index) {
//			1, 2, 4, 5 -> {
//				println("不对")
//			}
//			0, -1, -3 -> println("差一点")//只有一行，其实不用花括号
//			3 -> {
//				println("OK")
//			}
//			else -> {
//				println("nothing")
//			}
//		}
//		//3、突破局限，条件语句可任意,这里就相当于if了
//		when (a > 0) {
//			false -> {
//				println("不对")
//			}
//			true -> {
//				println("OK")
//			}
//			else -> println()//这里可以省略掉了，因为true和false必然走一个
//		}
//		//4、case条件突破常规
//		var any: Any? = null
//		when (any) {
//			in 0..7 -> println("index 在0～7之间，index是$index")
//			is Int -> println("index$index 是Int类型")
//			!is String -> println("咋可能不是Int")
//			any as Int !in -1..9 -> println("凑数呢？，仅仅演示when的强大case用")//这里前面加个as 转换类型，不然语句!in 编译报错
//			else -> {
//				println("Nothing 吧")
//			}
//		}
//		//5、无表达式when,类似于if了，在when中，else代码块，放在最后，兜底用的
//		when {
//			true ->
//				println("www")
//			false -> println()
//			else -> {
//				println("nnn")
//			}
//		}
//	}
//
//	fun testWhile() {
//		//while 与 do while 用法在好多编程语言中，都是类似的
//		while (a > 3) {
//			a--
//			println(a)
//		}
//		do {
//			println("hhh")
//		} while (a > 2)
//	}
//
//	fun testBRC() {
//		//break,return continue
//
//		//1、return 返回最近一层的函数，或匿名函数
//		var str = ""
//		//不同于java，kotlin中函数内可以定义函数
//		fun aa() {
//			if (str.isEmpty()) {
//				println("str empty")
//				return//这里就return出aa函数了
//			}
//		}
//		aa()//调用aa函数，
//
//		//2、终止最近的一层循环
//		for (i in 0..5) {
//			println("i 是啥$i")
//			if (i == 2) {
//				print("=2时候出去了")
//				break//=2的时候循环终止，到不了5
//			}
//		}
//
//		//3、continue，在循环中，终止本次的循环后操作，进入下一圈
//		for (i in 0..5) {
//			if (i == 2) {
//				println("=2时候下一个了，所以出不来i=2")
//				continue//=2的时候循环终止，到不了5
//			}
//			println("i =$i")
//		}
//	}
//}