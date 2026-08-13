package com.programs.challenges.hackerrank.breadthFirstSearch.shortestpath;

import java.util.ArrayDeque;
import java.util.Queue;

public class SafeRouteToDestination {

    private static final int M = 10;
    private static final int N = 10;

    private static final int[] row = {-1, 0, 0, 1};
    private static final int[] col = {0, -1, 1, 0};

    public static void main(String[] args) {

        findSafeRoute();

    }


    public static boolean findSafeRoute() {
        Position source = new Position(0 , 1);
        Position destination = new Position(5,8);

        boolean[][] visited = new boolean[M][N];

        int[][] mat =
        {
                {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 1, 0, 0, 1},
        };

        Position result = bfs(source,destination,mat,visited);

        if(result != null){
            System.out.println("Steps to destination : "+result.dist);
            printSafestRoute(result);
            return true;
        } else {
            System.out.println("No safe route");
            return false;
        }

    }

    private static void printSafestRoute(Position position){
        if(position != null){
            System.out.println(position);
            printSafestRoute(position.previous);
        }
    }

    private static Position bfs(Position source, Position destination,int[][] mat, boolean[][] visited) {
        Queue<Position> queue = new ArrayDeque<>();

        visited[source.x][source.y] = true;

        queue.add(source);

        while (!queue.isEmpty()) {
            Position pos = queue.poll();

            int x = pos.x;
            int y = pos.y;

            if (x == destination.x && y == destination.y) {
                return pos;
            }

            for (int i = 0; i < 4; i++) {
                int _x = x + row[i];
                int _y = y + col[i];
                if (validMove(_x, _y, mat, visited)) {
                    visited[_x][_y] = true;
                    queue.add(new Position(_x,_y, pos.dist + 1, pos));
                }
            }

        }

        return null;

    }

    private static boolean validMove(int x, int y, int[][] mat, boolean[][] visited) {
        return (x > 0) && (y > 0) && (x < M) && (y < N) && mat[x][y] != 0 && !visited[x][y];
    }


}
