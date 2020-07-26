package org.zhiwei.jetpack.live

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
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


    val liveAppleData = MutableLiveData<String>()


    val liveMappedData = liveAppleData.map {
        Pair<Int, String>(it.hashCode(), it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)
        //Fragment
        val appleFragment = AppleFragment()
        btn_create_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container_live, appleFragment)
                .commit()
        }
        //销毁
        btn_destroy_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .remove(appleFragment)
                .commit()
        }
        //变更livedata的值
        btn_change_live.setOnClickListener {
            liveAppleData.value = "当前liveData的值：${System.currentTimeMillis()}"
        }

        //观察值
        liveAppleData.observe(this, Observer {
            tv_live_data_activity.text = it
            Log.i("LiveActivity", "LiveData在LiveActivity中 $it")
        })
        //map转换后的数值
        liveMappedData.observe(this, Observer {
            tv_mapped_data_activity.text = it.toString()
            Log.i("LiveActivity", "LiveData在LiveActivity中 map 后 $it")
        })

    }

}