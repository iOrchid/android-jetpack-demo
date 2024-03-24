package org.zhiwei.jetpack.components.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * room数据库框架使用，1，创建entity类,记得@Entity注解
 */
@Entity(tableName = "tb_student")//定义数据表的名称
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,//定义数据表索引id，设置为主键，自增
    @ColumnInfo("uName")//可以定义表字段名
    val name: String,
    val age: Int,
    val sex: Int,//性别 0 女，1 男
    val height: Int,//身高
    val school: String,
    val address: String,
)

/**
 * 2，创建对表的crud操作
 */
@Dao//注解不可少，定义为interface类型
interface StudentDao {

    //定义插入数据条目的函数，使用@Insert注解，标记类型，可配置冲突策略
    @Insert(Student::class, OnConflictStrategy.ABORT)
    fun insertStudent(student: Student): Int

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
     * 根据业务需要，可以定义函数为suspend的，或者不用
     */
    @Query("select uName,age, sex, school from tb_student")
    suspend fun queryStudent(): Flow<Student>
}