/**
 * @author pranshu.shrivastava
 * @date Mar 13, 2020
 */
package com.programs.challenges.hackerrank.breadthFirstSearch.shortestpath;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class KnightOnChessBoard {

	private static final int[] row = { 2, 2, -2, -2, 1, 1, -1, -1 };
	private static final int[] col = { -1, 1, 1, -1, 2, -2, 2, -2 };

	public static void main(String[] args) {
		int N = 8;

		Node source = new Node(0, 7);

		Node destination = new Node(7, 0);

		Node node = findShortestPath(source, destination, N);

		if (node != null) {
			System.out.println("Minimum steps required to reach destination : " + node.dist);
			System.out.println("Path to destination");
			printPath(node);
		} else {
			System.out.println("Path not available");
		}

	}

	private static Node findShortestPath(Node source, Node dest, int N) {
		Set<Node> visited = new HashSet<>();
		Queue<Node> q = new ArrayDeque<Node>();

		q.add(source);

		while (!q.isEmpty()) {
			Node node = q.poll();
			int x = node.x;
			int y = node.y;
			int dist = node.dist;

			if (x == dest.x && y == dest.y) {
				return node;
			}

			if (!visited.contains(node)) {
				visited.add(node);

				for (int i = 0; i < N; i++) {

					int x1 = x + row[i];
					int y1 = y + col[i];
					if (valid(x1, y1, N)) {
						q.add(new Node(x1, y1, dist + 1, node));
					}
				}
			}
		}
		return null;

	}

	private static void printPath(Node node) {
		if (node != null) {
			printPath(node.parent);
			System.out.println(node + "");
		} else {
			return;
		}
	}

	private static boolean valid(int x, int y, int N) {
		if (x < 0 || y < 0 || x >= N || y >= N) {
			return false;
		}
		return true;
	}

}

class Node {
	int x, y, dist;

	Node parent;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Node(int x, int y, Node parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public Node(int x, int y, int dist, Node parent) {
		this.x = x;
		this.y = y;
		this.dist = dist;
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dist;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Node other = (Node) obj;
		if (dist != other.dist)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y + " dist: " + dist;
	}

}
