package org.zhiwei.jetpack.aac.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.zhiwei.jetpack.aac.R
import org.zhiwei.jetpack.aac.model.UserModel
import org.zhiwei.jetpack.aac.viewmodel.UserViewModel

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 19:52
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Android Architecture Components 的架构演示界面
 */
class AacActivity : AppCompatActivity() {

	private var viewModel: UserViewModel? = null
	private var tvList: TextView? = null
	private var tvInfo: TextView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_aac)
		tvList = findViewById(R.id.tv_user_list_aac)
		tvInfo = findViewById(R.id.tv_user_info_aac)
		viewModel =
			ViewModelProvider.AndroidViewModelFactory(application).create(UserViewModel::class.java)
		viewModel!!.userList.observe(
			this,
			Observer { userModels: List<UserModel> ->
				val sb = StringBuilder()
				for (model in userModels) {
					sb.append(model.toString()).append("\n")
				}
				tvList!!.text = sb.toString()
			}
		)
		viewModel!!.userInfo.observe(
			this,
			Observer { s: String -> tvInfo?.text = s }
		)
	}

	fun load(view: View?) {
		viewModel!!.loadData()
		viewModel!!.loadUserName("小明 3")
	}

}