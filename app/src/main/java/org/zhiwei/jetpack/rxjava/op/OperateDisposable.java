package org.zhiwei.jetpack.rxjava.op;

import android.os.SystemClock;
import android.util.Log;

import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateDisposable extends BaseOp {

	private static String TAG = "OperateDisposable";

	private static CompositeDisposable disposables = new CompositeDisposable();

	/*
	 * disposable是管道控制阀，用于控制rx的上下游的数据流
	 */
	public static void doSome() {
		disposables.add(Observable.defer(() -> {
			//模拟耗时
			SystemClock.sleep(2000);
			//just操作符，就是直译，from是将list单个元素发送，just直接一个list就是list,但是这里十多个元素，而不是一个数组
			return Observable.just("one", "two", "three", "four", "five");
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(new DisposableObserver<String>() {
					@Override
					public void onNext(String s) {
						Log.w(TAG, "onNext: " + s);
					}

					@Override
					public void onError(Throwable e) {

						Log.e(TAG, "onError: " + e.getMessage());
					}

					@Override
					public void onComplete() {
						Log.i(TAG, "onComplete");

					}
				}));
	}

	public void shutDown() {
		if (disposables != null) {
			disposables.clear();
		}
	}
}
