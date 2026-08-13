package commands;

import viewer.Viewer;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

public class GoToCommand implements Command {
    private Viewer viewer;

    public GoToCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        String lineNumberStr = JOptionPane.showInputDialog(viewer.getTextPane(), "Enter line number:");
        if (lineNumberStr != null && !lineNumberStr.isEmpty()) {
            try {
                int lineNumber = Integer.parseInt(lineNumberStr);
                goToLine(viewer.getTextPane(), lineNumber);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid line number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (BadLocationException e) {
                JOptionPane.showMessageDialog(null, "Line number out of range.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void goToLine(JTextComponent textComp, int lineNumber) throws BadLocationException {
        Element root = textComp.getDocument().getDefaultRootElement();
        if (lineNumber > 0 && lineNumber <= root.getElementCount()) {
            int startOffset = root.getElement(lineNumber - 1).getStartOffset();
            textComp.setCaretPosition(startOffset);
            textComp.requestFocusInWindow();
        } else {
            throw new BadLocationException("Line number out of range", 0);
        }
    }
}