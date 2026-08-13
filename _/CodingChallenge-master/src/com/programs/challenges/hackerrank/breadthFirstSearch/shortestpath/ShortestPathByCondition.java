/**
 * @author pranshu.shrivastava
 * @date Mar 17, 2020
 */
package com.programs.challenges.hackerrank.breadthFirstSearch.shortestpath;

import java.util.ArrayDeque;
import java.util.Queue;

public class ShortestPathByCondition {

	private static int N;

	private static int[] row = { -1, 0, 0, 1 };
	private static int[] col = { 0, -1, 1, 0 };

	public static void main(String[] args) {
		// condition to move
		// mat[i + mat[i][j]][j] or mat[i - mat[i][j]][j] or mat[i][j + mat[i][j]] or mat[i][j - mat[i][j]]
		int[][] mat =
		{
			{ 4, 4, 6, 5, 5, 1, 1, 1, 7, 4 },
			{ 3, 6, 2, 4, 6, 5, 7, 2, 6, 6 },
			{ 1, 3, 6, 1, 1, 1, 7, 1, 4, 5 },
			{ 7, 5, 6, 3, 1, 3, 3, 1, 1, 7 },
			{ 3, 4, 6, 4, 7, 2, 6, 5, 4, 4 },
			{ 3, 2, 5, 1, 2, 5, 1, 2, 3, 4 },
			{ 4, 2, 2, 2, 5, 2, 3, 7, 7, 3 },
			{ 7, 2, 4, 3, 5, 2, 2, 3, 6, 3 },
			{ 5, 1, 4, 2, 6, 4, 6, 7, 3, 7 },
			{ 1, 4, 1, 7, 5, 3, 6, 5, 3, 4 }
		};
		
		findShortestPath(mat);
	}

	public static boolean findShortestPath(int[][] mat) {

		// destination is N - 1 cell
		N = mat.length;

		boolean[][] visited = new boolean[mat.length][mat[0].length];

		Node result = bfs(new Node(0, 0), mat, visited);

		if (result == null) {
			System.out.println("No path to destination");
			return false;
		} else {
			System.out.println("Steps taken : " + result.dist);
			printPath(result);
			return true;
		}

	}

	private static void printPath(Node node) {
		if (node != null) {
			System.out.println(node);
			printPath(node.parent);
		}
	}

	private static Node bfs(Node source, int[][] mat, boolean[][] visited) {
		Queue<Node> queue = new ArrayDeque<>();

		visited[source.x][source.y] = true;

		queue.add(source);

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			int cellValue = mat[node.x][node.y];

			if (node.x == N - 1 && node.y == N - 1) {
				System.out.println("Reached destination");
				return node;
			}

			for (int i = 0; i < row.length; i++) {
				int x = node.x + row[i] * cellValue;
				int y = node.y + col[i] * cellValue;
				if (valid(x, y, mat, visited)) {
					visited[x][y] = true;
					queue.add(new Node(x, y, node.dist + 1, node));
				}
			}

		}

		return null;

	}

	private static boolean valid(int x, int y, int[][] mat, boolean[][] visited) {
		return (x >= 0) && (y >= 0) && (x < mat.length) && (y < mat[0].length) && !visited[x][y];
	}

}
