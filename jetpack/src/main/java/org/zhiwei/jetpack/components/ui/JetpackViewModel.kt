package org.zhiwei.jetpack.components.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.nextDown
import kotlin.random.Random

/**
 * 作用于JetpackActivity和JetpackFragment的viewModel；
 * viewModel作为数据管理层，可以给多个fragment共用。
 *
 */
class JetpackViewModel : ViewModel() {
    //一般使用ViewModel作为基类即可，另有AndroidViewModel，内含application参数，根据业务场景来选择使用与否。

    //region liveData
    private val _liveStudentScore =
        MutableLiveData<Double>()//一般mutable的都是可修改的，开发习惯，可修改额私有，对外提供不可变的。
    val liveScore: LiveData<Double> = _liveStudentScore

    //endregion

    internal fun startSendScore() {
        viewModelScope.launch {
            repeat(20) {
                delay(500)
                _liveStudentScore.postValue(Random.nextDouble(20.0, 100.0).nextDown())
            }
        }

    }

}