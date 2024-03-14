package org.zhiwei.jetpack

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : Activity() {

    private val llKotlin: LinearLayout by lazy { findViewById(R.id.ll_kotlin_main) }
    private val llJetpack: LinearLayout by lazy { findViewById(R.id.ll_jetpack_main) }
    private val llCompose: LinearLayout by lazy { findViewById(R.id.ll_compose_main) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llKotlin.setOnClickListener {  }
        llJetpack.setOnClickListener {  }
        llCompose.setOnClickListener {  }
    }

}