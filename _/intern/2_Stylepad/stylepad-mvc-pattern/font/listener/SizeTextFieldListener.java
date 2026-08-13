package font.listener;

import font.model.FontContainer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class SizeTextFieldListener implements DocumentListener {
    private FontContainer fontContainer;

    public SizeTextFieldListener() {
        fontContainer = FontContainer.getInstance();
    }

    public void insertUpdate(DocumentEvent event) {
        changeSize(event);
    }

    public void removeUpdate(DocumentEvent event) {
        changeSize(event);
    }

    public void changedUpdate(DocumentEvent event) {
    }

    private void changeSize(DocumentEvent event) {
        try {
            if (0 < event.getDocument().getLength()) {
                int size = Integer.parseInt(event.getDocument().getText(0, event.getDocument().getLength()));
                fontContainer.setSize((float) size);
            }
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }
}
