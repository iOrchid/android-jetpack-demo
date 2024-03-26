package org.zhiwei.jetpack.components.paging

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.zhiwei.jetpack.components.R

/**
 * 使用paging的数据适配器
 */
class TeacherPagingAdapter :
    PagingDataAdapter<Teacher, TeacherPagingAdapter.TeacherVH>(TEACHER_ITEM_CALLBACK) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TeacherVH, position: Int) {
        getItem(position)?.also { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherVH {
        return TeacherVH.create(parent)
    }


    class TeacherVH(view: View) : ViewHolder(view) {
        private val tvName: TextView by lazy { view.findViewById(R.id.tv_name_item_teacher) }
        private val tvTitle: TextView by lazy { view.findViewById(R.id.tv_title_item_teacher) }
        private val tvTime: TextView by lazy { view.findViewById(R.id.tv_time_item_teacher) }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(teacher: Teacher) {
            tvName.text = teacher.name
            tvTitle.text = teacher.title
            tvTime.text = teacher.createText
        }


        companion object {
            fun create(parent: ViewGroup): TeacherVH {
                return LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_teacher_paging, parent, false).let { TeacherVH(it) }
            }
        }
    }

}

private val TEACHER_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Teacher>() {

    override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem == newItem
    }
}