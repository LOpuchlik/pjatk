import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class DictServer {

    static String l;
    static int dictServerPort;
    private ServerSocket serverSocket;
    private Socket socket;

    private BufferedReader input;
    private PrintWriter output;


    DictServer(int port){
        dictServerPort = port;
    }

    void listen(){   // metoda listen w ogole nie startuje
        try {
            serverSocket = new ServerSocket(dictServerPort);
            System.out.println("Server is running and listening...");
            while (true) {
                new DictServerService(serverSocket.accept()).start();
                System.out.println("Conection established");
                System.out.println("Server port: "+serverSocket.getLocalPort());
                //l = getKey(MainServerService.dict, serverSocket.getLocalPort());
                System.out.println("l: " + l);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    private void introduce(String host, int port) throws IOException {
        socket = new Socket(host, port);
        exchangeData();
    }

    private void exchangeData () throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private String send(String message)throws IOException  {
        output.println(message);
        return input.readLine();
    }

    private void closeDataExchange() throws IOException {
        input.close();
        output.close();
    }

    private void disconnect() throws IOException {
        socket.close();
    }


    public static void setL(String l) {
        DictServer.l = l;
    }

    public static String getL() {
        return l;
    }

    public static void main(String[] args){
        System.out.println("Args[0]: " + args[0]);
        System.out.println("Args[1]: " + args[1]);
        try {
            DictServer server = new DictServer(Integer.parseInt(args[1]));

            new Thread(server::listen).start();

            DictServer.setL(args[0]);
            server.introduce("localhost", MainServer.MAINSERVER_PORT);
            server.send("IntroduceYourself,"+ args[1] +"," + args[0]);

            server.closeDataExchange();
            server.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}