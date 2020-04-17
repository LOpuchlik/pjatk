import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DictServerService extends Thread {
    Socket socket;
    BufferedReader input;
    Map<String, Integer> dicts = new TreeMap<>();
    private Map<String, String> requestedDictionary = new HashMap();

    String wordToTranslate;
    static String languageShortcut;
    int targetPort;

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
                System.out.println(command);
                if (command.equals("TranslationRequest")) {
                    wordToTranslate = line.split(",")[1];
                    languageShortcut = line.split(",")[2];
                    targetPort = Integer.parseInt(line.split(",")[3]);
                    System.out.println(line.split(",")[1] + line.split(",")[2] + Integer.parseInt(line.split(",")[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        makeDictionary();

        String translated = getTranslation(wordToTranslate);
        System.out.println("Translated: " + translated);
    }


    void makeDictionary () {
        String fileName = DictServerService.languageShortcut;
        // Initial content of the dictionary
        // is read from the file - ES, EN or DE
        // storing lines of the form: word,translation
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] translation = line.split(",");
                requestedDictionary.put(translation[0], translation[1]);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    public String getTranslation (String wordToTranslate) {
        return wordToTranslate + " : " + requestedDictionary.get(wordToTranslate);
    }

}