package org.zhiwei.jetpack.binding.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import kotlinx.android.synthetic.main.activity_common_use.*
import org.zhiwei.jetpack.binding.R
import org.zhiwei.jetpack.binding.adapter.LAdapter
import org.zhiwei.jetpack.binding.adapter.RAdapter
import org.zhiwei.jetpack.binding.bean.CommonUser
import org.zhiwei.jetpack.binding.bean.FieldUser
import org.zhiwei.jetpack.binding.bean.ObUser
import org.zhiwei.jetpack.binding.databinding.ActivityCommonUseBinding

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月09日 15:40
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * DataBinding的进阶用法的演示界面
 * 1、响应式user对象的演示
 * 2、list、recyclerView的演示使用
 * 3、kotlin中binding的写法的优势
 */
class CommonUseActivity : AppCompatActivity(), View.OnClickListener {


	//<editor-folder desc="成员变量代码块">

	//这是一种懒加载的初始化控件的方式
	private val tvCommon: AppCompatTextView by lazy { findViewById<AppCompatTextView>(R.id.tv_common_user_info) }
	//这是由于使用了apply plugin: 'kotlin-android-extensions'，会自动将xml中的id，关联为控件。（也可以在代码中直接用tv_field_user_info）
	private val tvField: AppCompatTextView by lazy { tv_field_user_info }
	//这是稍后初始化的方式
	private lateinit var tvObservable: AppCompatTextView


	private lateinit var user: CommonUser//普通user对象
	private lateinit var fuser: FieldUser//部分属性可变的user
	private lateinit var obuser: ObUser//observable的user

	//</editor-folder>


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//关联布局，binding对象
		val binding = DataBindingUtil.setContentView<ActivityCommonUseBinding>(
			this,
			R.layout.activity_common_use
		)
		//普通的类似java的方式，关联控件和注册事件
		binding.btnChangeCommon.setOnClickListener(this)
		binding.btnChangeField.setOnClickListener(this)
		binding.btnChangeOb.setOnClickListener(this)
		//初始化user、fuser、obuser的对象，并赋值到binding中去
		user = CommonUser("张三", 33, 1, "张家第三代庶支嫡传葵花宝典继承人")
		binding.user = user
		fuser = FieldUser(
			"李四",
			ObservableInt(44),
			0,
			ObservableField<String>("李四，据说不是李斯，李白第32世无名弟子的后人")
		)
//        fuser.desc.get()//在代码中要获取desc这样的observable的属性，就需要用get()，而且get()的是可null的返回，
//        在xml中则直接用user.desc即可，不需要写.get()。写了也无妨
		binding.fuser = fuser
		obuser = ObUser("王二", 22, 0, "你会看到，这个王二的性别，你是改不了的，因为内部val声明了不可变量")
		binding.ouser = obuser
		//lateinit延迟初始化
		tvObservable = tv_ob_user_info
		//checkbox的响应
		cb_common.setOnCheckedChangeListener { button, checked ->
			//设置变量
			binding.show = checked
		}
		//adapter设置
		binding.ladapter = LAdapter()
		binding.radapter = RAdapter()
	}

	//点击事件的处理
	override fun onClick(view: View?) {
		//kotlin中的when，类似于java中的switch case，用法更灵活
		when (view?.id) {
			R.id.btn_change_common -> {
				user.age = 10;user.desc = "张三的改变desc，普通user对象不会响应binding"
				Toast.makeText(this, "注意上面的common user的info信息不会变化", Toast.LENGTH_SHORT).show()
				//当然，如果你在此处,显式的代码设置text，会变化。但是这不是数据绑定，因为你每次改变都要setText才生效
//                tvCommon.text = user.toString()
			}
			R.id.btn_change_field -> {
				fuser.name = "李四/里斯"
//				fuser.age.set(20)
//				fuser.sex = 1
//				fuser.desc.set("虽然只有两个observable的field，但是整个user对象都会被刷新。")
			}
			R.id.btn_change_ob -> {
				obuser.name = "王二二"
//				obuser.age = 222;
//				obuser.desc = "王二的名字，年龄，描述都会响应binding的变化"
			}
			else -> {
				//do nothing
			}
		}
	}


}