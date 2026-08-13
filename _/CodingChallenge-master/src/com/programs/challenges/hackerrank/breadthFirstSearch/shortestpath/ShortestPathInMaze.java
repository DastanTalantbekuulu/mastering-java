package com.programs.challenges.hackerrank.breadthFirstSearch.shortestpath;

import java.util.*;

public class ShortestPathInMaze {

    private static final int M = 10;
    private static final int N = 10;

    private static final int[] row = {1, 0, -1, 0};
    private static final int[] col = {0, 1, 0, -1};

    public static void main(String[] args) {
        // input maze
        int[][] mat =
        {
                {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 1, 0, 0, 1},
        };
        Position source = new Position(0, 0);
        Position destination = new Position(9, 2);

        boolean[][] discovered = new boolean[M][N];

        Position result = findShortestPath(source, destination, mat, discovered);

        System.out.println("Minimum distance :" + result.dist);
        printPath(result);

    }

    static void printPath(Position node) {
        if (node != null) {
            System.out.println(node.x + ", " + node.y);
            printPath(node.previous);
        }
    }

    static boolean isValidMove(int x, int y, int[][] mat, boolean[][] discovered) {
        return (x >= 0) && (x < M) && (y >= 0) && (y < N)
                && mat[x][y] == 1 && !discovered[x][y];

    }

    static Position findShortestPath(Position source, Position destination, int[][] mat, boolean[][] discovered) {
        Queue<Position> queue = new ArrayDeque<>();

        discovered[source.x][source.y] = true;

        queue.add(source);

        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            if (pos.x == destination.x && pos.y == destination.y) {
                return pos;
            }
            for (int i = 0; i < 4; i++) {
                int _x = pos.x + row[i];
                int _y = pos.y + col[i];
                if (isValidMove(_x, _y, mat, discovered)) {
                    discovered[_x][_y] = true;
                    queue.add(new Position(_x, _y, pos.dist + 1, pos));
                }
            }

        }
        return source;

    }

}

class Position {
    int x, y, dist;

    Position previous;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, Position previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }

    public Position(int x, int y, int dist, Position previous) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.previous = previous;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dist;
        result = prime * result + ((previous == null) ? 0 : previous.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (dist != other.dist)
            return false;
        if (previous == null) {
            if (other.previous != null)
                return false;
        } else if (!previous.equals(other.previous))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "x: " + this.x + " y: " + this.y + " step: " + dist;
    }

}

