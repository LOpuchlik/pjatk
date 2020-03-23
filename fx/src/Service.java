import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {

    private String country;
    private String city;
    private String currencyAbbrev;
    private String currencyCode;

    private double res = 0;

    public Service() {
    }

    public Service(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrencyAbbrev() {
        return currencyAbbrev;
    }

    public void setCurrencyAbbrev(String currencyAbbrev) {
        this.currencyAbbrev = currencyAbbrev;
    }

    public String getWeather(String city) {
        JSONObject jsonObject = null;
        HttpResponse<JsonNode> response = null;
        try {
            String host = "http://api.openweathermap.org/data/2.5/weather?q=";
            String unitSystem = "&units=metric";
            String apiKey = "&appid=fc7c65da529852ed5a55de9ee62f43f9";

            String query = host + city + "," + country + unitSystem + apiKey;
            //System.out.println(query);
            response = Unirest.get(query).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        jsonObject = response.getBody().getObject();
        return jsonObject.toString();

    }




    public Double getRateFor(String currencyAbbrev) {
        String[] locales = Locale.getISOCountries();
        HashMap<String, String> codeCountryMap = new HashMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            codeCountryMap.put(obj.getDisplayCountry(Locale.ENGLISH), obj.getCountry());
        }
        String codeFromCountry = codeCountryMap.get(country);
        Locale countryLocale = new Locale("", codeFromCountry);
        Currency currency = Currency.getInstance(countryLocale);
        String currencyCode = currency.getCurrencyCode();


        HttpResponse<JsonNode> responseCurrency = null;
        JSONObject jsonObjectCurrency = null;
        try {
            String hostAPI = "https://api.exchangeratesapi.io/latest?base=";
            String againstCurrency = currencyCode;
            String queryCurrency = hostAPI + againstCurrency;
            responseCurrency = Unirest.get(queryCurrency).asJson();
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
        jsonObjectCurrency = responseCurrency.getBody().getObject();

        double result = 0;
            if (!currencyAbbrev.equals(currencyCode))
                result = jsonObjectCurrency.getJSONObject("rates").getDouble(currencyAbbrev);
            else
                result = 1;
        return result;

    }





    public Double getNBPRate() { //zwraca kurs złotego wobec waluty danego kraju
        URL NBP_a = null;
        try {
            NBP_a = new URL("http://www.nbp.pl/kursy/kursya.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        List<String> listaWalutA = new ArrayList<>();
        List<String> listaKursowStringA = new ArrayList<>();

        StringBuffer NBPText_A = null;
        URLConnection urlConnection_A;

        try {
            urlConnection_A = NBP_a.openConnection();
            urlConnection_A.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection_A.getInputStream()));

            NBPText_A = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                NBPText_A.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sA = NBPText_A.toString();

        Pattern waluta = Pattern.compile("[1]{1}[0]{0,4}\\s[A-Z]{3}");
        Matcher matcherWaluta = waluta.matcher(sA);
        while (matcherWaluta.find())
            listaWalutA.add(matcherWaluta.group());

        Pattern kurs = Pattern.compile("[0-9]{1,2}[,]{1}[0-9]{4}");
        Matcher matcherKurs = kurs.matcher(sA);
        while (matcherKurs.find())
            listaKursowStringA.add(matcherKurs.group());


//---------------------------------------------------------------------------------------
        URL NBP_b = null;
        try {
            NBP_b = new URL("http://www.nbp.pl/kursy/kursyb.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        List<String> listaWalutB = new ArrayList<>();
        List<String> listaKursowStringB = new ArrayList<>();


        StringBuffer NBPText_B = null;
        URLConnection urlConnection_B;


        try {
            urlConnection_B = NBP_b.openConnection();
            urlConnection_B.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection_B.getInputStream()));

            NBPText_B = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                NBPText_B.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sB = NBPText_B.toString();

        waluta = Pattern.compile("[1]{1}[0]{0,4}\\s[A-Z]{3}");
        matcherWaluta = waluta.matcher(sB);
        while (matcherWaluta.find())
            listaWalutB.add(matcherWaluta.group());

        kurs = Pattern.compile("[0-9]{1,2}[,]{1}[0-9]{4}");
        matcherKurs = kurs.matcher(sB);
        while (matcherKurs.find())
            listaKursowStringB.add(matcherKurs.group());

// -----------------------------------------------------------------------------------------------

//kumulacja

        // czesc dotyczaca walut

        List<String> walutyStringCumulated = Stream.of(listaWalutA, listaWalutB)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        String bothWaluty = walutyStringCumulated.toString();

        String cumrepl_w = bothWaluty.replace(", "," ");
        String cumrepl_wa = cumrepl_w.replace("[", "");
        String cumrepl_wal = cumrepl_wa.replace("]","");

        String[] cumSplitWal = cumrepl_wal.split(" ");

        List<Double> prefixes = new ArrayList<>(); // <----------------------------------- PREFIXY

        List<String> symbols = new ArrayList<>(); // <----------------------------------- SYMBOLE WALUT

        for (int i = 0; i < cumSplitWal.length; i++){
            if (i % 2 == 0)
                prefixes.add(Double.parseDouble(cumSplitWal[i]));
            else
                symbols.add(cumSplitWal[i]);
        }

// czesc dotycząca kursów

        List<String> kursyStringCumulated = Stream.of(listaKursowStringA, listaKursowStringB)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        String bothKursy = kursyStringCumulated.toString();

        String cumrepl_k = bothKursy.replace(", "," ");
        String cumrepl_ku = cumrepl_k.replace("[", "");
        String cumrepl_kur = cumrepl_ku.replace("]","");
        String comaForDot = cumrepl_kur.replace(",", ".");

        String[] cumSplitKur = comaForDot.split(" ");

        List<Double> kursy = new ArrayList<>(); // <----------------------------------- KURSY WALUT

        for (int i = 0; i < cumSplitKur.length; i++){
            kursy.add(Double.parseDouble(cumSplitKur[i]));
        }

        double [] pref = new double[prefixes.size()];
        double [] ratesArr = new double[kursy.size()];
        double [] ratesRecounted = new double[kursy.size()];

        for (int i = 0; i < prefixes.size(); i++){
            pref[i] = prefixes.get(i);
            ratesArr[i] = kursy.get(i);
        }

        for (int i = 0; i < prefixes.size(); i++){
            ratesRecounted[i] = ratesArr[i]/pref[i];
        }

        List<Double> rates = new ArrayList<>();
        for (Double d: ratesRecounted) {
            rates.add(d);
        }

        HashMap<String, Double> symbolRate = new HashMap<>();
        for (int i = 0; i < rates.size(); i++){
            symbolRate.put(symbols.get(i), rates.get(i));
        }


        String[] locales = Locale.getISOCountries();
        HashMap<String, String> codeCountryMap = new HashMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            codeCountryMap.put(obj.getDisplayCountry(Locale.ENGLISH), obj.getCountry());
        }
        String codeFromCountry = codeCountryMap.get(country);
        Locale countryLocale = new Locale("", codeFromCountry);
        Currency currency = Currency.getInstance(countryLocale);
        String currencyCode = currency.getCurrencyCode();

        double out = 0;
        if (currencyCode.equals("PLN"))
            out = 1.0;
        else
            out = symbolRate.get(currencyCode);

        res = 1.0/out;

        return res;
    }


}
