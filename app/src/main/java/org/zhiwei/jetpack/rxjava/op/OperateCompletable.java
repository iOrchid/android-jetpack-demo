package org.zhiwei.jetpack.rxjava.op;

import android.util.Log;

import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateCompletable extends BaseOp {
	private static String TAG = "OperateCompletable";

	//completable操作符，只关心 发送完成指令
	public static void doSome() {

		Completable completable = Completable.timer(1000, TimeUnit.MILLISECONDS);

		completable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CompletableObserver() {
					@Override
					public void onSubscribe(Disposable d) {
						Log.d(TAG, " onSubscribe : " + d.isDisposed());
					}

					@Override
					public void onComplete() {
						Log.i(TAG, " onComplete");
					}

					@Override
					public void onError(Throwable e) {
						Log.e(TAG, " onError : " + e.getMessage());
					}
				});
	}

}
