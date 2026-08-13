package commands;

import viewer.Viewer;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.Color;


public class FindNextCommand implements Command {
    private Viewer viewer;

    public FindNextCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        findNext();
    }

    public void findNext() {
        JTextComponent textPane = viewer.getTextPane();
        String lastSearchText = viewer.getLastSearchText();
        int lastSearchPos = viewer.getLastSearchPos();

        if (lastSearchText != null && !lastSearchText.isEmpty()) {
            try {
                String text = textPane.getDocument().getText(0, textPane.getDocument().getLength());
                int searchPos = text.indexOf(lastSearchText, lastSearchPos + 1);
                if (searchPos == -1){
                     searchPos = text.indexOf(lastSearchText);
                }

                if (searchPos >= 0) {
                    Highlighter highlighter = textPane.getHighlighter();
                    highlighter.removeAllHighlights();
                    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(170, 195, 220));
                    highlighter.addHighlight(searchPos, searchPos + lastSearchText.length(), painter);
                    textPane.setCaretPosition(searchPos + lastSearchText.length());
                    viewer.setLastSearchPos(searchPos);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No search text specified. Use the Find button first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}