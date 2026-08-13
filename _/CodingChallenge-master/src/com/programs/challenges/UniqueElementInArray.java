package com.programs.challenges;

public class UniqueElementInArray {
	
	public static void main(String[] args) {
		int ar[] = {2, 3, 5, 4, 5, 3, 4, 2, 1, 1, 8};
		int result = findUniqueElement(ar, ar.length);
		System.out.println("Uniquie element is : "+result);
	}

	
	static int findUniqueElement(int ar[], int ar_size) { 
        int res = ar[0]; 
        for (int i = 1; i < ar_size; i++) 
            res = res ^ ar[i]; 
      
        return res; 
    } 
	
}
