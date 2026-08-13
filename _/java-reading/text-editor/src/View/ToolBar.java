package View;


import Model.EditorModel;
import Controller.EditorListener;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
/**
 * 
 * @author kamil
 */
public class ToolBar extends JToolBar{
    private JButton btnExit, btnNewFile, btnPrint, btnSelect,btnOpen, btnSave;
    private final JComboBox cmbList;   
    private final EditorModel model;

    
    public ToolBar(Window window) {
        create();
        model=new EditorModel(window);
        cmbList=new JComboBox((ComboBoxModel) model);
        add();
        setCommand();
        addListener();
    }

    private void create(){
        btnExit=new JButton("Zamknij");
        btnNewFile=new JButton("Nowy Plik");
        btnPrint=new JButton("Drukuj");
        btnSelect=new JButton("Wybierz");
        btnOpen=new JButton("Otworz");
        btnSave=new JButton("Zapisz plik");        
    }
    private void add() {
        add(btnNewFile);
        add(btnExit);
        add(cmbList);
        add(btnSelect);
        add(Box.createGlue());
        add(btnPrint);
        add(btnOpen);
        add(btnSave);
    }
    
    private void setCommand(){
        btnExit.setActionCommand("Zamknij");
        btnNewFile.setActionCommand("NowyPlik");
        btnPrint.setActionCommand("Drukuj");
        cmbList.setActionCommand("Lista");
        btnSelect.setActionCommand("Wybierz");
        btnOpen.setActionCommand("Otworz");
        btnSave.setActionCommand("ZapiszPlik");
    }
    
    private void addListener(){
        btnExit.addActionListener(new EditorListener(model));
        btnPrint.addActionListener(new EditorListener(model));
        btnNewFile.addActionListener(new EditorListener(model));
        btnSelect.addActionListener(new EditorListener(model));
        btnSave.addActionListener(new EditorListener(model));
        btnOpen.addActionListener(new EditorListener(model));
    }  

}
