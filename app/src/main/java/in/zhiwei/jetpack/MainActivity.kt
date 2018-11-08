package `in`.zhiwei.jetpack

import `in`.zhiwei.jetpack.aac.view.AacActivity
import `in`.zhiwei.jetpack.dagger.DaggerActivity
import `in`.zhiwei.jetpack.databinding.DataBindingActivity
import `in`.zhiwei.jetpack.kotlin.KotlinActivity
import `in`.zhiwei.jetpack.lifecycle.LifeActivity
import `in`.zhiwei.jetpack.livedata.LiveDataActivity
import `in`.zhiwei.jetpack.navigation.NaviActivity
import `in`.zhiwei.jetpack.paging.PagingActivity
import `in`.zhiwei.jetpack.room.RoomActivity
import `in`.zhiwei.jetpack.rxjava.RxActivity
import `in`.zhiwei.jetpack.workmanager.WorkActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dataBinding(v: View) {
        startActivity(Intent(this, DataBindingActivity::class.java))
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
