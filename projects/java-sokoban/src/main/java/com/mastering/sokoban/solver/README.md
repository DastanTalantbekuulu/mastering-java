# Sokoban Solver Project

## Overview

The Sokoban Solver Project is a Java-based application designed to find solutions for various configurations of the Sokoban game, a classic puzzle where the player pushes boxes (or in this case, a single machine) onto target locations on the game board. The project showcases different search algorithms to explore and identify the shortest path to solve the puzzle, considering obstacles, objects to collect, and an exit point on the board.
The approach of this project is to conceive Sokoban as a [state space search](https://en.wikipedia.org/wiki/State_space_search) problem, that is, the problem was modelled as a state space. The set of states forms a graph where two states are connected if there is an operation that can be performed to transform the first state into the second. Consider this example:

```
_ | _ | X | _ | E | _ | _
X | _ | X | _ | _ | _ | X
_ | _ | _ | _ | _ | _ | _
_ | O | _ | X | _ | _ | _
_ | _ | _ | X | _ | _ | _
_ | M | _ | X | _ | O | _
_ | A | _ | _ | _ | _ | _
```

The letter A represents the agent, the letter M represents the machine, the letter O represents objects to be caught by the machine, X's are obstacles, underlines are empty spaces and E represents the exit. The goal of the game is for the agent to push the machine in order to catch all the objects (the machine should be in the same cell as the object for that to happen), and then, push the machine towards the exit. The agent can only move up, down, right or left.

## Features

- Multiple Scenarios: The solver includes three predefined board configurations (scenarios) that represent different levels of complexity in the Sokoban game.
- Interactive Selection: Users can select the scenario they wish to solve and the search algorithm to use through a simple console interface.
- Search Algorithms: Implements various search algorithms, including:
  1. Breadth-First Search (BFS), both optimized (ignoring repeated states) and non-optimized.
  2. Depth-First Search (DFS), both optimized (ignoring repeated states) and non-optimized.
  3. A\* Search with two heuristics:
     - Optimized: Uses the Manhattan distance to the nearest object or exit if no objects remain.
     - Non-Optimized: Considers the Manhattan distance to the exit plus the count of remaining objects.
- Path Solution: Displays the solution path for the selected scenario using the chosen search algorithm.
- Performance Statistics: For each search execution, the application prints statistics such as the number of nodes expanded, the number of unique nodes visited, and the maximum number of nodes stored in memory.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine.

### Running the Application

1. Clone or download the project repository to your local machine.
2. Open a terminal or command prompt.
3. Navigate to the directory containing the project files.
4. Compile the Java files using the following command:

```
javac *.java
```

5. Run the application with:

```
java Main
```

6. Follow the on-screen instructions to select a scenario and a search algorithm.

### How It Works

The application asks the user to choose from predefined Sokoban scenarios, each with a unique configuration of obstacles, objects (to be collected by the machine), and an exit. After selecting a scenario, the user chooses one of the search algorithms to solve the puzzle. The solver then calculates the solution path, if one exists, and displays the steps on the console along with performance statistics.

### Project Structure

- Main: The entry point of the application. Handles user inputs and displays results.
- Example: A utility class that encapsulates a scenario configuration including the board dimension, exit position, initial state, and so on.
- State: Represents a state in the Sokoban game, including the positions of the agent, machine, objects, and obstacles.
- Position: A simple class to represent the coordinates (x, y) of entities on the board.
- SokobanProblem: Defines the problem space for the Sokoban game, including generating successor states and checking goal conditions.
- Search algorithms (BreadthFirstSearch, DepthFirstSearch, AStarSearch): Implementation of different search strategies.
- Node: A class used by search algorithms to represent nodes in the search tree, including the state, parent node, cost, and heuristic value (for A\*).
