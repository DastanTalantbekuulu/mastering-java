package grid;

public class Main {
    private static StringBuilder map = new StringBuilder(
            "444444444444 400000000004 400000000004 400000000004 400001000004 400000020004 400000000004 400000030004 400000000004 400000000004 400000000004 444444444444");

    public static void main(String[] args) {
        Cell cellZero = new Cell();
        Cell cellLeft;
        Cell cellRight;
        for (int i = 0; i < 30; i++) {
            cellLeft = i == 0 ? cellZero : new Cell();
            for (int j = 0; j < 30; j++) {
                cellRight = new Cell();
                cellLeft.right = cellRight;
                cellRight.left = cellLeft;
                cellLeft = cellRight;
            }
        }
    }

    private static void create(char c) {
        if (c == '0') {
        }
    }
}
