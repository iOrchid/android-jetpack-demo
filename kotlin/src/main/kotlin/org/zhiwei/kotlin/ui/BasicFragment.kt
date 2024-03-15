package org.zhiwei.kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        Toast.makeText(requireContext(), "请参照basic包下QuickSyntax.kt文件", Toast.LENGTH_LONG)
            .show()
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }


}