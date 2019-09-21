package org.zhiwei.jetpack.binding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.zhiwei.jetpack.binding.bean.ObUser
import org.zhiwei.jetpack.binding.databinding.ItemLvBinding

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月04日 14:49
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 简单演示的RecyclerView的adapter
 */
class RAdapter : RecyclerView.Adapter<RAdapter.MyHolder>() {

	private var users: MutableList<ObUser> = arrayListOf()

	init {
		//初始化三个数据
		for (i in 0..2) {
			users.add(
				ObUser("LiLy$i", 20 + i, i % 2, "LiLy Jim are friends $i")
			)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemLvBinding.inflate(inflater)
		return MyHolder(binding)
	}

	//kotlin中，return的方式，可以简写
	override fun getItemCount() = users.size


	override fun onBindViewHolder(holder: MyHolder, position: Int) {
		//java 写法可以setVariable
		holder.binding.user = users[position]
		holder.binding.executePendingBindings()
	}

	//在构造函数中声明binding变量，这样上面的holder才能引用到，如果不加val/var，就引用不到，就需要在class的{}内写get函数
	class MyHolder(val binding: ItemLvBinding) : RecyclerView.ViewHolder(binding.root)
}