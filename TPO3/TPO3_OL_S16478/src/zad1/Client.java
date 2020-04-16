package zad1;// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client {
    // initialize socket and input output streams
    private Socket socketForCommunicationWithMainServer = null;
    private ServerSocket serverForConnectingWithDictServer = null;
    private Socket socketForCommunicationWithDictServer = null; // tu nie bedzie null tylko bedzie numer portu, który podaje user w Gui (givenPort)
    private String passedInfo = null;

    // constructor to put ip address and port 
    public Client(String address, int port) {



// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------
        // establish a connection
        try {
            socketForCommunicationWithMainServer = new Socket(address, port);
            System.out.println("Client and MainServer connected");
        } catch(IOException u) {
            System.out.println(u);
        }

        // these three variables are packed into one - passedInfo
        // depending on the value of languageCode variable, this information will be further passed to respective DictServer

        String word = "kot";
        String languageCode = "DE";
        int givenPort = 1234;

        // this String object is passed to the MainServer --> it has to be unpacked to variables in THE MAINSERVER
        passedInfo = word+","+languageCode+","+givenPort;
        System.out.println("Infor passed from Client to MainServer: " + passedInfo);

        try {
            sendMessage(passedInfo);
        } catch (IOException e) {
            System.err.println(e);
        }
        // close the connection
        try {
            socketForCommunicationWithMainServer.close();
            System.out.println("Connection between Client and MainServer closed");
        }
        catch(IOException e) {
            System.err.println(e);
        }

// -------------------------------- END OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ------------------------------------



// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND DICTSERVER ----------------------------------
        // this Client has to act like a server and has to listen for an incoming communication on a given port
        // the port is given by the user in Gui and stored in an int givenPort variable

        try {
            serverForConnectingWithDictServer = new ServerSocket(givenPort);
            System.out.println("Client(server side) has started and is waiting for a DictServer");

            socketForCommunicationWithDictServer = serverForConnectingWithDictServer.accept();
            System.out.println("DictServer accepted communication with Client!");
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
        System.out.println("Translation received: " + word + " : " + messageReceivedFromDictServer);

        // close the connection
        try {
            socketForCommunicationWithDictServer.close();
            System.out.println("Connection between Client and DictServer closed");
        }
        catch(IOException e) {
            System.err.println(e);
        }


    }

    public void sendMessage(String message) throws IOException {
        ObjectOutputStream infoPassToMainServer = new ObjectOutputStream(socketForCommunicationWithMainServer.getOutputStream());
        infoPassToMainServer.writeObject(message);
        infoPassToMainServer.flush();
    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream messageFromDictServer = new ObjectInputStream(socketForCommunicationWithDictServer.getInputStream());
        return (String) messageFromDictServer.readObject();
    }


    public static void main(String [] args) {
        new Thread((Runnable) new Client("localhost", 1111)).start();
    }
} 