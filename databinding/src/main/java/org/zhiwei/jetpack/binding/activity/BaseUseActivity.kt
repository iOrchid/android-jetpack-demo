package org.zhiwei.jetpack.binding.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.zhiwei.jetpack.binding.R
import org.zhiwei.jetpack.binding.databinding.ActivityBaseUseBinding
import org.zhiwei.jetpack.binding.tools.BindHelp

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月09日 15:39
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * dataBinding的基础用法演示界面
 */
class BaseUseActivity : AppCompatActivity() {

	/*
	DataBinding使用步骤简要：
	  1、使用最新版的AndroidStudio，至少AS3.0以上吧。
	  2、在项目module下的build.gradle的android闭包下，配置 databinding{enabled=true}
	  3、对于布局的xml文件，将原有的正常布局，外面用<layout></layout>包裹作为跟节点。<data></data>节点下存放用于xml布局的一些变量，工具类之类的
	  4、在代码无误的情况下，build一下module或整个project。然后就可以在代码中使用binding方式coding了。

	 */


	//<editor-folder desc="成员变量代码块">

	//</editor-folder>


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//Activity使用DataBindingUtil.setContentView的方式关联xml布局文件，替代原有的setContentView方式。其中`ActivityBaseUseBinding`为databinding根据xml自动生成的类文件
		//命名方式为layout的name+Binding。(可以自定义名称，在<data>标签内的className属性)
		val binding =
			DataBindingUtil.setContentView<ActivityBaseUseBinding>(this, R.layout.activity_base_use)
		//设置在xml中声明的变量值
		binding.age = 10
		binding.isStudent = true
		binding.name = "BindName"
		binding.title = "BD标题"
		//list,map,这里的ages和map，赋值给xml中的变量
		val ages = listOf("20", "18", "19")
		val map = mapOf(19 to "Lily", 21 to "Jim", 20 to "Aili")
		binding.ages = ages
		binding.map = map
		//静态点击的helper
		binding.helper = BindHelp
	}


}