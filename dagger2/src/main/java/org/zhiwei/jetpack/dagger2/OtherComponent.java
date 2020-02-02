package org.zhiwei.jetpack.dagger2;

import dagger.Component;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,13:51.
 */
@Component(modules = {OtherModule.class})
public interface OtherComponent {
	//对外提供必要信息
	String getInfo();
}
