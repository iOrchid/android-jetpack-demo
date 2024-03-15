package org.zhiwei.kotlin.basic

//region Kotlin语言的基础语法

//0. 前言：所有编程语言的变量，命名，路径之类的，最好都是纯英文字母和标点符号，切记，可避免日后编程输入的一些问题

//1. 类似于Java语言，包声明处于文件顶部（可以在版权声明注释之后），如上package org.zhiwei.kotlin.basic 的包名；一般用唯一域名倒置方式区分；

//2. 程序入口点main函数，类似于Java ,其中args的形参部分可以不写
fun main(args: Array<String>) {
    //3. 输出打印信息到控制台
    print("输出打印文字，该函数不换行")
    println("Hello world")//换行
}

//4. 函数: 定义函数使用关键字fun 格式：fun 函数名(参数名:参数类型,等等):返回参数类型{ .... return xxx } ;
// 在函数定义处方法（或者叫做函数）入参的参数名称，成为形参，调用该函数的时候，传入的参数具体，叫做实参。
private fun sum(a: Int, b: Int): Int {
    return a + b
}

//4.1 kotlin支持表达式和语法推导，如上的函数可简写为： fun sum(a: Int, b: Int) = a + b
//4.2 若返回类型为Unit，空，类似于Java中的void，则可以省略不写
private fun printSum(a: Int, b: Int) {//此处后面标准格式为：Unit的返回类型
    //在String字符串中，可使用$符号调用函数或者变量，此时上面的sum函数，传入3，5两个参数就是实参，函数定义的a，b叫做形参
    println("打印3+5的计算和： ${sum(3, 5)}")
}

//5. 变量 使用var 定义可变量； val 定义局部或区域内的不可变量，其只能赋值一次；变量的声明格式 var/val 变量名:变量类型 = 值 。可省略类型，如果能够自动推导的。或者可以延迟初始化。
var name: String = "Kotlin"//可变量，name 可以被多次赋值
private val platform: String = "Android" //不可变量，在作用域内被一次赋值之后，不可再改
const val PI: Double = 3.14 //常量，类似于Java的static final  其存放在Jvm的静态常量区，调用使用类名. 或者直接导入。

//6. 不同于Java文件，在Kotlin的kt文件中，可以直接在文件内声明函数，变量，常量，类等定义，而不是Java那种必须在某个类中定义。且一个kt文件可以定义多个public的类。
//6.1 kotlin中定义函数，变量，类，接口等，如果在文件层级，权限修饰符默认是public，则会省略不写。类与函数默认是final的。
//7. 类定义格式class 类名(参数):父类,接口 。类定义可继承父类，实现接口，如果最简化，则如下，class 类名 即可。
public class QuickSyntax //默认前面有修饰符public的，省略不写

interface Shape

//7.1 相对完整的类定义的格式，实现接口
private class Rectangle(var height: Double, var length: Double) : Shape {
    //类内部定义的属性，
    var perimeter = (height + length) * 2
}

//8. 注释，类似于Java语言，分为单行、多行，文档注释；
//单行注释，或者行末注释
/*
多行注释
也叫 块注释
 */
/**
 * 文档注释一般写在函数，类，接口上，用于生成函数文档，对外提供
 */
//但是kotlin中可以块注释内嵌套块注释

//9. 字符串模版
private val strTemplate =
    "这是一个字符串string，里面要引用变量$name 所以就叫字符串模版，\n 注意这里会换行，因为有转义符号 '$' 符号引变量也可用{}包裹。这个string声明就没有写类型，因为可推导出来"
private val stringLongText =
    """这样使用三个双引号的字符串，$name 可以在内部使用"双引号"，或者其他符号\n 不会被转义"""

//10. 条件表达式 if ， if...else... , if...else if ... else ... ,for 循环，while ，do..while循环，when表达式；Kotlin中没有switch..case，因为when更强大；
fun conditionSyntax(number: Int) {
    //10.1 if，if..else,表达式
    if (number > 10) {
        println("数字大于10")
    }
    if (number > 20) {
        println("数字大于20")
    } else if (number < 15) {
        println("数字小15")
    } else {
        println("数字是$number")
    }
    //for循环 ,关键字 in 用于判断，是否包含，在其中，此处是Range类型，kotling的list，集合等都有in的用法，也就是contains
    for (i in 0..number) {
        println("数字$i，递增循环, 0..$number 的取值是左闭右闭的闭区间取值")
    }
    //减小的循环方式
    for (i in number downTo 0) {
        println("数字$i ,递减循环")
    }
    for (i in 0..number step 2) {
        println("数字$i ，2步一阶的递增循环")
    }
    for (i in number downTo 0 step 3) {
        println("数字$i ，3步一阶的递减循环")
    }
    for (i in 0 until number) {
        println("数字$i ，until 构造的数据是左闭右开的区间，")
    }
    for (i in IntRange(0, number)) {
        println("数字$i ，IntRange 构造的数据是左闭右闭的区间，")
    }
    //while do..while循环
    while (number > 100) {
        print("都是大于100的循环while")
    }
    do {
        print("do while中循环")
    } while (number < 0)
}

//10.2 测试when表达式，在kotlin中没有Java中的基础类型，都是对象类型。Int，Double都是包装类，所有类的基类是Any；
//同时注意，kotlin中 可空 null的与非空的，是不同的类型，Any与Any? 是两个类型，String与String?也是严格区分,非空类型不可赋值null。
fun testWhen(data: Any?) {
    when (data) {
        is String -> {
            println("data 是string，且非空的String类型")
        }

        is Number? -> print("data是Number？类型，可能为null")
        else -> {
            println("啥也不说了呗")
        }
    }
    //when表达式可以随意判断多种条件类型
    when {
        data == null -> println("是null")
        data is String -> print("是string类型")
        """English and 中文字符串""".contains("ab") -> print("随心的判断")
        else -> print("其他呗")
    }
}

//11. 集合 list array map collection等

fun testCollection() {
    val items = listOf("apple", "banana", "unknown fruit")
    for (item in items) {
        println("list集合的for循环")
    }
    items.forEach {
        println("list集合的forEach循环 ,每个元素默认是$it it来指代，也可以显示自定义名字如 ii 加上 -> 即可")
    }
    items.forEachIndexed { index, s ->
        println("list集合的forEachIndexed循环 ,$index $s")
    }
    items.filter { it.length > 3 }
        .sortedBy { it }
        .map { it.uppercase() }
        .forEach { str ->
            println("list集合的链式转化后的foreach $str")
        }
}

//12. 空类型的判断

/**
 * 这里的string解析成int，可能解析失败，可能不是int数字，所以返回可能是null，如果不考虑抛异常的场景的话
 */
private fun parseInt(numberStr:String):Int?{
    return numberStr.toIntOrNull()
}

fun testParseIntNull(){
    val x= parseInt("78")
    val y = parseInt("s35")
//   val cc =  x+3 //这里直接当作数字运算，IDE是报错的，因为Int?可能null，不能参与预算
    if (x!=null&&y!=null){
        //这里因为if条件里判断了！=null，非空判断，IDE就会自动推导出类型，以及非空
        x+y
    }
    //同理，string，或者其他类型的带?的可null类型，也可以在判空之后，IDE会自动推导类型和判空处理。
    val yy = y?:999//这里使用了?:运算符，意思判断若null则取值:后面,与Java中三目运算符不一样。
    val xx = x!!+8 //这里使用了!!双叹号来强行告诉IDE，数据对象非空，此时就是代码自己处理逻辑了，null与否责任自负。
}

//endregion