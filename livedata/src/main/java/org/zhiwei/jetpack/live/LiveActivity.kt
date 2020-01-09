package org.zhiwei.jetpack.live

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import kotlinx.android.synthetic.main.activity_livedata.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 19:58
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * liveData的演示界面
 */
class LiveActivity : AppCompatActivity() {

    private val tvAge: TextView by lazy { tv_age_live }
    private val tvName: TextView by lazy { tv_name_live }
    private val tvCity: TextView by lazy { tv_city_live }
    private val tvInfo: TextView by lazy { tv_info_live }

    private var user: LiveUser? = null//部分属性可以live响应的数据对象
    //用于演示 transformation
    private val liveUserLiveData = MutableLiveData<LiveUser?>()
    //map转化liveData的返回类型，用一个liveData可以向下转化出所需要的liveData。还有一个switchMap函数类似
    private val userInfo = Transformations.map(
        liveUserLiveData
    ) { user: LiveUser? -> "姓名： " + user!!.name.value + " 年龄：" + user.age.value + " 城市：" + user.city }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)

        user = LiveUser("北京", "小明", 20)
        //普通的 user属性
        tvCity.text = user!!.city
        //liveData的user属性
        user!!.age.observe(this, Observer { integer: Int ->
            val text = integer.toString()
            tvAge.text = text
        })
        //liveData的变化setValue，应在model中，View只负责显示
        packLive(user!!.name, tvName)
        //liveData转换的操作
        liveUserLiveData.value = user
        //liveData形式的数据显示整个user对象数据
        userInfo.observe(
            this,
            Observer { s: String? -> tvInfo.text = s }
        )
    }

    /**
     * 简单封装
     *
     * @param liveData liveData
     * @param view     view
     */
    private fun packLive(
        liveData: LiveData<String>,
        view: TextView?
    ) { //JDK8 中的 方法引用 view::setText
        liveData.observe(
            this,
            Observer { text: String? -> view!!.text = text }
        )
    }

    fun changeLive(view: View?) { //UI上这两个会变化，因为使用了liveData
        user?.age?.value = 22
        user?.name?.value = "小明明"
        //UI上这个不会变化，因为是普通的属性字段
        user!!.city = "上海"
        //transformations map 的演示变化，这里只是将数据源的user改变，UI界面上就能响应变化，不需要setText手动调用。
        liveUserLiveData.value = user
        Toast.makeText(this, "请注意，city字段在View上并未变化", Toast.LENGTH_SHORT).show()
        //普通属性的字段，想要同步变化，还需要再setText一次，而且是要每变化一次，都要手动调用代码setText。liveData则会响应变化
//        tvCity.setText(user.getCity());
    }

}