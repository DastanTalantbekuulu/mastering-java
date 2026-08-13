package font.dialog;

public class Position {
    private int start;
    private int familyW;
    private int styleW;
    private int sizeW;
    private int offset;
    private int chooseH;
    private int previewH;
    private int itemH;
    private int buttonW;
    private int panelW;
    private int panelH;

    public int width;
    public int height;
    public int[] panel;
    public int[] labelFamily;
    public int[] labelStyle;
    public int[] labelSize;
    public int[] labelComboBox;
    public int[] comboBox;
    public int[] labelPreview;
    public int[] textFieldFamily;
    public int[] textFieldSize;
    public int[] scrollPaneFamily;
    public int[] scrollPaneStyle;
    public int[] scrollPaneSize;
    public int[] buttonOK;
    public int[] buttonCancel;

    public Position(int width, int heigth) {
        int w = width / 10;
        int h = heigth / 10;
        start = 20;
        offset = 20;
        familyW = w * 5;
        styleW = w * 3;
        sizeW = w * 2;
        chooseH = h * 5;
        previewH = h * 4;
        itemH = h / 2;
        buttonW = w * 2;
        panelW = (start * 2) + familyW + styleW + sizeW + (offset * 2) + 15;
        panelH = (start * 2) + chooseH + (itemH * 2) + previewH + (offset * 3) + 50;
        this.width = panelW;
        this.height = panelH;

        panel = new int[]{
                0,
                0,
                panelW,
                panelH
        };
        labelFamily = new int[]{
                start,
                start,
                familyW,
                itemH
        };
        labelStyle = new int[]{
                start + familyW + offset,
                start,
                styleW,
                itemH
        };
        labelSize = new int[]{
                start + familyW + offset + styleW + offset,
                start,
                sizeW,
                itemH
        };
        labelComboBox = new int[]{
                start,
                start + chooseH + offset,
                familyW / 2,
                itemH
        };
        comboBox = new int[]{
                start + (familyW / 2) + offset,
                start + chooseH + offset,
                styleW + sizeW + offset + (familyW / 2),
                itemH
        };
        labelPreview = new int[]{
                start,
                start + chooseH + 2 * offset,
                panelW - start * 3,
                previewH
        };
        textFieldFamily = new int[]{
                start,
                start + itemH,
                familyW,
                itemH
        };
        textFieldSize = new int[]{
                start + familyW + offset + styleW + offset,
                start + itemH,
                sizeW,
                itemH
        };
        scrollPaneFamily = new int[]{
                start,
                start + itemH + itemH,
                familyW,
                chooseH - itemH * 2
        };
        scrollPaneStyle = new int[]{
                start + familyW + offset,
                start + itemH,
                styleW,
                chooseH - itemH
        };
        scrollPaneSize = new int[]{
                start + familyW + offset + styleW + offset,
                start + itemH + itemH,
                sizeW,
                chooseH - itemH * 2
        };
        buttonOK = new int[]{
                panelW / 2 - offset - buttonW,
                45 + start + chooseH + previewH + (offset * 3),
                buttonW,
                itemH
        };
        buttonCancel = new int[]{
                panelW / 2 + offset,
                45 + start + chooseH + previewH + (offset * 3),
                buttonW,
                itemH
        };
    }
}
