package org.zhiwei.jetpack.work

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

/**
 * 继承worker，实现doWork，类似于异步任务
 * Author: zhiwei.
 * Date: 2018/11/6 0006,13:52.
 */
class MyWorker(private val context: Context, private val workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    //1、创建worker，执行任务的执行者
    override suspend fun doWork(): Result { //在workRequest中传递来的data，
        val data = inputData
        val name = data.getString("name")
        val age = data.getInt("age", 0)
        //do some work
        delay(5 * 1000)
        context.startActivity(Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse("https://www.baidu.com")
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })

        Log.i("test", "worker doWork() name: $name age: $age")

        //返回任务的结果数据，
        val out = Data.Builder()
            .putString("result", "哈哈哈，真的可以返回呀")
            .putInt("status", 200)
            .build()
        return Result.success(out)
    }
}