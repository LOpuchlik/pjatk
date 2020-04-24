import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainServerService extends  Thread{
    private Socket socket;
    private static Map<String, Integer> dict = new HashMap<>();
    private PrintWriter output;
    private BufferedReader input;
    private String languageShortcut;

    MainServerService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line = input.readLine()) != null) {
                //check
                //System.out.println(line);
                String command = line.split(",")[0];

                switch (command){
                    case "IntroduceYourself":
                        dict.put(line.split(",")[2],Integer.parseInt(line.split(",")[1]));   // path[2] to jezyk a path[1] to numer port
                    break;
                    case "TranslationRequest":
                        languageShortcut = line.split(",")[3];
                        String wordToTranslate = line.split(",")[1];
                        String targetPort = line.split(",")[2];

                        forwardToDictServer("localhost",  "TranslationRequest,"+wordToTranslate+","+"localhost,"+targetPort);
                    break;
                    default:
                        System.out.println("No such command to perform!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }

    private void forwardToDictServer(String host, String info) throws IOException {
        new Thread(() -> {

            try {
                socket = new Socket(host, dict.get(languageShortcut));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(info);
                closeDataExchange();
                disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }


    private void closeDataExchange() throws IOException {
        input.close();
        output.close();
    }

    private void disconnect() throws IOException {
        socket.close();
    }


}