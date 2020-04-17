package unused;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Dict {

    private Map<String, String> dict = new HashMap();

    public Dict() {
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
                dict.put(translation[0], translation[1]);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    public String getTranslation (String wordToTranslate) {
        return wordToTranslate + " : " + dict.get(wordToTranslate);
    }


}