package org.zhiwei.jetpack.work

import android.app.Application
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
		assertEquals("org.zhiwei.jetpack.work.test", appContext.packageName)
	}
}
