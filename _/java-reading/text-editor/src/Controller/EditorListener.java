package Controller;


import Model.EditorModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JEditorPane;
/**
 * Słuchacz obsługujący ToolBar (dodawanie, usuwanie, zapisywanie itd. plików)
 * @author kamil
 */
public class EditorListener extends FocusAdapter implements ActionListener {
    
    private final EditorModel model;
    
    public EditorListener(EditorModel model){
        this.model=model;
    }
        
    @Override
    public void actionPerformed(ActionEvent event) {
        String source=event.getActionCommand();
        switch(source){
            case "NowyPlik":
                model.newFile();
                break;
            case "Zamknij":
                model.closeFile();
                break;
            case "Drukuj":
                 model.print();
                 break;
            case "Wybierz":
                model.update();
                break;
            case "ZapiszPlik":
                model.saveFile();
                break;
            case "Otworz":
                model.openFile();
                break;
            default:
                break;
        }
    }
    /**
     * Sprawdza aktywne okienko i aktualizuje ComboBox z nazwą danego pliku.
     * @param e 
     */
    @Override
    public void focusGained(FocusEvent e) {
            if (e.getSource() instanceof JEditorPane){
                JEditorPane a=(JEditorPane) e.getSource();
                model.setSelected(a);
            }
    }

}
