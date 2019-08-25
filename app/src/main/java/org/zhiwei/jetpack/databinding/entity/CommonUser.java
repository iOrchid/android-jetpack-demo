package org.zhiwei.jetpack.databinding.entity;

/**
 * Author: zhiwei.
 * Date: 2018/11/2 0002,11:34.
 */
public class CommonUser {
	int age;//年龄
	String name;//名称
	boolean single;//是否单身

	public CommonUser() {
	}

	public CommonUser(int age, String name, boolean single) {
		this.age = age;
		this.name = name;
		this.single = single;
	}

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

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
}
