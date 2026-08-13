package com.programs.challenges.string;

public class RotatedPalindrome {

    public static void main(String[] args) {
        String str = "ABCCBA";

        str = "CCBAAB";

        if(isRotatedPalindrome(str + str,str.length())){
            System.out.println("Given string is a rotated palindrome");
        } else {
            System.out.println("Given string is not a palindrome");
        }


    }


    static boolean isRotatedPalindrome(String str, int k){

        for (int i = 0; i < k - 1; i++){
            if (expand(str,i,i,k) || expand(str,i,i+1,k)){
                return true;
            }
        }

        return false;

    }

    static boolean expand(String str, int low,int high,int k){
        while(low >= 0 && high <= str.length() && (str.charAt(low) == str.charAt(high))){
            if (high - low + 1 == k){
                return true;
            }

            low--;
            high++;
        }

        return false;
    }

}
