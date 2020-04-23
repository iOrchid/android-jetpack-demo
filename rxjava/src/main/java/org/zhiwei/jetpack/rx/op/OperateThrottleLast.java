package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateThrottleLast extends BaseOp {

	private static String TAG = "OperateThrottleLast";

	/*
	 * ThrottleFirst操作符，会在指定的时间间隔之内，发送最后一条指令，类似于debounce
	 * 还有个throttleLatest
	 */
	public static void doSome() {
		getObservable()
				.throttleLast(500, TimeUnit.MILLISECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}

	/*
		d.isDisposed():false
		b
		c
		d
		e
		onComplete
	 */
	private static Observable<String> getObservable() {
		return Observable.create(emitter -> {
			// send events with simulated time wait
			emitter.onNext("a"); // skip
			Thread.sleep(400);
			emitter.onNext("b"); // deliver
			Thread.sleep(505);
			emitter.onNext("c"); // skip
			Thread.sleep(100);
			emitter.onNext("d"); // deliver，3和4属于同一个500ms间隔
			Thread.sleep(605);
			emitter.onNext("e"); // deliver
			Thread.sleep(510);
			emitter.onComplete();
		});
	}

}
