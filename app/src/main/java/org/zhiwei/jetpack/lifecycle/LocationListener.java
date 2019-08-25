package org.zhiwei.jetpack.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,14:31.
 */
public class LocationListener implements LifecycleObserver {

	private boolean enable;
	private LifecycleOwner owner;
	private LocationCallBack callBack;

	public LocationListener(LifecycleOwner owner, LocationCallBack callBack) {
		this.owner = owner;
		this.callBack = callBack;
		//......
	}

	//如此则会自动感知生命周期，并调用相应操作
	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		if (enable) {
			//开始定位，在此之前可能需要检测网络、用户信息、等各种状态，存在耗时，若不用lifecycle时候，就会出现在这里耗时检测
			// 而未真的start定位之前，activity就被stop，同时listener也被调用stop，即出现了本listener在start之前调用了stop的异常现象
		}
	}

	public void enable() {
		enable = true;
		if (owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			//判断，如果没有开始定位，就再次开启定位,定位成功，使用callback回调UI刷新
			callBack.onSuccess();
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	public void onStop() {
		//do some stop operate
	}

	interface LocationCallBack {
		void onSuccess();
	}
}
