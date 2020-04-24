import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class DictServerService extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Map<String, String> requestedDictionary = new HashMap<>();
    private String address;

    private String wordToTranslate;
    private int targetPort;


    public DictServerService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                String command = line.split(",")[0];
                //check
                //System.out.println("Tu dochodzi 1: " +command);
                if (command.equals("TranslationRequest")) {
                    wordToTranslate = line.split(",")[1];
                    address = line.split(",")[2];
                    targetPort = Integer.parseInt(line.split(",")[3]);

                    String fileName = DictServer.getL();
                    prepareDictionary(fileName);


                /*    System.out.println("--------------------------------------\nPrepared dictionary");
                    // metoda wczytująca słownik - jest OK
                    requestedDictionary.entrySet().forEach(entry->{
                        System.out.println(entry.getKey() + ", " + entry.getValue());
                    });*/


                    // metoda tłumacząca - jest OK
                    //translated = getTranslation(); // to trzeba wyslac do klienta
                    //System.out.println("Translated: " + translated);
                    send();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void prepareDictionary (String filename) {
        System.out.println("Filename: " + filename);
        System.out.println();
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                requestedDictionary.put(line.split(",")[0], line.split(",")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String getTranslation () {
        return "Response,"+wordToTranslate + " --> " + requestedDictionary.get(wordToTranslate);
    }

    //zaimplementować metodę send do wysyłania odpowiedzi do klienta
    private void send() {
        new Thread(() -> {
            try {
                socket = new Socket(address, targetPort);
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(getTranslation());
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}