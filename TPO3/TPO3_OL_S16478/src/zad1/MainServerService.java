package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
                String command = line.split(",")[0];
                switch (command){
                    case "IntroduceYourself":
                        dict.put(line.split(",")[2],Integer.parseInt(line.split(",")[1]));   // path[2] to jezyk a path[1] to numer port
                    break;
                    case "TranslationRequest":
                        languageShortcut = line.split(",")[3];
                        String wordToTranslate = line.split(",")[1];
                        String targetPort = line.split(",")[2];
                        String h = InetAddress.getLocalHost().toString().split("/")[1];
                        forwardToDictServer("TranslationRequest,"+wordToTranslate+","+h+","+targetPort);
                        //System.out.println("TranslationRequest,"+wordToTranslate+","+h+","+targetPort);
                    break;
                    default:
                        System.out.println("No such command to perform!");
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void forwardToDictServer(String info) {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", dict.get(languageShortcut));
                System.out.println("Forwarded to port: " + dict.get(languageShortcut));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(info);
                closeDataExchange();
                //socket.close();
            } catch (IOException e) {
                System.err.println("This: "+e);
            }

        }).start();
    }

    private void closeDataExchange() throws IOException {
        input.close();
        output.close();
    }
}