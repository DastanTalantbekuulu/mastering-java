package com.mastering.sokoban.graph;


import com.mastering.sokoban.graph.level.Level;
import com.mastering.sokoban.graph.model.Box;
import com.mastering.sokoban.graph.model.Coordinates;
import com.mastering.sokoban.graph.model.Player;
import com.mastering.sokoban.graph.model.Vertex;

import java.awt.Graphics;
import java.util.Map;

public class Model {
    private Viewer viewer;
    private Coordinates coordinates;
    private Player player;
    private Box boxes;
    private Level level;
    private Vertex vertex;
    private Map<Integer, String> levels;

    public Model(Viewer viewer) {
        long start = System.currentTimeMillis();

        this.viewer = viewer;
        coordinates = new Coordinates(1, 1);
        player = new Player();
        boxes = Box.getTop();
        level = new Level();
        vertex = Vertex.load(player, boxes, coordinates, level.nextLevel());
        levels = level.getMapLevels();
        levels.forEach((key, value) -> System.out.println(key + " " + value));

        long end = System.currentTimeMillis();
        System.out.println("Level " + 1 + " loaded in " + (end - start) + " milliseconds");
    }

    public void draw(Graphics graphics) {
        vertex.draw(graphics);
    }

    public void move(int direction) {
        coordinates.setDirection(direction);
        if (player.move(coordinates)) {
            viewer.update();
        }
    }

    public void move(int x, int y) {
    }
}
