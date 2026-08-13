package file;

import javax.swing.text.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SynchronizeDocumentModel {
    private ObjectInputStream objectInputStream;
    private FileInputStream inputStream;
    private File file;

    public SynchronizeDocumentModel() {
        objectInputStream = null;
        inputStream = null;
    }

    public Document read() {
        try {
            return (Document) objectInputStream.readObject();
        } catch (IOException ioe) {
            System.out.println("Exception " + ioe);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error: " + cnfe);
        }
        return null;
    }

    public boolean openFile(File file) {
        try {
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            return true;
        } catch (IOException ioe) {
            System.out.println(ioe);
            return false;
        }
    }

    public void closeFile() {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ioe) {
            System.out.println("Exception: " + ioe);
        }
    }
}
