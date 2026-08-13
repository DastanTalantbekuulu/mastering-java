package com.mastering.sokoban.solver;

public class Node<T> {
    private T state; // The state represented by this node
    private Node<T> parent; // The parent node in the path
    private int cost; // The cost of reaching this node from the start node
    private int heuristic;

    // Constructor
    public Node(T state, Node<T> parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    public Node(T state, Node<T> parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    // Getters and Setters
    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTotalCost() {
        return cost + heuristic;
    }
}