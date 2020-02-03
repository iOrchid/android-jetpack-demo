package org.zhiwei.jetpack.dagger2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;

/**
 * Dagger2演示使用
 * Author: zhiwei.
 * Date: 2018/11/7 0007,11:39.
 */
public class DaggerActivity extends AppCompatActivity {

	//    @Named("default")
	@PersonDefault
	@Inject
//    Person inPerson;//使用Dagger2的注入
			Lazy<Person> inPerson;//使用懒加载，在需要的地方，就要用inPerson.get()
	//    @Named("info")
	@PersonInfo
	@Inject
	Provider<Person> inPerson2;
	private Person person = new Person(10, "小明", "上海");//普通的引用方式
//    Person inPerson2;//第二个注入，若@component、@Provides尚没有singleton的标记，则，这两个inject的person，是不同的。

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dagger);
		TextView tvCommon = findViewById(R.id.tv_info_common_person);
		TextView tvInject = findViewById(R.id.tv_info_inject_person);

		//dagger依赖注入,类似于DataBinding的生成，然后注入关联。
		OtherComponent otherComponent = DaggerOtherComponent.builder().otherModule(new OtherModule("第三方info")).build();//被依赖对象的创建

		PersonModule module = new PersonModule();
		module.setParams(18, "张三", "纽约");
//        PersonComponent component = DaggerPersonComponent.builder().personModule(module).build();
		PersonComponent component = DaggerPersonComponent.builder()
				.otherComponent(otherComponent)//如此othermodule创建的信息，就会被personmodule中使用。
				.personModule(module)
				.build();
		component.connectIt(this);

//        tvCommon.setText(person.toString());
		tvCommon.setText(inPerson2.get().toString());//provider使用get

//        tvInject.setText(inPerson.toString());
		tvInject.setText(inPerson.get().toString());//lazy 则需要get


		Log.d("test", "onCreate person " + person + " hashcode " + person.hashCode());
		Log.i("test", "onCreate inPerson " + inPerson + " hashcode " + inPerson.hashCode());
		Log.w("test", "onCreate inPerson2 " + inPerson2 + " hashcode " + inPerson2.hashCode());
	}
}
