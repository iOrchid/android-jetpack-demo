package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.core.Observable;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateDefer extends BaseOp {

	private static String TAG = "OperateDefer";

	/*
	 * defer操作符，延迟加载
	 */
	public static void doSome() {

		Car car = new Car();
		//这里car已经实例化，传统方式，get的肯定为null
		Observable<String> brandDeferObservable = car.getBrand();
		//set在get调用之后，
		car.setBrand("BMW");

		brandDeferObservable.subscribe(getObserver(TAG, ""));
	}

	static class Car {
		String brand;

		//todo 实现延迟加载 defer操作符
		public Observable<String> getBrand() {
			return Observable.defer(() -> Observable.just(brand));
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}
	}

}
