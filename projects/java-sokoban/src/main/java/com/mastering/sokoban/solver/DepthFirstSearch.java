package com.mastering.sokoban.solver;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DepthFirstSearch<T> {

    private int maxStatesInMemory = 0; // Maximum number of states stored in memory
    private int expandedNodes = 0;

    public List<T> search(ProblemSpace<T> problem, T initialState, boolean visitRepeatedStates) {
        Deque<Node<T>> frontier = new LinkedList<>(); // Use Deque as a stack
        frontier.push(new Node<>(initialState, null, 0)); // Push the initial node onto the stack

        Set<T> visited = new HashSet<>();

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop(); // Pop the node from the top of the stack

            if (problem.isGoal(currentNode)) {
                // Print statistics
                System.out.println("Expanded nodes: " + expandedNodes);
                System.out.println("Visited states: " + visited.size());
                System.out.println("Max states in memory: " + maxStatesInMemory);
                return constructPath(currentNode);
            }

            // Mark the current state as visited when it's popped from the stack
            visited.add(currentNode.getState());

            List<Node<T>> successors = problem.generateSuccessors(currentNode);
            expandedNodes += successors.size();

            for (Node<T> successor : successors) {

                if (visitRepeatedStates) {
                    frontier.push(successor); // Push all successors onto the stack
                } else if (!visited.contains(successor.getState())) {
                    frontier.push(successor); // Push only non-visited successors onto the stack
                }

            }

            // Update maximum states in memory counter
            maxStatesInMemory = Math.max(maxStatesInMemory, frontier.size() + visited.size());
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
