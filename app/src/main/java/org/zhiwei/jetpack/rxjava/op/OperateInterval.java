package org.zhiwei.jetpack.rxjava.op;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import org.zhiwei.jetpack.rxjava.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateInterval extends BaseOp {

    private static String TAG = "OperateInterval";

    /*
     * interval操作符，间隔指定时间,一直发送数字指令，默认从0开始的Long类型 ，同类的还有intervalRange会指定一个上限，
     * 可用作计时、倒计时
     *  accept: 0
        accept: 1
        accept: 2
        accept: 3
        accept: 4
     */
    public static void doSome() {
        Observable.intervalRange(0L, 5, 0, 2, TimeUnit.SECONDS)
//                .interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> Log.i(TAG, "accept: " + aLong));
    }
}
