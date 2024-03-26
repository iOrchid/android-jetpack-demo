package org.zhiwei.jetpack.components.ui

import android.icu.math.BigDecimal
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.zhiwei.jetpack.components.paging.Teacher
import org.zhiwei.jetpack.components.room.TeacherRepo
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
        MutableLiveData<String>()//一般mutable的都是可修改的，开发习惯，可修改额私有，对外提供不可变的。
    val liveScore: LiveData<String> = _liveStudentScore

    //endregion

    internal fun startSendScore() {
        //在viewModel的协程里，模拟间隔500ms变化一次随机数，在JetpackFragment中就会observe感知到。
        viewModelScope.launch {
            repeat(20) {
                delay(500)
                val randomNum = Random.nextDouble(20.0, 100.0)
                //将随机数保留2位小数
                val num = BigDecimal(randomNum).setScale(2, BigDecimal.ROUND_UP)
                _liveStudentScore.postValue("序号 $it , 随机数:$num")
            }
        }
        //map函数，就是将一种数据类型，按照你需要的规则，转化为另一种数据类型，然后得到一个新的liveData，可用于观察
        val map = _liveStudentScore.map { it.length }
        //switchMap是将上游livedata的数据，以新的liveData的形式重新发射出去。
        val switchMap = _liveStudentScore.switchMap { liveData { emit(it.length) } }

        //支持协程方式的liveData的创建方式
        liveData<Int> {
            repeat(10) {
                emit(it)
            }
            //emitSource可以发送另一个liveData的数据，只要它们是同类型的。
            emitSource(liveData { emit("false".length) })
            latestValue//获取最新值
        }

        viewModelScope.launch {
            //liveData的另一种协程方式的创建
            liveData<String>(coroutineContext, 2000L) {
                //这个就是包含有超时🏷️的liveData，如果在这里发送数据的时候，外层liveData的观察停止了，那么这里会在超时计时⌛️结束后，cancel掉协程；
                //如果在计时⌛️结束前，liveData重新活跃，正常继续。如果超时结束了，没有活跃，数据会丢弃；
                //如果超时后，liveData重新活跃，则数据会从头开始一次，或者可以手动判断逻辑，使用latestValue来处理。
                //如果整个协程块已经complete完成，或者其他原因（非liveData活跃变化）而cancel了，则不会再重发数据，即使liveData再来活跃observe也无济于事。
            }
        }
        //创建一个新的liveData，感知数据变化，如果临近的两次数据值是一样的，它不会感知变化。
        val distinctUntilChanged = _liveStudentScore.distinctUntilChanged()
        //flow和liveData可以互相转化
        flow<Int> { }.asLiveData()
        liveData<Int> { }.asFlow()

        //中介liveData,可用作桥接多个数据源，同一合并发送
        val liveOne = MutableLiveData<Int>()
        val liveTwo = MutableLiveData<Int>()
        val merge = MediatorLiveData<Int>()
        merge.addSource(liveOne) { merge.postValue(it) }
        merge.addSource(liveTwo) { merge.postValue(it) }

    }

    internal fun switchMapLive(): LiveData<Double> {
//        return  _liveStudentScore.map { it.length.toDouble() }
        return _liveStudentScore.switchMap { anotherLive(it) }
    }

    private fun anotherLive(string: String): LiveData<Double> {
        //string是  “序号 14 , 随机数：94.20” 这样的，所以要拆出来
        return liveData {
            val substringAfterLast = string.substringAfterLast(':')
            val doubleOrNull = substringAfterLast.toDoubleOrNull() ?: 0.0
            emit(doubleOrNull)
        }
    }

    private val repo = TeacherRepo()
    val teachers: Flow<PagingData<Teacher>> = Pager(
        config = PagingConfig(100, enablePlaceholders = false),
        pagingSourceFactory = { repo.loadPagingTeachers() }
    ).flow.cachedIn(viewModelScope)

}