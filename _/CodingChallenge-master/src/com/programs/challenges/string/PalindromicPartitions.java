package com.programs.challenges.string;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PalindromicPartitions {

    public static void main(String[] args) {
        String input = "nitin";

        findAllPalindromicPartitions(input);

    }


    static void findAllPalindromicPartitions(String input){
        int n = input.length();

        List<List<String>> allParts = new ArrayList<>();

        Deque<String> currentPart = new LinkedList<>();

        allPartitionsUtil(allParts,currentPart,input,0,n);

        for (int i = 0; i < allParts.size(); i++)
        {
            for (int j = 0; j < allParts.get(i).size(); j++)
            {
                System.out.print(allParts.get(i).get(j) + " ");
            }
            System.out.println();
        }

    }

    static void allPartitionsUtil(List<List<String>> allParts,Deque<String> currentPart,
                                  String input,int start,int end){

        if(start >= end){
            allParts.add(new ArrayList<>(currentPart));
            return;
        }

        for(int i = start; i < end;i++){

            if(isPalindrome(input,start,i)){
                currentPart.addLast(input.substring(start,i+1));
                allPartitionsUtil(allParts,currentPart,input,i+1,end);
                currentPart.removeLast();
            }
        }

    }


    static boolean isPalindrome(String input,int start,int end){
        while(start <= end){
            if(input.charAt(start++) != input.charAt(end--)){
                return false;
            }
        }
        return true;
    }


}
