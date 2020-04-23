package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateScan extends BaseOp {

	private static String TAG = "OperateScan";

	/*
	 *  scan操作符，向下依次遍历，类似于reduce
	 */
	public static void doSome() {
		Observable.just("A", "B", "C", "D", "E", "F")
				.scan((s, s2) ->
						//指定某个算法，依次执行运算
						s + s2
				)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}

}
