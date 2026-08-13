package font.listener;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FamilyTextFieldUpperCaseDocumentFilter extends DocumentFilter {
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
            throws BadLocationException {
        if (fb.getDocument().getLength() == 0) {
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            text = sb.toString();
        }
        fb.insertString(offset, text, attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (fb.getDocument().getLength() == 0) {
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            text = sb.toString();
        }
        fb.replace(offset, length, text, attrs);
    }
}
