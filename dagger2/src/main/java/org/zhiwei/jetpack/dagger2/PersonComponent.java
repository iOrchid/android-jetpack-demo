package org.zhiwei.jetpack.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,12:00.
 */
@Singleton
@Component(dependencies = {OtherComponent.class}, modules = {PersonModule.class})
//dagger注解，连接器，用于链接module与activity、使用方,可以多个module.dependencies可以添加其他依赖的component，那么就能使用其他的compoent内对象的信息
public interface PersonComponent {

	void connectIt(DaggerActivity daggerActivity);

}
