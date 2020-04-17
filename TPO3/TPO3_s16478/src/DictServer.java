import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DictServer {

    private ServerSocket serverSocket;
    private int dictServerPort;
    private Socket socket;

    private BufferedReader input;
    private PrintWriter output;


    DictServer(int port){
        dictServerPort = port;
    }

    void listen(){   // metoda listen w ogole nie startuje
        try {
            serverSocket = new ServerSocket(dictServerPort);
            System.out.println("Server is running \nSerwer and listening...");
            while (true) {
                new DictServerService(serverSocket.accept()).start();
                System.out.println("Conection established");
                connect("localhost", 3354);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        exchangeData();
    }

    void exchangeData () throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    void closeDataExchange() throws IOException {
        input.close();
        output.close();
    }

    private void disconnect() throws IOException {
        socket.close();
    }

    private void introduce(String host, int port) throws IOException {
        socket = new Socket(host, port);
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private String send(String message)throws IOException  {
        output.println(message);
        return input.readLine();
    }

    private String receiveForwardedInfoFromMainServer () throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = input.readLine()) != null)
            //check
            System.out.println("Receiver form MainServer: " + line);
        return line;
    }

    public static void main(String[] args){
        System.out.println(args[0]);
        System.out.println(args[1]);
        try {
            DictServer server = new DictServer(Integer.parseInt(args[1]));
            server.introduce("localhost", MainServer.MAINSERVER_PORT);
            server.send("IntroduceYourself,"+ args[1] +","+args[0]);
            server.listen();  // do tej metody w ogole nie dochodzi
            System.out.println("tu powinien zaczac nasluchiwac");
            System.out.println("----------");
            server.receiveForwardedInfoFromMainServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}