package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.subjects.ReplaySubject;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateReplaySubject extends BaseOp {

	private static String TAG = "OperatePublishSubject";

	/*
	 *  replay重复发送之前的指令，并保持原有顺序，给新的observer
	 */
	public static void doSome() {
		ReplaySubject<String> subject = ReplaySubject.create();
		subject.onNext("A");
		subject.subscribe(getObserver(TAG, "First"));
		subject.onNext("B");
		subject.onNext("C");
		subject.onNext("D");
		subject.onNext("E");
		subject.onNext("F");
		subject.onComplete();

		subject.subscribe(getObserver(TAG, "Second"));


        /*
        First d.isDisposed():false
        First A
        First B
        First C
        First D
        First E
        First F
        First onComplete
        Second d.isDisposed():false
        Second A
        Second B
        Second C
        Second D
        Second E
        Second F
        Second onComplete
         */
	}

}
