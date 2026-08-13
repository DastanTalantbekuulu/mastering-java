package font.dialog;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;
import java.awt.Font;

public class StyleLabelListCellRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 6601801196515123980L;
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
    ) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Font font = (Font) value;
        setText(font.getFamily().equals(font.getName()) ? font.getName() : font.getName().replace(font.getFamily(), ""));
        setFont(font);
        return this;
    }
}
