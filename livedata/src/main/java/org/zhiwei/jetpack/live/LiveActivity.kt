package org.zhiwei.jetpack.live

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
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


    val liveOne = MutableLiveData<String>()
    val liveTwo = MutableLiveData<String>()
    val mediatorLive = MediatorLiveData<Pair<String, String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)
        //Fragment
        val appleFragment = AppleFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container_live, appleFragment)
            .commit()
        btn_create_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .show(appleFragment)
                .commit()
            Log.e("LiveActivity", "onCreate: 创建fg $appleFragment")
        }
        //销毁
        btn_destroy_fg_live.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .hide(appleFragment)
                .commit()
            Log.e("LiveActivity", "onCreate: 销毁fg $appleFragment")

        }
        //变更livedata的值
        btn_change_live.setOnClickListener {
            liveAppleData.value = "当前liveData的值：${System.currentTimeMillis()}"
        }
        btn_change1_live.setOnClickListener {
            liveOne.value = "one:${System.currentTimeMillis().toString().takeLast(6)}"
        }
        btn_change2_live.setOnClickListener {
            liveTwo.value = "two:${System.currentTimeMillis().toString().takeLast(6)}"
        }

        //观察值
        liveAppleData.observe(this, Observer {
            tv_live_data_activity.text = it
            Log.d("LiveActivity", "LiveData在LiveActivity中 $it")
        })
        //map转换后的数值
        liveMappedData.observe(this, Observer {
            tv_mapped_data_activity.text = it.toString()
            Log.i("LiveActivity", "LiveData在LiveActivity中 map 后 $it")
        })


        //中介者
        mediatorLive.addSource(liveOne) {
            Log.d("LiveActivity", "LiveActivity中 LiveOne ---> $it")
            mediatorLive.value = "one >>" to it
        }
        mediatorLive.addSource(liveTwo) {
            Log.d("LiveActivity", "LiveActivity中 LiveTwo ---> $it")
            mediatorLive.value = "two >>>>>" to it
        }
        mediatorLive.observe(this, Observer {
//            Log.w("LiveActivity", "LiveActivity中 mediatorLive ---> $it")
        })
    }

}