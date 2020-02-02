package org.zhiwei.jetpack.live

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * live属性的user数据类
 * Author: zhiwei.
 * Date: 2018/11/5 0005,11:17.
 */
class LiveUser(//普通属性字段
    var city: String, name: String, age: Int
) {
    val name: MutableLiveData<String> = MediatorLiveData() //live响应的属性
    val age = MutableLiveData<Int>() //live属性

    fun setName(name: String) {
        this.name.value = name
    }

    fun setAge(age: Int) {
        this.age.value = age
    }

    init {
        this.name.value = name
        this.age.value = age
    }
}