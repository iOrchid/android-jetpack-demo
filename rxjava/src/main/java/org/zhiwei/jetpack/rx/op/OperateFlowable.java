package org.zhiwei.jetpack.rx.op;

import android.util.Log;

import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableFromObservable;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateFlowable extends BaseOp {

	private static String TAG = "OperateFlowable";

	/*
	 * flowable操作符，会瀑布流一般的扫落而进，可以设置偏移量，配合reduce
	 */
	public static void doSome() {
		FlowableFromObservable.just("a", "b", "c", "dd", "ad", "ce", "f")
				.reduce("seed", (s, s2) -> {
					//指定某个算法，在seed基础上，依次操作所有数据,这里的操作就是简单的字符拼接
					return s + s2;
				}).subscribe(new SingleObserver<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.d(TAG, "onSubscribe: " + d.isDisposed());
			}

			@Override
			public void onSuccess(String s) {
				Log.w(TAG, "onSuccess: " + s);
			}

			@Override
			public void onError(Throwable e) {
				Log.e(TAG, "onError: " + e.getMessage());
			}
		});
	}
}
