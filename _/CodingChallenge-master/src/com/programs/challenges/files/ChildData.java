package com.programs.challenges.files;

public class ChildData {
	private String name;
	private int age;
	private float height;

	public ChildData(String name, int age, float height) {
		super();
		this.name = name;
		this.age = age;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "name="+name+",age="+age+",height="+height;
	}
	
}
