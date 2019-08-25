package org.zhiwei.jetpack.rxjava.op;

import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateSwitchMap extends BaseOp {

	private static String TAG = "OperateSwitchMap";

	/*
	 *  switchMap 转换输入流的Observable
	 */
	public static void doSome() {
		Observable.just("A", "B", "C", "D", "E", "F")
				.switchMap((Function<String, ObservableSource<String>>) s -> Observable.just(s + " @@"))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}

}
