package org.zhiwei.jetpack.kt.common

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2019年10月23日 10:18
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 演示Set的Api示例
 */
object SetApi {

	private val setOne = mutableSetOf(1, 8, 3, 2, 0, 100, 39, 93, 2033, 12, 3, 8)
	private val setTwo = mutableSetOf(12, 3, 8, 99, 22, 1, 0)
	private val setStr = mutableSetOf("a", "c,", "SSD", "dde", "ppt", "word")

	fun testSet() {
		//集合 转化为数组，
		setOne.toTypedArray()
		//todo 其实set，list,array 都是集合，整体上都具有Collections的api，所以这set的函数和listApi中的基本类似，便不做演示。
	}
}