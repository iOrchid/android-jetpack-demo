package in.zhiwei.jetpack.rxjava.op.base;

import android.util.Log;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:28.
 */
public class BaseOp {

    public static Observer<String> getObserver(String tag, String flag) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(tag, flag + " d.isDisposed():" + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                Log.w(tag, flag + " " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(tag, flag + " e:" + e);
            }

            @Override
            public void onComplete() {
                Log.i(tag, flag + " onComplete");
            }
        };
    }
}
