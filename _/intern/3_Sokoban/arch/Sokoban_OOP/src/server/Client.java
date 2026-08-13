package server;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

public class Client implements Runnable {

    private final Socket socket;
    private Thread thread;

    public Client(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
    }

    public void run() {

        System.out.println("-------------------------------------------------");

        System.out.println("[ " + socket + " ];");

        sendMessage("levels");
        
        try {
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Socket Error " + ioe);
        }
        System.out.println("--------------------------------------------------");
    }

    public void go() {
        thread.start();
    }

    private void sendMessage(String text){
        byte[] array = text.getBytes();
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(array);
            outputStream.flush();
        } catch (IOException ioe) {
            System.out.println("Client Error " + ioe);
        }
    }

}
