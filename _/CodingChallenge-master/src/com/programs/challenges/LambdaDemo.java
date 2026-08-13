package com.programs.challenges;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.util.CollectionUtils;

import com.programs.challenges.cloning.Department;
import com.programs.challenges.cloning.Employee;

public class LambdaDemo {

	private static final String salutation = "Hello!";
	private static TimeDemo td;

	interface Greeting {
		void sayMessage(String message);
	}

	public static void main(String[] args) {
		//td = new TimeDemo();
		// oldMethod();
		// newMethod();
		// streamMethods();
		// streamDemo();
		//System.out.println("=======================\nJAVA 8 Time Package Demo\n=======================");
		//td.getLocalTime();
		//td.getLocalDate();
		//td.diffDate(LocalDateTime.now(), LocalDateTime.of(2016, 02, 01, 0, 0));

		//pauseScreen(1000);

		//fizzBuzz();

		//System.out.println(transformList());
		
		streamMethods();
		
	}
	
	public static void printValues(List<Integer> intList) {
		System.out.println(intList.stream().map(String::valueOf).collect(Collectors.joining(" ")));
	}
	

	public static List<Integer> reverse(int arr[]) {
		List<Integer> intList = IntStream.rangeClosed(1, arr.length)
				.mapToObj(i -> arr[arr.length - i])
				.collect(Collectors.toList());
		return intList;
	}

	public static List<User> transformList() {
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

		final List<User> usersList = new ArrayList();
		if (!CollectionUtils.isEmpty(employeeList)) {
			employeeList.stream().filter(Objects::nonNull).forEach(emp -> {
				final User user = new User(emp.getEmail(), emp.getName());
				usersList.add(user);
			});
		}

		List<User> usrLs = Arrays.stream("id name".split(" ")).map(transformList(User::new, User::setId, User::setName))
				.collect(Collectors.toList());
		System.out.println(usrLs.get(0));

		return usersList;
	}

	public static <T, V> Function<V, T> transformList(Supplier<? extends T> constructor,
			BiConsumer<? super T, ? super V>... setter) {
		return v -> {
			T t = constructor.get();
			for (BiConsumer<? super T, ? super V> s : setter) {
				s.accept(t, v);
			}

			return t;
		};
	}

	public static void pauseScreen(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			System.err.println("Error pausing console :" + e.getMessage());
		}
		System.out.flush();
	}

	// using java 1.7
	public static void oldMethod() {
		System.out.println("Using version 1.7.......");
		Greeting greeting = new Greeting() {

			@Override
			public void sayMessage(String message) {
				System.out.println(salutation + message);

			}
		};
		greeting.sayMessage("Rahul");
	}

	// using java 1.8
	public static void newMethod() {
		System.out.println("Using version 1.8 Lambda Expression......");
		Greeting greeting = message -> System.out.println(salutation + message);
		greeting.sayMessage("Pranshu");
	}

	// functional interface 1.8 (Predicate interface)
	public static void functionalInterface() {
		List<String> names = new ArrayList<String>();
		names.add("Pranshu");
		names.add("Rahul");
		names.add("Sumit");
		names.add("Amit");
		names.add("");
		names.add("Neymar 2");

		Predicate<String> pred = n -> n.equals("Sumit");
		names.forEach(n -> System.out.println(pred.test(n)));
	}

	public static void streamMethods() {
		List<Integer> numbers = Arrays.asList(3, 2, 2, 4, 5, 7, 9, 7);
		System.out.println(numbers.stream().sorted().distinct().collect(Collectors.toList()));
	}

	public static void streamDemo() {
		System.out.println("Using Streams to filter empty strings....");
		List<String> names = new ArrayList<String>();
		names.add("Pranshu");
		names.add("Rahul");
		names.add("Sumit");
		names.add("Amit");
		names.add("");
		names.add("Neymar 2");

		Predicate<String> p = val -> val.matches("[a-zA-z]+");
		names.stream().filter(string -> !string.isEmpty()).filter(p).forEach(System.out::println);

	}

	public static void fizzBuzz() {

		System.out.println("\n=========Lets Play Fizz Buzz !!==========");
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter the number range seperated with '-' eg:10-100");
			String[] range = sc.nextLine().split("-");
			int start = Integer.parseInt(range[0]);
			int end = Integer.parseInt(range[1]);
			IntStream.range(start, end)
					.mapToObj(i -> i % 5 == 0 ? (i % 7 == 0 ? "FizzBuzz" : "Fizz") : (i % 7 == 0 ? "Buzz" : i))
					.forEach(System.out::println);
			;

		} catch (Exception e) {
			System.err.printf("Error Occured : %s", e.getMessage());
		}

	}

}
