import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client implements Runnable {
    private final Socket socket;
    private Thread thread;

    public Client(Socket socket) {
        this.socket = socket;
        this.thread = new Thread(this);
    }

    public void run() {
        System.out.println("-------------------------------------------------");
        System.out.println("[ " + socket + " ]");
        String text = readData();
        System.out.println(text);

        text = text.substring(7);
        String postfix = Parser.convertToPostfix(text).toString();
        sendMessage("" + postfix);

        String postfixFromString = readData();
        postfixFromString = postfixFromString.substring(4);
        System.out.println(postfixFromString);

        Double result = Solution.evaluatePostfix(postfixFromString);

        sendMessage("" + result);

        try {
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Socket error " + ioe);
        }
        System.out.println("-------------------------------------------------");

    }

    public void go() {
        thread.start();
    }

    private void sendMessage(String text) {
        byte[] array = text.getBytes();
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(array);
            outputStream.flush();
            System.out.println("Data is sent");
        } catch (IOException ioe) {
            System.out.println("Client error " + ioe);
        }
    }

    private String readData() {
        try {
            String text = "";
            InputStream input = socket.getInputStream();
            while (true) {
                int unicode = input.read();
                char symbol = (char) unicode;
                text = text + symbol;
                if (input.available() == 0) {
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
