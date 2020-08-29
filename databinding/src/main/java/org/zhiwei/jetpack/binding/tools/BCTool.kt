package org.zhiwei.jetpack.binding.tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import org.zhiwei.jetpack.binding.R

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月04日 11:53
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Binding高级用法中，辅助工具类，演示@BindingConversion，@bindadapter等
 */
object BCTool {
	/**
	 * 兼容适配view的background的str转color属性，这里函数名 可以随意，而且不需要其他地方显式的调用。
	 * 只需要在此 ，静态函数的声明即可。（java的写法就是public static，这里不写java版的了。）
	 */
	@JvmStatic
	@BindingConversion
	fun converStr2Color(str: String): Drawable {
		return when (str) {
			"red" -> {
				ColorDrawable(Color.RED)
			}
			"blue" -> ColorDrawable(Color.BLUE)
			else -> {
				ColorDrawable(Color.YELLOW)
			}
		}
	}

	/**
	 * 用于appCompatImageView的自定义属性，bind:imgSrc，命名空间bind:可以省略，也就是写作 imgSrc亦可。可以用于加载url的图片
	 * 函数名也是随意，主要是value的声明，就是新加的属性名了，可以多个属性同用，并配置是否必须一起作用
	 * 函数名随意，方法签名才重要，匹配对象控件，以及属性参数。这里还可以添加old 参数，获取修改新参数 之前对应的值。
	 * todo 加载网络图片，需要网络权限，别忘了
	 */
	@JvmStatic
	@BindingAdapter(value = ["bind:imgSrc"], requireAll = false)
	fun urlImageSrc(view: AppCompatImageView, /*old: String?, */url: String) {
		Glide.with(view)
			.load(url)
			.placeholder(R.drawable.img_banner)
			.centerInside()
			.into(view)
	}

	/**
	 * 这个是 databinding高级用法中，配合演示swipeRefreshLayout的刷新状态的感知
	 * 第一步：单向的，数据变化，刷新UI
	 */
	@JvmStatic
	@BindingAdapter("sfl_refreshing", requireAll = false)
	fun setSwipeRefreshing(view: SwipeRefreshLayout, oldValue: Boolean, newValue: Boolean) {
		//判断是否是新的值，避免陷入死循环
		if (oldValue != newValue)
			view.isRefreshing = newValue
	}

	/**
	 * 第二步：ui的状态，反向绑定给数据变化
	 */
	@JvmStatic
	@BindingAdapter("sfl_refreshingAttrChanged", requireAll = false)
	fun setRefreshCallback(view: SwipeRefreshLayout, listener: InverseBindingListener?) {

		listener ?: return
		view.setOnRefreshListener {
			//由ui层的刷新状态变化，反向通知数据层的变化
			listener.onChange()
		}
	}

	/**
	 * 反向绑定的实现，将UI的变化，回调给bindingListener，listener就会onChange，通知数据变化
	 * 注意这里的attr和event，是跟上面两步配合一致才有效
	 */
	@JvmStatic
	@InverseBindingAdapter(attribute = "sfl_refreshing", event = "sfl_refreshingAttrChanged")
	fun isSwipeRefreshing(view: SwipeRefreshLayout): Boolean {
		return view.isRefreshing
	}

}