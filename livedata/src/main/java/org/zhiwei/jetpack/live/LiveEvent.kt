package org.zhiwei.jetpack.live

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 针对liveData用作event的时候，由于即使在inactive状态下，不论有无observer都能set值，并被持有，恢复active即被通知observer
 * 在特定场景下，不适用。（如vm中的liveData，在UI中感知，来跳转界面；就会出现返回界面后，会再次跳转的问题。参照liveData知识速览的问题一栏）
 * 处理方案，是判断处理情况，再决定通知事件
 */

// 通俗的，低级的方式
open class LowLiveEvent<out T>(private val content: T) {

    private var hasHandled = false;//标记位


    //如果没有处理，就返回content，否则null
    fun getContentIfNotHandled(): T? {
        return if (hasHandled) {
            hasHandled = true
            content
        } else {
            content
        }
    }

    //不论已经处理与否，都感知变化
    fun peekContent(): T = content
}

//singleLiveEvent 原理类似于上面的，通过AtomicBoolean来标记对比处理情况，
//会选择通知一个处于active的observe变化，但是不确保是哪一个
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Log.w(
                "SingleLiveEvent",
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}