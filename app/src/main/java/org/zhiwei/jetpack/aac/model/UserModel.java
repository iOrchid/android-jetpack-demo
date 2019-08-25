package org.zhiwei.jetpack.aac.model;

/**
 * 简单的model对象类，其实也可以使用接口形式，由具体子类实现
 * Author: zhiwei.
 * Date: 2018/11/6 0006,19:28.
 */
public class UserModel {

	private int age;
	private String name;
	private String city;


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "UserModel{" +
				"age=" + age +
				", name='" + name + '\'' +
				", city='" + city + '\'' +
				'}';
	}
}
