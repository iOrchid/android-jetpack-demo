package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.subjects.PublishSubject;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateReplay extends BaseOp {

	private static String TAG = "OperateReplay";

	/*
	 *  根据设定的replay容量大小，来缓存一个的数据指令，给下一个observer，即使是已经complete。需要配合ConnectableObservable使用
	 *
	 */
	public static void doSome() {
		PublishSubject<String> subject = PublishSubject.create();

		ConnectableObservable<String> connectableObservable = subject.replay(3);//默认是全部
		connectableObservable.connect();//开启关联

		subject.onNext("A");

		connectableObservable.subscribe(getObserver(TAG, "First"));//也会有A指令，因为replay=3

		subject.onNext("B");
		subject.onNext("C");
		subject.onNext("D");

		connectableObservable.subscribe(getObserver(TAG, "Second"));

		subject.onNext("E");
		subject.onNext("F");
		subject.onComplete();

		connectableObservable.subscribe(getObserver(TAG, "Third"));//只有D、E、F 3 个 以及complete指令，

        /*
        First d.isDisposed():false
        First A
        First B
        First C
        First D
        Second d.isDisposed():false
        Second B
        Second C
        Second D
        First E
        Second E
        First F
        Second F
        First onComplete
        Second onComplete
        Third d.isDisposed():false
        Third D
        Third E
        Third F
        Third onComplete



         */
	}

}
