package org.zhiwei.jetpack.rx;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.zhiwei.jetpack.rx.op.OperateAsyncSubject;
import org.zhiwei.jetpack.rx.op.OperateBehaviorSubject;
import org.zhiwei.jetpack.rx.op.OperateBuffer;
import org.zhiwei.jetpack.rx.op.OperateCompletable;
import org.zhiwei.jetpack.rx.op.OperateConcat;
import org.zhiwei.jetpack.rx.op.OperateDebounce;
import org.zhiwei.jetpack.rx.op.OperateDefer;
import org.zhiwei.jetpack.rx.op.OperateDelay;
import org.zhiwei.jetpack.rx.op.OperateDisposable;
import org.zhiwei.jetpack.rx.op.OperateDistinct;
import org.zhiwei.jetpack.rx.op.OperateFilter;
import org.zhiwei.jetpack.rx.op.OperateFlowable;
import org.zhiwei.jetpack.rx.op.OperateInterval;
import org.zhiwei.jetpack.rx.op.OperateLast;
import org.zhiwei.jetpack.rx.op.OperateMap;
import org.zhiwei.jetpack.rx.op.OperateMerge;
import org.zhiwei.jetpack.rx.op.OperatePublishSubject;
import org.zhiwei.jetpack.rx.op.OperateReplay;
import org.zhiwei.jetpack.rx.op.OperateReplaySubject;
import org.zhiwei.jetpack.rx.op.OperateScan;
import org.zhiwei.jetpack.rx.op.OperateSkip;
import org.zhiwei.jetpack.rx.op.OperateSwitchMap;
import org.zhiwei.jetpack.rx.op.OperateTake;
import org.zhiwei.jetpack.rx.op.OperateThrottleFirst;
import org.zhiwei.jetpack.rx.op.OperateThrottleLast;
import org.zhiwei.jetpack.rx.op.OperateTimer;
import org.zhiwei.jetpack.rx.op.OperateWindow;
import org.zhiwei.jetpack.rx.op.OperateZip;


/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,19:23.
 */
public class RxActivity extends AppCompatActivity {
	private EditText etInput;
	private int index;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx);
		etInput = findViewById(R.id.et_rx);
//        Observable.amb();//集合之多9个observable，使之竞争，夺得连接权的，将会执行，其他observable丢弃。
//        Observable.combineLatest();
//        Observable.sequenceEqual();
//        Observable.switchOnNext();

	}

	public void doSome(View view) {
		String s = etInput.getText().toString();
		if (s.isEmpty()) {
			Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
			return;
		}
		index = Integer.parseInt(s);
		switch (index) {
			case 0:
				OperateAsyncSubject.doSome();
				break;
			case 1:
				OperateBehaviorSubject.doSome();
				break;
			case 2:
				OperateBuffer.doSome();
				break;
			case 3:
				OperateCompletable.doSome();
				break;
			case 4:
				OperateConcat.doSome();
				break;
			case 5:
				OperateDebounce.doSome();
				break;
			case 6:
				OperateDefer.doSome();
				break;
			case 7:
				OperateDelay.doSome();
				break;
			case 8:
				OperateDisposable.doSome();
				break;
			case 9:
				OperateDistinct.doSome();
				break;
			case 10:
				OperateFilter.doSome();
				break;
			case 11:
				OperateFlowable.doSome();
				break;
			case 12:
				OperateInterval.doSome();
				break;
			case 13:
				OperateLast.doSome();
				break;
			case 14:
				OperateMap.doSome();
				break;
			case 15:
				OperateMerge.doSome();
				break;
			case 16:
				OperatePublishSubject.doSome();
				break;
			case 17:
				OperateReplay.doSome();
				break;
			case 18:
				OperateReplaySubject.doSome();
				break;
			case 19:
				OperateScan.doSome();
				break;
			case 20:
				OperateSkip.doSome();
				break;
			case 21:
				OperateSwitchMap.doSome();
				break;
			case 22:
				OperateTake.doSome();
				break;
			case 23:
				OperateThrottleFirst.doSome();
				break;
			case 24:
				OperateThrottleLast.doSome();
				break;
			case 25:
				OperateTimer.doSome();
				break;
			case 26:
				OperateWindow.doSome();
				break;
			case 27:
				OperateZip.doSome();
				break;
			default:
				Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
