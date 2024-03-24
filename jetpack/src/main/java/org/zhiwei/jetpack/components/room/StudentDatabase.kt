package org.zhiwei.jetpack.components.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * 步骤3，创建database，注意使用@dataBase注解，声明所有表entity，注明版本号
 */
@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    /**
     * 对应的操作表的dao函数，也是抽象的。
     */
    abstract fun studentDao(): StudentDao


    companion object {

        private const val DB_NAME = "student"

        @Volatile
        private var instance: StudentDatabase? = null

        fun createDatabase(context: Context): StudentDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                StudentDatabase::class.java,
                DB_NAME
            ).build().also { instance = it }
        }
    }


}