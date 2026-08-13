package commands;

import viewer.Viewer;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.text.JTextComponent;

public class ClearHighlightCommand implements Command {
    private Viewer viewer;

    public ClearHighlightCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        clearHighlights(viewer.getTextPane());
        hideButtons();
    }

    private void clearHighlights(JTextComponent textComp) {
        textComp.getHighlighter().removeAllHighlights();
    }

    private void hideButtons() {
        JToolBar toolBar = viewer.getToolBar();
        viewer.setLastSearchPos(-1);
        viewer.setLastSearchText(null);
        for (int i = 0; i < toolBar.getComponentCount(); i++) {
            if (toolBar.getComponent(i) instanceof JButton) {
                JButton button = (JButton) toolBar.getComponent(i);
                String s = "";
                if (button.getIcon() != null) {
                    s = button.getIcon().toString();
                }
                if (s.equals("images/toolbar/findExit.png") || s.equals("images/toolbar/findNext.png") || s.equals("images/toolbar/findPrev.png")) {
                    button.setVisible(false);
                }
            }
        }
        toolBar.revalidate();
        toolBar.repaint();
    }
}