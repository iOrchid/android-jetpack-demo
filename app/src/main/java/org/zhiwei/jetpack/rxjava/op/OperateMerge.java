package org.zhiwei.jetpack.rxjava.op;

import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateMerge extends BaseOp {

	private static String TAG = "OperateMerge";

	/*
	 * merge操作符，合并成一个observable发布，类似与concat，但是不会保证合并后的顺序与之前一致。
	 * 这里数据量小，并未有耗时操作，一般情况还是顺序一致的
	 */
	public static void doSome() {
		final String[] aStrings = {"A1", "A2", "A3", "A4"};
		final String[] bStrings = {"B1", "B2", "B3"};

		final Observable<String> aObservable = Observable.fromArray(aStrings);
		final Observable<String> bObservable = Observable.fromArray(bStrings);

		Observable.merge(aObservable, bObservable)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}

}
