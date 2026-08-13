package font.listener;

import font.model.FontContainer;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class FamilyTextFieldSearchListener implements DocumentListener {
    private FontContainer fontContainer;

    public FamilyTextFieldSearchListener() {
        fontContainer = FontContainer.getInstance();
    }

    public void insertUpdate(DocumentEvent event) {
        select(event);
    }

    public void removeUpdate(DocumentEvent event) {
        select(event);
    }

    public void changedUpdate(DocumentEvent event) {
    }

    private void select(DocumentEvent event) {
        try {
            if (event.getDocument().getLength() > 0) {
                fontContainer.find(event.getDocument().getText(0, event.getDocument().getLength()));
            }
        } catch (BadLocationException ble) {
            System.out.println(ble);
        }
    }
}
