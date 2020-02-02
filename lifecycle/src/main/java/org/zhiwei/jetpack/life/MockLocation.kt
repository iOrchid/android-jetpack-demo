package org.zhiwei.jetpack.life

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,14:31.
 */
class MockLocation(private val owner: LifecycleOwner?, private val callBack: LocationCallBack?) :
    LifecycleObserver {

    private var enable = false

    //如此则会自动感知生命周期，并调用相应操作
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (enable) {
            //开始定位，在此之前可能需要检测网络、用户信息、等各种状态，存在耗时，若不用lifecycle时候，就会出现在这里耗时检测
            // 而未真的start定位之前，activity就被stop，同时listener也被调用stop，即出现了本listener在start之前调用了stop的异常现象
        }
    }

    /**
     * 模拟开始定位
     */
    fun startLocation() {
        enable = true
        if (owner!!.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            //判断，如果没有开始定位，就再次开启定位,定位成功，使用callback回调UI刷新
            callBack!!.onSuccess()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        //do some stop operate
    }

    interface LocationCallBack {
        fun onSuccess()
    }

}