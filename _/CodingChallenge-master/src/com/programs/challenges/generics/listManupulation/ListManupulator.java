package com.programs.challenges.generics.listManupulation;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.programs.challenges.User;
import com.programs.challenges.cloning.Department;
import com.programs.challenges.cloning.Employee;

public class ListManupulator {
	
	public static Function<Employee, User> func = new Function<Employee,User>() {

		@Override
		public User apply(Employee emp) {
			final User user = new User();
			
			user.setId(emp.getEmail());
			user.setName(emp.getName());
			
			return user;
		}
		
	};
	
	
			
	public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
	    return from.stream().map(func).collect(Collectors.toList());
	}
	
	
	public static void convertObject() {
		Employee emp = new Employee("Pradeep", "pradeep@gmail.com", new Department("IT", "INTELLECT"));
		User user = func.apply(emp);
		System.out.println(user);
	}
}
