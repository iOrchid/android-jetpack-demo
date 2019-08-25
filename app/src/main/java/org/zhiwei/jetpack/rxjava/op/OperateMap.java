package org.zhiwei.jetpack.rxjava.op;

import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateMap extends BaseOp {

	private static String TAG = "OperateMap";

	/*
	 * map操作符，用于映射转换
	 */
	public static void doSome() {
		//这里fromArray，里面的参数会被逐个发送出去
		Observable.fromArray(1, 23, 453, 123, 51, 54, 2)
				.map(integer -> integer + " str")//将int转为string
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(getObserver(TAG, ""));
	}
}
