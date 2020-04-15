package zad1.servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

public class DictServer {

    private ServerSocket serverForMainServer = null;
    private Socket socketForMainServer = null;
    private Socket socketForClient = null;

    // konstruktor
    public DictServer(String address, int port) {
         try {
             serverForMainServer = new ServerSocket(serverForMainServer.getLocalPort()); // jaki bedzie ten port?
            System.out.println("Server started on port " + serverForMainServer.getLocalPort());
            System.out.println("Waiting for a MainServer ...");
             socketForMainServer = serverForMainServer.accept();
            System.out.println("MainServer accepted");
            }
            catch(IOException e){
                System.err.println(e);
            }

        // tutaj ma sie odbywac komunikacja pomiedzy main serverem a dict serverem
        // main server przesyla info do dict servera, a dict server tylko odbiera metodą receiveMessage
            String receivedFromMainServer = null;
            try {
                receivedFromMainServer = receiveMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            // check
            System.out.println(receivedFromMainServer);


        // Zamykanie połaczenia MainServer - DictServer
            System.out.println("Closing connection");
            try {
                assert socketForMainServer != null;
                socketForMainServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


// -------------------------------- START OF COMMUNICATION BETWEEN DICTSERVER AND CLIENT ----------------------------------
        try {
            socketForClient = new Socket("localhost", port); // rozpoczecie komunikacji pomiedzy DictServer i Client //port ten, który podal MainServer w swoim komunilacie --> trzeba to jeszcze zmienić
            System.out.println("Connected");
        } catch(IOException e) {
            System.err.println(e);
        }


    }



    public void sendMessage(Socket socket, String message) throws IOException {
        ObjectOutputStream replyPassedToClient = new ObjectOutputStream(socketForClient.getOutputStream());
        replyPassedToClient.writeObject(message);
        replyPassedToClient.flush();
    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream messageFromMainServer = new ObjectInputStream(socketForMainServer.getInputStream());
        return (String) messageFromMainServer.readObject();
    }


    public static void main(String[] args) {
        Map<String, String> ENdict = new Hashtable<>();
        ENdict.put("choinka", "christmas tree");
        ENdict.put("słońce", "sun");
        ENdict.put("księżyc", "moon");
        ENdict.put("wiatr", "wind");
        ENdict.put("jajko", "egg");
        ENdict.put("szynka", "ham");
        ENdict.put("czekolada", "chocolate");
        ENdict.put("pies", "dog");
        ENdict.put("kot", "cat");
        ENdict.put("ryba", "fish");

        Map<String, String> ESdict = new Hashtable<>();
        ESdict.put("choinka", "árbol de Navidad");
        ESdict.put("słońce", "sol");
        ESdict.put("księżyc", "luna");
        ESdict.put("wiatr", "viento");
        ESdict.put("jajko", "huevo");
        ESdict.put("szynka", "jamon");
        ESdict.put("czekolada", "chocolate");
        ESdict.put("pies", "perro");
        ESdict.put("kot", "gato");
        ESdict.put("ryba", "pescado");

        Map<String, String> DEdict = new Hashtable<>();
        DEdict.put("choinka", "Tannenbaum");
        DEdict.put("słońce", "Sonne");
        DEdict.put("księżyc", "Mond");
        DEdict.put("wiatr", "Wino");
        DEdict.put("jajko", "Ei");
        DEdict.put("szynka", "Schinken");
        DEdict.put("czekolada", "Schocolade");
        DEdict.put("pies", "Hund");
        DEdict.put("kot", "Katze");
        DEdict.put("ryba", "Fisch");


    }
}
