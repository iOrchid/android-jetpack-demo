package org.zhiwei.kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.zhiwei.kotlin.R

/**
 * 演示kotlin基础语法的basic部分的UI页面
 */
internal class BasicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }

}