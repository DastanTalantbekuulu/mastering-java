package com.mastering.sokoban.solver;

import java.util.Arrays;
import java.util.Objects;

class State {
    Position agentPosition;
    Position machinePosition;
    Position[] obstaclePositions;
    Position[] objectPositions;

    public State(Position agentPosition, Position machinePosition, Position[] obstaclePositions,
            Position[] objectPositions) {
        this.agentPosition = agentPosition;
        this.machinePosition = machinePosition;
        this.obstaclePositions = obstaclePositions;
        this.objectPositions = objectPositions;
    }

    // Copy constructor
    public State(State state) {
        this.agentPosition = state.agentPosition;
        this.machinePosition = state.machinePosition;
        this.objectPositions = state.objectPositions;
        this.obstaclePositions = state.obstaclePositions;
    }

    // The equals method needs to be overriden so that we can later check if a State
    // was already visited
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        State state = (State) o;
        return Objects.equals(agentPosition, state.agentPosition) &&
                Objects.equals(machinePosition, state.machinePosition) &&
                Arrays.equals(obstaclePositions, state.obstaclePositions) &&
                Arrays.equals(objectPositions, state.objectPositions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(agentPosition, machinePosition);
        result = 31 * result + Arrays.hashCode(obstaclePositions);
        result = 31 * result + Arrays.hashCode(objectPositions);
        return result;
    }
}