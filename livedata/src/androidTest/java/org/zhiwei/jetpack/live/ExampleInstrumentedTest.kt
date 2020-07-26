package org.zhiwei.jetpack.live

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {
	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = ApplicationProvider.getApplicationContext<Application>()
		assertEquals("org.zhiwei.jetpack.live.test", appContext.packageName)
	}

	/**
	 * LiveEvent的测试
	 */
	@Test
	fun testLiveEvent() {
		val lowEvent = MutableLiveData<LowLiveEvent<String>>()

//		lowEvent.observe(viewLifeCycleOwner, Observer {
//			it.getContentIfNotHandled()?.let {
//				//这样处理，避免liveEvent的多次触发
//			}
//		})
//
//		val singleLive = SingleLiveEvent<String>()
//		singleLive.observe(viewLifeCycleOwner, Observer {
//			//同理
//		})
	}
}
