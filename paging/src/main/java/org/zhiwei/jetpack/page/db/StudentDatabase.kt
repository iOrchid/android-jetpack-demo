package org.zhiwei.jetpack.page.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*
import java.util.concurrent.Executors

/**
 * 继承RoomDatabase，且依旧是抽象函数，并且获取dao 的函数，也是抽象的
 * Author: zhiwei.
 * Date: 2018/11/6 0006,10:57.
 */
//todo 虽然可以写作单例模式，但是这个是抽象类，不能私有构造函数
@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
	//Room数据库的标准写法，规则
	abstract val studentDao: StudentDao

	/**
	 * io线程异步执行
	 */
	internal class IOExecutor(task: Runnable?) {
		private val IO_EXECUTOR =
			Executors.newSingleThreadExecutor()

		init {
			IO_EXECUTOR.execute(task)
		}
	}

	companion object {
		val data = arrayOf(
			"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
			"Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
			"Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
			"Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
			"Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
			"Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
			"Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
			"Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
			"Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
			"Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
			"Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
			"Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)"
		)
		private var instance: StudentDatabase? = null

		fun getInstance(context: Context): StudentDatabase? {
			if (instance == null) {
				instance = Room.databaseBuilder(
					context,
					StudentDatabase::class.java,
					"st.db"
				)
					.addCallback(object : Callback() {
						override fun onCreate(db: SupportSQLiteDatabase) {
							super.onCreate(db)
							IOExecutor(Runnable {
								//插入一些模拟数据
								val list: MutableList<Student> = ArrayList()
								var student: Student
								for (d in data) {
									student = Student(d)
									list.add(student)
								}
								getInstance(context)!!.studentDao.insert(list)
							})
						}
					})
					.build()
			}
			return instance
		}
	}
}