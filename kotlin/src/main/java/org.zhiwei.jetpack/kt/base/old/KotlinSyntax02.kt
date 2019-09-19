package org.zhiwei.jetpack.kt.base.old

//package org.zhiwei.jetpack.kt.base
//
///**
// * Kotlin基础语法(二)，数据类型详解
// * 之后章节都会在class中定义和演示，避免topLevel中非必要的冲突，如在其他kt文件中有了定义，这里再定义，可能同名，冲突。
// * Author: zhiwei.
// * Github: https://github.com/zhiwei1990
// * Date: 2019-04-21,13:25.
// * You never know what you can do until you try !
// */
//class KotlinSyntax02 {
//	//基础数据类型，及内存大小和范围
//	var b: Byte = 2//8位（bit），一个字节Byte
//	var s: Short = 3//16位，两个字节
//	var i: Int = 7//32位，4个字节
//	var l: Long = 8L//64位，8个字节
//	var f: Float = 9.0f//32位，4个字节
//	var d: Double = 10.0//64位，8个字节
//	var c: Char = '3'//不能写'30'或'wf',char只能是一个字符,16位，2个字节
//	var c2: Char = 'w'
//	var bool: Boolean = false//只占一个标记为
//	var str: String = "Hello $d \n world "//与下面的三双引号的区别在于，这里可以有转义符号，而下面的，就不转义了。
//	var strLong: String = """Hi ! $d \n Nice to meet you \\\"""//注意这里面就失效了各种转义符号,$字符模版仍有效
//
//	//进制
//	var aa = 1080//10进制
//	var b16 = 0x99//16进制
//	var b2 = 0b0001//二进制
//	//字面常量表示
//	var money = 9999_9999_9900//只是分隔标记，不影响实际数值和输出展示，注意，这种表示方式，默认Long类型，不是Int
//
//
//	//参见 ExampleUnitTest 中 单元测试，需要时，可以解除注释
//	fun testV() {
////        println("Byte b: $b 8bit,min=${Byte.MIN_VALUE} max=${Byte.MAX_VALUE} sizeBit=${Byte.SIZE_BITS} sizeBytes=${Byte.SIZE_BYTES}")
////        println("Short s: $s 16bit min=${Short.MIN_VALUE} max=${Short.MAX_VALUE} sizeBits=${Short.SIZE_BITS} sizeBytes=${Short.SIZE_BYTES}")
////        println("Int i:$i 32bit,min=${Int.MIN_VALUE} max=${Int.MAX_VALUE} sizeBit=${Int.SIZE_BITS} SizeBytes=${Int.SIZE_BYTES}")
////        println("Long l:$l 64bit,min=${Long.MIN_VALUE} max=${Long.MAX_VALUE} sizeBit=${Long.SIZE_BITS} SizeBytes=${Long.SIZE_BYTES}")
////        println("Float f:$f 32bit,min=${Float.MIN_VALUE} max=${Float.MAX_VALUE} NaN=${Float.NaN} negativeInfinity=${Float.NEGATIVE_INFINITY},positiveInfinity=${Float.POSITIVE_INFINITY}")
////        println("Double d:$d 64bit,min=${Double.MIN_VALUE} max=${Double.MAX_VALUE} NaN=${Double.NaN} negativeInfinity=${Double.NEGATIVE_INFINITY},positiveInfinity=${Double.POSITIVE_INFINITY}")
////        println("Char c:$c c2:$c2 1bit,min=${Char.MIN_VALUE} max=${Char.MAX_VALUE} maxHighSurrogate=${Char.MAX_HIGH_SURROGATE} maxLowSurrogate=${Char.MAX_LOW_SURROGATE},maxSurrogate=${Char.MAX_SURROGATE}  minHighSurrogate=${Char.MIN_HIGH_SURROGATE} minLowSurrogate=${Char.MIN_LOW_SURROGATE},minSurrogate=${Char.MIN_SURROGATE} sizeBits=${Char.SIZE_BITS} sizeBytes=${Char.SIZE_BYTES}")
////
////        println("bool:$bool toString $Boolean")
////        println(str)
////        println(strLong)
//		//字面数值
////        println("9999_9999这两种字符，实际输出 $money")
//		//== 与 ===，前者对比对象值，后者对比内存地址
//		var t1: Int = 127//数值在-128到127之间，则t1===t2就会true，因为t2是Int?，自动拆装箱，就是封装类型。
//		//Kotlin中这个缓存是一个byte，所以在超出这个范围，t1===t2 就是false
//		var t2: Int? = null
//		var t3: Int = t1
//		t2 = t1
//
////        println("t1==t2 ${t1==t2} t1===t2 ${t1===t2}")
////        println("t1==t3 ${t1==t3} t1===t3 ${t1===t3}")
////        println("t2==t3 ${t2==t3} t2===t3 ${t2===t3}")
//
//		//数据类型转换，分为显式转换和隐式转换
//		t1.toLong()//显式转换
//		var tL = b + l//隐式转化为Long类型
////        println("tL 类型 ${tL.javaClass}")
//		//字符转义符合标准编程语言的\转义，有的未必能转义，那就用unicode编码，如\uff00
//		val ss = '\u00d2'
//		println(ss)
//
//		//字符串模版，就是在string中引用其他变量 ，使用$符号开始，如果出现了分隔，可用{}包裹
//		var sss = "$ss ${str.length}"
//
//		//数组Array
//		val arr = arrayOf(t1, ss, str)//不同于java，kotlin的数组，元素可以不是同一个类型
//		val arr2 = arrayOfNulls<Int>(2)//声明一个Int的数组，就不能添加其他类型了
//		arr2[0] = 3;arr2[1] = 9 //在kotlin中，一般不用;，但是一行代码写多个语句，可以用;分隔。
//		//这种初始化数组的方式，声明数组长度，并且通过表达式，初始化每个元素的值
//		val arr3 = Array(5) { i -> i * 2 }//lambda表达式，index的参数写为i，所有lambda的参数，不写则默认为it，如下
//		val arr33 = Array(5) { it * 2 }
//
//	}
//
//	//运算符，逻辑运算符号 && || ！ 与或非
//	fun testK2() {
//		if (bool && 3 > 2 || str.length > 0) {
//			println()
//		}
//		//位运算符，对应与Java中，短路与 & ，短路或 | ，短路异或^，取反~，左移<<，右移>>,有符号右移 >>>
//		//只能作用于Int Long
//		val sn = 0b0000_0000_0001_0001//二进制，对应十进制位17,这种写法，默认就是Long类型了，所以下面的位运算，实际位数是64位，注意高位补全
//		println("sn= $sn")
//		sn.shl(1)//左移1位，则为 34
//		println("sn.shl(1)=${sn.shl(1)}")
//		sn.shr(1)//有符号右移1位
//		println("sn.shr(1)=${sn.shr(1)}")
//		sn.ushr(1)//无符号右移1位
//		println("sn.ushr(1)=${sn.ushr(1)}")
//		sn.and(10)//与10进行与运算 10的二进制位0000 0000 0000 1010 ，与sn二进制数值相对齐，运算，都是1的才为1，其余位0，
//		println("sn.and(10)=${sn.and(10)}")
//		sn.or(10)//or运算，对齐运算位，双方有一个为1，则为1
//		println("sn.or(10)=${sn.or(10)}")
//		sn.xor(10)
//		println("sn.xor(10)=${sn.xor(10)}")
//		sn.inv()
//		println("sn.inv=${sn.inv()}")
//	}
///*
//位运算,原码、反码，
//1、补码：
//    十进制的负数转为二进制，步骤：1、对应正数转为二进制，然后各个位置字节码（0或1）取相反数，然后在加上十进制的1，也就是二进制的0001。（这里示例的是8位表示法，Int位32位，Long64位，高位补全即可。）
//        例如：-10 先将10 转二进制0000 1010 然后取反1111 0101 然后加0001逢2进1
//        即得 -10的二进制为1111 0110
//2、按位与 &
//    两个二进制数，对应位运算，都是1的时候，运算位才为1 例如：
//    0010 十进制是2 与 0101 十进制是5 ，2&5 结果为 0 ，因为没有对应位同时为1的。
//3、按位或 |
//    两个数的二进制位只要有一个1 结果就取1，例如
//    0010 和 0101 2|5 结果为 0111 即7
//4、异或^
//    两个二进制数，对应位不同的时候，结果取1
//    例如，0010 和 0101 2^5 结果为7 后三位不同所以都是1，
//5、取反
//    二进制数的位上0变1，1变0 例如 0010 变为 1101 即 ～2 = 13
//6、左移
//    二进制数，向左移动指定位数，不足的补上0
//7、右移
//    二进制数，向右移动指定位数，无符号右移 不足补0，有符号右移是高位补1
//
//
//
// */
//}