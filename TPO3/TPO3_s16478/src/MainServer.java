import java.io.IOException;
import java.net.ServerSocket;

public class MainServer {

    static int MAINSERVER_PORT = 1005;
    ServerSocket serverSocket;


    private void initialize(){
        try {
            serverSocket = new ServerSocket(MAINSERVER_PORT);
            System.out.println("Server has been initialized!");
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