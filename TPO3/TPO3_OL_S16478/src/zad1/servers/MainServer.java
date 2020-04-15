
package zad1.servers;

// A Java program for a Server

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
    // Tu będzie lista z paramenteami
    List<Object> params = new ArrayList<>();
    // do tych zmiennych przypisać to co zostało pobrane
    private String word; // <-- to podać dalej jako slowko do tlumaczenia
    private String ip; // <-- to podać dalej do serwera slownikowego
    private int portToPass; // <-- to podać dalej do serwera slownikowego


    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port
    public MainServer(int port) {
// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // receiving of the message with variables sent by a client and ascribing it to messageFromCLient String variable
            String messageFromClient = receiveMessage();

            // unpacking of messageFromClient String into separate variables
            String word = messageFromClient.split(",")[0];
            String languageCode = messageFromClient.split(",")[1];
            String portClient = messageFromClient.split(",")[2]; // on the DictServer side, this will have to be casted (parsed) into an int
            // check
            System.out.println("Language code: " + languageCode);
            System.out.println("Word: " + word);
            System.out.println("Port: " + portClient);
            //System.out.println(messageFromClient);


            // pobieranie i zapisywanie numeru IP klienta <---zmienna clientIP
            String IPOfClient = socket.getRemoteSocketAddress().toString();
            ip = IPOfClient.split(":")[0].replace("/","");
            System.out.println("IP clienta: " + ip);

            System.out.println("Closing connection");
            socket.close();

        }
        catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // -------------------------------- END OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------------

        // -------------------------------- START OF COMMUNICATION BETWEEN MAINSERVER AND DICTSERVER ----------------------------------








    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        //socket is a variable get from Player class socket = new Socket("severHost", PORT);
        ObjectInputStream messageFromClient = new ObjectInputStream(socket.getInputStream());
        return (String) messageFromClient.readObject();
    }

    public static void main(String [] args) {
        MainServer ms = new MainServer(1111);
    }

}
