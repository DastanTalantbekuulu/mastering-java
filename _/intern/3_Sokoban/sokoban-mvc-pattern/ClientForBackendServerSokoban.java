import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientForBackendServerSokoban {

    private Socket socket;
    private boolean stateSocket;

    public ClientForBackendServerSokoban(String host, int port) {
        try {
            socket = new Socket(host, port);
            stateSocket = true;
        } catch (UnknownHostException uhe) {
            System.out.println(uhe);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public String readLevelFromServer(int level) {

        OutputStream outputStream = null;
        InputStream inputStream = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        String response = null;

        try {
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeInt(level);
            objectOutputStream.flush();

            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            response = objectInputStream.readUTF();
            if (response.equals("File Not Found")) {
                return null;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                socket.close();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }

        return response;
    }

    public boolean isConnect() {
        return stateSocket;
    }
}
