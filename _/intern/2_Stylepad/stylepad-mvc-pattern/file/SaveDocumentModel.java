package file;

import javax.swing.text.DefaultStyledDocument;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

public class SaveDocumentModel {

    public boolean  saveToFile(File file, DefaultStyledDocument document) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(document);
            objectOutputStream.flush();
            return true;
        } catch (NotSerializableException nse) {
            System.out.println("NotSerializableException: " + nse);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe);
            }
        }
        return false;
    }
}
