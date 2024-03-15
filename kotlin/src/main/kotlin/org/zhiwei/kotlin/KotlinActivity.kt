package org.zhiwei.kotlin

import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.zhiwei.kotlin.ui.BasicFragment
import org.zhiwei.kotlin.ui.CoroutinesFragment
import org.zhiwei.kotlin.ui.FlowFragment
import org.zhiwei.kotlin.ui.SyntaxFragment

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/iOrchid
 * 日期： 2019年08月05日 19:13
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Kotlin的用法演示入口主界面 可参考官方文档[kotlin Language](https://kotlinlang.org/docs/getting-started.html)
 */
class KotlinActivity : FragmentActivity() {

    private val tabLayout: TabLayout by lazy { findViewById(R.id.tl_kotlin) }
    private val viewPager2: ViewPager2 by lazy { findViewById(R.id.vp2_kotlin) }

    //使用list缓存已经创建的fragment的对象，避免在下面createFragment中重复创建对象，消耗性能
    private val fragments =
        listOf(BasicFragment(), SyntaxFragment(), CoroutinesFragment(), FlowFragment())
    private val tabTitles = arrayOf("基础速览", "语法详解", "协程Coroutines", "数据流Flow")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //设置viewPager2的adapter
        viewPager2.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }
        //不同于viewPager，viewPager2使用此种方式关联tablayout，但是要求必须viewPager2先设置adapter之后才可以,
        // 使用tabLayoutMediator就不需要viewPager2设置onPageChangeCallback了。但是tab的名字就需要动态设置
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}