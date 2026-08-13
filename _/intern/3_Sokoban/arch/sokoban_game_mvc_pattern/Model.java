public class Model {
    private Viewer viewer;
    private int[][] desktop;
    private int[][] goals;
    private int x;
    private int y;
    private boolean stateDesktop;
    private Levels levels;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        levels = new Levels();
        x = 3;
        y = 2;
        initialization();
    }

    private void initialization() {
        desktop = levels.getNextLevel();
        stateDesktop = true;
        int countOne = 0;
        int countThree = 0;
        int countFour = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 1) {
                    countOne = countOne + 1;
                    x = i;
                    y = j;
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
        goals = new int[2][countFour];
        int a = 0;
        for (int i = 0; i < desktop.length; i++) {
            for (int j = 0; j < desktop[i].length; j++) {
                if (desktop[i][j] == 4) {
                    goals[0][a] = i;
                    goals[1][a] = j;
                    a = a + 1;
                }
            }
        }
    }

    public void move(String direction) {
        if (direction.equals("left")) {
            moveLeft();
        } else if (direction.equals("right")) {
            moveRight();
        } else if (direction.equals("up")) {
            moveUp();
        } else if (direction.equals("down")) {
            moveDown();
        }
        checkGoal();
        viewer.update();
        won();
    }

    public int[][] getDesktop() {
        return desktop;
    }

    private void moveLeft() {
        if (desktop[x][y - 1] == 3) {
            if (desktop[x][y - 2] == 0 || desktop[x][y - 2] == 4) {
                desktop[x][y - 1] = 0;
                desktop[x][y - 2] = 3;
            }
        }
        if (desktop[x][y - 1] == 0 || desktop[x][y - 1] == 4) {
            desktop[x][y] = 0;
            y = y - 1;
            desktop[x][y] = 1;
        }
    }

    private void moveRight() {
        if (desktop[x][y + 1] == 3) {
            if (desktop[x][y + 2] == 0 || desktop[x][y + 2] == 4) {
                desktop[x][y + 1] = 0;
                desktop[x][y + 2] = 3;
            }
        }
        if (desktop[x][y + 1] == 0 || desktop[x][y + 1] == 4) {
            desktop[x][y] = 0;
            y = y + 1;
            desktop[x][y] = 1;
        }
    }

    private void moveUp() {
        if (desktop[x - 1][y] == 3) {
            if (desktop[x - 2][y] == 0 || desktop[x - 2][y] == 4) {
                desktop[x - 1][y] = 0;
                desktop[x - 2][y] = 3;
            }
        }
        if (desktop[x - 1][y] == 0 || desktop[x - 1][y] == 4) {
            desktop[x][y] = 0;
            x = x - 1;
            desktop[x][y] = 1;
        }
    }

    private void moveDown() {
        if (desktop[x + 1][y] == 3) {
            if (desktop[x + 2][y] == 0 || desktop[x + 2][y] == 4) {
                desktop[x + 1][y] = 0;
                desktop[x + 2][y] = 3;
            }
        }
        if (desktop[x + 1][y] == 0 || desktop[x + 1][y] == 4) {
            desktop[x][y] = 0;
            x = x + 1;
            desktop[x][y] = 1;
        }
    }

    private void checkGoal() {
        for (int i = 0; i < goals[0].length; i++) {
            int k = goals[0][i];
            int g = goals[1][i];
            if (desktop[k][g] == 0) {
                desktop[k][g] = 4;
            }
        }
    }

    private void won() {
        boolean flag = true;
        for (int i = 0; i < goals[0].length; i++) {
            int k = goals[0][i];
            int g = goals[1][i];
            if (desktop[k][g] != 3) {
                flag = false;
                break;
            }
        }
        if (flag) {
            viewer.showWonDialog();
            viewer.update();
            initialization();
        }
    }

    public boolean getState() {
        return stateDesktop;
    }

    public void doClick(int x, int y) {
        System.out.println(x + " " + y);
//        if ((600 <= x) && (50 <= y) && (x <= (100 + 600)) && (y <= (50 + 50))) {
//            System.out.println("Start button clicked!");
//        }
    }
}
