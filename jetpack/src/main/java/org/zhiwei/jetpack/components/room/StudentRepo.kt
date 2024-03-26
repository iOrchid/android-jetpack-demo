package org.zhiwei.jetpack.components.room

import kotlinx.coroutines.flow.Flow
import org.zhiwei.jetpack.components.paging.TeacherPagingSource

/**
 * 定义数据层的管理类，上接viewModel，下承数据库或网络
 */
class StudentRepo(
    private val studentDao: StudentDao,
//    private val networkSource:NetworkSource,//这里就是模拟说有个网络加载数据的方式，在MVVM或MVI等设计架构中，
    //    上层UI的数据源是统一收口管理的。网络数据先落地数据库，通过liveData/flow来让UI感知更新
) {

    fun loadAllStudents(): Flow<List<Student>> {
        return studentDao.queryAllStudents()
    }

    /**
     * 创建一些模拟的数据
     */
    fun mockStudents(dao: StudentDao) {
        repeat(100) {
            val student = Student(
                name = "🧑‍🎓学生 $it",
                age = 10 + (it % 30),
                sex = it % 2,
                height = 80 + it,
                school = "实验中学 $it 班级",
                address = "人民路 $it 号"
            )
            dao.insertStudent(student = student)
        }
    }
}

class TeacherRepo {

    fun loadPagingTeachers() = TeacherPagingSource()
}