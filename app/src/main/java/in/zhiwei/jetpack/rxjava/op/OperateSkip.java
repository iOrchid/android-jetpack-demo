package in.zhiwei.jetpack.rxjava.op;

import in.zhiwei.jetpack.rxjava.op.base.BaseOp;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateSkip extends BaseOp {

    private static String TAG = "OperateSkip";

    /*
     *  skip会跳跃过指定数目的指令
     */
    public static void doSome() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .skip(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

}
