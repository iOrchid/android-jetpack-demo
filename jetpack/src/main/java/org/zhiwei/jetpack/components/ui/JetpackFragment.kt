package org.zhiwei.jetpack.components.ui

import android.icu.math.BigDecimal
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.zhiwei.jetpack.components.R

/**
 * 这部分主要演示除dataBinding外的，主要的几个jetpack组件的用法；
 * 1. liveData、lifecycle、viewModel，room，paging，work，navigation
 * 2. 其实在JetpackActivity中就已经使用了navigation结合bottomNavigationView的简单用法
 */
class JetpackFragment : Fragment() {
    //fragment-ktx提供的扩展函数，便于获取viewModel的实例；该方式获取的vm，不同的fragment对象获取后的vm，不是同一个实例。
    private val vm: JetpackViewModel by viewModels()

    //fragment-ktx提供的扩展函数，便于获取该fragment依附的activity的viewModel的实例。
    // 该方式获取的vm，如果是同一个activity下不同的fragment获取vm，对象是同一个。
//    private val vm2: JetpackViewModel by activityViewModels()

    private val tvLive: TextView by lazy { requireView().findViewById(R.id.tv_live_ret_jetpack) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //这里注意，第三个参数一定要设置false，否则报错
        return inflater.inflate(R.layout.fragment_jetpack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testLiveData()
    }

    private val TAG = "JetpackFragment"
    private fun testLiveData() {
        //模拟生成数据
        vm.startSendScore()
        //观察live的数据变化，不要关联生命周期的lifecycleOwner，observeForever不需要。
        //viewLifecycleOwner是fragment的，activity就是自身。
        vm.liveScore.observe(viewLifecycleOwner) {
            //保留小数点2位
            val num = BigDecimal(it).setScale(2, BigDecimal.ROUND_UP)
            Log.d(TAG, "testLiveData:👀到$it ，保留2位：$num")
            tvLive.text = "文本$num"
        }
    }


}