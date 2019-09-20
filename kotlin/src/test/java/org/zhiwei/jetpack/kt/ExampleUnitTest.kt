package org.zhiwei.jetpack.kt

import org.junit.Assert.assertEquals
import org.junit.Test
import org.zhiwei.jetpack.kt.base.KtClazz4
import org.zhiwei.jetpack.kt.base.User

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		assertEquals(4, (2 + 2).toLong())
	}

	@Test
	fun testKt() {
		println("Byte范围: ${Byte.MIN_VALUE}~${Byte.MAX_VALUE}")
		println("Short范围: ${Short.MIN_VALUE}~${Short.MAX_VALUE}")
		println("Int范围: ${Int.MIN_VALUE}~${Int.MAX_VALUE}")
		println("Long范围: ${Long.MIN_VALUE}~${Long.MAX_VALUE}")
		println("Float范围: ${Float.MIN_VALUE}~${Float.MAX_VALUE}")
		println("Double范围: ${Double.MIN_VALUE}~${Double.MAX_VALUE}")
	}

	@Test
	fun testKtClazz() {
		val tt = KtClazz4("名字")
		val (name, age, sex, desc) = User("小明", 22, 1, "小明是个男的，大学生一枚")
		println(tt.toString())
	}
}