package File;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 * Klasa określa wybrany przez użytkownika plik z dysku (nazwa pliku i ścieżka).
 * @author kamil
 */
public class OpenFile{
    private String text;
    private String name;
    private JFileChooser fc;
    private File file;
    
    public OpenFile(){
        fc=new JFileChooser();
        if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            file=fc.getSelectedFile();
            try(Scanner sc=new Scanner(file)){
                while(sc.hasNext())
                    text=text+sc.nextLine()+"\n";
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.toString(), "Plik nie zostal utworzony", JOptionPane.ERROR_MESSAGE);
            }
            name=file.getName();
        }    
    }
    
    public String getName(){
            return name;
    }
    public String getText(){
            return text;
    }   

}