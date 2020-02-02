package org.zhiwei.jetpack.room

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_room.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 20:02
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * _              _           _     _   ____  _             _ _
 * / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 * / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 * / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 * /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Room数据库的演示界面
 */
class RoomActivity : AppCompatActivity() {
	//两种不同的by lazy初始化view，因为使用可kotlin ext工具，也可以直接用 tv_insert_room这样作为view使用
	private val tvInsert: TextView by lazy { tv_insert_room }
	private val tvDelete: TextView by lazy { findViewById<TextView>(R.id.tv_delete_room) }
	private val tvUpdate: TextView by lazy { tv_update_room }
	private val tvQueryID: TextView by lazy { tv_query_id_room }
	private val tvQueryAll: TextView by lazy { tv_query_all_room }

	private val tvSize: TextView by lazy { tv_size_room }
	private val tvAll: TextView by lazy { tv_all_room }
	private var instance: UserDatabase? = null
	private var userDao: UserDao? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_room)
		//
		instance = UserDatabase.getInstance(this)
		deleteDatabase(UserDatabase.DB_NAME) //删除数据库，属于contextWrapper中的函数
		userDao = instance?.userDao
	}

	override fun onDestroy() {
		super.onDestroy()
		instance!!.clearAllTables()
	}

	//todo 可以看出，这里插入数据的single是有区分的，但是查询出的结果，却都是默认值，因为single字段在数据库中没有，在entity中却有，所以数据库中出去来生成的对象，这个字段就是默认值。配置ignore的作用
	fun insert(view: View?) { //        List<DbUser> users = new ArrayList<>();
		val sb = StringBuilder()
		var user: DbUser
		for (i in 0..4) {
			user = DbUser()
			user.age = 20 + i
			user.city = "北京 $i"
			user.name = "小明 $i"
			user.isSingle = i % 2 == 0
			userDao!!.insertAll(user)
			sb.append(user.toString()).append("\n")
		}
		//        userDao.insertAll(users.get(0),users.get(1));
		tvInsert.text = sb.toString()
		all
	}

	private val all: Unit
		private get() {
			val all = userDao!!.getAll() ?: return
			val sb = StringBuilder()
			all.forEach { user ->
				sb.append("uid: ")
					.append(user?.uid)
					.append("姓名: ")
					.append(user?.name)
					.append("年龄: ")
					.append(user?.age)
					.append("城市: ")
					.append(user?.city)
					.append("Single: ")
					.append(user?.isSingle)
					.append("\n")
			}
			val text = "All Size ： " + all.size
			tvSize.text = text
			tvAll.text = sb.toString()
		}

	fun delete(view: View?) {
		val user = userDao!!.findByName("小明 " + 3, 23)
		userDao!!.delete(user)
		//
		tvDelete.text = user.toString()
		all
	}

	fun update(view: View?) {
		val user = userDao!!.findByName("小明 " + 2, 22) ?: return
		user.age = 33
		user.city = "上海"
		user.name = "张三"
		user.isSingle = true
		userDao!!.update(user)
		tvUpdate.text = user.toString()
		all
	}

	fun queryId(view: View?) {
		val userById = userDao!!.getUserById(3)
		if (userById != null) {
			tvQueryID.text = userById.toString()
		} else {
			Toast.makeText(this, "id=3 的user查询不到", Toast.LENGTH_SHORT).show()
		}
		//为了显示操作后的更新数据
		all
	}

	fun queryAll(view: View?) {
		all
	}
}