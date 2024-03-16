package org.zhiwei.kotlin.concepts

/**
 * kotlin语法概念基础 之 数据类型
 * todo 小tips：在IDE中可以通过 注释//region 与 //endregion实现代码区域的折叠
 */
class SyntaxTypes {

    //0. Kotlin中一切皆对象，所有的数据类型都是一个类，对应Java中的基础类型的int，double，float等也都是装箱类型的Int，Double，Float，可实现自动拆装箱。

    // region 1. kotlin中基本类型有数字（包括无符号数字）、布尔、字符、字符串、数组

    //1.1 数字类型Number，包括整数、浮点
    var byte: Byte = 8 //一个Byte字节，内存占用 8个bit，取值范围 [-128,127]
    var short: Short = 16 //一个Short数值，为两个字节，内存占用16个bit，取值范围[-2^(16),2^(16)-1]
    var intNumber: Int = 32 //一个Int数值，为四个字节，内存占用32个bit，取值范围[-2^(31),2^(31)-1]
    var long: Long = 64 //一个Long数值，为八个字节，内存占用64个bit，取值范围[-2^(63),2^(63)-1]

    // 1.2 在kotlin中声明变量如果未显式的指定数值类型，编译器会自动推导数据类型。如果是整数，数值范围在Int之内则默认为Int，超过Int，则默认为Long
    // 如有需要，则可以如上，显式的身影指定为Long类型，或者使用l或L在数字后面，标记为Long类型。但是一般写L，因为小写的l容易与数字1混淆。
    var longNum = 666L

    /* 1.3 在kotlin中 有Float（32bit）表示单精度浮点数，有效精度小数点在6-7位十进制，
     Double（64bit）表示双精度浮点数；有效精度小数点在15-16位十进制；
     声明Float可以显示，也可以数字后加f或F
     */
    var pi = 3.14//默认Double类型，Double类型类型必须有小数点,Float类型则需要f/F后缀
    val PI: Double = 3.1415926
    var ff: Float = 2.33F
    var floatNO =
        2.7182818284f//todo 标记为Float类型，但是编译器IDE会提示超出精度范围，Float的精度小数点后7位；所以这个数值实际只有2.7182818284f

    //虽然Float和Double都是浮点数，但是不同于其他编程语言，Double范围大于Float也不会自动接收Float类型，需要手动转化
    private fun someDouble(some: Double) {}

    private fun testFloatDouble() {
//        someDouble(ff)//给Double类型入参的函数，传入Float是不可编译的
    }

    //1.4 在Kotlin中不支持8进制的表达写法，二进制，十进制，十六进制，有字面常量写法，Long可以加L后缀，Float加F后缀，科学数字可以e表达，长数值可以_分隔
    var ten = 10 //十进制写法
    var two = 0b10//二进制写法
    var sixteen = 0xFF100//十六进制写法

    var aLongNumber = 100_000_000_01//不同进制，不同类型的数值，都可以分隔写法
    var aScienceNumber = 2.86e10//e的10次方

    //1.5 Jvm平台数值特性。Kotlin语言可用于web，jvm和native，在JVM平台上就要考虑其特性；Kotlin的类型都有可空和非空
    /*
    Int与Int?就属于不同类型，前者不可空，后者可为null，因此在jvm平台上取值就需要注意。java基本数据类型会有默认值0或0.0，而不会null
     */
    private fun testInt() {
        //声明数据类型，在Byte取值范围内，Jvm会优化内存，对应与Java中Integer等拆装箱类型，可空的对象引用实际上为同一个
        val a: Int = 100
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        //声明数据类型，超出Byte取值范围，Jvm没有优化，可空的对象引用则不同
        val b: Int = 10000
        val boxedB: Int? = b
        val anotherBoxedB: Int? = b
        //kotlin中==判断类似于equals，判断值相等与否，===则判断内存引用是否同一对象地址。
        println(boxedA === anotherBoxedA) // true
        println(boxedB === anotherBoxedB) // false
        //todo 注意这里就是简单演示，生命的boxedA，boxedB等是Int?可空类型，才会有这个现象，若是Int非空类型，则两种对比都是true
    }

    private fun testConvert() {
        //Kotlin中不会自动转化数据类型，即使Byte比Int小，也是不同类型，不同直接赋值；todo 但是在表达式中，编译器可以推导
        var aByte: Byte = 88;
        var bInt = 100//todo 在Kotlin中一般不用;分号，但是如果同一行多个语句，则可以用;来分隔。
//        bInt = aByte//这是不行的，即使Int类型大于Byte也不行，必须显示转化，或者在表达式可推导中；
        bInt = aByte.toInt()//kotlin提供了toInt，toDouble，toFloat等
        bInt = 99 + aByte//这样的表达式中，也可以，因为99默认是Int，所以推导出结果是Int，则aByte不需要转化类型
    }

