package sokoban.controller;

import sokoban.Model;
import sokoban.viewer.Viewer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controller extends AbstractController {
    private final Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    public void keyPressed(KeyEvent event) {
        int direction;
        switch (event.getKeyCode()) {
            case 37:
                direction = 4;
                break;
            case 38:
                direction = 1;
                break;
            case 39:
                direction = 2;
                break;
            case 40:
                direction = 3;
                break;
            default:
                return;
        }
        model.move(direction);
    }

    public void mousePressed(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        model.move(x, y);
    }
}
