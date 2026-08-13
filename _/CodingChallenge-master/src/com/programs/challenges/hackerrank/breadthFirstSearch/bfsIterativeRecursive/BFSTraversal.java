/**
 * @author pranshu.shrivastava
 * @date Mar 13, 2020
 */
package com.programs.challenges.hackerrank.breadthFirstSearch.bfsIterativeRecursive;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class BFSTraversal {

	public static void main(String[] args) {
		System.out.println("====Iterative BFS====\n");
		runIterativeBFS();
		System.out.println("\n====Recursive BFS====\n");
		runRecursiveBFS();
	}

	/**
	 * Initialize a graph and trigger iterative bfs traversal
	 */
	private static void runIterativeBFS() {
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(
				new Edge(1, 2),
				new Edge(1, 3),
				new Edge(1, 4),
				new Edge(2, 5),
				new Edge(2, 6),
				new Edge(5, 9),
				new Edge(5, 10),
				new Edge(4, 7),
				new Edge(4, 8),
				new Edge(7, 11),
				new Edge(7, 12)
		// vertex 0, 13 and 14 are single nodes
		);

		int N = 15;

		Graph graph = new Graph(edges, N);

		boolean[] discovered = new boolean[N];

		for (int i = 0; i < N; i++) {
			if (discovered[i] == false)
				BFSIterative(graph, i, discovered);
		}
	}

	/**
	 * Initialize a graph and trigger recursive bfs traversal
	 */
	private static void runRecursiveBFS() {
		Queue<Integer> q = new ArrayDeque<>();
		// List of graph edges as per above diagram
		List<Edge> edges = Arrays.asList(new Edge(1, 2), new Edge(1, 3), new Edge(1, 4), new Edge(2, 5), new Edge(2, 6),
				new Edge(5, 9), new Edge(5, 10), new Edge(4, 7), new Edge(4, 8), new Edge(7, 11), new Edge(7, 12)
		// vertex 0, 13 and 14 are single nodes
		);

		int N = 15;

		Graph graph = new Graph(edges, N);

		boolean[] discovered = new boolean[N];
		
		for (int i = 0; i < N; i++) {
			if (discovered[i] == false) {
				// mark source node as discovered
				discovered[i] = true;
				// add to queue
				q.add(i);
				// start bfs
				BFSRecursive(graph, q, discovered);
			}
				
		}

	}

	/**
	 * Demonstrate recursive traversal of graph
	 * @param graph the graph
	 * @param q queue holding nodes to traverse
	 * @param discovered array to mark nodes which are traversed
	 */
	private static void BFSRecursive(Graph graph, Queue<Integer> q, boolean[] discovered) {

		if (q.isEmpty())
			return;

		int v = q.poll();

		System.out.println(v + "");

		for (int u : graph.adjList.get(v)) {
			if (!discovered[u]) {
				discovered[u] = true;
				q.add(u);
			}
		}

		BFSRecursive(graph, q, discovered);
	}
	
	

	/**
	 * Demonstrate BFS traversal of graph with iterative approach
	 * @param graph the graph
	 * @param v the source node
	 * @param discovered array to mark nodes which are traversed
	 */
	private static void BFSIterative(Graph graph, int v, boolean[] discovered) {
		Queue<Integer> q = new ArrayDeque<>();

		discovered[v] = true;

		q.add(v);

		while (!q.isEmpty()) {
			v = q.poll();
			System.out.println(v + "");
			for (int u : graph.adjList.get(v)) {
				if (!discovered[u]) {
					discovered[u] = true;
					q.add(u);
				}
			}
		}
	}

}

class Graph {
	List<List<Integer>> adjList = null;

	Graph(List<Edge> edges, int N){
		adjList = new ArrayList<>(N);
		for(int i = 0; i < N ; i++){
			adjList.add(i,new ArrayList<>());
		}

		for(int i = 0; i < edges.size() ; i++){
			int src = edges.get(i).source;
			int dest = edges.get(i).destination;

			adjList.get(src).add(dest);
			adjList.get(dest).add(src);
		}
	}


}

class Edge {
	int source, destination;

	Edge(int src, int dest) {
		this.source = src;
		this.destination = dest;
	}
}
