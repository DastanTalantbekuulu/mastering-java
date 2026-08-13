package commands;

import viewer.Viewer;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

public class RedoCommand implements Command {
    private Viewer viewer;

    public RedoCommand(Viewer viewer) {
        this.viewer = viewer;
    }

    public void execute() {
        UndoManager undoManager = viewer.getUndoManager();

        try {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } catch (CannotRedoException e) {
            System.out.println("CannotRedoException: " + e);
        }
    }
}