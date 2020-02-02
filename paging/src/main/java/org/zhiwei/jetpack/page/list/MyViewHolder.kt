package org.zhiwei.jetpack.page.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.zhiwei.jetpack.page.R
import org.zhiwei.jetpack.page.db.Student

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,11:29.
 */
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	private val tvName: TextView = itemView.findViewById(R.id.tv_name_item_rv_paging)

	fun setName(student: Student?) {
		tvName.text = student?.name
	}

}