package com.programs.challenges.hackerrank.breadthFirstSearch.island;

import java.util.ArrayDeque;
import java.util.Queue;

public class CountTotalIsland {

    private static final int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    private static final int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };

    public static void main(String[] args) {
        int[][] mat = {
                {1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 1, 0, 0, 0},
                {1, 0, 0, 1, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 1, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 1, 1, 0},
                {1, 0, 1, 0, 1, 0, 0, 1, 1, 0},
                {1, 1, 1, 1, 0, 0, 0, 1, 1, 1}
        };

        System.out.println("Total islands found : "+countIsland(mat));

    }

    public static int countIsland(int[][] mat) {

        int totalIslands = 0;

        int M = mat.length;
        int N = mat[0].length;

        boolean[][] discovered = new boolean[M][N];

        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                // do for every undiscovered islands
                if(!discovered[i][j] && mat[i][j] == 1){
                    bfs(mat,new Pair(i,j),discovered);
                    // increment count by 1 if new island found
                    totalIslands++;
                }
            }

        }

        return totalIslands;

    }


    private static void bfs(int[][] mat, Pair position, boolean[][] discovered) {
        Queue<Pair> queue = new ArrayDeque<>();

        discovered[position.x][position.y] = true;

        queue.add(position);

        while (!queue.isEmpty()) {

            Pair pos = queue.poll();

            for (int i = 0; i < row.length; i++) {
                int x = pos.x + row[i];
                int y = pos.y + col[i];
                if (valid(x, y, mat, discovered)) {
                    discovered[x][y] = true;
                    queue.add(new Pair(x, y));
                }
            }

        }
    }

    private static boolean valid(int x, int y, int[][] mat, boolean[][] discovered) {
        return (x >= 0) && (y >= 0)
                && (x < mat.length)
                && (y < mat[0].length)
                && (mat[x][y] == 1)
                && !discovered[x][y];
    }


}

class Pair {
    int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "" + x + " " + y;
    }
}
