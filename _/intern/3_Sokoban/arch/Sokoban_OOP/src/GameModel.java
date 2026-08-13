import java.awt.Image;
import java.util.Map;

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
        if(player.move(direction, grid)){
            gameView.repaint();
        }
    }
    public void next(){}
    public void prev(){}
    public void nextLevel(){}
    public void prevLevel(){}
}
