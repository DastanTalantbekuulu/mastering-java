package com.programs.challenges.cloning;

public class Employee implements Cloneable{

	private String name;
	private String email;
	private Department department;
	
	public Employee(String name, String email, Department department) {
		super();
		this.name = name;
		this.email = email;
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "Employee Name :"+getName()+"\nEmail :"+getEmail()
		+"\nDepartment :"+getDepartment().getName();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Employee cloned = (Employee)super.clone();
		cloned.setDepartment((Department)cloned.getDepartment().clone());
		return cloned;
	}
	
}
