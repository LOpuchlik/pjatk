package zad1.clients;// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private ServerSocket server = null;
    private Socket socket1 = null; // tu nie bedzie null tylko bedzie numer portu, który podaje user w Gui (givenPort)
    private String passedInfo = null;

    // constructor to put ip address and port 
    public Client(String address, int port)
    {

// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------
        // establish a connection 
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
        } catch(IOException u) {
            System.out.println(u);
        }

        // these three variableS are packed into one - passedInfo
        // depending on the value of languageCode variable, this information will be further passed to respective DictServer

        //String word;
        //String languageCode;
        //int givenPort;

        // this String object is passed to the MainServer --> it has to be unpacked to variables in THE MAINSERVER
        passedInfo = "word,languageCode,givenPort";

        try {
            sendMessage(socket, passedInfo);
        } catch (IOException e) {
            System.err.println(e);
        }
        // close the connection 
        try {
            socket.close();
        }
        catch(IOException e) {
            System.err.println(e);
        }

// -------------------------------- END OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ------------------------------------

// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND DICTSERVER ----------------------------------
        // this Client has to act like a server and has to listen for an incoming communication on a given port
        // the port is given by the user in Gui and stored in an int givenPort variable

        try {
            server = new ServerSocket(port);
            System.out.println("Client(server side) has started");
            System.out.println("Waiting for a DictServer ...");

            socket1 = server.accept();
            System.out.println("DictServer accepted");
        }
        catch(IOException e) {
            System.err.println();
        }

        // teraz tu trzeba zrobić część do odbierania informacji od DictServera
        String messageReceivedFromDictServer = null;
        try {
            messageReceivedFromDictServer= receiveMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(messageReceivedFromDictServer);

    }

    public void sendMessage(Socket socket, String message) throws IOException {
        ObjectOutputStream infoPassToMainServer = new ObjectOutputStream(socket.getOutputStream());
        infoPassToMainServer.writeObject(message);
        infoPassToMainServer.flush();
    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream messageFromDictServer = new ObjectInputStream(socket1.getInputStream());
        return (String) messageFromDictServer.readObject();
    }


    public static void main(String [] args) {
        Client client = new Client("localhost", 1111);
    }
} 