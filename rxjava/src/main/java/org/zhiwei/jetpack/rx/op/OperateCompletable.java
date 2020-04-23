package org.zhiwei.jetpack.rx.op;

import android.util.Log;

import org.zhiwei.jetpack.rx.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
