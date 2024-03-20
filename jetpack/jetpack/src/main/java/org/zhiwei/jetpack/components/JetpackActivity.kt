package org.zhiwei.jetpack.components

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import org.zhiwei.jetpack.databinding.DataBindingActivity
import org.zhiwei.jetpack.work.WorkActivity

/**
 * jetpack相关演示代码的功能模块的主入口页面
 */
class JetpackActivity : Activity() {

    private val btnDataBinding: Button by lazy { findViewById(R.id.btn_databinding_jetpack) }
    private val btnWorkManager: Button by lazy { findViewById(R.id.btn_work_jetpack) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //此界面在AndroidManifest中使用了theme，状态栏和导航栏的透明
        setContentView(R.layout.activity_jetpack)
        btnDataBinding.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DataBindingActivity::class.java
                )
            )
        }
        btnWorkManager.setOnClickListener { startActivity(Intent(this, WorkActivity::class.java)) }
    }
}