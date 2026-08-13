package com.mastering.sokoban.solver;

import java.util.List;

public interface ProblemSpace<T> {
    List<Node<T>> generateSuccessors(Node<T> node);

    boolean isGoal(Node<T> node);
}
