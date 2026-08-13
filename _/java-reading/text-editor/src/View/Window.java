package View;

import java.awt.*;
import javax.swing.*;
/**
 * 
 * @author kamil
 */
public class Window extends JFrame {
    private MenuBar menu;
    private ToolBar tool;
    public JDesktopPane desktop;
    
    public Window() {
        super("Edytor Tekstowy");
        desktop=new JDesktopPane();
        menu=new MenuBar(this);
        tool=new ToolBar(this); 
        add();
        settings();  
        
    }

    private void add() {
        setLayout(new BorderLayout());
        setJMenuBar(menu);
        add(tool,BorderLayout.NORTH);  
        add(desktop);
    }
    
    private  void settings(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(80, 80);
        setSize(800, 500);
        setResizable(true);
        setVisible(true);
    }

    public void close(){
        this.dispose();
    }
    
    public JDesktopPane getDesktop() {
        return desktop;
    }

     

}
            

