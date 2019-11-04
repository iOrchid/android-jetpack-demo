package org.zhiwei.jetpack.kt

import org.junit.Test
import org.zhiwei.jetpack.kt.common.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:15
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 主要用于演示Kotlin中集合Collections、List、Set以及相关的Map的Api操作
 */
class CollectionsUnitTest {

    @Test
    fun testColl() {
        CollectionsApi().testCC()
    }

    @Test
    fun testMapApi() {
        MapApi.testMap()
    }

    @Test
    fun testListApi() {
        ListApi.testList()
    }

    @Test
    fun testSetApi() {
        SetApi.testSet()
    }

    @Test
    fun testOperatorApi() {
        OperatorApi.testStr()
    }
}