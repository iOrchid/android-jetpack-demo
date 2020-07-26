package org.zhiwei.jetpack.life

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_lifecycle.*
import org.zhiwei.jetpack.life.vm.VmActivity

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年08月05日 19:56
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * LifeCycle的演示界面
 */
class LifeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        btn_life_vm.setOnClickListener {
            startActivity(Intent(this, VmActivity::class.java))
        }
        //调用定位
        MockLocation(this, object : MockLocation.LocationCallBack {
            override fun onSuccess() {
                //此时的回调，就是确保了，已经在UI显示的状态，

            }

        }).startLocation()

        lifecycleScope.launchWhenCreated {

        }

    }
    /*
    1、使用mockLocation，此LifeActivity是ComponentActivity的子类，所以也是LifeCycleOwner的实现类
    2、MOckLocation模拟定位服务，这里面就持有了lifeCycleOwner，且其实现了LifeCycleObserver，就能感知到UI的生命周期
    3、如此调用，则定位服务就能根据生命周期，来决定回调。
    4、倘若没有这类生命周期管理，需要手动关联，onStart定位 onStop 停止，则会出现在onCreate中调用startLocation的话，
    由于某些异常情况，导致可能会出现，先走到了location的onStop，后 onStart，可能就出异常报错。
     */

}