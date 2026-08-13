package View;

import Controller.MenuBarListener;
import javax.swing.*;
/**
 * 
 * @author kamil
 */
public class MenuBar extends JMenuBar{
    private JMenu menuFile,menuSettings, menuHelp;
    private JMenuItem mEnd, mDefault, mNimbus, mIApk, mIAuthor,mMotif;
    
    
    private void create(){
        menuFile = new JMenu("Program");
        mEnd = new JMenuItem("Koniec", 'K');

        menuSettings = new JMenu("Styl");
        mDefault = new JMenuItem("Systemowy(Domyślny)");
        mNimbus= new JMenuItem("Nimbus");
        mMotif= new JMenuItem("Motif");

        menuHelp = new JMenu("Pomoc");
        mIApk = new JMenuItem("O Aplikacji");
        mIAuthor = new JMenuItem("O Autorze");
    }
    
    
    public MenuBar(Window window) {
        create();
        add();
        setCommand();
        addListener(window);
    }

    private void add() {

        add(menuFile);
        menuFile.add(mEnd);

        add(menuSettings);
        menuSettings.add(mDefault);
        menuSettings.add(mNimbus);
        menuSettings.add(mMotif);
        
        add(Box.createGlue());

        add(menuHelp);
        menuHelp.add(mIApk);
        menuHelp.add(mIAuthor);

    }
    
    private void setCommand(){
        mEnd.setActionCommand("Koniec");
        mIAuthor.setActionCommand("OAut");
        mIApk.setActionCommand("OApk");
        mDefault.setActionCommand("Domyslny");
        mNimbus.setActionCommand("Nimbus");
        mMotif.setActionCommand("Motif");
    }
    
    private void addListener(Window window){
        mEnd.addActionListener(new MenuBarListener(window));
        mIAuthor.addActionListener(new MenuBarListener(window));
        mIApk.addActionListener(new MenuBarListener(window));
        mDefault.addActionListener(new MenuBarListener(window));
        mNimbus.addActionListener(new MenuBarListener(window));
        mMotif.addActionListener(new MenuBarListener(window));
    }  
}