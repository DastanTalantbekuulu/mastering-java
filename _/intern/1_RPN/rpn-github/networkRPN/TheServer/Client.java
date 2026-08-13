import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
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

        String text = readData();
        System.out.println(text);
        text = text.substring(7, text.length());
        String postfixForm = Parser.convertToPostfix(text);
        sendMessage(postfixForm);

        String postfixFormString = readData();
        System.out.println(postfixFormString);
        postfixFormString = postfixFormString.substring(4, postfixFormString.length());
        String result = Solution.evaluatePostfix(postfixFormString);
        sendMessage(result);
        System.out.println("[" + result + "]");

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

    private String readData() {
        try {
            String text = "";
            InputStream input = socket.getInputStream();
            while(true) {
                int unicode = input.read();
                char symbol = (char)unicode;
                text = text + symbol;
                if(input.available() == 0) {
                    break;
                }
            }
            return text;
        } catch (IOException ioe) {
            System.out.println("Client error " + ioe);
            return null;
        }
    }
}
