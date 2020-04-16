
package zad1;

// A Java program for a Server

import java.net.*;
import java.io.*;


public class MainServer {
    // do tych zmiennych przypisać to co zostało pobrane
    String messageFromClient;
    static String languageCode;
    private String word; // <-- to podać dalej jako slowko do tlumaczenia
    private String ip; // <-- to podać dalej do serwera slownikowego
    private String clientPortForReply; // <-- to podać dalej do serwera slownikowego, może być na razie jako String - serwer słownikowy sobie zamieni na int


    //initialize socket and input stream
    private ServerSocket serverForConnectingWithClient = null;
    private Socket socketForCommunicatingWithClient = null;
    private Socket socketForCommunicatingWithDictServer = null;
    private DataInputStream in = null;

    // constructor with port
    public MainServer(int portClient){
// -------------------------------- START OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------
        // starts server and waits for a connection
        try {
            serverForConnectingWithClient = new ServerSocket(portClient);
            System.out.println("MainServer started and is waiting for a Client...");

            socketForCommunicatingWithClient = serverForConnectingWithClient.accept();
            System.out.println("Client accepted connection with MainServer");

            // receiving of the message with variables sent by a client and ascribing it to messageFromCLient String variable
            messageFromClient = receiveMessage();
            System.out.println("Information from Client: " + messageFromClient + " has been received!");
        }
        catch(IOException i) {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Closing connection between Client and MainServer");
        try {
            assert socketForCommunicatingWithClient != null;
            socketForCommunicatingWithClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// -------------------------------- END OF COMMUNICATION BETWEEN CLIENT AND MAINSERVER ----------------------------------------


// --------------------------------------- PROCESSING INFORMATION GOT FROM CLIENT ---------------------------------------------


        // unpacking of messageFromClient String into separate variables
            word = messageFromClient.split(",")[0];
            languageCode = messageFromClient.split(",")[1];
            clientPortForReply = messageFromClient.split(",")[2]; // on the DictServer side, this will have to be casted (parsed) into an int
            // check
            //System.out.println("Language code: " + languageCode);
            //System.out.println("Word: " + word);
            //System.out.println("Port: " + clientPortForReply);
            //System.out.println(messageFromClient);


            // pobieranie i zapisywanie numeru IP klienta <---zmienna clientIP
            String IPOfClient = socketForCommunicatingWithClient.getRemoteSocketAddress().toString();
            ip = IPOfClient.split(":")[0].replace("/","");
            //System.out.println("IP clienta: " + ip);

            String forwardMessage = word+","+ip+","+clientPortForReply;


// bede wysylac word, ip, clientPortForReply



// -------------------------------- START OF COMMUNICATION BETWEEN MAINSERVER AND DICTSERVER ----------------------------------
// trzeba zrobić warunek, że ma się komunikować tylko z DictServerem, który bierze Dict o określonym - skrócie językowym



        // tu trzeba skomunikować się z klientem, czyli teraz Dict Server będzie działał jak klient
        try {
            socketForCommunicatingWithDictServer = new Socket(InetAddress.getLocalHost(), DictServer.dictServerWaitingPort);   // ten port tutaj to musi być ten port, który otrzymał od MainServera, a address to localhost
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Main Server and DictServer are now connected");

        try {
            // sending a response to the Client
            sendMessage(forwardMessage);
            System.out.println("Forward with following info "+ forwardMessage + " sent to DictServer!");
        } catch (IOException e) {
            System.err.println(e);
        }
        // close the connection
        try {
            socketForCommunicatingWithDictServer.close();
        }
        catch(IOException e) {
            System.err.println(e);
        }


        // close the connection

        try {
            assert socketForCommunicatingWithDictServer != null;
            socketForCommunicatingWithDictServer.close();
            System.out.println("Connection between MainServer and DictServer");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        //socket is a variable get from Player class socket = new Socket("severHost", PORT);
        ObjectInputStream messageFromClient = new ObjectInputStream(socketForCommunicatingWithClient.getInputStream());
        return (String) messageFromClient.readObject();
    }


    // method for communication with client
    public void sendMessage(String message) throws IOException {
        ObjectOutputStream replyPassedToClient = new ObjectOutputStream(socketForCommunicatingWithDictServer.getOutputStream());
        replyPassedToClient.writeObject(message);
        replyPassedToClient.flush();
    }
    public static void main(String [] args) {
        new Thread((Runnable) new MainServer(1111)).start();
    }

}
