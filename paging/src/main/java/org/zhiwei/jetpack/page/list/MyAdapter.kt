package org.zhiwei.jetpack.page.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.zhiwei.jetpack.page.R
import org.zhiwei.jetpack.page.db.Student

/**
 * 分页paging的listAdapter，这个PagedListAdapter继承自RecyclerViewAdapter
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:42.
 */
class MyAdapter : PagedListAdapter<Student, MyViewHolder>(DIFF_CALLBACK) {
	/**
	 * 继承PagedListAdapter，必须要调用基类的一个构造函数
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
		val inflate =
			LayoutInflater.from(parent.context).inflate(R.layout.item_rv_paging, parent, false)
		return MyViewHolder(inflate)
	}

	override fun onBindViewHolder(
		holder: MyViewHolder,
		position: Int
	) { //数据源的提供来自diff
		holder.setName(getItem(position))
	}

	companion object {
		//DiffUtil是RecyclerView提供的，用于对比和生成差异数据
		private val DIFF_CALLBACK: DiffUtil.ItemCallback<Student> =
			object : DiffUtil.ItemCallback<Student>() {
				override fun areItemsTheSame(
					oldItem: Student,
					newItem: Student
				): Boolean {
					return oldItem.id == newItem.id
				}

				@SuppressLint("DiffUtilEquals")
				override fun areContentsTheSame(
					oldItem: Student,
					newItem: Student
				): Boolean {
					return oldItem === newItem
				}
			}
	}
}