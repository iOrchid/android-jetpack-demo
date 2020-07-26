package org.zhiwei.jetpack.life.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

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
 * ViewModel
 *
 */
class Vvm : ViewModel() {

    val vvm = "vvm 的 vvm"

    init {
        Log.d("ViewModel", "Vvm init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("ViewModel", "Vvm Cleared")
    }
}

//android application 的viewModel
class VaVm(application: Application) : AndroidViewModel(application) {

    val vam = "vApplication 的 vavm"


    init {
        Log.d("ViewModel", "VaVm init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("ViewModel", "VaVm Cleared")
    }
}