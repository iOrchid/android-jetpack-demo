package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
