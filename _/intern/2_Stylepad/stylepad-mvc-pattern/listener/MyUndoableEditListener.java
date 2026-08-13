package listener;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class MyUndoableEditListener implements UndoableEditListener {
    private final UndoManager undoManager;

    public MyUndoableEditListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }


    public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());
    }
}