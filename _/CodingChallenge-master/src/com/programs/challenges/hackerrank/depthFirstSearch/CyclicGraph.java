package com.programs.challenges.hackerrank.depthFirstSearch;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a connected undirected graph , find if it contains cycle or not
 */
public class CyclicGraph {

    public static void main(String[] args) {

        List<Edge> edges = Arrays.asList(
                            new Edge(1, 2), new Edge(1, 7),
                            new Edge(1, 8), new Edge(2, 3),
                            new Edge(2, 6), new Edge(3, 4),
                            new Edge(3, 5), new Edge(8, 9),
                            new Edge(8, 12), new Edge(9, 10),
                            new Edge(9, 11), new Edge(11, 12)
                            // edge (11->12) introduces a cycle in the graph
                            );

        int N = 13;

        Graph graph = new Graph(edges,N);

        boolean[] discovered = new boolean[N];

        if(DFS(graph,1,-1,discovered)){
            System.out.println("Graph contains cycle");
        }else{
            System.out.println("Graph does not contain cycle");
        }

    }


    static boolean DFS(Graph graph,int w, int parent, boolean[] discovered){

        discovered[w] = true;

        for(int v : graph.adjList.get(w)) {

            if (!discovered[v]) {

                if (DFS(graph,v,w,discovered))
                    return true;

            } else if(w != parent){
                return true; // if w is discovered and is not parent graph contain cycle

            }
        }

        return false;

    }



    static class Graph{

        List<List<Integer>> adjList;

        Graph(List<Edge> edges,int N){
            adjList = new ArrayList<>(N);
            for(int i= 0; i < N;i++){
                adjList.add(i,new ArrayList<>());
            }

            for (Edge edge : edges){
                int src = edge.src;
                int dest = edge.dest;

                adjList.get(src).add(dest);
                adjList.get(dest).add(src);
            }

        }

    }

    static class Edge{
        int src,dest;

        Edge(int src,int dest){
            this.src = src;
            this.dest = dest;
        }

    }


}
