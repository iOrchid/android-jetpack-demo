package org.zhiwei.jetpack.lifecycle;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.zhiwei.jetpack.R;

/**
 * 演示lifeCycle的使用
 * Author: zhiwei.
 * Date: 2018/11/5 0005,14:26.
 */
public class LifeActivity extends AppCompatActivity {

    private LocationListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        //AppcompatActivity 的基类 componentActivity实现了lifecycle接口
        mListener = new LocationListener(this, () -> {
            //更新UI的定位信息
        });
        //检测网络、用户信息之类的，这里存在耗时，
//        Utils.checkInfo(result -> if (result) {
//            //检测完成，才会enable，然后listener里面才会真正的start操作
//            mListener.enable();
//        });
    }

/*
    这里面就不用onStart 和onStop了，因为lifecycle可自动感知生命周期

  @Override
    protected void onStart() {
        super.onStart();
        mListener.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mListener.onStop();
    }
*/

}