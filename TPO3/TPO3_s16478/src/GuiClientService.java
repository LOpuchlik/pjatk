import java.io.*;
import java.net.Socket;

public class GuiClientService {

    Socket socket;
    PrintWriter output;
    BufferedReader input;
    public GuiClientService(String wordToTranslate, int port, String languageShortcut) {
//

        try {
            sendClientInfoToMainServer("TranslationRequest,"+wordToTranslate+","+port+","+languageShortcut, MainServer.MAINSERVER_PORT, "localhost");
        }catch (IOException ex){
            System.err.println(ex);
        }
    }


   private void sendClientInfoToMainServer(String info, int port, String host) throws IOException {
        socket = new Socket(host, port);
        exchangeData();
        output.println(info);
        // closing data transfer and connection
        closeDataExchage();
        closeConnection();
    }

    private void exchangeData () throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void closeDataExchage () throws IOException {
        input.close();
        output.close();
    }

    private void closeConnection() throws IOException {
        socket.close();
    }
}