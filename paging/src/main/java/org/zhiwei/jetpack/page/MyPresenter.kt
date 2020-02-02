package org.zhiwei.jetpack.page

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import org.zhiwei.jetpack.page.db.Student
import org.zhiwei.jetpack.page.db.StudentDao
import org.zhiwei.jetpack.page.db.StudentDatabase

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,11:37.
 */
class MyPresenter(private val context: Context) {

	val allStudents: LiveData<PagedList<Student>>

	private val dao: StudentDao = StudentDatabase.getInstance(context)!!.studentDao

	fun insertStudent(name: String) {
		dao.insert(Student(name))
	}

	companion object {
		private const val PAGE_SIZE = 15
		private const val ENABLE_PLACEHOLDERS = false
	}

	init {
		allStudents = LivePagedListBuilder<Int, Student>(
			dao.allStudent,
			PagedList.Config.Builder()
				.setPageSize(PAGE_SIZE) //配置分页加载的数量
				.setEnablePlaceholders(ENABLE_PLACEHOLDERS) //配置是否启动PlaceHolders
				.setInitialLoadSizeHint(PAGE_SIZE) //初始化加载的数量
				.build()
		).build()
	}
}