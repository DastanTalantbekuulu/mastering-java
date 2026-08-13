package com.mastering.sokoban.generate;

import com.mastering.sokoban.generate.model.AreaType;
import com.mastering.sokoban.generate.model.Box;
import com.mastering.sokoban.generate.model.Coordinates;
import com.mastering.sokoban.generate.model.Player;
import com.mastering.sokoban.generate.model.TargetType;
import com.mastering.sokoban.generate.model.Vertex;

import java.awt.Graphics;
import java.util.Map;

public class Model {
    private Viewer viewer;
    private Coordinates coordinates;
    private Player player;
    private Box boxes;
    private Vertex vertex;
    private Map<Integer, String> levels;
    private BoxLink boxLink;
    private String map;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        coordinates = new Coordinates(1, 1);
        player = new Player();
        boxes = Box.getTop();

        SokobanRandom random = new SokobanRandom();
        boxLink = random.getBox();
        int maxX = boxLink.maxX(Integer.MIN_VALUE) + 1;
        int maxY = boxLink.maxY(Integer.MIN_VALUE) + 1;

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (boxLink.constains(x, y)) {
                    coordinates.set(x, y);
                    vertex = Vertex.set(coordinates, TargetType.getInstance());
                    boxes.addVertex(vertex);
                }
            }
        }
        
        int boxRandomNumber = random.getRandom(boxes.count);
        Box boxRandom = boxes.get(boxRandomNumber);
        vertex = null;
        for (int i = 1; i < 5; i++) {
            Vertex vertexBox = boxRandom.getVertex().get(i);
            if (vertexBox == null || vertexBox != null && vertexBox.isWalkable()) {
                if (i == 1) {
                    coordinates.set(boxRandom.getX(), boxRandom.getY() - 1);
                    if (vertexBox == null) {
                        vertex = Vertex.set(coordinates, AreaType.getInstance());
                        if (vertex != null) {
                            player.setVertex(vertex);
                            break;
                        }
                    }
                } else if (i == 2) {
                    coordinates.set(boxRandom.getX() + 1, boxRandom.getY());
                    if (vertexBox == null) {
                        vertex = Vertex.set(coordinates, AreaType.getInstance());
                        if (vertex != null) {
                            player.setVertex(vertex);
                            break;
                        }
                    }
                } else if (i == 3) {
                    coordinates.set(boxRandom.getX(), boxRandom.getY() + 1);
                    if (vertexBox == null) {
                        vertex = Vertex.set(coordinates, AreaType.getInstance());
                        if (vertex != null) {
                            player.setVertex(vertex);
                            break;
                        }
                    }
                } else if (i == 4) {
                    coordinates.set(boxRandom.getX() - 1, boxRandom.getY());
                    if (vertexBox == null) {
                        vertex = Vertex.set(coordinates, AreaType.getInstance());
                        if (vertex != null) {
                            player.setVertex(vertex);
                            break;
                        }
                    }
                }
            }
        }
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
