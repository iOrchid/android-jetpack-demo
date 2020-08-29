package org.zhiwei.jetpack.binding.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.library.baseAdapters.BR

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月02日 20:04
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于DataBinding的数据对象类，user，这里在kotlin中，多个public的class可以放在一个kt文件中
 */

/**
 * 普通的数据类，这里用了kotlin的数据类 data class 也就是一个特殊的class而已。
 * val 表示不可变量，var 可变。这些在kotlin的代码演示中会有描述。这里不做深究。
 */
data class CommonUser(
	val name: String,//姓名
	var age: Int,//年龄
	var sex: Int,//性别 0 女 1 男
	var desc: String//描述
)

/**
 * 这是部分属性可以databinding响应的user类，注意observable的属性需要public权限，否则dataBinding则无法通过反射处理数据响应
 */
data class FieldUser(
	var name: String,
	var age: ObservableInt,//可响应的Int类型
	var sex: Int,
	var desc: ObservableField<String>//可响应的String类型。
)

/**
 * 继承dataBinding的baseObservable的user对象类
 * 这里是kotlin的写法，类似于java中，继承BaseObservable的对象类，
 * 需要响应变化的字段，就在对应变量的get函数上加 @Bindable 。然后set中notifyChange kotlin的写法，免去了java的getter setter的方式
 * 成员属性需要响应变化的，就在其set函数中，notify一下属性变化，那么set的时候，databinding就会感知到。
 */
class ObUser() : BaseObservable() {
	//kotlin中类的构造函数可以多个，有主次之分，且次级构造函数必须调用主构造函数，如这里的this()
	constructor(name: String, age: Int, sex: Int, desc: String) : this() {
		this.name = name
		this.age = age
		this.desc = desc
	}


	//这是单独在set上@bindable，name可以声明private
	var name: String = ""
		//kotlin的成员属性必须初始化（或者lateinit）
		set(value) {
			//BR.name表示通知name这个属性的变化。 notifyChange() 通知所有变化
			field = value
			notifyPropertyChanged(BR.name)
		}
		@Bindable
		get() = field

	//这是在整个变量上声明@bindable，所以必须是public的
	@Bindable
	var age = 18
		set(value) {
			field = value
			notifyPropertyChanged(BR.age)
		}
		get() = field

	val sex = 1

	var desc: String = ""
		set(value) {
			field = "$value\n set中多余的拼接"//描述
			notifyPropertyChanged(BR.desc)
		}
		@Bindable
		get() {
			return field
		}

	override fun toString(): String {
//		notifyChange()
		return "$name $age $sex $desc"
	}
}