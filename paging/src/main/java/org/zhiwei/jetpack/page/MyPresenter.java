package org.zhiwei.jetpack.page;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import org.zhiwei.jetpack.page.db.Student;
import org.zhiwei.jetpack.page.db.StudentDao;
import org.zhiwei.jetpack.page.db.StudentDatabase;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,11:37.
 */
public class MyPresenter {

	private static final int PAGE_SIZE = 15;
	private static final boolean ENABLE_PLACEHOLDERS = false;
	public final LiveData<PagedList<Student>> allStudents;
	private Context context;
	private StudentDao dao;

	public MyPresenter(Context context) {
		this.context = context;
		dao = StudentDatabase.getInstance(context).getStudentDao();
		allStudents = new LivePagedListBuilder(dao.getAllStudent(),
				new PagedList.Config.Builder()
						.setPageSize(PAGE_SIZE)                         //配置分页加载的数量
						.setEnablePlaceholders(ENABLE_PLACEHOLDERS)     //配置是否启动PlaceHolders
						.setInitialLoadSizeHint(PAGE_SIZE)              //初始化加载的数量
						.build()
		).build();
	}

	void insertStudent(String name) {
		dao.insert(new Student(name));
	}
}
