![android-jetpack-demo](./images/jetpack-hero.png)

## 🔥Android-Jetpack-Demo

[![jetpack](https://img.shields.io/badge/android-jetpack-brightgreen.svg)](https://developer.android.com/jetpack/)[![apache2.0](https://img.shields.io/badge/license-apache2.0-brightgreen.svg)](./LICENSE)

[TOC]

####  关于

> **Android-Jetpack-Demo** 是一个简单的、集成当前Google主流Android技术的示例Demo，旨在帮助又需要的初级开发者，快速上手Android新技术、新架构。主要使用`livedata`、`lifecycle`、`paging`、`room`、`navigation`、`workmanager`、`rxjava`、`kotlin`、`dagger2`以及`viewmodel`的**MVVM**简单架构
> 代码中都有详细的注释，主要操作演示**关注`logcat`的日志**输出



#### 关注及反馈

[![Github stars](https://img.shields.io/github/stars/zhiwei1990/android-jetpack-demo.svg?style=social&label=star)](https://github.com/zhiwei1990/android-jetpack-demo)[![Github followers](https://img.shields.io/github/followers/zhiwei1990.svg?style=social&label=follow)](https://github.com/zhiwei1990/android-jetpack-demo)[![Github issues](https://img.shields.io/github/issues/zhiwei1990/android-jetpack-demo.svg?style=social&label=issues)](https://github.com/zhiwei1990/android-jetpack-demo)



#### 项目组成[^1]

项目主要简单的演示一些android技术与框架库的入门使用，RxJava操作符、Kotlin基本语法，以及JetPack的组件的简单使用。

- Android Architecture Components
- RxJava
- Kotlin
- Dagger2
- Jetpack
  - lifecycle
  - livedata
  - databinding
  - navigation
  - paging
  - room
  - workmanager

#### 规划

- [x] 基础使用入门的代码演示
- [ ] 进阶技术&架构的演示
- [ ] 对应代码的详细博文



#### 示例内容(部分）

![databinding](./images/databinding.png)![lifecycle](./images/lifecycle.png)![logcat](./images/logcat.png)

```kotlin
class KotlinActivity : AppCompatActivity() {

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
    var cc: Char = '9'//不能直接写9 需要单引号，不同于java。只能是单个字符

    //可用下划线分割长的数字
    var millon: Int = 1_242_143_253


    //fun 关键字，定义函数
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //多参数
        getSum(1, 2, 3, 4, 9)
        //匿名函数定义
        sumLambda(1, 32)
		//...
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
}
```

更多详情，请下载代码，内有详细注释，**鉴于本人才学有限，若有不足之处，请大神不吝赐教**。

#### 关于作者 [![jianshu](./images/jianshu.png)](https://www.jianshu.com/u/72294e6848c0)[![github](./images/github.png)](https://github.com/zhiwei1990)[![csdn](./images/csdn.png)](https://blog.csdn.net/zhiwei9001)

> 作者本人只是一个，就职于某`知名`(@_@ 有名字的)互联网公司的，技术小白一枚，对于编程有着某种兴趣和热爱，然技术确实渣渣，好读书、不求甚解~~

`人之为学有难易乎、为之，则难者亦易矣；不为，则易者亦难矣。`



[^1]: 项目开发环境为AndroidStudio `3.2.1`、Jdk10、Windows10