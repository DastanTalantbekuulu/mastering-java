package commands;
import listener.CommandActionListener;
import viewer.Viewer;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

public class FindCommand implements Command {
    private Viewer viewer;
    private JButton clearButton;
    private JButton nextButton;
    private JButton prevButton;

    public FindCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        String searchText = JOptionPane.showInputDialog(viewer.getTextPane(), "Enter text to find:");
        if (searchText != null && !searchText.isEmpty()) {
            viewer.setLastSearchText(searchText);
            highlightFirstOccurrence(viewer.getTextPane(), searchText);
            addToolBar();
        } else {
            JOptionPane.showMessageDialog(null, "Search text cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void highlightFirstOccurrence(JTextComponent textComp, String pattern) {
        Highlighter highlighter = textComp.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(170, 195, 220));

        highlighter.removeAllHighlights();

        try {
            String text = textComp.getDocument().getText(0, textComp.getDocument().getLength());
            int pos = text.indexOf(pattern);

            if (pos >= 0) {
                highlighter.addHighlight(pos, pos + pattern.length(), painter);
                textComp.setCaretPosition(pos + pattern.length());
                viewer.setLastSearchPos(pos);
            } else {
                JOptionPane.showMessageDialog(null, "Cannot find \"" + pattern + "\".", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void addToolBar() {
        JToolBar toolBar = viewer.getToolBar();
        Dimension buttonSize = new Dimension(20, 20);

        if (clearButton == null) {
            clearButton = new JButton(new ImageIcon("images/toolbar/findExit.png"));
            clearButton.addActionListener(new CommandActionListener(new ClearHighlightCommand(viewer)));
            clearButton.setSize(20, 20);
            clearButton.setPreferredSize(buttonSize);
            toolBar.add(clearButton);
        }

        if (prevButton == null) {
            prevButton = new JButton(new ImageIcon("images/toolbar/findPrev.png"));
            prevButton.addActionListener(new CommandActionListener(new FindPreviousCommand(viewer)));
            toolBar.add(prevButton);
        }

        if (nextButton == null) {
            nextButton = new JButton(new ImageIcon("images/toolbar/findNext.png"));
            nextButton.addActionListener(new CommandActionListener(new FindNextCommand(viewer)));
            toolBar.add(nextButton);
        }

        clearButton.setVisible(true);
        prevButton.setVisible(true);
        nextButton.setVisible(true);

        toolBar.setVisible(true);
        toolBar.revalidate();
        toolBar.repaint();
    }
}