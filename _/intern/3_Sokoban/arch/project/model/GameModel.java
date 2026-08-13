package model;

import level.Level;
import model.cell.Cell;
import model.mobile.Player;
import viewer.GameView;

public class GameModel {
    private GameView gameView;
    private Grid grid;
    private Player player;
    private Level level;

    public Cell getCell(int x, int y) {
        return grid.getCell(x, y);
    }

    public Player getPlayer() {
        return player;
    }

    public void movePlayer(Direction direction) {
        if (GridMovement.move(player, direction, grid)) {
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
