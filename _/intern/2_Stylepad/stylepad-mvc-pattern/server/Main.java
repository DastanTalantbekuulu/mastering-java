package server;

public class Main {
    public static void main(String[] args) {
        BackendServerStylepad backend = new BackendServerStylepad(7654);
        backend.startServer();
    }
}
