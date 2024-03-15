package org.zhiwei.jetpack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : Activity() {

    private val llKotlin: LinearLayout by lazy { findViewById(R.id.ll_kotlin_main) }
    private val llJetpack: LinearLayout by lazy { findViewById(R.id.ll_jetpack_main) }
    private val llCompose: LinearLayout by lazy { findViewById(R.id.ll_compose_main) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llKotlin.setOnClickListener { startActivity(Intent(JETPACK_INTENT_ACTION_KOTLIN)) }
        llJetpack.setOnClickListener { startActivity(Intent(JETPACK_INTENT_ACTION_JETPACK)) }
        llCompose.setOnClickListener { startActivity(Intent(JETPACK_INTENT_ACTION_COMPOSE)) }
    }

    companion object {
        const val JETPACK_INTENT_ACTION_KOTLIN = "org.zhiwei.jetpack.KOTLIN_ACTIVITY"
        const val JETPACK_INTENT_ACTION_JETPACK = "org.zhiwei.jetpack.JETPACK_ACTIVITY"
        const val JETPACK_INTENT_ACTION_COMPOSE = "org.zhiwei.jetpack.COMPOSE_ACTIVITY"
    }

}