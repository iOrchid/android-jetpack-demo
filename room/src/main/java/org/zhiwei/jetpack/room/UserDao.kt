package org.zhiwei.jetpack.room

import androidx.room.*

/**
 * Room数据框架的Dao层,对数据表的CRUD
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:11.
 */
@Dao //room注解
interface UserDao {
	//todo room数据库，内部封装了对liveData的支持，可以在此直接返回的数据类型，变为LiveData<T>的，注意，不能是其子类，如mutableLiveData等。注意，返回的LiveData不能直接用getValue，拿到的会是null，只有在liveData的oberve函数中才能拿到实际值。
	//查询所有数据，若返回liveData则为 LiveData<List<DbUser>>
	@Query(value = "select * from db_user")
	fun getAll(): List<DbUser?>?

	@Query("SELECT * FROM db_user WHERE uid IN (:userIds)")
	fun loadAllByIds(userIds: IntArray?): List<DbUser?>? //根据uid查询

	@Query(
		"SELECT * FROM db_user WHERE uname LIKE :name AND "
				+ "age LIKE :age LIMIT 1"
	)
	fun findByName(name: String?, age: Int): DbUser?

	@Query("select * from db_user where uid like :id")
	fun getUserById(id: Int): DbUser?

	@Insert
	fun insertAll(vararg users: DbUser?) //支持可变参数

	@Delete
	fun delete(user: DbUser?) //删除指定的user

	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun update(user: DbUser?) //更新，若出现冲突，则使用替换策略，还有其他策略可选择
}