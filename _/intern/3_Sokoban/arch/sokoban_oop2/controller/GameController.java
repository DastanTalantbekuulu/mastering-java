package controller;

import model.Direction;
import model.GameModel;
import viewer.GameView;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private GameModel model;

    public GameController(GameModel model) {
        this.model = model;
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.movePlayer(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                model.movePlayer(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                model.movePlayer(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                model.movePlayer(Direction.RIGHT);
                break;
        }
    }
}
