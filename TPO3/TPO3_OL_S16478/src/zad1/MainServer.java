package zad1;

import java.io.IOException;
import java.net.ServerSocket;

public class MainServer {

    static int MAINSERVER_PORT = 1027;
    ServerSocket serverSocket;


    private void initialize(){
        try {
            serverSocket = new ServerSocket(MAINSERVER_PORT);
            System.out.println("Server has been initialized on port " + MAINSERVER_PORT + "!");
            while (true) {
                new MainServerService(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        new MainServer().initialize();
    }
}
