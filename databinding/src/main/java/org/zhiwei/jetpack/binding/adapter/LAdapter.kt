package org.zhiwei.jetpack.binding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import org.zhiwei.jetpack.binding.bean.ObUser
import org.zhiwei.jetpack.binding.databinding.ItemLvBinding

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年09月04日 14:32
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * ListView的adapter，简单演示，所以也不写viewHolder了
 */
class LAdapter : BaseAdapter() {

	private var users: MutableList<ObUser> = arrayListOf()

	init {
		//初始化三个数据
		for (i in 0..2) {
			users.add(
				ObUser("小明$i", 20 + i, i % 2, "小明小强从小学就伴随你一直到现在$i")
			)
		}
	}

	//简单演示，就不用viewHolder了。实际使用，不应该这样写
	@SuppressLint("ViewHolder")
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val inflater = LayoutInflater.from(parent?.context)
		val binding = ItemLvBinding.inflate(inflater)
		binding.user = users[position]
		return binding.root
	}

	override fun getItem(position: Int): ObUser {
		return users[position]
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getCount(): Int {
		//就简单演示3条数据
		return users.size
	}
}