package font.dialog;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumericDocument extends PlainDocument {
    private static final long serialVersionUID = 4791367436996503005L;

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null || offs > 1 || !Character.isDigit(str.charAt(0)) || offs == 0 && str.charAt(0) == '0') {
            return;
        }
        super.insertString(offs, str, a);
    }
}
