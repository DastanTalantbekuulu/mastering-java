import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BackendServerRPN {
    private int serverPort;
    private final Object lock;
    private static int count;
    private ServerSocket serverSocket;

    public BackendServerRPN(int serverPort){
        this.serverPort = serverPort;
        lock = new Object();
        count = 1;
        try{
            serverSocket = new ServerSocket(serverPort);
        }catch(IOException ioe){
            System.out.println("Error: " + ioe);
        }
    }

    public void startServer(){
        while (true) {
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println(count + " : [ " + clientSocket + " ];");
                synchronized(lock){
                    Client client = new Client(clientSocket);
                    client.go();
                    count = count + 1;
                }
            } catch(IOException ioe){
                System.out.println("Error " + ioe);
            }
        }
    }
}