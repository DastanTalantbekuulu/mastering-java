package com.programs.challenges.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComparableMovie {

	public static void main(String[] args) {
		ArrayList<Movie> movielist = new ArrayList<>();
		movielist.add(new Movie("StarWars", 6, 2016));
		movielist.add(new Movie("PK", 7, 2015));
		movielist.add(new Movie("Empire Strikes Back", 8.8, 1980));
		movielist.add(new Movie("Mughal-E-Azam", 8.8, 1980));
		movielist.add(new Movie("Ab tak chappan", 8.8, 2013));
		//Collections.sort(movielist);
		Collections.sort(movielist, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				if(o1.getYear() < o2.getYear()){
					return -1;
				}
				if(o1.getYear() > o2.getYear()){
					return 1;
				}else{
					return 0;
				}
			}
        	
		});
		System.out.println("Movies after sorting by year: ");
        for (Movie movie: movielist)
        {
            System.out.println(movie.getName() + " " +
                               movie.getRating() + " " +
                               movie.getYear());
        }
		System.out.println("-------------------------------");
        Collections.sort(movielist, new Comparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
        	
		});
		System.out.println("Movies after sorting by name: ");
        for (Movie movie: movielist)
        {
            System.out.println(movie.getName() + " " +
                               movie.getRating() + " " +
                               movie.getYear());
        }
		
  
        
	}

}
