package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.core.Observable;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateDistinct extends BaseOp {

	private static String TAG = "OperateDistinct";

	/*
	 * distinct 自动去重复，后面若出现前面已经有的数据的时候，会自动跳过。
	 */
	public static void doSome() {
		Observable.just("a", "b", "c", "f", "ad", "c", "f", "g")
				.distinct()
				.subscribe(getObserver(TAG, ""));
	}
}
