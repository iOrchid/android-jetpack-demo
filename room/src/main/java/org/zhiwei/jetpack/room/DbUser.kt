package org.zhiwei.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Room数据库的entity对象类
 * Author: zhiwei.
 * Date: 2018/11/5 0005,15:05.
 */
@Entity(tableName = "db_user") //room数据库的注解标记,数据表entity  (tableName="db_user",indices = {@Index(value = "uname",unique = true)})
class DbUser {
	@PrimaryKey(autoGenerate = true)
	var uid = 0
	@ColumnInfo(name = "uname")
	var name: String? = null
	@ColumnInfo
	var city: String? = null
	@ColumnInfo
	var age = 0
	//如此数据表中不会有@Ignore标记的属性字段
	@Ignore
	var isSingle = false

	override fun toString(): String {
		return "DbUser{" +
				"uid=" + uid +
				", name='" + name + '\'' +
				", city='" + city + '\'' +
				", age=" + age +
				", single=" + isSingle +
				'}'
	}
}