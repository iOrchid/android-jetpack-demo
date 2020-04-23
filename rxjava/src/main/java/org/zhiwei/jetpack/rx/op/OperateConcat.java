package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.core.Observable;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateConcat extends BaseOp {

	private static String TAG = "OperateConcat";

	/*
	 * concat组合操作符，会保留原有的顺序，组合成一个observable发布
	 */
	public static void doSome() {
		final String[] aStrings = {"A1", "A2", "A3", "A4"};
		final String[] bStrings = {"B1", "B2", "B3"};

		final Observable<String> aObservable = Observable.fromArray(aStrings);
		final Observable<String> bObservable = Observable.fromArray(bStrings);

		Observable.concat(aObservable, bObservable)
				.subscribe(getObserver(TAG, ""));
	}

}
