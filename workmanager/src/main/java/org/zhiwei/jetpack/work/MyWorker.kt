package org.zhiwei.jetpack.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * 继承worker，实现doWork，类似于异步任务
 * Author: zhiwei.
 * Date: 2018/11/6 0006,13:52.
 */
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
	//1、创建worker，执行任务的执行者
	override fun doWork(): Result { //在workRequest中传递来的data，
		val data = inputData
		val name = data.getString("name")
		val age = data.getInt("age", 0)
		//do some work
		Log.i("test", "worker doWork() name: $name age: $age")
		//返回任务的结果数据，
		val out = Data.Builder()
			.putString("result", "哈哈哈，真的可以返回呀")
			.putInt("status", 200)
			.build()
		return Result.success(out)
	}
}