package org.zhiwei.jetpack.life.vm

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lifecycle.*
import org.zhiwei.jetpack.life.R

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 简单viewModel的演示使用
 */
class VmActivity : AppCompatActivity() {
    //创建方法
    val vm by viewModels<Vvm> {
        defaultViewModelProviderFactory
    }

    val vavm by viewModels<VaVm> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        tv_flag_life_vm.text = "当前为vm的演示界面"
        btn_life_vm.text = "关闭"
        btn_life_vm.setOnClickListener { finish() }

        Log.i("VmActivity", "vm的值 ${vm.vvm}")
        Log.i("VmActivity", "----vavm的值---- ${vavm.vam}")
        //创建vm的方式 其实原理一样的
//        val vvv = ViewModelProvider(this).get(Vvm::class.java)
//        val vaa = ViewModelProvider(this).get(VaVm::class.java)
//
//        Log.d("VmActivity", "vm的值 ${vvv.vvm}")
//        Log.d("VmActivity", "----vavm的值---- ${vaa.vam}")

    }
}