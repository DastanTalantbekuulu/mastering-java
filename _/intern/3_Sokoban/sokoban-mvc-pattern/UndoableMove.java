public class UndoableMove {
    private int player;
    private int playerX;
    private int playerY;
    private int[][] boxPositions;

    public UndoableMove(int playerX, int playerY, int player, int[][] boxPositions) {
        this.player = player;
        this.playerX = playerX;
        this.playerY = playerY;
        this.boxPositions = initBoxPositions(boxPositions);
    }

    private int[][] initBoxPositions(int[][] boxPositions) {
        int[][] boxes = new int[2][boxPositions[0].length];

        for(int i = 0; i < boxPositions[0].length; i++) {
            boxes[0][i] = boxPositions[0][i];
            boxes[1][i] = boxPositions[1][i];
        }

        return boxes;
    }

    public int getPlayer() {
        return player;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int[][] getBoxPositions() {
        return boxPositions;
    }

}
