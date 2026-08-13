import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class Service {

    private Socket socket;
    private String host;
    private int port;

    public Service() {
        loadConfig();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("../config.properties")) {
            props.load(input);
            this.host = props.getProperty("host");
            this.port = Integer.parseInt(props.getProperty("port"));
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    public String calculate(String temp) {
        loadConfig();

        String infix = "Bearer " + temp;
        try {
            socket = new Socket(host, port);
            sendMessage(infix);

            String postfixForm = readData();
            sendMessage("RPN " + postfixForm);

            String result = readData();

            char point = result.charAt(result.length() - 2);
            char zero = result.charAt(result.length() - 1);

            if((point == '.') && (zero == '0')) {
                result = result.substring(0, result.indexOf("."));
            }
            return result;
        } catch (UnknownHostException uhe) {
            System.out.println("Error " + uhe);
            return null;
        } catch (IOException ioe) {
            System.out.println("Error " + ioe);
            return null;
        }
    }

    private void sendMessage(String text) {
        byte[] array = text.getBytes();
        try {
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
