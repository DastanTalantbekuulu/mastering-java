package com.programs.challenges.hackerrank.repeatstring;

/**
 * Lilah has a string, s, of lowercase English letters that she repeated infinitely many times.
 * <p>
 * Given an integer, n, find and print the number of letter a's in the first n letters of Lilah's infinite string.
 * <p>
 * For example, if the string s = "abcac" and n=10, the substring we consider is abcacabcac, the first 10 characters of her infinite string.
 * There are 4 occurrences of a in the substring.
 * <p>
 * Complete the repeatedString function in the editor below.
 * It should return an integer representing the number of occurrences of a in the prefix of length n in the infinitely repeating string.
 * <p>
 * repeatedString has the following parameter(s):
 * <p>
 * s: a string to repeat
 * n: the number of characters to consider
 *
 * <p>
 * <b>Sample Input</b>
 * <p>
 * aba
 * </p>
 * <p>10</p>
 * </p>
 * <b>Sample Output</b>
 * 7
 */
public class RepeatedString {
    // Complete the repeatedString function below.
    static long repeatedString(String s, long n) {
        if((s != null && s.trim().equals("")) || n <= 0){
            return 0;
        }
        int len = s.length();
        long count;

        String temp = s.replaceAll("[b-zB-Z]","");

        count = ((n / len) * temp.length());

        count += s.substring(0, (int)(n % len)).replaceAll("[b-zB-Z]", "").length();

        return count;
    }



    public static void main(String[] args) {

        long result = repeatedString("aba", 10);

        System.out.println(result);
    }
}
