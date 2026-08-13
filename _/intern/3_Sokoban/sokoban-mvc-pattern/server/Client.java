package server;

import java.io.BufferedInputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client implements Runnable {

    private final Socket socket;
    private final Thread thread;

    public Client(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
    }

    public void run() {
        InputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            int level = objectInputStream.readInt();
            String fileName = "levels/level" + level + ".txt";

            InputStream in = Client.class.getResourceAsStream(fileName);
            StringBuilder map = new StringBuilder();
            if (in == null) {
                System.out.println("File not found: " + fileName);
                map.append("File Not Found");
            } else {
                bufferedInputStream = new BufferedInputStream(in);
                while (bufferedInputStream.available() > 0) {
                    map.append((char) bufferedInputStream.read());
                }
            }

            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeUTF(map.toString());
            objectOutputStream.flush();
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                socket.close();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }

    public void go() {
        thread.start();
    }
}
