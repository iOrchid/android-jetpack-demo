package in.zhiwei.jetpack.rxjava.op;

import in.zhiwei.jetpack.rxjava.op.base.BaseOp;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateDelay extends BaseOp {

    private static String TAG = "OperateDelay";

    /*
     * delay 操作符，推后操作，延后
     */
    public static void doSome() {
        Observable.just("abc")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }
}
