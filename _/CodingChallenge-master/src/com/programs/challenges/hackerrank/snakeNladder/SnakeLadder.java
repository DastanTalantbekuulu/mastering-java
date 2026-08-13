/**
 * @author pranshu.shrivastava
 * @date Mar 13, 2020
 */
package com.programs.challenges.hackerrank.snakeNladder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SnakeLadder {

	public static void main(String[] args) {
		// snakes and ladders are represented using a map.
		Map<Integer, Integer> ladder = new HashMap<>();
		Map<Integer, Integer> snake = new HashMap<>();

		// insert ladders into the map
		ladder.put(1, 38);
		ladder.put(4, 14);
		ladder.put(9, 31);
		ladder.put(21, 42);
		ladder.put(28, 84);
		ladder.put(51, 67);
		ladder.put(72, 91);
		ladder.put(80, 99);

		// insert snakes into the map
		snake.put(17, 7);
		snake.put(54, 34);
		snake.put(62, 19);
		snake.put(64, 60);
		snake.put(87, 36);
		snake.put(93, 73);
		snake.put(95, 75);
		snake.put(98, 79);

		findSolution(ladder, snake);

	}

	private static void BFS(Graph graph, int source, int N) {
		Queue<Node> queue = new ArrayDeque<>();

		boolean[] discovered = new boolean[N + 1];

		Node node = new Node(source, 0);

		queue.add(node);
		
		System.out.println("Starting from : "+node);

		while (!queue.isEmpty()) {
			//System.out.println("Queue : "+queue);
			node = queue.poll();
			System.out.println("At : "+node);
			if (node.ver == N) {
				System.out.println("Minimumn throws needed : "+node.minDist);
				return;
			}

			//System.out.println("AdjList : "+graph.adjList.get(node.ver));
			for (int n : graph.adjList.get(node.ver)) {
				if (!discovered[n]) {
					System.out.println("Discovered : "+n);
					discovered[n] = true;
					System.out.println("Pushing to queue : "+n);
					queue.add(new Node(n, node.minDist + 1));
				}
			}

		}
	}

	private static void findSolution(Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
		int N = 10 * 10;

		List<Edge> edges = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 1; j <= 6 && i + j <= N; j++) {
				int src = i;

				int _ladder = ladder.get(i + j) == null ? 0 : ladder.get(i + j);
				int _snake = snake.get(i + j) == null ? 0 : snake.get(i + j);
				int dist;
				if (_ladder != 0 || _snake != 0)
					dist = _ladder + _snake;
				else
					dist = i + j;

				edges.add(new Edge(src, dist));
			}
		}

		Graph graph = new Graph(edges, N);
		System.out.println("Edges : "+edges);
		System.out.println("Graph : "+graph);
		BFS(graph, 0, N);
	}

}

class Node {
	int ver, minDist;

	Node(int ver, int minDist) {
		this.ver = ver;
		this.minDist = minDist;
	}
	
	@Override
	public String toString() {
		return "(ver : "+ver+"-dist : "+minDist+")";
	}
}

class Graph {

	List<List<Integer>> adjList = null;

	Graph(List<Edge> edges, int N) {
		adjList = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			adjList.add(i, new ArrayList<>());
		}

		for (int i = 0; i < edges.size(); i++) {
			int src = edges.get(i).src;
			int dest = edges.get(i).dest;

			// as this is a directed graph
			adjList.get(src).add(dest);
		}
	}
	
	@Override
	public String toString() {
		return adjList.toString();
	}

}

class Edge {

	int src, dest;

	Edge(int src, int dest) {
		this.src = src;
		this.dest = dest;
	}
	
	@Override
	public String toString() {
		return "src:"+src+",dest:"+dest;
	}
}
