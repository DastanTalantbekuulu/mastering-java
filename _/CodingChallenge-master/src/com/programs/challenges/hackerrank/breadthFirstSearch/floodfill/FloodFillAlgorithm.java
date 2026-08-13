package com.programs.challenges.hackerrank.breadthFirstSearch.floodfill;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FloodFillAlgorithm {

    private static final int M = 10;
    private static final int N = 10;

    private static final int[] row = {1 ,-1 , 1 ,-1, 0, 0, 1,-1};
    private static final int[] col = {1 ,-1 ,-1 , 1, 1,-1, 0, 0};

    public static void main(String[] args) {
        /**
         * Approach
         * 1. Create and empty queue
         * 2. Enqueue starting pixel and mark it processed
         * 3. Do till queue is empty
         *  a. pop from front node of queue and process it
         *  b. replace color of current pixel(popped node) with replacement color
         *  c. process all 8 possible adjacent pixels and enqueue each valid pixel which has
         *     same color as of current pixel.
         */

        // matrix showing portion of the screen having different colors
        char[][] mat = {
                "YYYGGGGGGG".toCharArray(),
                "YYYYYYGXXX".toCharArray(),
                "GGGGGGGXXX".toCharArray(),
                "WWWWWGGGGX".toCharArray(),
                "WRRRRRGXXX".toCharArray(),
                "WWWRRGGXXX".toCharArray(),
                "WBWRRRRRRX".toCharArray(),
                "WBBBBRRXXX".toCharArray(),
                "WBBXBBBBXX".toCharArray(),
                "WBBXXXXXXX".toCharArray()
        };

        Pixel start = new Pixel(9, 0);

        boolean[][] processed = new boolean[M][N];

        printMat(mat);

        System.out.println("Filling color from pixel : "+start);

        for(int i = 0; i < mat.length;i++){
            if(processed[i][i] == false){
                floodFill(start,'C',mat,processed);
            }
        }



    }


    private static void floodFill(Pixel start, char replaceColor, char[][]mat, boolean[][] processed){

        Queue<Pixel> pixelQueue = new ArrayDeque<>();

        processed[start.x][start.y] = true;

        pixelQueue.add(start);

        char target = mat[start.x][start.y];

        while(!pixelQueue.isEmpty()){
            Pixel current = pixelQueue.poll();
            int x = current.x;
            int y = current.y;

            mat[x][y] = replaceColor;

            for(int i = 0; i < row.length;i++){
                int _x = x + row[i];
                int _y = y + col[i];
                if(valid(_x,_y,target,mat,processed)){
                    processed[_x][_y] = true;
                    pixelQueue.add(new Pixel(_x,_y,current));
                }
            }

        }

        System.out.println("Color is filled");

        printMat(mat);
    }


    private static boolean valid(int x, int y, char color, char[][]mat ,boolean[][] processed){
        return (x >= 0) && (y >= 0) && (x < mat.length) && (y < mat[0].length) && (mat[x][y] == color) && !processed[x][y];
    }

    private static void printMat(char[][]mat){
        // print the colors after replacement
        for (int i = 0; i < mat.length; i++) {
            System.out.println(Arrays.toString(mat[i]));
        }
    }


}

class Pixel {
    int x,y;

    Pixel lastPixel;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pixel(int x, int y, Pixel lastPixel) {
        this.x = x;
        this.y = y;
        this.lastPixel = lastPixel;
    }

    @Override
    public String toString() {
        return "x: "+x+" y: "+y;
    }
}
