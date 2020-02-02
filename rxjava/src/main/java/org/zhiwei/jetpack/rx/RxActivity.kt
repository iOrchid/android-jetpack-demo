package org.zhiwei.jetpack.rx

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.zhiwei.jetpack.rx.op.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 20:02
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * _              _           _     _   ____  _             _ _
 * / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 * / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 * / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 * /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * rxJava的演示界面
 */
class RxActivity : AppCompatActivity() {
	private var etInput: EditText? = null
	private var index = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_rx)
		etInput = findViewById(R.id.et_rx)
		//        Observable.amb();//集合之多9个observable，使之竞争，夺得连接权的，将会执行，其他observable丢弃。
//        Observable.combineLatest();
//        Observable.sequenceEqual();
//        Observable.switchOnNext();
	}

	fun doSome(view: View?) {
		val s = etInput!!.text.toString()
		if (s.isEmpty()) {
			Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show()
			return
		}
		index = s.toInt()
		when (index) {
			0 -> OperateAsyncSubject.doSome()
			1 -> OperateBehaviorSubject.doSome()
			2 -> OperateBuffer.doSome()
			3 -> OperateCompletable.doSome()
			4 -> OperateConcat.doSome()
			5 -> OperateDebounce.doSome()
			6 -> OperateDefer.doSome()
			7 -> OperateDelay.doSome()
			8 -> OperateDisposable.doSome()
			9 -> OperateDistinct.doSome()
			10 -> OperateFilter.doSome()
			11 -> OperateFlowable.doSome()
			12 -> OperateInterval.doSome()
			13 -> OperateLast.doSome()
			14 -> OperateMap.doSome()
			15 -> OperateMerge.doSome()
			16 -> OperatePublishSubject.doSome()
			17 -> OperateReplay.doSome()
			18 -> OperateReplaySubject.doSome()
			19 -> OperateScan.doSome()
			20 -> OperateSkip.doSome()
			21 -> OperateSwitchMap.doSome()
			22 -> OperateTake.doSome()
			23 -> OperateThrottleFirst.doSome()
			24 -> OperateThrottleLast.doSome()
			25 -> OperateTimer.doSome()
			26 -> OperateWindow.doSome()
			27 -> OperateZip.doSome()
			else -> Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_SHORT).show()
		}
	}
}