package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateZip extends BaseOp {

	private static String TAG = "OperateZip";

	/*
	 *  zip操作符，合并数据操作，与concat、merge不同，这里会合并数据另为一个，且数量为最小observable的长度
	 */
	public static void doSome() {
		final String[] aStrings = {"A1", "A2", "A3", "A4"};
		final String[] bStrings = {"B1", "B2", "B3"};

		final Observable<String> aObservable = Observable.fromArray(aStrings);
		final Observable<String> bObservable = Observable.fromArray(bStrings);

		Observable.zip(aObservable, bObservable, (s, s2) -> s + "--" + s2)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}

}
