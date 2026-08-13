package file;

import javax.swing.text.Document;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class OpenDocumentModel {

    public Document openFile(File file) {
        ObjectInputStream objectInputStream = null;
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);

            Document dataModelFromFile = (Document) objectInputStream.readObject();

            return dataModelFromFile;
        } catch(IOException ioe) {
            System.out.println("Exception " + ioe);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error: " + cnfe);
        } finally {
            try {
                if(objectInputStream != null) {
                    objectInputStream.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                System.out.println("Exception: " + ioe);
            }
        }
        return null;
    }
}
