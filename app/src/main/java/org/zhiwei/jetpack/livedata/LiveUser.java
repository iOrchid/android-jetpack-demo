package org.zhiwei.jetpack.livedata;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * live属性的user数据类
 * Author: zhiwei.
 * Date: 2018/11/5 0005,11:17.
 */
public class LiveUser {

	private String city;//普通属性字段

	private MutableLiveData<String> name = new MediatorLiveData<>();//live响应的属性
	private MutableLiveData<Integer> age = new MutableLiveData<>();//live属性

	public LiveUser(String city, String name, Integer age) {
		this.city = city;

		this.name.setValue(name);
		this.age.setValue(age);

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public MutableLiveData<String> getName() {
		return name;
	}

	public void setName(String name) {
		this.name.setValue(name);
	}

	public MutableLiveData<Integer> getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age.setValue(age);
	}
}
