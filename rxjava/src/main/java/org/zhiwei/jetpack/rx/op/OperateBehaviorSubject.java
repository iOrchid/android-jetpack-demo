package org.zhiwei.jetpack.rx.op;


import org.zhiwei.jetpack.rx.op.base.BaseOp;

import io.reactivex.rxjava3.subjects.BehaviorSubject;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateBehaviorSubject extends BaseOp {
	private static String TAG = "OperateBehaviorSubject";

	//Behavior操作符，不同于Async的发送complete以及最后指令。它会发送切换observer之前的一条数据，然后也同步发送给所有observer，切换后的指令。
	public static void doSome() {
		BehaviorSubject<String> subject = BehaviorSubject.create();
		subject.onNext("a");
		subject.onNext("b");
		subject.subscribe(getObserver(TAG, "first"));//todo 会接收到自身上线前的最新一条指令，以及之后所有指令。
		subject.onNext("c");
		subject.onNext("d");
		subject.subscribe(getObserver(TAG, "second"));//接收一条上线前最新指令，然后是所有之后的指令。（只要不下线）
		subject.onNext("e");
		subject.onNext("f");
		subject.onComplete();
        /*
            first d.isDisposed():false
            first b
            first c
            first d
            second d.isDisposed():false
            second d
            first e
            second e
            first f
            second f
            first onComplete
            second onComplete

         */
	}

}
