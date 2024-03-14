package com.zenmen.jetpack

import android.app.Activity
import android.os.Bundle

/**
 * jetpack相关演示代码的功能模块的主入口页面
 */
class JetpackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
    }
}