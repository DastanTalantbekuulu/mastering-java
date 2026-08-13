package com.mastering.sokoban.solver;

import java.util.*;

public class AStarSearch {

    // Utility method to calculate Manhattan distance between two positions
    private static int manhattanDistance(Position a, Position b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    // This heuristic is the Manhattan distance from the machine to the nearest
    // object,
    // and if there are no objects, the Manhattan distance from the machine to the
    // exit.
    private static int calculateHeuristic(State state, Position exit) {
        int nearestObjectDistance = Integer.MAX_VALUE;
        if (state.objectPositions.length > 0) {
            // Find the Manhattan distance to the nearest object
            for (Position objectPosition : state.objectPositions) {
                int distance = manhattanDistance(state.machinePosition, objectPosition);
                nearestObjectDistance = Math.min(nearestObjectDistance, distance);
            }
            return nearestObjectDistance;
        } else {
            // If no objects left, use the distance to the exit
            return manhattanDistance(state.machinePosition, exit);
        }
    }

    // This heuristic is the Manhattan distance from the machine to the exit
    // plus the number of objects left to collect.
    private static int calculateHeuristic2(State state, Position exit) {
        return manhattanDistance(state.machinePosition, exit) + state.objectPositions.length;
    }

    public List<State> search(SokobanProblem problem, State initialState, boolean optimizedHeuristic) {
        PriorityQueue<Node<State>> frontier = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
        Map<State, Integer> costSoFar = new HashMap<>(); // Tracks the cost of the cheapest path to a state
        Map<State, State> cameFrom = new HashMap<>(); // Tracks the best parent for each visited state

        int maxStatesInMemory = 0; // Maximum number of states stored in memory
        int expandedNodes = 0; // Total number of nodes expanded
        int visitedStates = 0; // Total number of unique nodes visited

        frontier.add(new Node<>(initialState, null, 0, calculateHeuristic(initialState, problem.exit)));
        costSoFar.put(initialState, 0);

        while (!frontier.isEmpty()) {
            Node<State> currentNode = frontier.poll();
            visitedStates++;
            State currentState = currentNode.getState();

            if (problem.isGoal(currentNode)) {
                // Print statistics
                System.out.println("Expanded nodes: " + expandedNodes);
                System.out.println("Visited states: " + visitedStates);
                System.out.println("Max states in memory: " + maxStatesInMemory);
                return reconstructPath(cameFrom, currentState);
            }

            List<Node<State>> successors = problem.generateSuccessors(currentNode);
            expandedNodes += successors.size();

            for (Node<State> nextNode : successors) {
                State nextState = nextNode.getState();
                int newCost = costSoFar.get(currentState) + nextNode.getCost();
                if (!costSoFar.containsKey(nextState) || newCost < costSoFar.get(nextState)) {
                    costSoFar.put(nextState, newCost);
                    int heuristic = optimizedHeuristic ? calculateHeuristic(nextState, problem.exit)
                            : calculateHeuristic2(nextState, problem.exit);
                    int priority = newCost + heuristic;
                    frontier.add(new Node<>(nextState, currentNode, newCost, priority));
                    cameFrom.put(nextState, currentState);
                }
            }

            maxStatesInMemory = Math.max(maxStatesInMemory, frontier.size());
        }

        return null; // No solution found
    }

    private List<State> reconstructPath(Map<State, State> cameFrom, State currentState) {
        List<State> path = new LinkedList<>();
        while (currentState != null) {
            path.add(currentState);
            currentState = cameFrom.get(currentState);
        }
        Collections.reverse(path);
        return path;
    }
}
