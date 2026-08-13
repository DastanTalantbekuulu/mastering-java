import javax.swing.Timer;
import java.util.LinkedList;
import java.util.Queue;

public class Model implements Mediator {

    private Viewer viewer;
    private UndoManager undoManager;
    private int[][] desktop;
    private int[][] goalPositions;
    private int[][] boxPositions;
    private int indexX;
    private int indexY;
    private int tileSize;
    private int levelWidth;
    private int levelHeight;
    private boolean stateDesktop;
    private Level levels;
    private boolean canMove;
    private GameMusic playLoop;
    private GameMusic moveSound;
    private GameMusic winSound;
    private GameMusic gameOverSound;
    private StatsCanvas statsCanvas;
    private int moveCount;
    private int elapsedTime;
    private Timer timer;
    private boolean isTimerStarted;
    private GameMusic gameMusic;

    private int currentStep;
    private int undoCount;
    private PlayerDrag playerDrag;
    private Queue<int[]> queue;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        undoManager = new UndoManager();
        levels = new Level();
        queue = new LinkedList<>();
        moveSound = new GameMusic("resources/music/move.wav", 5);
        winSound = new GameMusic("resources/music/gamewin.wav", 5);
        gameMusic = new GameMusic("resources/music/loopmusic.wav", 5);
        gameOverSound = new GameMusic("resources/music/gameOver.wav", 5);
        gameMusic.playLoop();
        initialization();
        timer = new Timer(1000, e -> updateElapsedTime());
        playerDrag = new PlayerDrag();
    }

    private void initialization(){
        desktop = levels.nextLevel();
        setTileSize();
        resetMoveCount();
        stateDesktop = true;
        int countOne = 0;
        int countThree = 0;
        int countFour = 0;

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    countOne = countOne + 1;
                    indexY = i;
                    indexX = j;
                } else if (desktop[i][j] == 3) {
                    countThree = countThree + 1;
                } else if (desktop[i][j] == 4) {
                    countFour = countFour + 1;
                }
            }
        }

        if ((countOne != 1) || (countThree == 0) || (countFour == 0) || (countThree != countFour)) {
            stateDesktop = false;
            return;
        }

        boxPositions = new int[2][countThree];
        goalPositions = new int[2][countFour];

        int boxIndex = 0;
        int goalIndex = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 3) {
                    boxPositions[0][boxIndex] = i;
                    boxPositions[1][boxIndex] = j;
                    boxIndex = boxIndex + 1;
                } else if (desktop[i][j] == 4) {
                    goalPositions[0][goalIndex] = i;
                    goalPositions[1][goalIndex] = j;
                    goalIndex = goalIndex + 1;
                }
            }
        }
        undoManager.setupUndoManager(createNewUndoableMove());
    }

    public boolean getState() {
        return stateDesktop;
    }

    public void enableMovementControls(boolean enable) {
      canMove = enable;
    }

    public void move(int direction) {
        boolean moved;

        if(!canMove) {
            return;
        }

        if (!isTimerStarted) {
            if (!timer.isRunning()) {
                startTimer();
            }
            isTimerStarted = true;
        }

        // Up
        if (direction == 1) {
            moved = moveUp();
            // Right
        } else if (direction == 2) {
            moved = moveRight();
            // Down
        } else if (direction == 3) {
            moved = moveDown();
            // Left
        } else if (direction == 4) {
            moved = moveLeft();
        } else {
            return;
        }

        if (moved) {
            incrementMoveCount(1);
            statsCanvas.updateMoveCount(moveCount);
            checkGoal();
            checkBox(direction);
            UndoableMove undoableMove = createNewUndoableMove();
            undoManager.addUndoableMove(undoableMove);
            viewer.updateGameField();
            win();
        }
    }

    private UndoableMove createNewUndoableMove() {
        return new UndoableMove(indexX, indexY, desktop[indexY][indexX], boxPositions);
    }

    private boolean moveDown() {
        boolean moved = false;
        if (desktop[indexY + 1][indexX] == 3 || desktop[indexY + 1][indexX] == 8) {
            if (desktop[indexY + 2][indexX] == 0) {
                desktop[indexY + 1][indexX] = 0;
                desktop[indexY + 2][indexX] = 3;
                moveSound.play();
                moved = true;
            }
            if (desktop[indexY + 2][indexX] == 4) {
                desktop[indexY + 1][indexX] = 0;
                desktop[indexY + 2][indexX] = 8;
                moveSound.play();
            }
        }
        if (desktop[indexY + 1][indexX] == 0 || desktop[indexY + 1][indexX] == 4) {
          desktop[indexY][indexX] = 0;
          indexY = indexY + 1;
          desktop[indexY][indexX] = 1;
          moveSound.play();
          moved = true;
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;

        if (desktop[indexY - 1][indexX] == 3 || desktop[indexY - 1][indexX] == 8) {
            if (desktop[indexY - 2][indexX] == 0) {
                desktop[indexY - 1][indexX] = 0;
                desktop[indexY - 2][indexX] = 3;
                moveSound.play();
                moved = true;
            }
            if (desktop[indexY - 2][indexX] == 4) {
                desktop[indexY - 1][indexX] = 0;
                desktop[indexY - 2][indexX] = 8;
                moveSound.play();
            }
        }
        if (desktop[indexY - 1][indexX] == 0 || desktop[indexY - 1][indexX] == 4) {
            desktop[indexY][indexX] = 0;
            indexY = indexY - 1 ;
            desktop[indexY][indexX] = 5;
            moveSound.play();
            moved = true;
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;

        if (desktop[indexY][indexX - 1] == 3 || desktop[indexY][indexX - 1] == 8) {
            if (desktop[indexY][indexX - 2] == 0) {
                desktop[indexY][indexX - 1] = 0;
                desktop[indexY][indexX - 2] = 3;
                moveSound.play();
                moved = true;
            }
            if (desktop[indexY][indexX - 2] == 4) {
                desktop[indexY][indexX - 1] = 0;
                desktop[indexY][indexX - 2] = 8;
                moveSound.play();
            }
        }

        if (desktop[indexY][indexX - 1] == 0 || desktop[indexY][indexX - 1] == 4) {
            desktop[indexY][indexX] = 0;
            indexX = indexX - 1 ;
            desktop[indexY][indexX] = 6;
            moveSound.play();
            moved = true;
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;

        if (desktop[indexY][indexX + 1] == 3 || desktop[indexY][indexX + 1] == 8) {
            if (desktop[indexY][indexX + 2] == 0) {
                desktop[indexY][indexX + 1] = 0;
                desktop[indexY][indexX + 2] = 3;
                moveSound.play();
                moved = true;
            }
            if (desktop[indexY][indexX + 2] == 4) {
                desktop[indexY][indexX + 1] = 0;
                desktop[indexY][indexX + 2] = 8;
                moveSound.play();
            }
        }

        if (desktop[indexY][indexX + 1] == 0 || desktop[indexY][indexX + 1] == 4) {
            desktop[indexY][indexX] = 0;
            indexX = indexX + 1 ;
            desktop[indexY][indexX] = 7;
            moveSound.play();
            moved = true;
        }
        return moved;
    }

    private void checkGoal() {
        for (int k = 0; k < goalPositions[0].length; k++) {
            int i = goalPositions[0][k];
            int j = goalPositions[1][k];
            if (desktop[i][j] == 0) {
                desktop[i][j] = 4;
            }
        }
    }

    private void checkBox(int direction) {
        for (int index = 0; index < boxPositions[0].length; index++) {
            int i = boxPositions[0][index];
            int j = boxPositions[1][index];
            if (desktop[i][j] != 3 && desktop[i][j] != 8) {
                if(direction == 1) {
                    boxPositions[0][index] = i - 1;
                } else if(direction == 2) {
                    boxPositions[1][index] = j + 1;
                } else if(direction == 3) {
                    boxPositions[0][index] = i + 1;
                } else if(direction == 4) {
                    boxPositions[1][index] = j - 1;
                }
            }
        }
    }

    private void win() {
        boolean isWin = true;
        for (int k = 0; k < goalPositions[0].length; k++) {
            int i = goalPositions[0][k];
            int j = goalPositions[1][k];
            if(desktop[i][j] != 8){
                isWin = false;
                break;
            }
        }
        if (isWin) {
            timer.stop();
            winSound.play();
            int minute = statsCanvas.getMinutes();
            int second = statsCanvas.getSeconds();
            viewer.showWonDialog(moveCount, minute, second);
            resetLevel();
            currentStep = 1;
            undoCount =  0;
            setupHeart(undoCount);
        }
        if (statsCanvas != null) {
            statsCanvas.updateLevel(levels.getCurrentLevel());
        }
    }

    public boolean isPathAvailable(int destX, int destY) {
        if(!canMove) {
            return false;
        }

        if (destX < 0 || destX >= desktop.length || destY < 0 || destY >= desktop[destX].length) {
            return false;
        }

        if (desktop[destX][destY] == 2 || desktop[destX][destY] == 3 || desktop[destX][destY] == 8) {
            return false;
        }

        int startX = -1, startY = -1;

        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1 || desktop[i][j] == 5 || desktop[i][j] == 6 || desktop[i][j] == 7) {
                    startX = i;
                    startY = j;
                    break;
                }
            }
            if (startX != -1) {
                break;
            }
        }

        if (startX == -1 || startY == -1) {
            return false;
        }

        int[] dX = {-1, 0, 1, 0};
        int[] dY = {0, 1, 0, -1};

        queue.clear();
        queue.add(new int[]{startX, startY, 0});

        boolean[][] visited = new boolean[desktop.length][desktop[0].length];
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int steps = current[2];

            if (x == destX && y == destY) {
                incrementMoveCount(steps);
                moveSound.play();
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int newX = x + dX[i];
                int newY = y + dY[i];

                if (newX >= 0 && newX < desktop.length && newY >= 0 && newY < desktop[newX].length) {
                    if (!visited[newX][newY] && desktop[newX][newY] != 2 && desktop[newX][newY] != 3 && desktop[newX][newY] != 8) {
                        visited[newX][newY] = true;
                        queue.add(new int[]{newX, newY, steps + 1});
                    }
                }
            }
        }
        return false;
    }

    public void handleMouseClick(int x, int y) {

        int startX = ((GameCanvas)viewer.getContainerCanvas().get("fieldCanvas")).getStartX();
        int startY = ((GameCanvas)viewer.getContainerCanvas().get("fieldCanvas")).getStartY();
        int clickedX = x - startX;
        int clickedY = y - startY;
        if(clickedX < 0 || clickedY < 0) {
            return;
        }
        // indeces of clicked cell in desktop[][]
        int destX = clickedX / tileSize;
        int destY = clickedY / tileSize;

        if(0 <= destY && destY < desktop.length && 0 <= destX && destX < desktop[0].length) {

            System.out.println("Clicked cell is " + desktop[destY][destX]);
            if(isPathAvailable(destY, destX)) {
                setPlayerPosition(destX, destY);
                checkGoal();
                undoManager.addUndoableMove(createNewUndoableMove());
                viewer.updateGameField();
            }
        }
    }

    public void setPlayerPosition(int x, int y) {
        desktop[indexY][indexX] = 0;
        indexX = x;
        indexY = y;
        desktop[y][x] = 1;
    }

    public int[][] getDesktop() {
        return desktop;
    }

    private void setTileSize() {
        int longestRow = 0;
        int longestColumn = 0;

        for (int i = 0; i < desktop.length; i++) {
            if (desktop[i].length > longestRow) {
                longestRow = desktop[i].length;
            }
        }

        for (int j = 0; j < longestRow; j++) {
            int columnLength = 0;
            for (int i = 0; i < desktop.length; i++) {
                if (desktop[i].length > j) {
                    columnLength++;
                }
            }
            if (longestColumn < columnLength) {
                longestColumn = columnLength;
            }
        }

        levelHeight = longestColumn;
        levelWidth = longestRow;

        if (longestColumn * 1.0 / longestRow < viewer.getGameFieldHeight() * 1.0 / viewer.getGameFieldWidth()){
            tileSize = viewer.getGameFieldWidth() / longestRow;
            return;
        }
        tileSize = viewer.getGameFieldHeight() / longestColumn;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public int getTileSize(){
        return tileSize;
    }

    public void prevLevel() {
        levels.prevLevel();
        resetLevel();
        updateCurrentLevel();

        currentStep = 1;
        undoCount =  0;
        statsCanvas.showImage(currentStep);
        setupHeart(undoCount);
    }

    public void nextLevel() {
        resetLevel();
        statsCanvas.updateLevel(levels.getCurrentLevel());

        currentStep = 1;
        undoCount =  0;
        statsCanvas.showImage(currentStep);
        setupHeart(undoCount);
    }

    public void startGame() {
        enableMovementControls(true);
    }

    public void restartGame() {
        levels.actualLevel();
        currentStep = 1;
        undoCount =  0;
        setupHeart(undoCount);
        resetLevel();
    }

    public void undo() {
        UndoableMove previousGameState = undoManager.undo();
        if(previousGameState != null) {
            applyPreviousGameState(previousGameState);
            viewer.updateGameField();
            moveSound.play();
            incrementMoveCount(1);

            undoCount++;
            setupHeart(undoCount);
            if (3 <= undoCount) {
                timer.stop();
                gameMusic.pause();
                gameOverSound.play();
                viewer.showGameOverDialog();
                restartGame();
                undoCount = 0;
                gameMusic.play();
            }
        }
    }

    public void redo() {
      UndoableMove previousGameState = undoManager.redo();
      if(previousGameState != null) {
        applyPreviousGameState(previousGameState);
        viewer.updateGameField();
        moveSound.play();
        incrementMoveCount(1);
      }
    }

    private void applyPreviousGameState(UndoableMove previousGameState) {
        desktop[indexY][indexX] = 0;
        indexX = previousGameState.getPlayerX();
        indexY = previousGameState.getPlayerY();
        checkGoal();
        int[][] previousBoxPositions = previousGameState.getBoxPositions();
        for(int i = 0; i < boxPositions[0].length; i++) {
            int currentY = boxPositions[0][i];
            int currentX = boxPositions[1][i];
            int previousY = previousBoxPositions[0][i];
            int previousX = previousBoxPositions[1][i];
            if(currentX != previousX || currentY != previousY) {
                desktop[currentY][currentX] = 0;
                desktop[previousY][previousX] = desktop[previousY][previousX] == 4 ? 8 : 3;
                boxPositions[0][i] = previousY;
                boxPositions[1][i] = previousX;
            }
        }
        desktop[indexY][indexX] = previousGameState.getPlayer();
        checkGoal();
    }

    private void setupHeart(int undoCount) {
        currentStep = undoCount + 1;
        if (currentStep > 4) {
            currentStep = 1;
        }
        statsCanvas.showImage(currentStep);
    }

    public void pauseMusic() {
        gameMusic.pause();
    }

    public void resumeMusic() {
        gameMusic.resume();
    }

    public boolean isPlayerOnTarget(int player) {
        for(int i = 0; i < goalPositions[0].length; i++) {
            int goalY = goalPositions[0][i];
            int goalX = goalPositions[1][i];
            if(desktop[goalY][goalX] == player) {
                return true;
            }
        }
        return false;
    }

    public void setStatsCanvas(StatsCanvas statsCanvas) {
        this.statsCanvas = statsCanvas;
    }

    public void incrementMoveCount(int count) {
        moveCount = moveCount + count;
        if (statsCanvas != null) {
            statsCanvas.updateMoveCount(moveCount);
        }
    }

    public void resetMoveCount() {
        moveCount = 0;
        if (statsCanvas != null) {
            statsCanvas.updateMoveCount(moveCount);
        }
    }

    private void updateElapsedTime() {
        elapsedTime = elapsedTime + 1;
        if (statsCanvas != null) {
            statsCanvas.updateTime(elapsedTime);
        }
    }

    public void startTimer() {
        elapsedTime = 0;
        if (statsCanvas != null) {
            statsCanvas.updateTime(elapsedTime);
        }
        timer.restart();
    }

    private void resetLevel() {
        initialization();
        viewer.updateGameField();
        timer.stop();
        elapsedTime = 0;
        if (statsCanvas != null) {
            statsCanvas.updateTime(elapsedTime);
        }
        isTimerStarted = false;
    }

    public PlayerDrag getPlayerDrag() {
        return playerDrag;
    }

    public void dragPlayer() {
        int mousePressedX = playerDrag.getPressedX();
        int mousePressedY = playerDrag.getPressedY();
        int mouseReleasedX = playerDrag.getReleasedX();
        int mouseReleasedY = playerDrag.getReleasedY();

        int minDistance = 50;
        int maxOffset = 30;

        if (mousePressedX + minDistance < mouseReleasedX &&
                Math.abs(mousePressedY - mouseReleasedY) <= maxOffset) {
            move(2);
        } else if (mousePressedX - minDistance > mouseReleasedX &&
                Math.abs(mousePressedY - mouseReleasedY) <= maxOffset) {
            move(4);
        } else if (mousePressedY + minDistance < mouseReleasedY &&
                Math.abs(mousePressedX - mouseReleasedX) <= maxOffset) {
            move(3);
        } else if (mousePressedY - minDistance > mouseReleasedY &&
                Math.abs(mousePressedX - mouseReleasedX) <= maxOffset) {
            move(1);
        }
    }
    public void handleMouseWheel(int wheelRotation) {
        if (wheelRotation < 0) {
            move(1);
        } else {
            move(3);
        }
    }
    public void updateCurrentLevel() {
        if (statsCanvas != null) {
           statsCanvas.updateLevel(levels.getCurrentLevel());
       }
   }
}
