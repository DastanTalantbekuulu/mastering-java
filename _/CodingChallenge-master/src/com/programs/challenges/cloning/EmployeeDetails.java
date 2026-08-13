package com.programs.challenges.cloning;

public class EmployeeDetails {

	public static void main(String[] args) {
		Department dept = new Department("Engineering", "Pepperfry");
		Employee e1 = new Employee("Pranshu Shrivastava", "spranshu1@gmail.com", dept);
		Employee e2 = null;
		try {
			e2 = (Employee)e1.clone();
			System.out.println("Employee 1:\n"+e1.toString());
			System.out.println("Employee 2:\n"+e2.toString());
			
			e1.setName("Rahul Kumar Nawal");
			e2.getDepartment().setName("IT");
			System.out.println("Employee 1:\n"+e1.toString());
			System.out.println("Employee 2:\n"+e2.toString());
			
			
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}

		
		
	}

}
