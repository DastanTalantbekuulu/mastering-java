package com.programs.challenges.cloning;

public class Department implements Cloneable{

	private String name;
	private String company;
	
	public Department(String name, String company) {
		super();
		this.name = name;
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		
		return "Department :"+getName()+"\nCompany :"+getCompany();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
