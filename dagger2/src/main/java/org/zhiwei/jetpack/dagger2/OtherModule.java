package org.zhiwei.jetpack.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,13:52.
 */
@Module()
public class OtherModule {
	String info;

	public OtherModule(String info) {
		this.info = info;
	}

	@Provides
	public String provideInfo() {
		return this.info;
	}
}
