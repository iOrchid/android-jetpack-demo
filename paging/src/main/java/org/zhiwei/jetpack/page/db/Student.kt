package org.zhiwei.jetpack.page.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * student的对象类，在room数据库中的表
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:44.
 */
@Entity //使用java写Room的entity类，似乎必须要有getter和setter
data class Student(
	//一般情况也可以不写，默认是加入数据表的，除非注释ignore，才会忽略掉
	@ColumnInfo var name: String,
	@PrimaryKey(autoGenerate = true)
	var id: Int = 0
)
