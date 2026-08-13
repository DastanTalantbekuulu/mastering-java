package Model;
import File.SaveFile;
import File.OpenFile;
import Controller.EditorListener;
import View.DocumentInternal;
import View.Window;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
/**
 * Model do ComboBox reprezentujący nazwy utworzynych plików. Metody są powiązane z tworzeniem, usuwaniem (...) okien wewnętrzynych.
 * @author kamil
 */
public class EditorModel  extends AbstractListModel implements ComboBoxModel{
    
    private DocumentInternal selectedItem;
    private ArrayList<DocumentInternal> documents;
    private Window window;

    public EditorModel(Window window){
        documents=new ArrayList<DocumentInternal>();
        this.window=window;
    }
    
    public void newFile(){
        try{
            DocumentInternal doc=new DocumentInternal(documents);
            doc.getEditorPanel().addFocusListener(new EditorListener(this));
            window.getDesktop().add(doc);
            documents.add(doc);
            selectedItem=doc;
            update();
        }catch(Exception e){
            JOptionPane.showMessageDialog(window,"Plik nie zostal utworzony: "+e.getMessage(),"Blad", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void closeFile(){
       try{ 
            selectedItem.dispose();
            documents.remove(selectedItem);
            selectedItem=null;
            fireContentsChanged(this,0,getSize()-1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(window, "Plik nie zostal utworzony: "+e.getMessage(),"Blad", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Wybrany plik tekstowy zostaje przeniesiowny w górny lewy róg i wyrzucony na wierzch.
     */
    public void update(){
        if(documents.size()!=0){          
            this.selectedItem.setLocation(0, 0);
            window.getDesktop().setComponentZOrder(this.selectedItem,0);
            fireContentsChanged(this,0,getSize()-1);
        }
    }
    
    public void print(){
        try{
            System.out.println(this.selectedItem.getEditorPanel().getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(window, "Plik nie zostal utworzony: "+e.getMessage(),"Blad", JOptionPane.ERROR_MESSAGE);
        }
    }    
  
    /**
     * Metoda ustawiająca zaznaczone okienko do ComboBox jako aktualne.
     * @param a 
     */
    public void setSelected(JEditorPane a){
        for(int i=0;i< documents.size();i++){
            if(a.getName().equals(documents.get(i).getName())){
                selectedItem=documents.get(i);
                fireContentsChanged(this,0,getSize()-1);
                return;
            }
        }
    }

    public void saveFile(){
        try{
            new SaveFile(selectedItem.getName(),selectedItem.getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(window,"Plik nie zostal utworzony: "+e.getMessage(),"Blad", JOptionPane.ERROR_MESSAGE);
        }  
    }
    public void openFile(){
        try{
            OpenFile file=new OpenFile();
            DocumentInternal doc=new DocumentInternal(file.getName(),documents);
            doc.getEditorPanel().addFocusListener(new EditorListener(this));
            doc.setText(file.getText());
            window.getDesktop().add(doc);
            documents.add(doc);
            selectedItem=doc;
            update();
        }catch(Exception e){
            JOptionPane.showMessageDialog(window,"Plik nie zostal utworzony: "+e.getMessage(),"Blad", JOptionPane.ERROR_MESSAGE);
        }      
    }
    
    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public void setSelectedItem(Object newValue) {
        selectedItem = (DocumentInternal) newValue;
    }

    @Override
    public int getSize() {
        return documents.size();
    }

    @Override
    public Object getElementAt(int i) {
        return documents.get(i);
    }
  
    
    public Window getWindow() {
        return window;
    }


}
