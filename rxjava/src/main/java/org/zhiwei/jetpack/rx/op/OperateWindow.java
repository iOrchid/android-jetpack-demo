package org.zhiwei.jetpack.rx.op;

import android.util.Log;

import org.zhiwei.jetpack.rx.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateWindow extends BaseOp {

	private static String TAG = "OperateWindow";

	/*
	 * window操作符，在正常的操作之中插入某个操作，
	 *   -----插入操作开始------
		 Next:0
		 Next:1
		 -----插入操作开始------
		 Next:2
		 Next:3
		 Next:4
		 -----插入操作开始------
		 Next:5
		 Next:6
		 Next:7
		 -----插入操作开始------
		 Next:8
		 Next:9
		 Next:10
		 -----插入操作开始------
		 Next:11
	 *
	 *
	 *
	 */
	public static void doSome() {
		Observable.interval(1, TimeUnit.SECONDS).take(12)
				.window(3, TimeUnit.SECONDS)//每3秒，执行一次插入操作,从连接上就开始计时。
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getConsumer());
	}

	private static Consumer<Observable<Long>> getConsumer() {
		return observable -> {
			observable
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(value -> Log.d(TAG, "Next:" + value));
			Log.w(TAG, "-----插入操作开始------");
		};
	}


}
