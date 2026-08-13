package com.mastering.sokoban.solver;

public class Example {
    int dimension;
    Position exit;
    State initialState;

    public Example(int dimension, Position exit, State initialState) {
        this.dimension = dimension;
        this.exit = exit;
        this.initialState = initialState;
    }
}
