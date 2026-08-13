package model;

import level.Level;
import model.mobile.Player;
import viewer.GameView;

public class GameModel {

    private GameView gameView;
    private GridContainer container;

    public GameModel(GameView gameView) {
        this.gameView = gameView;
        container = new GridContainer(Level.lvl1);
    }

    public Grid getGrid() {
        return container.getGrid();
    }

    public Player getPlayer() {
        return container.getPlayer();
    }

    public void movePlayer(Direction direction) {
        if (container.move(direction)) {
            gameView.repaint();
        }
    }

    public void next() {
    }

    public void prev() {
    }

    public void nextLevel() {
    }

    public void prevLevel() {
    }
}
