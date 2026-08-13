package com.programs.challenges;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class Tester {

    public static void main(String[] args) {
        //String A = "A586QkO";
        //String B = "JJ653kK";

        //System.out.println(solution(A,B));
        int[] A = {1,2,3,3};
        System.out.println(solution(4, A));

    }

    public int solution2(Tree T) {

        HashMap<Integer, Integer> dataMap = new HashMap<>();

        return getLongestPath(T,dataMap);

    }

    static int getLongestPath(Tree T, HashMap<Integer, Integer> dataMap) {
        if (T == null)
            return dataMap.size();

        if (dataMap.containsKey(T.x)) {
            dataMap.put(T.x, dataMap.get(T.x) + 1);
        } else {
            dataMap.put(T.x, 1);
        }

        int max_path = Math.max(getLongestPath(T.l, dataMap), getLongestPath(T.r, dataMap));


        if (dataMap.containsKey(T.x)) {
            dataMap.put(T.x, dataMap.get(T.x) - 1);
        }

        // if we reached a condition where all duplicate value
        // of current node is deleted
        if (dataMap.get(T.x) == 0)
            dataMap.remove(T.x);

        return max_path;
    }

    public int solution(Tree T) {



        if(T == null)
            return 0;

        return solnUtil(T, new HashSet<Integer>());
    }

    int solnUtil(Tree t, HashSet<Integer> set){

        if(t == null)
            return set.size();

        int left=0,right=0,curr=0;

        if(set.contains(t.x)){
            return set.size();
        }
        else{
            set.add(t.x);
            return Math.max(solnUtil(t.l,new HashSet<Integer>(set)),solnUtil(t.r,new HashSet<Integer>(set)));

        }

    }



    static int solution(int M, int[] A) {
        int N = A.length;
        int[] count = new int[M + 1];
        for (int i = 0; i <= M; i++)
            count[i] = 0;
        int maxOccurence = 0;
        int index = 0;
        for (int i = 0; i < N; i++) {
            if (count[A[i]] > 0) {
                int tmp = count[A[i]];
                if (tmp > maxOccurence) {
                    maxOccurence = tmp;
                    index = i;
                }
                count[A[i]] = tmp + 1;
            } else {
                count[A[i]] = 1;
            }
        }
        return A[index];
    }


    public static int solution(String A, String B) {
        int alecWinCount = 0;

        char[] a = A.toCharArray();

        char[] b = B.toCharArray();

        for (int i = 0; i < a.length; i++) {
            if (Character.isDigit(a[i]) && Character.isDigit(b[i])) {
                int a1 = Character.getNumericValue(a[i]);
                int b1 = Character.getNumericValue(b[i]);
                if ((a1 - b1) > 0) {
                    alecWinCount++;
                }
            }
            if (Character.isLetter(a[i]) && Character.isLetter(b[i])) {
                if (a[i] == 'A') {
                    alecWinCount++;
                } else if (a[i] == 'J' && b[i] == 'T') {
                    alecWinCount++;
                } else if (a[i] == 'Q' && (b[i] == 'T' || b[i] == 'J')) {
                    alecWinCount++;
                } else if (a[i] == 'K' && (b[i] == 'T' || b[i] == 'J' || b[i] == 'Q')) {
                    alecWinCount++;
                }

            }

            if (Character.isLetter(a[i]) && Character.isDigit(b[i])) {
                alecWinCount++;
            }
        }

        return alecWinCount;
    }


    static String encode(String input) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            b.append(c += c + i);
        }
        return b.reverse().toString();
    }

    static String decode(String input) {
        StringBuilder b = new StringBuilder(input);
        input = b.reverse().toString();
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            result += Character.toString(c -= c - i);
        }
        return result;
    }

}
