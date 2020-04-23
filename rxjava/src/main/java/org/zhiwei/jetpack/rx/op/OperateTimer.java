package org.zhiwei.jetpack.rx.op;

import android.util.Log;

import org.zhiwei.jetpack.rx.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateTimer extends BaseOp {

	private static String TAG = "OperateTimer";

	/*
	 *  timer操作符，延迟指定时间，开始发送long类型的指令，类似于Interval.但是这发送一条
	 */
	public static void doSome() {
		Observable.timer(2, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {
						Log.d(TAG, "onSubscribe: " + d.isDisposed());
					}

					@Override
					public void onNext(Long aLong) {
						Log.w(TAG, "onNext: " + aLong);
					}

					@Override
					public void onError(Throwable e) {
						Log.e(TAG, "onError: " + e.getMessage());
					}

					@Override
					public void onComplete() {
						Log.i(TAG, "onComplete");
					}
				});
	}

}
