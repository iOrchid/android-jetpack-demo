package org.zhiwei.jetpack.kt.base.old

//package org.zhiwei.jetpack.kt.base
//
///**
// * Kotlin基础语法（四） 操作符重载,Null，类型相关
// * Author: zhiwei.
// * Github: https://github.com/zhiwei1990
// * Date: 2019-05-04,16:53.
// * You never know what you can do until you try !
// */
//class KotlinSyntax04 {
//
//	fun testOp() {
//		var a = 1
//		var b = -3
//		var c = true
//		//+ - ! 操作符，表示正数，负数，boolean的相反，kotlin中提供操作符重载，是
//		//unaryPlus,unaryMinus,not
////        println("${-a},${+b}, ${!c}")
////        //操作符重载后的函数，其输出结果一致
////        println("${a.unaryPlus()}, ${b.unaryMinus()},${c.not()}")
//
//		//todo a++，a--操作符，意思就是在a的值，加1/减1，但是要在下一次用到这个变量，才会体现出来，而++a，--a则是这次使用之前先加1/减1，所以当下的操作就会出现效果
//		//a++，a--，++a，--a的操作符重载
//		var a1 = 10;
//		var a2 = 10;
//		var a3 = 10;
//		var a4 = 10
//		//++a，--a的操作效果，会当次就体现
////        println("${a1++},${a2--},${++a3},${--a4}")
//		//a++，a--操作的效果，下面才展现
////        println("$a1,$a2,$a3,$a4")
//		//kotlin的重载函数，++ 对应inc（），--对应dec（），但是a++与++a就需要结合到kotlin的高级函数also{}
//		//便于对比，重新定义变量，因为上面的已经操作了，
//		var b1 = 10;
//		var b2 = 10;
//		var b3 = 10;
//		var b4 = 10
////        b1.inc()//虽然inc对应++操作符，但是这么写，只是得到新的值，新的指针还没有指引到b1上，所以输出显示无效，要用also将结果定向给自身
////        b2.dec()
//		b1.also { b1.inc() }
//		b2.also { b2.dec() }
//		//下面俩个对应++a，--a操作
//		b3.inc().also { b3 = it }//这里also是高阶函数，lambda表达式，只有一个参数，默认为形参为it，可以自定义为别的，如下面的
//		b4.dec().also { num -> b4 = num }
//		println("$b1,$b2,$b3,$b4")
//		//与a++写法的效果不同，这里操作后b1，b2的值不会变，除非手动赋值
//		b1.also { b1 = it.inc() }
//		println("$b1,$b2,$b3,$b4")
//	}
//
//	fun testOp2() {
//		//+ - * / % .. 操作符
//		//plus minus times div % rem(老版本的mod函数，废弃掉了)
//		var a = 10
//		var b = 12
//		var c = "20"
//		var s = "Kotlin"
//
//		println("a+b=" + a + b)//这里演示+拼接字符，
//		println("c+d=${c + s}")
////        println("a+c=${a+c}")//使用字符模版方式，$符号之后不能数字与字符在一起用这些基本运算
//
//		a.plus(b)
//		c.plus(s)
////        a.plus(s)//数字和字符，不能这么用这些运算
////        a.minus(s)
//		a.rem(b)
//		//+= -= *= /= %=
////        a+=b
////        a-=b
////        c+=s
////        a%=b
//		a = a.plus(b)//a+=b
//		println(a)
//
//		//.. 表示取值区间 [a,b]，左右闭区间
//		a.rangeTo(b)
//		a..b
//
//	}
//
//	fun testNull() {
//		var a = 2//默认是Int
//		var aN: Int? = 2
////        a = null//不可以，报错，因为a是Int类型，不是Int?
//		aN = null
//		println("aN=$aN")
////        testANull(null)//调用函数，如果参数类型不是可空，传空会报错，但是 todo 请注意，由于kotlin可以和其他语言的库，尤其是java的，对接时候，不敢保证类似testANull中c是非空类型，java不会传个null过来。这是隐藏级别的Bug
//		//判空，只针对可空变量和对象
//		var str: String? = "str can be null"
////        str = null//这里模拟测试值 null
//		if (str == null) {
//			println("str null")
//		} else {
//			println("str=$str")
//		}
//		//使用?.判断，如果能确保非null，可以用!!，两者区别在于，前者遇null，会返回null，后者则抛出异常
//		str?.isNullOrEmpty()//在链式调用中，建议使用该形式，有利于判空，前部为null，则不下行。
//		//设置默认值,str为null时候，则返回定义值，非null，则自身
//		var result = str ?: ""
//		str?.length?.plus(7)?.times(20)
//		str!!.length.plus(7).times(20)
//
//		val arr = arrayOf(3, 2, 23, null, 29, 99, 109)
//		//常规写法
//		for (any in arr) {
////            if (any==null){
////                continue
////            }
//			//业务中，这个操作不能用null对象，所以有上面的判空
//			println("模拟业务操作$any")//如不判空，这样any就是取到一个null
//		}
//		//let写法
//		for (a in arr) {
//			//使用let操作符号，就可以将null对象，自动丢弃掉
//			a?.let { println("模拟业务操作$it") }
//		}
//
//		(str as? String)?.length
//
//	}
//
//	//这里的c参数是String，不可空的，
//	private fun testANull(c: String) {
//		println(c)
//	}
//}