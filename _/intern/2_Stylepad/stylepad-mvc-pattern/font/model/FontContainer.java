package font.model;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Font;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public class FontContainer {

    private static FontContainer INSTANCE;

    public static FontContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FontContainer();
        }
        return INSTANCE;
    }

    private JLabel labelPreview;
    private final FontData fontData;
    private JList<Font> familyList;
    private JList<Font> styleList;
    private JList<Integer> sizeList;
    private NavigableSet<Integer> sizeSet;
    private float size;

    private FontContainer() {
        fontData = new FontData();

        labelPreview = new JLabel();

        familyList = new JList<>();
        familyList = new JList<>(fontData.getFamilyModel());
        familyList.setSelectedIndex(0);

        styleList = new JList<>(fontData.getStyleModel(familyList.getSelectedValue().getFamily()));

        sizeSet = new TreeSet<>(Arrays.asList(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72));
        DefaultListModel<Integer> sizeModel = new DefaultListModel<>();
        sizeModel.addAll(sizeSet);
        sizeList = new JList<>(sizeModel);
        sizeList.setSelectedIndex(10);

        size = sizeList.getSelectedValue();
        setDefaultStyle();
    }

    public JList<Font> getFamilyList() {
        return familyList;
    }

    public JList<Font> getStyleList() {
        return styleList;
    }

    public JList<Integer> getSizeList() {
        return sizeList;
    }

    public void setFont(Font font) {
        String style = font.getFamily().equals(font.getName()) ?
                font.getName() : font.getName().replace(font.getFamily(), "");
        selectFamily(font.getFamily());
        selectStyle(style);
        size = font.getSize();
        setSize();
    }

    public void selectFamily(String family) {
        for (int i = 0; i < familyList.getModel().getSize(); i++) {
            if (familyList.getModel().getElementAt(i).getFamily().equals(family)) {
                familyList.setSelectedIndex(i);
                familyList.ensureIndexIsVisible(i);
                break;
            }
        }
        styleList.setModel(fontData.getStyleModel(family));
    }

    public void selectStyle(String style) {
        for (int i = 0; i < styleList.getModel().getSize(); i++) {
            if (styleList.getModel().getElementAt(i).getName().equals(style)) {
                setSelectedIndexStyle(i);
                break;
            }
        }
    }

    public void setDefaultStyle() {
        setSelectedIndexStyle(0);
    }

    public void setSelectedIndexStyle(int index) {
        if(!familyList.isSelectionEmpty()) {
            styleList.setModel(fontData.getStyleModel(familyList.getSelectedValue().getFamily()));
            styleList.setSelectedIndex(index);
            styleList.ensureIndexIsVisible(index);
            setFontPreview();
        }
    }

    public void setFontPreview() {
        if (!styleList.isSelectionEmpty()) {
            labelPreview.setFont(styleList
                    .getSelectedValue()
                    .deriveFont(size));
        }
    }

    public void setSize(float size) {
        this.size = size;
        if (sizeSet.contains((int) size)) {
            sizeList.setSelectedValue((int) size, true);
        } else {
            sizeList.clearSelection();
        }
        labelPreview.setFont(labelPreview.getFont().deriveFont(size));
    }

    public void setSize() {
        if (!sizeList.isSelectionEmpty()) {
            size = sizeList.getSelectedValue().floatValue();
            labelPreview.setFont(labelPreview.getFont().deriveFont(size));
        }
    }

    public JLabel getLabelPreview() {
        return labelPreview;
    }

    public Font getFont() {
        return labelPreview.getFont();
    }

    public void find(String str) {
        familyList.setSelectedValue(fontData.find(str), true);
        setDefaultStyle();
    }
}
