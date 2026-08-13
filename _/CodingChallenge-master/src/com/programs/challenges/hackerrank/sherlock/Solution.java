/**
 * @author pranshu.shrivastava
 * @date Oct 1, 2019
 */
package com.programs.challenges.hackerrank.sherlock;

public class Solution {

	static String isValid(String s) {
		int [] count = new int [26];
		String result = "YES";
		int max_occur = -1;
		boolean removedChar = false;
		
		for(int i = 0 ; i < s.length(); i++) {
			count[s.charAt(i) -'a']++;
		}
				
		for(int i = 0 ; i < 26;i++) {
			if(count[i] == 0){
	            continue;
	        }else if(max_occur == -1){
	            max_occur = count[i];
	            continue;
	        }else if(count[i] == max_occur){
	            continue;
	        }else if(!removedChar && (count[i] == max_occur + 1 || count[i] == 1)){
	        	removedChar = !removedChar;
	            continue;  
	        }else{
	            return "NO";
	        }
						
		}
		return result;
	}

	public static void main(String[] args) {
		String s = "aaabbcc";
		System.out.println(isValid(s));
	}

}
