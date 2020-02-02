package org.zhiwei.jetpack.page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.zhiwei.jetpack.page.db.Student
import org.zhiwei.jetpack.page.list.MyAdapter

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 20:01
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * paging的演示界面
 */
class PagingActivity : AppCompatActivity() {
	//滑动list的时候，可以看到滚动条的变化，感知到数据的平滑加载
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_paging)
		val recyclerView = findViewById<RecyclerView>(R.id.rv_paging)
		recyclerView.layoutManager = LinearLayoutManager(this)
		val adapter = MyAdapter()
		recyclerView.adapter = adapter
		val presenter = MyPresenter(this)
		presenter.allStudents.observe(
			this,
			Observer { pagedList: PagedList<Student> ->
				adapter.submitList(pagedList)
			}
		)
	}
}