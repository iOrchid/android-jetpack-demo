package org.zhiwei.jetpack.components

import android.app.Activity
import android.os.Bundle

/**
 * jetpack相关演示代码的功能模块的主入口页面
 */
class JetpackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //此界面在AndroidManifest中使用了theme，状态栏和导航栏的透明
        setContentView(R.layout.activity_jetpack)
    }
}