    private fun testOperate() {
        //Kotlin数值运算，整数之间的加减乘除，得到的也是整数，尤其是除法，会丢掉小数点，是丢掉，不是四舍五入。
        val result = 5 / 2 //得到的是2，而不是2.5
        val div = 9 / 4f //这样会得到小数点，所以根据需要，可以显式的Float或者Double，或者toFloat/toDouble等转化
        //(5/2).toFloat()结果是2F，而不是2.5，5/2f或者5f/2 才会是2.5F
    }

    //1.6 Kotlin中位运算，使用中缀符号 且只能作用于Int或Long类型 左移shl,右移shr,无符号右移ushr,与and,或or,异或xor,非inv 等

    //1.7 浮点数的比较 Float和Double，在kotlin中如果在作为引用类型的值的时候，其对比有一些特殊性质。Double.NaN
    val fnan = Float.NaN //NaN表示Not a Number，是个常量值
    //endregion 1.7.1 特性认为 Float.NaN或Double.NaN等与其自身。且比其范围内的正无穷大∞还要大。认为-0.0 小于 0.0

    //2. 布尔 Boolean，有对应Boolean?可null的类型，
    private fun testBoolean() {
        var nullBoolean: Boolean? = false//演示代码
        if (nullBoolean == true) {
            println("判断可null的Boolean时候，要用==true")
        }
    }
    //Boolean支持逻辑判断，逻辑与 && ，逻辑或 || ，逻辑非 ! ，且是短路的惰性的，意思就是，多个逻辑条件一连串的时候，前面判断能确定之后，后面就没必要判断了；
    //例如 if(a > 30 && b <20){} //此时如果a =1 则false，就不需要再运算b的值了，没意义。

    //3. Char字符，支持单个字符，Unicode码和转义符号
    val c = 'c';
    val c2 = '\t';
    val c3 = '\u3393';
    val c2Int = 'c'.digitToInt()//就将字符转化为对应的编码的数字表示

    //region 4. String字符串
    //4.1 字符串String一旦创建，就是final的，常量，不可修改；使用""双引号；
    // 可以for循环遍历其内部字符；可以+号操作拼接，toUppercase等，但是原字符不变，得到新的字符而已。
    //双引号内可以有''单引号，可以有转义符号\t等，可以多行字符串"""  """三个双引号包裹，
    // 则其内部转义符号失效，然后可以有.trimMargin()等格式化的操作
    // 可以内部引用变量、常量、函数等 使用$符号
    val aString = "你好呀，欢迎学习Kotlin语言，这里面拼接一个类名${String::class.simpleName}"

    //endregion

    //5. 数组 Array，Kotlin数据类型都是对象的，所以Jvm的基础类型，建议使用对应优化后的数组类，如IntArray等
    val aaa = intArrayOf(1, 2, 2)

    //其他场景尽可能使用集合，因为数组array的读取方便，增删则耗费内存和时间。
    val arrayWithNullElements = arrayOfNulls<String?>(3)//如此则创建了3个null元素的String数组

    //数组内可嵌套数组，如此形成二维，三维，多维数组；
    val manyArray = arrayOf(3, arrayOf("ddd"), arrayOf(9, "wwe", arrayOf('d')))
    val a0 = aaa[0]//读取数组元素，使用index下标

    //vararg关键字标记，传入的参数可能是1或多个，这样的参数，必须是函数签名的自后一个声明的参数
    private fun testArray(level: Int, vararg names: String) {//names声明在level之前就不行了，
        //这样的可变参数，kotlin中理解为数组，可遍历
        for (name in names) {

        }
    }

    private fun testArrayInput() {
        val sss = arrayOf("dd", "cc")
        testArray(0, *sss)//接收可变参数的函数，传入数组的时候，使用 * 星号将数组展开平铺为多元素

        //todo 数组的对比，使用contentEquals函数或者中缀函数，不要用==号或!=来判断，因为后者是判断数组引用点，而非内部值
        val aa1 = arrayOf(1, 2, 3)
        val aa2 = arrayOf(1, 2, 3)
        aa1 == aa2 //false
        aa1.contentEquals(aa2) //true
        aa1 contentEquals aa2 //true 中缀函数形式，中缀函数 就是作用域两个对象之间的操作符 比如 in is 等

        //todo 数组提供了一些函数，sum求和，shuffle 重洗元素 等 toSet，toList等转化为集合；
    }


    // 6. 无符号类型，不包含负数的数据类型，UByte，UShort，UInt，ULong,没有无符号浮点数；
    val uaa = 1U//默认UInt
    val ubb: UByte = 8u//都必须是u或者U结尾
    val uLL = 999UL//ULong类型需要UL
    //使用场景，比如用做一些颜色值的数值限定 0xFF0088UL

    //7. 类型检测，用is 或!is 判断是，不是，IDE编译器会根据判断结果，自动推导后续使用
    private fun testTypeCheck() {
        val ss: Any = ""//演示代码
        if (ss is String) {
            ss.length//这里就推导出String类型，就可以使用String类型的属性
        }
        // as 转化类型 as? 不确定的转化
        ss as Int
        ss.dec()//这里就是作为了Int类型，可以调用Int的属性函数操作了
    }
}