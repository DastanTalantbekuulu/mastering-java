package com.programs.challenges.sorting;

public class Movie {// implements Comparator<Movie> {

	private String name;
	private double rating;
	private int year;
		
	public Movie(String name, double rating, int year) {
		super();
		this.name = name;
		this.rating = rating;
		this.year = year;
	}
	/*
	@Override
	public int compareTo(Movie m) {
		
		return m.year - this.year;
	}
	*/
	public String getName() {
		return name;
	}
	public double getRating() {
		return rating;
	}
	public int getYear() {
		return year;
	}
/*
	@Override
	public int compare(Movie o1, Movie o2) {
		
		return 0;
	}
	
	*/
	
	
}
