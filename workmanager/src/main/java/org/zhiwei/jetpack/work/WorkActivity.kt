package org.zhiwei.jetpack.work

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 20:04
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * _              _           _     _   ____  _             _ _
 * / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 * / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 * / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 * /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * workmanager的演示界面
 */
class WorkActivity : AppCompatActivity() {
    //todo 这里workmanager的request有个高级用法，就是添加环境约束 ，比如网络、电量等
    private var constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) //联网状态
        .setRequiresBatteryNotLow(true) //低电量不操作
        .setRequiresCharging(true) //todo 充电时候才开始
//            .setRequiresDeviceIdle(true)//待机状态下才执行，api 23 以上
        .setRequiresStorageNotLow(true) //存储空间不能太小
        .build()


    private val name = "zhiwei"
    private val age = 18

    //类似于intent的bundle
    private var data = Data.Builder()
        .putString("name", name)
        .putInt("age", age)
        .build()

    //2、创建一个workrequest 这里有onetime,还有个PeriodicWorkRequest
    private val workRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
        .setConstraints(constraints) //添加约束,todo 这里的条件会限制执行结果，比如上面开启了充电时候才执行的限制
        .setInputData(data) //传递data到worker中
        .build()

    //todo worker的角色定位用于特殊的任务操作，可以脱离于本App的进程，所以这里的定期任务，做了最小限制，间隔至少15分钟，最小弹性伸缩时间为5分钟
    private val request: WorkRequest =
        PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        //3、加入任务管理，但不是立即执行，执行的代码稍后，满足条件时候会触发
        WorkManager.getInstance(this@WorkActivity)
            .enqueue(workRequest)

        //4、通过workRequest的唯一标记id，来操作request，并获取返回数据
//todo 这里因为在oncreate中执行，会先与work执行，toast会弹出未执行work的空结果，work变化后，还会显示出成功后的结果。这是因为observe监测worker的status变化 enqueued、RUNNING、successed、retry、failure等
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workStatus: WorkInfo ->
                //接收从worker中返回的任务结果,最好在这里判断status为success再做具体操作
                if (workStatus.state == WorkInfo.State.SUCCEEDED) {
                    val data = workStatus.outputData
                    val result = data.getString("result")
                    val status = data.getInt("status", 0)
                    Toast.makeText(
                        this@WorkActivity,
                        "result: $result status: $status",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.i("WorkActivity", "workStatus: " + workStatus.state.name)
            })

    }

    //<editor-folder desc="工作流 演示">
    fun test() {
        val workA = OneTimeWorkRequestBuilder<MyWorker>()
            .build();
        val workB = OneTimeWorkRequestBuilder<MyWorker>()
            .build();
        val workC = OneTimeWorkRequestBuilder<MyWorker>()
            .build();
        val workD = OneTimeWorkRequestBuilder<MyWorker>()
            .build();
        val workE = OneTimeWorkRequestBuilder<MyWorker>()
            .build();
        //串行 A->B->C
        WorkManager.getInstance(this)
            .beginWith(workA)
            .then(workB)
            .then(workC)
            .enqueue();
        //并行合流
        /*  A-> |
                |->C
            B-> |
         */
        WorkManager.getInstance(this)
            .beginWith(listOf(workA, workB))
            .then(workC)
            .enqueue();
        //分支 合并
        val chainA = WorkManager.getInstance(this).beginWith(workA).then(workB);
        val chainB = WorkManager.getInstance(this).beginWith(workC).then(workD);
        WorkContinuation.combine(listOf(chainA, chainB))
            .then(workE)
            .enqueue();//合并 A->B  C->D 两个之流后，再执行workE

        //避免任务的重复添加，保持唯一性
        WorkManager.getInstance(this)
            //使用unique，配置策略
            .beginUniqueWork("unique name", ExistingWorkPolicy.REPLACE, workA)
            .enqueue();
    }
//</editor-folder>

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this).cancelWorkById(workRequest.id)
    }
}