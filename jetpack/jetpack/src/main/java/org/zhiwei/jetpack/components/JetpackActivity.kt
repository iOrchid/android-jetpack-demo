package org.zhiwei.jetpack.components

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * jetpack相关演示代码的功能模块的主入口页面
 */
class JetpackActivity : FragmentActivity() {

    private val fcv: FragmentContainerView by lazy { findViewById(R.id.fcv_jetpack) }
    private val bnv: BottomNavigationView by lazy { findViewById(R.id.bnv_jetpack) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //此界面在AndroidManifest中使用了theme，状态栏和导航栏的透明
        setContentView(R.layout.activity_jetpack)
    }

    /**
     * 配置navigation连接Fragment和BottomNavigationBar
     * 由于xml中使用了androidx.fragment.app.FragmentContainerView替换fragment标签
     * 源代码既有Bug，在onCreate中findNavController是找不到FragmentContainerView的，如果是fragment标签则没这个Bug
     */
    override fun onStart() {
        super.onStart()
        val navController = fcv.findNavController()
//        val navController = findNavController(R.id.fcv_jetpack)//也可以这么写
        bnv.setupWithNavController(navController)
    }
}