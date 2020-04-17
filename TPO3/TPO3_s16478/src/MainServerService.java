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
    Socket socket;
    static Map<String, Integer> dict = new HashMap<>();
    PrintWriter output;
    BufferedReader input;

    public MainServerService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while((line = input.readLine()) != null) {
                //check
                System.out.println(line);
                String command = line.split(",")[0];

                switch (command){
                    case "IntroduceYourself":
                        //check
                        System.out.println("here: "+ line.split(",")[2]+" "+ line.split(",")[1]);
                        dict.put(line.split(",")[2],Integer.parseInt(line.split(",")[1]));   // path[2] to jezyk a path[1] to numer port
                        System.out.println("Strategical check");
                break;
                    case "TranslationRequest":
                        String languageShortcut = line.split(",")[3];
                        String wordToTranslate = line.split(",")[1];
                        String targetPort = line.split(",")[2];
                        //check
                        System.out.println("lang: " + languageShortcut + ", word: " + wordToTranslate+", port: " + targetPort + " port danego slownika: " + dict.get(languageShortcut));

                        InetAddress ia = InetAddress.getLocalHost();
                        System.out.println("ia: " + ia.getHostAddress());
                        forwardToDictServer(ia.getHostAddress(), dict.get(languageShortcut), "TranslationRequest,"+wordToTranslate+","+targetPort);
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


    public void forwardToDictServer(String host, int port, String info) throws IOException {
        socket = new Socket(host, port);   // cos tu sapie, ale podaję dobry adres serwera slownikowego i dobry numer portu
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println(info);
        closeDataExchange();
        disconnect();
    }

    private void closeDataExchange() throws IOException {
        input.close();
        output.close();
    }

    private void disconnect() throws IOException {
        socket.close();
    }



}