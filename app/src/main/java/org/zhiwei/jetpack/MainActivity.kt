package org.zhiwei.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.zhiwei.jetpack.aac.view.AacActivity
import org.zhiwei.jetpack.binding.BindingActivity
import org.zhiwei.jetpack.dagger.DaggerActivity
import org.zhiwei.jetpack.kotlin.KotlinActivity
import org.zhiwei.jetpack.lifecycle.LifeActivity
import org.zhiwei.jetpack.livedata.LiveDataActivity
import org.zhiwei.jetpack.navigation.NaviActivity
import org.zhiwei.jetpack.paging.PagingActivity
import org.zhiwei.jetpack.room.RoomActivity
import org.zhiwei.jetpack.rxjava.RxActivity
import org.zhiwei.jetpack.workmanager.WorkActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dataBinding(v: View) {
        startActivity(Intent(this, BindingActivity::class.java))
    }

    fun liveData(v: View) {
        startActivity(Intent(this, LiveDataActivity::class.java))
    }

    fun lifeCycle(v: View) {
        startActivity(Intent(this, LifeActivity::class.java))
    }

    fun room(v: View) {
        startActivity(Intent(this, RoomActivity::class.java))
    }

    fun navi(v: View) {
        startActivity(Intent(this, NaviActivity::class.java))
    }

    fun paging(v: View) {
        startActivity(Intent(this, PagingActivity::class.java))
    }

    fun work(v: View) {
        startActivity(Intent(this, WorkActivity::class.java))
    }

    fun rxjava(v: View) {
        startActivity(Intent(this, RxActivity::class.java))
    }

    fun kotlin(v: View) {
        startActivity(Intent(this, KotlinActivity::class.java))
    }

    fun aac(v: View) {
        startActivity(Intent(this, AacActivity::class.java))
    }

    fun dagger(v: View) {
        startActivity(Intent(this, DaggerActivity::class.java))
    }
}
