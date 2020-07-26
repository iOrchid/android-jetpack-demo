package org.zhiwei.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room框架的database对象类,应该使用单例模式
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:19.
 */
@Database(entities = [DbUser::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
	abstract val userDao: UserDao?

	companion object {
		const val DB_NAME = "user.db"
		private var instance: UserDatabase? = null
		@Synchronized
		fun getInstance(context: Context?): UserDatabase? {
			if (instance == null) {
				instance = Room.databaseBuilder(
					context!!,
					UserDatabase::class.java,
					DB_NAME
				)
					.allowMainThreadQueries() //默认room不允许在主线程操作数据库，这里设置允许
					.build()
			}
			return instance
		}
	}
}