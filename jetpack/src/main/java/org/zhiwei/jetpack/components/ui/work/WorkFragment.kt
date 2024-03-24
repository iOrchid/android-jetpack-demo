package org.zhiwei.jetpack.components.ui.work

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.zhiwei.jetpack.components.R
import java.util.concurrent.TimeUnit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 20:04
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * workmanager的演示界面
 */
class WorkFragment : Fragment() {
    //因为使用了safe args插件，且在graph中声明了参数，这里可以获取对应的入参
    private val args by navArgs<WorkFragmentArgs>()

    //todo 这里workmanager的request有个高级用法，就是添加环境约束 ，比如网络、电量等
    private var constraints: Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) //联网状态
        .setRequiresBatteryNotLow(true) //低电量不操作
//		.setRequiresCharging(true) // TODO 充电时候才开始,这个条件开启后，测试机不充电则无演示效果
//		.setRequiresDeviceIdle(true)//待机状态下才执行，api 23 以上,此处开启的话，手机熄屏才会执行了
        .setRequiresStorageNotLow(true) //存储空间不能太小
        .build()

    //类似于intent的bundle，可用workData{}函数更方便。
    private var data = Data.Builder()
        .putString("name", "Kotlin小娜娜")
        .putInt("age", 18)
        .build()

    //2、创建一个workrequest 这里有onetime,还有个PeriodicWorkRequest
    private val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
        .setConstraints(constraints) //添加约束
        .setInputData(data) //传递data到worker中
        .build()
    private val jetWorkRequest = OneTimeWorkRequest.Builder(JetWork::class.java)
        .setInputData(workDataOf("inputStr" to "🚀🐯"))
        .build()

    //todo worker的角色定位用于特殊的任务操作，可以脱离于本App的进程，所以这里的定期任务，做了最小限制，间隔至少15分钟，最小弹性伸缩时间为5分钟
    private val request: WorkRequest =
        PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WorkManager.initialize(
//            requireContext(),
//            Configuration.Builder()
//                .setDefaultProcessName("WorkJet")
//                .build()
//        )//一般会默认自动初始化，如果需要自定义config的话，可以手动个初始化。
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //使用navigation返回上个fragment
        view.findViewById<Button>(R.id.btn_back_work)
            .setOnClickListener { findNavController().navigateUp() }
        val tvRet = view.findViewById<TextView>(R.id.tv_ret_work)
        //3、加入任务管理，但不是执行，执行的代码稍后
//        WorkManager.getInstance(requireContext()).enqueue(workRequest)
        WorkManager.getInstance(requireContext())
            .beginWith(workRequest)
            .then(jetWorkRequest)
            .enqueue()
        //4、通过workRequest的唯一标记id，来操作request，并获取返回数据
        lifecycleScope.launch {
            WorkManager.getInstance(requireContext())
                .getWorkInfosFlow(WorkQuery.fromIds(workRequest.id, jetWorkRequest.id))
                .collect { infos ->
                    //todo 这里可能重复执行，因为work变化后，还会显示出成功后的结果。
                    // 这是因为observe监测worker的status变化 enqueued、RUNNING、successed、retry、failure等
                    //所以业务自己判断，或者单独观察每个work的结果
                    if (infos.all { it.state == WorkInfo.State.SUCCEEDED }) {
                        //只有所有任务都successed的时候，再执行
                        infos.forEach { workStatus: WorkInfo ->
                            //接收从worker中返回的任务结果,最好在这里判断status为success再做具体操作
                            if (workStatus.state == WorkInfo.State.SUCCEEDED) {
                                val data = workStatus.outputData
                                val result = data.getString("result")
                                val status = data.getInt("status", 0)

                                //由于forEach太快，两次结果执行出来，设置text可能变化太快，而在UI上看不出来有两次，
                                delay(2000)//故意延迟以下，让UI上看出效果
                                val str = "work的执行结果： $result 状态status字段： $status"
                                Log.d("Jetpack WorkActivity", "onViewCreated: 执行结果 $str")
                                tvRet.text = str
                                Toast.makeText(
                                    requireContext(),
                                    "result: $result status: $status",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            Log.i(
                                "Jetpack WorkActivity",
                                "状态workStatus: " + workStatus.state.name
                            )
                        }
                    }

                }
        }

        Log.d("Jetpack Work", "接收navigation的参数: ${args.taskName} ,, ${args.taskTime} ")
    }

    //<editor-folder desc="工作流 演示">
    /*{
        OneTimeWorkRequest workA = null, workB = null, workC = null, workD = null, workE = null;
        //串行
        WorkManager.getInstance().beginWith(workA)
                .then(workB)
                .then(workC)
                .enqueue();
        //并行合流
        WorkManager.getInstance().beginWith(workA, workB)
                .then(workC)
                .enqueue();
        //分支 合并
        WorkContinuation chainA = WorkManager.getInstance().beginWith(workA).then(workB);
        WorkContinuation chainB = WorkManager.getInstance().beginWith(workC).then(workD);
        WorkContinuation.combine(chainA, chainB)
                .then(workE)
                .enqueue();//合并 A->B  C->D 两个之流后，再执行workE

        //避免任务的重复添加，保持唯一性
        WorkManager.getInstance()
                //使用unique，配置策略
                .beginUniqueWork("unique name",ExistingWorkPolicy.REPLACE,workA)
                .enqueue();
    }*/
    //</editor-folder>
    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(requireContext()).cancelWorkById(workRequest.id)
    }
}

/**
 * Work库中的worker角色的类定义，此处使用的是协程形式的worker
 */
class JetWork(context: Context, private val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    /**
     * 具体执行后台任务的部分，此处可以使用协程作用域
     */
    override suspend fun doWork(): Result {
        val inputStr = workerParameters.inputData.getString("inputStr")
        val scope = coroutineScope {
            val aOne = async {
                delay(2000)
                "$inputStr 延迟2s后的结果😄"
            }
            val aTwo = async {
                delay(3000)
                "3s后的😱"
            }
            aOne.await() + aTwo.await()
        }
        //构造返回结果的数据，传统方式和workDataOf都行。
        val data = Data.Builder().putString("result", scope).build()
        return Result.success(workDataOf("result" to scope))
    }

}