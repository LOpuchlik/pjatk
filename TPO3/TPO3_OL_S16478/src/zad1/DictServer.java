package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DictServer {
    private Dict dictionary = null;  // the map associating words and their translations
    private Socket socketForCommunicationWithMainServer = null;
    private Socket socketForCommunicationWithClient = null;  // <-- tu będzie wartosc portu podana w zmiennej
    private BufferedReader in = null;  // socket streams

    // to są zmienne do pobrania z tego co przesłał Main Server i do przetworzenia do odpowiedzi dla klienta
    String word;
    String address;
    int port;
    String responseToClient;

    static int dictServerWaitingPort = 9999;



    public DictServer(Dict dictionary) {
        this.dictionary = dictionary;

// -------------------------------- START OF COMMUNICATION BETWEEN DICTSERVER AND MAINSERVER ----------------------------------------
        try {
            ServerSocket socketAsServerForMainServer = new ServerSocket(dictServerWaitingPort);
            System.out.println("DictServer started on port " + socketAsServerForMainServer.getLocalPort()+ " and is waiting for the MainServer ...");
            socketForCommunicationWithMainServer = socketAsServerForMainServer.accept();
            System.out.println("MainServer accepted connection with DictServer");
        } catch (IOException e) {
            System.err.println(e);
        }


        // tutaj ma sie odbywac komunikacja pomiedzy main serverem a dict serverem
        // main server przesyla info do dict servera, a dict server tylko odbiera metodą receiveMessage
        String receivedFromMainServer = null;   //<------------------------- w tej zmiennej jest request main servera do dict servera
        try {
            receivedFromMainServer = receiveMessage();
            word = receivedFromMainServer.split(",")[0];   // word będzie wzięte do tlumaczenia w slowniku
            address = receivedFromMainServer.split(",")[1];
            port = Integer.parseInt(receivedFromMainServer.split(",")[2]);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // check
        System.out.println(receivedFromMainServer);


        // Zamykanie połaczenia MainServer - DictServer
        System.out.println("Closing connection between MainServer and DictServer");
        try {
            assert socketForCommunicationWithMainServer != null;
            socketForCommunicationWithMainServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

// -------------------------------- END OF COMMUNICATION BETWEEN DICTSERVER AND MAINSERVER ----------------------------------------

// ------------------------------------------ PREPARING CONTENT TO SEND TO CLIENT -------------------------------------------------
        // performing translation
// TO DO wybieranie odpowiedniego słownika przez sktór języka
        responseToClient = getDictionary(MainServer.languageCode).getTranslation(word);   // obsraniec tlumaczy tylko na angielski
        System.out.println("Translation --> " + word + " : " + responseToClient);


// -------------------------------- START OF COMMUNICATION BETWEEN DICTSERVER AND CLIENT ------------------------------------------
        // tu trzeba skomunikować się z klientem, czyli teraz Dict Server będzie działał jak klient
        try {
            socketForCommunicationWithClient = new Socket(address, port);   // ten port tutaj to musi być ten port, który otrzymał od MainServera, a address to localhost
            System.out.println("Client and DictServer are connected");
        } catch(IOException u) {
            System.out.println(u);
        }

        try {
            // sending a response to the Client
            sendMessage(responseToClient);
            System.out.println("Response to Client sent by DictServer!");
        } catch (IOException e) {
            System.err.println(e);
        }

        // close the connection
        System.out.println("Closing connection between Client and DictServer");
        try {
            assert socketForCommunicationWithClient != null;
            socketForCommunicationWithClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


// -------------------------------- START OF COMMUNICATION BETWEEN DICTSERVER AND CLIENT ------------------------------------------

    }

    // method for communication between MainServer and DictServer
    public String receiveMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream messageFromMainServer = new ObjectInputStream(socketForCommunicationWithMainServer.getInputStream());
        return (String) messageFromMainServer.readObject();
    }

    // method for communication with client
    public void sendMessage(String message) throws IOException {
        ObjectOutputStream replyPassedToClient = new ObjectOutputStream(socketForCommunicationWithClient.getOutputStream());
        replyPassedToClient.writeObject(message);
        replyPassedToClient.flush();
    }

    public Dict getDictionary(String languageCode) {
        return dictionary;
    }

    // w mainie startuję 3 serwery slownikowe, które biorą jako parametr słowniki, które wczytują z plików
    public static void main(String[] args) {

        new Thread((Runnable) new DictServer(new Dict("ES"))).start();  // bierze tylko ten pierwszy thread :-(

    }
}




