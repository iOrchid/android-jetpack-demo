package org.zhiwei.kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.zhiwei.kotlin.R

/**
 * 演示kotlin语法flow数据流的UI页面
 */
internal class FlowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flow = flow<String> {
            repeat(20) {
                delay(2000)
                emit("🪄$it🧚")
            }
        }
        val launchIn = flow.launchIn(lifecycleScope)
        lifecycleScope.launch {
            flow.collect {
                view.findViewById<TextView>(R.id.tv_text_ticker_flow_kotlin).text = it
            }
        }
    }
}