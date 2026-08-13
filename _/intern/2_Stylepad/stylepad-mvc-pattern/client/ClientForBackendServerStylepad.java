package client;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

public class ClientForBackendServerStylepad {

    private Socket socket;
    private boolean stateSocket;

    public ClientForBackendServerStylepad(String host, int port) {
        try {
            socket = new Socket(host, port);
            stateSocket = true;
        } catch (UnknownHostException uhe) {
            System.out.println("Error: " + uhe);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    public boolean getStateSocket() {
        return stateSocket;
    }

    public boolean sendDocument(Document contentDocument) {
        return sendMessage(contentDocument);
    }

    public boolean sendMessage(Document content) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);
            objectOutputStream.flush();
        } catch (IOException ioe) {
            System.out.println(ioe);
            return false;
        }
        return true;
    }

    public Document readDocumentFromServer(){

        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            Document dataModelFromServer = (Document) objectInputStream.readObject();

            return dataModelFromServer;
        } catch (ClassNotFoundException cnfe){
            System.out.println("cnfe = " + cnfe);
        } catch (IOException ioe){
            System.out.println("ioe = " + ioe);
        } finally {
            try{
                if (objectInputStream != null){
                    objectInputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException ioe){
                System.out.println("ioe = " + ioe);
            }
        }
        return null;
    }

}