/**
 * @author pranshu.shrivastava
 * @date Oct 1, 2019
 */
package com.programs.challenges.hackerrank.alternatingcharacters;

public class Solution {
	
	// Complete the alternatingCharacters function below.
    static int alternatingCharacters(String s) {
    	char [] arr = s.toCharArray();
    	int count = 0;
    	char c = ' ';
    	for(int i = 0 ; i < s.length();i++) {
    		if(c == arr[i]) {
    			count++;
    		} else {
    			c = arr[i];
    		}
    	}

    	return count;
    }

	public static void main(String[] args) {
		String s = "BABABABB";
		System.out.println(alternatingCharacters(s));
	}

}
