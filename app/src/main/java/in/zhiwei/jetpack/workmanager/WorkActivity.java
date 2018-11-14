package in.zhiwei.jetpack.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.*;
import in.zhiwei.jetpack.R;

import java.util.concurrent.TimeUnit;

/**
 * workmanager使用演示
 * Author: zhiwei.
 * Date: 2018/11/6 0006,13:51.
 */
public class WorkActivity extends AppCompatActivity {

    private String name = "tmc";
    private int age = 18;
    //类似于intent的bundle
    Data data = new Data.Builder()
            .putString("name", name)
            .putInt("age", age)
            .build();

    //todo 这里workmanager的request有个高级用法，就是添加环境约束 ，比如网络、电量等
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)//联网状态
            .setRequiresBatteryNotLow(true)//低电量不操作
            .setRequiresCharging(true)//充电时候才开始
//            .setRequiresDeviceIdle(true)//待机状态下才执行，api 23 以上
            .setRequiresStorageNotLow(true)//存储空间不能太小
            .build();

    //2、创建一个workrequest 这里有onetime,还有个PeriodicWorkRequest
    private WorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
            .setConstraints(constraints)//添加约束
            .setInputData(data)//传递data到worker中
            .build();

    //todo worker的角色定位用于特殊的任务操作，可以脱离于本App的进程，所以这里的定期任务，做了最小限制，间隔至少15分钟，最小弹性伸缩时间为5分钟
    private WorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        //3、加入任务管理，但不是执行，执行的代码稍后
        WorkManager.getInstance().enqueue(workRequest);
        //4、通过workRequest的唯一标记id，来操作request，并获取返回数据
        //todo 这里因为在oncreate中执行，会先与work执行，toast会弹出未执行work的空结果，work变化后，还会显示出成功后的结果。这是因为observe监测worker的status变化 enqueued、RUNNING、successed、retry、failure等
        WorkManager.getInstance().getStatusByIdLiveData(workRequest.getId())
                .observe(this, workStatus -> {
                    //接收从worker中返回的任务结果,最好在这里判断status为success再做具体操作
                    if (workStatus.getState() == State.SUCCEEDED) {
                        Data data = workStatus.getOutputData();
                        String result = data.getString("result");
                        int status = data.getInt("status", 0);
                        Toast.makeText(WorkActivity.this, "result: " + result + " status: " + status, Toast.LENGTH_SHORT).show();
                        Log.i("WorkActivity", "workStatus: " + workStatus.getState().name());
                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance().cancelWorkById(workRequest.getId());
    }
}
