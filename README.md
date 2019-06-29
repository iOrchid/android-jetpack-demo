![android-jetpack-demo](./images/jetpack-hero.png)

## 🔥Android-Jetpack-Demo

[![jetpack](https://img.shields.io/badge/志威-Jetpack-brightgreen.svg)](https://developer.android.com/jetpack/) [![apache2.0](https://img.shields.io/badge/license-apache2.0-brightgreen.svg)](./LICENSE) ![Gitter](https://img.shields.io/gitter/room/zhiwei1990/android-jetpack-demo.svg?style=flat-square) 
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21) [![Build Status](https://travis-ci.org/zhiwei1990/android-jetpack-demo.svg?branch=master)](https://travis-ci.org/zhiwei1990/android-jetpack-demo) [![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=zhiwei1990_android-jetpack-demo&metric=alert_status)](https://sonarcloud.io/dashboard?id=zhiwei1990_android-jetpack-demo) ![GitHub repo size](https://img.shields.io/github/repo-size/zhiwei1990/android-jetpack-demo.svg?style=flat-square) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zhiwei1990/android-jetpack-demo.svg?style=flat-square) ![GitHub Release Date](https://img.shields.io/github/release-date/zhiwei1990/android-jetpack-demo.svg?color=orange&style=flat-square) ![GitHub language count](https://img.shields.io/github/languages/count/zhiwei1990/android-jetpack-demo.svg) ![GitHub top language](https://img.shields.io/github/languages/top/zhiwei1990/android-jetpack-demo.svg?style=flat-square) [![version](https://img.shields.io/github/release/zhiwei1990/android-jetpack-demo.svg)](https://github.com/zhiwei1990/android-jetpack-demo/releases) 
![GitHub last commit](https://img.shields.io/github/last-commit/zhiwei1990/android-jetpack-demo.svg?style=flat-square) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/zhiwei1990/android-jetpack-demo.svg?style=flat-square) ![GitHub All Releases](https://img.shields.io/github/downloads/zhiwei1990/android-jetpack-demo/total.svg?style=flat-square) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/zhiwei1990/android-jetpack-demo.svg)](http://isitmaintained.com/project/zhiwei1990/android-jetpack-demo "Average time to resolve an issue") [![Percentage of issues still open](http://isitmaintained.com/badge/open/zhiwei1990/android-jetpack-demo.svg)](http://isitmaintained.com/project/zhiwei1990/android-jetpack-demo "Percentage of issues still open") ![GitHub search hit counter](https://img.shields.io/github/search/zhiwei1990/android-jetpack-demo/jetpack.svg?style=flat-square) [![HitCount](http://hits.dwyl.io/zhiwei1990/android-jetpack-demo.svg)](http://hits.dwyl.io/zhiwei1990/android-jetpack-demo)

[TOC]

####  一、项目简介

> **Android-Jetpack-Demo** 是一个简单的、集成当前Google主流Android技术的示例Demo，旨在帮助有需要的初级开发者，快速上手Android新技术、新架构。主要使用`livedata`、`lifecycle`、`paging`、`room`、`navigation`、`workmanager`、`rxjava`、`kotlin`、`dagger2`以及`viewmodel`的**MVVM**简单架构。
>
> 代码中都有详细的注释，主要操作演示**关注`logcat`的日志**输出

#### 二、更新日志

- v1.1.0（2019年6月27日）
  - 更新`AndroidStudio`以及`Gradle`编译版本
  - 更新`jetpack`及`kotlin`版本为当前最新
  - 压缩存档，以备模块化版本
  - `kotlin`初级语法的基础博文
- v1.0.0（2018年11月14日）
  - 实现`jetpack`组件的初级基础用法的演示
  - `dagger2`的基础用法演示
  - `kotlin`初级语法的整理注释
  - `databinding`的基础用法
  - `AAC`的初级使用

#### 三、Sample示例

[下载体验apk](https://raw.githubusercontent.com/zhiwei1990/android-jetpack-demo/master/apk/app-debug.apk)或者手机扫描二维码下载`Demo`的`apk`包

![扫描下载](./images/QR_Jetpack.png)

#### 四、项目组成[^1]

项目主要简单的演示一些`android`技术与框架库的入门使用，`RxJava`操作符、`Kotlin`基本语法，以及`JetPack`的组件的简单使用。

- `Android Architecture Components`
- `RxJava`
- `Kotlin`
- `Dagger2`
- **Jetpack**
  - `lifecycle`
  - `livedata`
  - `databinding`
  - `navigation`
  - `paging`
  - `room`
  - `workmanager`

#### 五、规划

- [x] 基础使用入门的代码演示
- [ ] 进阶技术&架构的演示
- [ ] 对应代码的详细博文
- [ ] 其他前沿技术链接

#### 六、示例内容(部分）

![databinding](./images/databinding.png)![lifecycle](./images/lifecycle.png)![logcat](./images/logcat.png)

- `Kotlin`语法及部分博文[Kotlin](https://www.jianshu.com/p/bdbe2ab6e9b2)

```kotlin
package org.zhiwei.jetpack.kotlin

/**
 * Kotlin基础语法（一），变量/常量/注释，基础数据类型
 * Author: zhiwei.
 * Github: https://github.com/zhiwei1990
 * Date: 2019/3/17,19:25.
 * You never know what you can do until you try !
 */

//<editor-folder desc="kotlin 变量/常量的 类中定义">

/*
Kotlin中的单行、多行、文档注释与Java的注释无区别。
唯一不同的是在kotlin的注释中，多行/文档 注释内，可以且套多行注释。
/*
这个就是多行注释中的多行注释
 */
 * 要是在Java中就会报错了。但是他们都能在多行注释中嵌套单行注释
 */
class KotlinSyntax01 {

    //类属性的常量/变量的声明及初始化，大体和顶级toplevel中的声明，差不多。
    var cA: Int = 10
    var cB = 11
    val cC = 3.1415926
    lateinit var cS: String//不可空
    private var cE: String? = null//声明可空，则需要?
    //kotlin中class、var、function等默认修饰符不写，就是public，另有 protected、internal、private，类似于Java
    //使用by lazy延迟初始化，则必须是val修饰，不可变，引用类型，基础类型不能延迟初始化。涉及到栈 堆
    val list: Array<String> by lazy { arrayOf("cdd", "ddd", "dddd", "ddwecd") }

    //init 为kotlin中class文件实例化必然调用的函数，不论构造函数有几个，都会调用init的
    init {
        cE = "null string"
    }

    //伴生对象，每个类都会有自己个一个伴生对象，不论它实例化多少个对象，这个object对当前类可理解为单例，静态
    companion object {
        const val PI = 3.1415926
    }
}

//</editor-folder>


//<editor-folder desc="kotlin 变量/常量的 Top Level中定义,也就是kt文件根结点定义">


//以下的变量/常量声明，是在kt文件的顶级节点下，根据public，private等修饰权限，对于整个module是有效的。所以
//这里的topLevel中定义的public的变量，在其他kotlin文件中，顶级位置，就不能再次定义同名变量，会冲突。
var a: Int = 7//变量声明变量的标准格式 var name：type = xxx 其中xxx表示直接赋值或者实例化对象
var b = 6//自动推到数据类型,如 6 推断为Int，而 6.0则推断为Double

//kotlin中一切皆对象形式，没有Java中的那种基础数据类型。变量的声明必须初始化，要么null，要么延迟初始化，而且null的对象和非空对象，声明也不一样。
var s1: String? = null
var s2: String? = "abc"//类型后加个?表示这个变量可以为null，见36行
var s3: String = "abc"//上两个还可以s1=null，s2=null，但是s3是不可以的，因为类型是String，而不是String?
lateinit var d: String//如果想要延迟初始化，可以用lateinit关键字修饰,但是?与lateinit不能共用
val str: String by lazy { "sss val str" }//lazy 为高阶函数，延迟初始化，by连接词，但是不能与lateinit共用，且必须是val类型。
val ee: Int = 900//val表示不可变量

fun Test() {
    d = "dddf"//延迟初始化的
//        ee = 3//不可变量，常量，都是不能修改的。
//        s3 = null//这里s3=null的话，编译就会报错
    //局部变量，不可变量的声明
    val sd = "ss 20010"
    var ssa = 10080

}

//Java中不允许在类外定义常量，变量之类的，但是kotlin中可以。Java中一个.java文件只能又一个public的类，但是kotlin中可以有多个。
val lenggth: Int = 100//val 表示不可变量，可以为顶级，类成员，局部不变量。
const val PI: Double =
    3.1415926//const 修饰的val，表示常量，必须在top level或者companion object中声明，不能作为普通的类成员常量。其中单例类可以理解为伴生类就是自身的一个kotlin的类
//by lazy 延迟实例化，一个对象只会执行依次初始化
val ccc: String by lazy { "fdf" }

//object  可以理解为一个单例类
object Single {
    const val sP = 3.1415926
}
//</editor-folder>
```

更多详情，请下载代码，内有详细注释，**鉴于本人才学有限，若有不足之处，请大神不吝赐教**。

#### 七、关于作者 [![jianshu](./images/jianshu.png)](https://www.jianshu.com/u/72294e6848c0)[![github](./images/github.png)](https://github.com/zhiwei1990)[![csdn](./images/csdn.png)](https://blog.csdn.net/zhiwei9001)

> 作者本人只是一个，就职于某`知名`(@_@ 有名字的)互联网公司的，技术小白一枚，对于编程有着某种兴趣和热爱，然技术确实渣渣，好读书、不求甚解~~

`人之为学有难易乎、为之，则难者亦易矣；不为，则易者亦难矣。`

倘若本项目对你有一丝丝的帮助和价值，烦请给个`star`,或者有什么好的建议或意见，也可以发个`issues`，谢谢！:happy:

[![Github stars](https://img.shields.io/github/stars/zhiwei1990/android-jetpack-demo.svg?style=social&label=star)](https://github.com/zhiwei1990/android-jetpack-demo)[![Github followers](https://img.shields.io/github/followers/zhiwei1990.svg?style=social&label=follow)](https://github.com/zhiwei1990/android-jetpack-demo)[![Github issues](https://img.shields.io/github/issues/zhiwei1990/android-jetpack-demo.svg?style=social&label=issues)](https://github.com/zhiwei1990/android-jetpack-demo)

- **License**

```markdown
Copyright 2019 zhiwei.org

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



[^1]: 作者项目开发环境为AndroidStudio `3.4.1`、`gradle 5.1.1`、`Jdk8`、`Windows10`

