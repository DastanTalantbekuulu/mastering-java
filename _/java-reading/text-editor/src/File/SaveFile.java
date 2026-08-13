package File;

import java.io.File;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 * Zapisuje plik o nazwie podanej w konstruktorze i zawartości na lokalnym dysku.
 * @author kamil
 */
public class SaveFile{
    String name,text;
    public SaveFile(String name, String text){
        JFileChooser fc=new JFileChooser();
        fc.setSelectedFile(new File(name));
        if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            File file=fc.getSelectedFile();
            try{
		PrintWriter pw=	new PrintWriter(file);
                pw.println(text);
                pw.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.toString(), "Plik nie zostal utworzony", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.name=name;
        this.text=text;
    }
}