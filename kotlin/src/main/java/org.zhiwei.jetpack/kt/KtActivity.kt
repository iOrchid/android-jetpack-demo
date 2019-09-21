package org.zhiwei.jetpack.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 19:13
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Kotlin的用法演示入口主界面
 * 1、base包内为基础语法代码示例，包括变量/常量，函数的定义、类/接口/抽象类以及继承，数据类，object类等，建议查看顺序 topLevel、KtClazz; old包内为之前的学习笔记，稍微有点凌乱，可以不看
 * 2、common内为进阶语法的演示，包括属性扩展、函数扩展、密封类、泛型、枚举，对象表达式和委托等
 * 3、advanced高级语法的代码演示，包括高阶函数、协程/纤程、常用Api的释义等
 */
class KtActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_kotlin)
	}

}