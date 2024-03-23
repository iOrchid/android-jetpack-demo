package org.zhiwei.jetpack.components.ui.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 继承worker，实现doWork，类似于异步任务
 * Author: zhiwei.
 * Date: 2018/11/6 0006,13:52.
 */
public class MyWorker extends Worker {

	/**
	 * 必须有一个构造函数，调用基类的构造函数
	 *
	 * @param context
	 * @param workerParams
	 */
	public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	//1、创建worker，执行任务的执行者
	@NonNull
	@Override
	public Result doWork() {
		//在workRequest中传递来的data，
		Data data = getInputData();
		String name = data.getString("name");
		int age = data.getInt("age", 0);
		//do some work
		Log.i("test", "worker doWork() name: " + name + " age: " + age);
		//返回任务的结果数据，
		Data out = new Data.Builder()
				.putString("result", "哈哈哈，真的可以返回呀")
				.putInt("status", 200)
				.build();
		return Result.success(out);
	}
}
