package server;

public class Main {
    public static void main(String... args) {
        BackendServerSokoban backendServerSokoban = new BackendServerSokoban(4445);
        backendServerSokoban.startServer();
    }
}
