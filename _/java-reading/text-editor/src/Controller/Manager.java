package Controller;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
/**
 * Klasa Manager definiuje akcje cofnij i przywróć. 
 * @author kamil
 */
public class Manager {

    private UndoManager uman;
    private Action undo, redo;
    private JEditorPane panel;
        
    public Manager(JEditorPane panel){
        this.panel=panel;
        uman = new UndoManager();
        createUndoRedo();
    }
    
    private void createUndoRedo() {
        undo = new AbstractAction("Undo") {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    try {
                      uman.undo();
                    } catch (CannotUndoException ex) { }
                    setEnabled(uman.canUndo());
                    redo.setEnabled(uman.canRedo());
                  }
        };

        redo = new AbstractAction("Redo") {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    try {
                      uman.redo();
                    } catch (CannotUndoException ex) { }
                    undo.setEnabled(uman.canUndo());
                    setEnabled(uman.canRedo());
                  }
        };

        undo.setEnabled(false);
        redo.setEnabled(false);

        panel.getDocument().addUndoableEditListener( new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
               uman.addEdit(e.getEdit());
               undo.setEnabled(uman.canUndo());
               redo.setEnabled(uman.canRedo());
            }
          });
        }
    
    public Action getUndo() {
        return undo;
    }

    public Action getRedo() {
        return redo;
    }
}
