package org.zhiwei.jetpack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.zhiwei.jetpack.aac.view.AacActivity
import org.zhiwei.jetpack.binding.BindingActivity
import org.zhiwei.jetpack.dagger2.DaggerActivity
import org.zhiwei.jetpack.kt.KtActivity
import org.zhiwei.jetpack.life.LifeActivity
import org.zhiwei.jetpack.live.LiveActivity
import org.zhiwei.jetpack.nav.NavActivity
import org.zhiwei.jetpack.page.PagingActivity
import org.zhiwei.jetpack.room.RoomActivity
import org.zhiwei.jetpack.rx.RxActivity
import org.zhiwei.jetpack.work.WorkActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dataBinding(v: View) {
        startActivity(Intent(this, BindingActivity::class.java))
    }

    fun liveData(v: View) {
        startActivity(Intent(this, LiveActivity::class.java))
    }

    fun lifeCycle(v: View) {
        startActivity(Intent(this, LifeActivity::class.java))
    }

    fun room(v: View) {
        startActivity(Intent(this, RoomActivity::class.java))
    }

    fun navi(v: View) {
        startActivity(Intent(this, NavActivity::class.java))
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
        startActivity(Intent(this, KtActivity::class.java))
    }

    fun aac(v: View) {
        startActivity(Intent(this, AacActivity::class.java))
    }

    fun dagger(v: View) {
        startActivity(Intent(this, DaggerActivity::class.java))
    }
}
