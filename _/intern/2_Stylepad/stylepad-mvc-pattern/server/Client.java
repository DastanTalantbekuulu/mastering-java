package server;

import javax.swing.text.Document;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.NotSerializableException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client implements Runnable {

    private final Socket socket;
    private Thread thread;
    private FileManager fileManager;


    public Client(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
        fileManager = new FileManager("server/resources/file.properties");
    }

    public void run() {
        System.out.println("-------------------------------------------------");
        try {
            Document document = readData();

            if (document != null) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatForFileName = DateTimeFormatter.ofPattern("yyyyddMM HHmmss");
                String formattedDate = "SPMVCP_" + formatForFileName.format(now) + ".spd";

                fileManager.updateLastFilePath(formattedDate);

                FileOutputStream fileOutputStream = new FileOutputStream(formattedDate);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(document);
                objectOutputStream.flush();
            } else {
                sendData();
            }
            socket.close();
        } catch (NotSerializableException nse) {
            System.out.println("Not Serializable Error " + nse);
        } catch (IOException ioe) {
            System.out.println("Socket Error " + ioe);
        }
        System.out.println("--------------------------------------------------");
    }

    public void go() {
        thread.start();
    }

    private void sendData() {
        FileInputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            String lastFilePath = fileManager.getLastFilePath();
            File file = new File(lastFilePath);            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            Document dataModelFromServer = (Document) objectInputStream.readObject();

            OutputStream output = socket.getOutputStream();


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject(dataModelFromServer);
            objectOutputStream.flush();

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Client error " + cnfe);
        } catch (IOException ioe) {
            System.out.println("Client error " + ioe);
        }
    }
    private Document readData() {
        try {
            socket.setSoTimeout(5000);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Document dataModelFromServer = (Document) objectInputStream.readObject();
            return dataModelFromServer;

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Client error: " + cnfe);
            return null;
        } catch (IOException ioe) {
            if (ioe instanceof java.net.SocketTimeoutException) {
                System.out.println("Read timed out. No data received.");
            } else {
                System.out.println("Client error: " + ioe);
            }
            return null;
        }
    }
}
