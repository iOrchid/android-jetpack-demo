package org.zhiwei.jetpack.components.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * room数据库框架使用，1，创建entity类,记得@Entity注解
 * 注意，如果这里面定义的字段没有默认值，那么在dao文件里，获取student的对象，就必须读取没有默认值的所有字段
 * 要么使用普通class，需要有无参构造函数，如果是data class，则必须都有默认值。
 */
@Entity(tableName = "tb_student")//定义数据表的名称
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,//定义数据表索引id，设置为主键，自增,注意默认值只能是0，否则在自增过程中，会冲突。
    @ColumnInfo("uName")//可以定义表字段名
    var name: String? = null,
    var age: Int? = null,
    var sex: Int? = null,//性别 0 女，1 男
    var height: Int? = null,//身高
    var school: String? = null,
    var address: String? = null,
    @Ignore//则不会作为表字段存入
    var mark: String = "无",
)

/**
 * 2，创建对表的crud操作
 */
@Dao//注解不可少，定义为interface类型
interface StudentDao {

    //定义插入数据条目的函数，使用@Insert注解，标记类型，可配置冲突策略
    @Insert(Student::class, OnConflictStrategy.ABORT)
    fun insertStudent(student: Student)

    @Delete(Student::class)
    fun deleteStudent(student: Student): Int

    @Update(Student::class)
    fun updateStudent(student: Student): Int

    /**
     * 身高高于180的所有学生
     */
    @Query("select * from tb_student where height > 180")
    fun query180Student(): List<Student>

    /**
     * query语句内就是sqlite的基本查询语句，可使用sqlite的条件语句，限定语句等。
     * 返回类型可以是普通的List或者单个数据，也可以是LiveData的，也可以是flow的
     * 根据业务需要，可以定义函数为suspend的(就是普通同步的数据类型），，或者不用可以deferred/async
     * @param name 需要查询匹配的学生的姓名，在sqlite语句中，使用:paramName的形式引用
     */
    @Query("select * from tb_student where uName = :name ")//这里就不能只单独读取某些表字段，因为Student的很多属性都没设置默认值。
    suspend fun queryStudent(name: String): Student

    //todo 在AndroidStudio中，手机链接AS后，room相关的query语句，会有一个表格📊🔍的图标按钮，点击后可直接打开Database inspector，可以在其中查看和执行sqlite语句测试。
    //在AS的菜单栏 View--Tool Windows -- App Inspection 然后选择我们调试的设备和进程，就可以查看所有app的数据库，选择对应表，就可以执行sqlite测验

    @Query("select * from tb_student")
    fun queryAllStudents(): Flow<List<Student>>


}