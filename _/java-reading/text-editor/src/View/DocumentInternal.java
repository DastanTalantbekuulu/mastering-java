package View;


import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
/**
 * Klasa reprezentująca okienka wewnetrzne (dokumenty tekstowe). 
 * @author kamil
 */
public class DocumentInternal extends JInternalFrame{
    private JEditorPane editorPanel;
    private JScrollPane innerPanel;
    private MenuBarInternal menu;
    private String name;
          
    private void create(){
        editorPanel=new JEditorPane();
        innerPanel= new JScrollPane();
        menu=new MenuBarInternal(editorPanel);
        name="";
    }
    
    public DocumentInternal(ArrayList <DocumentInternal> documents){
        create();
        name = (String) JOptionPane.showInputDialog(null, "Wpisz nazwe pliku:", "Nazwa pliku",JOptionPane.WARNING_MESSAGE);
        check(documents);
        add();
        setting();
    }
    public DocumentInternal(String name, ArrayList <DocumentInternal> documents){
        this.name=name;
        check(documents);
        add();
        setting();
    }    
    /**
     * Sprawdza czy nazwa pliku jest poprawna
     * @param names 
     */
    private void check(ArrayList <DocumentInternal> names){
        if (name==null || name.equals(""))  
             throw new IllegalArgumentException("Brak nazwy pliku"); 
        for(int i=0;i< names.size();i++){
            if(name.equals(names.get(i).getName()))
                throw new IllegalArgumentException("Nazwa pliku już istnieje");
        }
        
    }
    
    private void setting(){
        editorPanel.setDragEnabled(true);
        setSize(200,200);
        setVisible(true);
        setResizable(true);
        setMaximizable(true);
        setTitle(name);
        setIconifiable(true);
        setName(name);
        editorPanel.setName(name);
    }
    
    private void add(){
        add(innerPanel);
        setJMenuBar(menu);
        innerPanel.getViewport().setView(editorPanel);
    }
    
    @Override
    public String toString(){
        return name;
    }

    public String getText() {
        return editorPanel.getText();
    }
    
    public void setText(String text){
        editorPanel.setText(text);
    }
 
    public JEditorPane getEditorPanel() {
        return editorPanel;
    }
}
