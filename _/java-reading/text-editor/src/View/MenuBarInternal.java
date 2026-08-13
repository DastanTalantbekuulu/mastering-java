package View;

import Controller.Manager;
import javax.swing.*;
/**
 * Ustawia ikony oraz odpowiednie akcje do odpowiedniego przycisku z skrótem klawiszowym.
 * @author kamil
 */
public class MenuBarInternal extends JMenuBar{
    private JMenu menuEdit;
    private final JEditorPane panel;
    private final Manager manager;
    private JMenuItem mUndo, mRedo, mCopy, mCut, mPaste;
    private final ActionMap map;

    
    private void create(){          
        menuEdit = new JMenu("Edycja");
        mUndo = new JMenuItem("Cofnij");
        mRedo = new JMenuItem("Przywroc");
        mCut = new JMenuItem("Wytnij");
        mCopy= new JMenuItem("Kopiuj");
        mPaste= new JMenuItem("Wklej");
    }
    
    
    public MenuBarInternal(JEditorPane panel) {
        create();
        manager=new Manager(panel);
        map=panel.getActionMap();
        this.panel=panel;
        
        add();
        set();
        addListener();
    }

    private void add() {
        add(menuEdit);
        menuEdit.add(mUndo);
        menuEdit.add(mRedo);
        menuEdit.addSeparator();
        menuEdit.add(mCopy);
        menuEdit.add(mCut);
        menuEdit.add(mPaste);
    }
    
    
    private void addListener(){
        mPaste.addActionListener(map.get("paste-from-clipboard"));
        mCut.addActionListener(map.get("cut-to-clipboard"));
        mCopy.addActionListener(map.get("copy-to-clipboard"));
        mUndo.addActionListener(map.get("Undo"));
        mRedo.addActionListener(map.get("Redo"));
    }
    
    private void set(){
        setKey(manager.getUndo(),"control Z");
        setKey(manager.getRedo(),"control Y");
    }
    
    /**
     * Powiązuje akcje z skrótem klawiszowym.
     * @param action
     * @param stringKey 
     */
    private void setKey(Action action, String stringKey) {
      String actionName;
      actionName = (String) action.getValue(Action.NAME);
      map.put(actionName, (Action) action);
      panel.getInputMap().put(KeyStroke.getKeyStroke(stringKey), actionName);
    }
}