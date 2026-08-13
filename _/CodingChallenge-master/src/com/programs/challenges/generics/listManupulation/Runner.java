package com.programs.challenges.generics.listManupulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.programs.challenges.User;
import com.programs.challenges.cloning.Department;
import com.programs.challenges.cloning.Employee;

public class Runner {

	public static void main(String[] args) {
		List<String> stringList = Arrays.asList("1","2","3");
		List<Integer> integerList = ListManupulator.convertList(stringList, s -> Integer.parseInt(s));
		System.out.println(integerList);
		System.out.println("===============================");
		
		final List<Employee> employeeList = new ArrayList();
		final Department department = new Department("IT", "INTELLECT");
		Employee employee1 = new Employee("Ravi Kishan", "ravi@gmail.com", department);
		Employee employee2 = new Employee("Manoj Kumar", "manoj@gmail.com", department);
		Employee employee3 = new Employee("Pikachu chuchu", "pika@gmail.com", department);
		Employee employee4 = new Employee("Maanwar mutant", "maanwar@gmail.com", department);
		
		employeeList.add(employee1);
		employeeList.add(employee2);
		employeeList.add(employee3);
		employeeList.add(employee4);
		
		//List<User> userList = ListManupulator.convertList(employeeList, emp -> emp.getName());
		ListManupulator.convertObject();
		System.out.println("===============================");
		
		final List<User> userList = employeeList.stream()
				.map(ListManupulator.func)
				.collect(Collectors.<User> toList());
		System.out.println(userList);
		
		
	}

}
