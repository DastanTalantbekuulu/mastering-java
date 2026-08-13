package com.programs.challenges.string;


/**
 * Reverse string without affecting special character
 * Input:   str = "a,b$c"
 * Output:  str = "c,b$a"
 * Note that $ and , are not moved anywhere.
 * Only subsequence "abc" is reversed
 */
public class ReverseStringSpecialChar {

    public static void main(String[] args) {
        String str = "a,b$c";

        System.out.println(rotateApproach1(str));
        System.out.println(rotateApproach2(str));

    }



    static String rotateApproach1(String str){
        int len = str.length();
        char[] temp = new char[len];

        for(int i = 0; i < len;i++){
            if(Character.isLetter(str.charAt(i))){
                temp[i] = str.charAt(i);
            }
        }

        reverse(temp);
        char[] original = str.toCharArray();
        for(int i = 0; i < len;i++){
            if(Character.isLetter(original[i])){
                original[i] = temp[i];
            }
        }

        return new String(original);

    }

    static String rotateApproach2(String str){
        char[] arr = str.toCharArray();
        int left = 0;int right = str.length() - 1;
        for(int i=0;i < arr.length ;i++){
            if(!Character.isLetter(arr[left])){
                left++;
            }else if(!Character.isLetter(arr[right])){
                right--;
            }else{
                swap(arr,left,right);
            }
        }
        return new String(arr);
    }

    static void swap(char[]arr,int left,int right){
        char temp = arr[right];
        arr[right] = arr[left];
        arr[left] = temp;
    }


    static void reverse(char[] arr){
        int n = arr.length;
        for(int i = 0;i < n/2;i++) {
            char temp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = temp;
        }
    }


}
