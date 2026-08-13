import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Levels {
    private int level;

    public Levels() {
        level = 1;
    }

    public int[][] getNextLevel() {
        int[][] array = null;
        switch (level) {
            case 1:
                array = getFirstLevel();
                break;
            case 2:
                array = getSecondLevel();
                break;
            case 3:
                array = getThirdLevel();
                break;
            case 4:
                array = getLevelFromFile("levels/level4.sok");
                break;
            case 5:
                array = getLevelFromFile("levels/level5.sok");
                break;
            case 6:
                array = getLevelFromFile("levels/level6.sok");
                break;
            default:
                array = getFirstLevel();
                level = 1;
                break;
        }
        level = level + 1;
        return array;
    }


    private int[][] getFirstLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 3, 0, 0, 3, 0, 0, 0, 2},
                {2, 0, 1, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 2},
                {2, 0, 0, 2, 0, 2, 2, 0, 0, 2},
                {2, 0, 3, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 4, 4, 4, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
    }

    private int[][] getSecondLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 1, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 3, 0, 0, 0, 2, 0, 0, 2},
                {2, 0, 0, 2, 2, 2, 2, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 4, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
    }

    private int[][] getThirdLevel() {
        return new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 3, 0, 0, 0, 2},
                {2, 0, 1, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 2, 0, 0, 2},
                {2, 0, 0, 2, 2, 2, 2, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 4, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
    }

    private int[][] getLevelFromFile(String fileName) {
        FileInputStream in = null;
        StringBuilder container = null;
        try {
            in = new FileInputStream(fileName);
            container = new StringBuilder();
            int unicode;
            while ((unicode = in.read()) != -1) {
                char symbol = (char) unicode;
                if (('0' <= symbol && symbol <= '4') || symbol == '\n') {
                    container.append(symbol);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
        int[][] array = null;
        if (container != null) {
            array = convertToArray(container);
        }
        return array;
    }

    private int[][] convertToArray(StringBuilder container) {
        int countArray = 0;
        for (int i = 0; i < container.length(); i++) {
            char element = container.charAt(i);
            if (element == '\n') {
                countArray = countArray + 1;
            }
            if (i == container.length() - 1) {
                countArray = countArray + 1;
            }
        }
        int[][] array = new int[countArray][];
        int a = 0;
        int b = 0;
        for (int i = 0; i < container.length(); i++) {
            char element = container.charAt(i);
            if (element == '\n') {
                array[b] = new int[a];
                b = b + 1;
                a = 0;
                continue;
            }
            a = a + 1;
            if (i == container.length() - 1) {
                array[b] = new int[a];
            }
        }

        int row = 0;
        int column = 0;
        for (int t = 0; t < container.length(); t++) {
            char element = container.charAt(t);
            if (element == '\n') {
                row = row + 1;
                column = 0;
                continue;
            }
            array[row][column] = Integer.parseInt(element + "");
            column = column + 1;
        }
        return array;
    }
}
