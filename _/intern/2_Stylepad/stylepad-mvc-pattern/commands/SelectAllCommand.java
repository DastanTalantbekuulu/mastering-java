package commands;

import viewer.Viewer;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.Color;

public class SelectAllCommand implements Command {
    private Viewer viewer;

    public SelectAllCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        JTextPane textPane = viewer.getTextPane();
        textPane.selectAll();

        textPane.setSelectionColor(new Color(170, 195, 220));

        class CustomCaretListener implements CaretListener {
            private JTextPane textPane;

            public CustomCaretListener(JTextPane textPane) {
                this.textPane = textPane;
            }

            public void caretUpdate(CaretEvent e) {
                textPane.setSelectionColor(new Color(170, 195, 220));
            }
        }
    }
}
