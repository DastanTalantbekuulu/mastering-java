package commands;

import viewer.Viewer;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;

public class UndoCommand implements Command {
    private Viewer viewer;

    public UndoCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        UndoManager undoManager = viewer.getUndoManager();

        try {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } catch (CannotUndoException e) {
            System.out.println("CannotUndoException: " + e);
        }
    }
}