package com.mastering.sokoban.solver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch<T> {

    private int maxStatesInMemory = 0; // Maximum number of states stored in memory
    private int expandedNodes = 0;

    public List<T> search(ProblemSpace<T> problem, T initialState, boolean visitRepeatedStates) {
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.add(new Node<>(initialState, null, 0)); // Initial node, with no parent and zero cost

        Set<T> visited = new HashSet<>();

        while (!frontier.isEmpty()) {

            Node<T> currentNode = frontier.poll();

            if (problem.isGoal(currentNode)) {
                // Print statistics
                System.out.println("Expanded nodes: " + expandedNodes);
                System.out.println("Visited states: " + visited.size());
                System.out.println("Max states in memory: " + maxStatesInMemory);
                return constructPath(currentNode);
            }

            // Mark the current state as visited when it's dequeued and processed
            visited.add(currentNode.getState());

            List<Node<T>> successors = problem.generateSuccessors(currentNode);
            expandedNodes += successors.size();

            if (visitRepeatedStates) {
                frontier.addAll(successors);
            } else {
                for (Node<T> successor : successors) {
                    if (!visited.contains(successor.getState())) {
                        frontier.add(successor); // Add only non-visited successors to the queue
                    }
                }
            }

            // Update maximum states in memory counter
            maxStatesInMemory = Math.max(maxStatesInMemory, frontier.size());

        }

        return null; // Return null if no solution is found
    }

    private List<T> constructPath(Node<T> goalNode) {
        List<T> path = new LinkedList<>();
        for (Node<T> node = goalNode; node != null; node = node.getParent()) {
            path.add(0, node.getState()); // Add state to the beginning of the list
        }
        return path;
    }
}
