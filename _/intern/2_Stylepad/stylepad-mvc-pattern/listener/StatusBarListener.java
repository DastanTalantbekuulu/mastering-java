package listener;

import viewer.Viewer;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class StatusBarListener implements DocumentListener {
    private JLabel label;
    private boolean flag;
    private Viewer viewer;
    private JTextPane textPane;

    public StatusBarListener(JLabel label, Viewer viewer) {
        this.label = label;
        textPane = viewer.getTextPane();
        this.viewer = viewer;
    }

    public void insertUpdate(DocumentEvent event) {
        update();
    }

    public void removeUpdate(DocumentEvent event) {
        update();
    }

    public void changedUpdate(DocumentEvent event) {
    }

    private void update() {
        if (flag) {
            label.setText("Symbols: " + viewer.getDocument().getLength() +
                    "            Lines: " + textPane.getText().split("\n").length +
                    "            Font Family: " + textPane.getFont().getFamily() +
                    "            Size: " + textPane.getFont().getSize());
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        update();
    }
}
