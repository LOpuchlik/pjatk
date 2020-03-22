import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test1 {
    public static void main(String[] args) throws MalformedURLException {

        URL NBP_a = new URL("http://www.nbp.pl/kursy/kursya.html");
        //URL NBP_b = new URL("http://www.nbp.pl/kursy/kursyb.html");
        System.out.println(getContentFromUrl(NBP_a));
        //System.out.println(getContentFromUrl(NBP_b));
    }

    public static String getContentFromUrl (URL NBPurl){

        StringBuffer NBPText = null;
        URLConnection urlConnection;

        try {
            urlConnection = NBPurl.openConnection();
            urlConnection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            NBPText = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                NBPText.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = NBPText.toString();
        assert NBPText != null;
        return NBPText.toString();
    }

}
