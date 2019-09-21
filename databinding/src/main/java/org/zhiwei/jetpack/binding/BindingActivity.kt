package org.zhiwei.jetpack.binding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_binding.*
import org.zhiwei.jetpack.binding.activity.AdvancedUseActivity
import org.zhiwei.jetpack.binding.activity.BaseUseActivity
import org.zhiwei.jetpack.binding.activity.CommonUseActivity

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 19:55
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * DataBinding的演示界面
 */
class BindingActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_binding)
		//设置注意事项
		setNotice()
	}

	/**
	 * 跳转到 DataBinding的基础用法演示界面
	 * 这里是响应xml中Button的点击事件，使用onClick配置属性的方式
	 */
	fun baseUse(v: View) {
		//kotlin的语法方式，没有new关键字，而且对应的java class类文件为::class.java方式表示。.class表示自身的kotlin类
		startActivity(Intent(this, BaseUseActivity::class.java))
	}

	/**
	 * 跳转到DataBinding的进阶用法
	 */
	fun commonUse(v: View) {
		//如果this表示不明晰的时候，可以@指定类名，即可明确对象指引
		startActivity(Intent(this@BindingActivity, CommonUseActivity::class.java))
	}

	/**
	 * 跳转到DataBinding的高级用法
	 */
	fun advancedUse(v: View) {
		startActivity(Intent(this, AdvancedUseActivity::class.java))
	}

	/**
	 * 使用DataBinding的注意事项点
	 */
	private fun setNotice() {
		//这里使用的是kotlin 在gradle中apply plugin: 'kotlin-android'
		//apply plugin: 'kotlin-android-extensions'后，就可以直接使用当前activity/fragment关联后的xml布局中的控件，直接使用其id即可表示该控件
		//kotlin中好多set/get方法都直接简化为属性赋值的方式。如此处setText/getText就变成了.text
		tv_notice_binding.text = """
DataBinding使用注意事项点:
1、变量声明variable后，需要在binding中赋值
2、xml中用到的类，需要import，如控制View的显示隐藏，需要导入View类
3、int类型对应为Integer，且在@{}用于text时候，需要转为String
4、在@{}中可以使用``反引号作为String，注意非英文编码可能出错，甚至会编译报错
5、资源引用使用@string、@color、@drawable等，IDE不会补全，注意拼写无误
6、使用default设置默认值，用于预览编辑
7、null判空配合??使用，默认值可以``反引号、资源引用或者变量、静态函数值
8、String的format使用也是可以``、@string或者其他变量等
9、静态函数，java的写法和kotlin的写法不同，实质都是jvm虚拟机的静态函数化
10、函数调用的多种写法，无参、有参、context，以及静态函数调用（针对对象，而非类）。
		""".trimIndent()
	}

}