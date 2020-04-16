package zad1;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Dict {

    private Map<String, String> dict = new HashMap();

    public Dict(String fileName) {    //filename to bedzie shortcut + .txt
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

    // Returns the translation of the given word
    public String getTranslation(String word) {
        return dict.get(word);
    }
}