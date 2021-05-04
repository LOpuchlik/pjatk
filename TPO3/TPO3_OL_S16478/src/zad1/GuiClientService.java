package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GuiClientService {

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
    static String response;


    public GuiClientService(String wordToTranslate, int port, String languageShortcut) throws IOException {
        sendClientInfoToMainServer("TranslationRequest," + wordToTranslate + "," + port + "," + languageShortcut, MainServer.MAINSERVER_PORT, "localhost");

        ServerSocket server = new ServerSocket(port);
        socket = server.accept();
        receiveTranslationFromDictServer();

        try{
            socket.close();
            server.close();
        }
        catch(NullPointerException e){
            System.out.println("No sockets to close!");
        }
    }


    private void sendClientInfoToMainServer(String info, int port, String host) throws IOException {
        socket = new Socket(host, port);
        exchangeData();
        output.println(info);
        closeDataExchage();
        socket.close();
    }

    private void receiveTranslationFromDictServer() throws IOException {
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            String command = line.split(",")[0];
            if (command.equals("Response")){
                response = line.split(",")[1];
            }
        }
        System.out.println(socket.getLocalPort());
        try {
            input.close();
        } catch (NullPointerException e){
            System.err.println(e);
        }
    }

    private void exchangeData() throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void closeDataExchage() throws IOException {
        input.close();
        output.close();
    }
}
