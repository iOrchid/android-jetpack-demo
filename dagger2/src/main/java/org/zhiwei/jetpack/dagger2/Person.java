package org.zhiwei.jetpack.dagger2;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,11:36.
 */
public class Person {
	private int age;
	private String name = "default";
	private String city = "default";

	public Person() {
		System.out.println("Person.Person");
	}

	public Person(int age, String name, String city) {
		this.age = age;
		this.name = name;
		this.city = city;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Person{" +
				"age=" + age +
				", name='" + name + '\'' +
				", city='" + city + '\'' +
				'}';
	}
}
