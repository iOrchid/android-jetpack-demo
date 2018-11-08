package in.zhiwei.jetpack.paging;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import in.zhiwei.jetpack.paging.db.Student;
import in.zhiwei.jetpack.paging.db.StudentDao;
import in.zhiwei.jetpack.paging.db.StudentDatabase;

/**
 * Author: zhiwei.
 * Date: 2018/11/6 0006,11:37.
 */
public class MyPresenter {

    private Context context;
    private StudentDao dao;
    public final LiveData<PagedList<Student>> allStudents;

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

    private static final int PAGE_SIZE = 15;
    private static final boolean ENABLE_PLACEHOLDERS = false;
}
