package listener;

import file.SynchronizeTextModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.io.File;

public class StylepadDocumentListener implements DocumentListener {
    private boolean flag;
    private SynchronizeTextModel synchronizeTextModel;

    public StylepadDocumentListener(SynchronizeTextModel synchronizeTextModel) {
        this.synchronizeTextModel = synchronizeTextModel;
    }

    public void insertUpdate(DocumentEvent event) {
        if (flag) {
            try {
                String insert = event.getDocument().getText(event.getOffset(), event.getLength());
                synchronizeTextModel.write(insert, event.getOffset());
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUpdate(DocumentEvent event) {
        if (flag) {
            int offset = event.getOffset();
            int length = event.getLength();
            System.out.println("Correctly deleted at offset: " + offset + ", length: " + length);
            synchronizeTextModel.write(length, offset);
        }
    }

    public void changedUpdate(DocumentEvent event) {
        if (flag) {
            System.out.println("change " + event.getLength() + " " + event.getOffset());
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        if (flag) {
            this.flag = synchronizeTextModel.openFile();
        } else {
            synchronizeTextModel.closeFile();
        }
    }

    public void setFile(File file) {
        synchronizeTextModel.setFile(file);
    }
}
