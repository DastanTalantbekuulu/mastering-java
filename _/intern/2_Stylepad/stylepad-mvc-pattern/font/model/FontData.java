package font.model;

import javax.swing.DefaultListModel;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.NavigableMap;
import java.util.TreeMap;

class FontData {

    private NavigableMap<String, DefaultListModel<Font>> fontTable;
    private DefaultListModel<Font> families;

    public FontData() {
        fontTable = new TreeMap<>();
        families = new DefaultListModel<>();
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        for (Font font : fonts) {
            if (font.getName().equals(font.getFamily())) {
                families.addElement(font.deriveFont(12f));
            }
            if (fontTable.containsKey(font.getFamily())) {
                fontTable.get(font.getFamily()).addElement(font.deriveFont(12f));
            } else {
                DefaultListModel<Font> fontList = new DefaultListModel<>();
                fontList.addElement(font.deriveFont(12f));
                fontTable.put(font.getFamily(), fontList);
            }
        }
    }

    public DefaultListModel<Font> getFamilyModel() {
        return families;
    }

    public DefaultListModel<Font> getStyleModel(String family) {
        return fontTable.get(family);
    }

    public Font find(String str) {
        String key = fontTable.ceilingKey(str);
        if (key != null) {
            for (int i = 0; i < families.size(); i++) {
                if (families.get(i).getFamily().equals(key)) {
                    return families.get(i);
                }
            }
        }
        return null;
    }
}