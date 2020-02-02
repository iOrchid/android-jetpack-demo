package org.zhiwei.jetpack.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,11:45.
 */
@Module//dagger中的注解标记，module表示为一个对象提供工厂
public class PersonModule {

	private Person person;

	//dagger的注解标记，对外提供方法的一个tag，一个module只能有一个 实例化对象的标记。若是多个构造函数，则需要使用named标记区分,且在调用方，需要named知名inject调用的构造函数
//    @Singleton
//    @Named("default")
	@PersonDefault
	@Provides
	public Person providePerson() {
//        return new Person(29, "小强", "南京");
//        return person;
		return new Person();//
	}

	//    @Named("info")
	@PersonInfo
	@Provides
	public Person getPersonWithInfo(String info) {
		return new Person(23, info, "拉萨");
	}

	//含参数的对象创建。也可以在personModule的构造函数中接收参数，
	public void setParams(int age, String name, String city) {
		person = new Person(age, name, city);
	}

   /* private Context context;

    PersonModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Provides
    public Person providePerson(Context context) {
        return new Person(context);
    }*/
}
