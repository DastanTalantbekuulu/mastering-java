
public class Database {
    public static String[] buttonLabels = {
            "(", ")", "C", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "\u00B1", "="
    };

    public static String[] actionCommands = {
            "(", ")", "Clear", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "ToggleSign", "Equal"
    };

    public static int startX = 10;
    public static int startY = 10;

    public static int buttonW = 100;
    public static int buttonH = 75;

    public static int offset = 15;

    public static int rows = 4;
    public static int columns = buttonLabels.length / rows + (buttonLabels.length % rows > 0 ? 1 : 0);

    public static int textFieldW = rows * buttonW + (rows - 1) * offset;
    public static int textFieldH = 75;

    public static int frameX = 50;
    public static int frameY = 50;
    public static int frameW = textFieldW + 2 * offset;
    public static int frameH = textFieldH + columns * buttonH + (columns + 4) * offset;

    public static int buttonX = startX;
    public static int buttonY = textFieldH + offset + startY;
}
