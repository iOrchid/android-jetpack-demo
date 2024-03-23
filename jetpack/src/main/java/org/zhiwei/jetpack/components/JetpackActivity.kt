package org.zhiwei.jetpack.components

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.zhiwei.jetpack.components.ui.JetpackViewModel

/**
 * jetpack相关演示代码的功能模块的主入口页面,
 * todo 如果要使用navigation，则外部activity必须是fragmentActivity或其子类。否则不会有fragmentManager，没法navController
 * 1. Navigation的使用要点：a.需要FragmentContainerView作为容器（配置defaultNavHost，navGraph，name三大属性）；
 *      b.需要res下navigation中有graph配置（注意startDestination，子节点fragment配置）
 *      c.使用处findNavController，可navController.navigateUp()/navigate()
 */
class JetpackActivity : FragmentActivity() {

    private val fcv: FragmentContainerView by lazy { findViewById(R.id.fcv_jetpack) }
    private val bnv: BottomNavigationView by lazy { findViewById(R.id.bnv_jetpack) }

    //activity-ktx库提供的扩展函数，
    private val vm by viewModels<JetpackViewModel>()


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
        //去掉bottom navigation view的color tint，则就变成了原始的imageView的效果，
        //navView.menu.getItem(2).icon 就是ImageView，可以加载gif，webp等icon效果
        bnv.itemIconTintList = null
    }
}