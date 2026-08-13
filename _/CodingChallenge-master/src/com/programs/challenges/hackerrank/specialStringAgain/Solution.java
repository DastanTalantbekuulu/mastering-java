/**
 */
package com.programs.challenges.hackerrank.specialStringAgain;

public class Solution {

	public static void main(String[] args) {
		String s = "aaaabcbaaaabcb";
		int len = s.length();
		int c, i = 0, j, ans = 0;
		int[] sameCharCount = new int[len];

		while (i < len) {
			j = i + 1;
			c = 1;
			while (j < len && s.charAt(i) == s.charAt(j)) {
				++j;
				++c;
			}

			// total substrings which have all same char(s)
			ans += (c * (c + 1)) >> 1;
			sameCharCount[i] = c;
			i = j;
		}

		for (j = 1; j < len - 1; ++j) {
			if (s.charAt(j) == s.charAt(j - 1)) {
				sameCharCount[j] = sameCharCount[j - 1];
			}

			// odd length substr(s) which has middle element diiferent
			if (s.charAt(j - 1) == s.charAt(j + 1) && s.charAt(j) != s.charAt(j - 1)) {
				ans += Math.min(sameCharCount[j - 1], sameCharCount[j + 1]);
			}
		}
		System.out.println(ans);
	}

	//Not used
	public static boolean isPalindrome(String val) {
		boolean isPalindrome = true;
		int len = val.length();
		if (len > 4) {
			isPalindrome = false;
		} else {
			for (int i = 0; i < len; i++) {
				if (val.charAt(i) != val.charAt(len - i - 1)) {
					isPalindrome = false;
				}
			}
		}
		return isPalindrome;
	}
}
