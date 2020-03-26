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

    public String getCurrencyCodE() {
        return currencyCode;
    }

    public void setCurrencyCodE(String currencyCode) {
        this.currencyCode = currencyCode;
    }



    public String getWeather(String city) {
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
        setCurrencyCodE(currencyCode);


        String jsonString = null;
        HttpResponse<JsonNode> response = null;
        try {
            String host = "http://api.openweathermap.org/data/2.5/weather?q=";
            String unitSystem = "&units=metric";
            String apiKey = "&appid=fc7c65da529852ed5a55de9ee62f43f9";

            String q = host + city + "," + codeFromCountry + unitSystem + apiKey;
            String query = q.replace(" ", "%20");
            //System.out.println(query);
            response = Unirest.get(query).asJson();
        } catch (UnirestException e) {

        }

            jsonString = response.getBody().getObject().toString();

        return jsonString;
    }


    public Double getRateFor(String currencyAbbrev) {

        HttpResponse<JsonNode> responseCurrency = null;
        JSONObject jsonObjectCurrency = null;
        try {
            String hostAPI = "https://api.exchangeratesapi.io/latest?base=";
            //String againstCurrency = currencyCode;
            String queryCurrency = hostAPI + currencyCode;
            responseCurrency = Unirest.get(queryCurrency).asJson();
        } catch (UnirestException ex) {

        }

            jsonObjectCurrency = responseCurrency.getBody().getObject();


        double result = 0.0;
        if (!currencyAbbrev.equals(currencyCode))
            try {
                result = jsonObjectCurrency.getJSONObject("rates").getDouble(currencyAbbrev);
            }
            catch (Exception exce) {

            }
        else
            result = 1.0;
        return result;

    }


    public Double getNBPRate() { //zwraca kurs złotego wobec waluty danego kraju

        List<String> listOfCurrenciesA = new ArrayList<>();
        List<String> ListOfRatesA = new ArrayList<>();
        List<String> listOfCurrenciesB = new ArrayList<>();
        List<String> ListOfRatesB = new ArrayList<>();
        URLConnection urlConnection;
        StringBuffer NBPText_A = null;
        StringBuffer NBPText_B = null;


        URL NBP_a = null;
        try {
            NBP_a = new URL("http://www.nbp.pl/kursy/kursya.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            urlConnection = NBP_a.openConnection();
            urlConnection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            NBPText_A = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                NBPText_A.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sA = NBPText_A.toString();

        Pattern currency = Pattern.compile("[1]{1}[0]{0,4}\\s[A-Z]{3}");
        Pattern rate = Pattern.compile("[0-9]{1,2}[,]{1}[0-9]{4}");

        Matcher matcherCurrency = currency.matcher(sA);
        while (matcherCurrency.find())
            listOfCurrenciesA.add(matcherCurrency.group());

        Matcher matcherRate = rate.matcher(sA);
        while (matcherRate.find())
            ListOfRatesA.add(matcherRate.group());


//---------------------------------------------------------------------------------------
        URL NBP_b = null;
        try {
            NBP_b = new URL("http://www.nbp.pl/kursy/kursyb.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            urlConnection = NBP_b.openConnection();
            urlConnection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            NBPText_B = new StringBuffer();
            String line;

            while ((line = rd.readLine()) != null) {
                NBPText_B.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sB = NBPText_B.toString();


        matcherCurrency = currency.matcher(sB);
        while (matcherCurrency.find())
            listOfCurrenciesB.add(matcherCurrency.group());


        matcherRate = rate.matcher(sB);
        while (matcherRate.find())
            ListOfRatesB.add(matcherRate.group());

// -----------------------------------------------------------------------------------------------

//kumulacja

        // czesc dotyczaca walut

        List<String> currenciesStringCumulated = Stream.of(listOfCurrenciesA, listOfCurrenciesB)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        String bothTablesCurrencies = currenciesStringCumulated.toString();

        String step1C = bothTablesCurrencies.replace(", "," ");
        String step2C = step1C.replace("[", "");
        String step3C = step2C.replace("]","");

        String[] cumSplitCur = step3C.split(" ");

        List<Double> prefixes = new ArrayList<>(); // <----------------------------------- PREFIXY

        List<String> symbols = new ArrayList<>(); // <----------------------------------- SYMBOLE WALUT

        for (int i = 0; i < cumSplitCur.length; i++){
            if (i % 2 == 0)
                prefixes.add(Double.parseDouble(cumSplitCur[i]));
            else
                symbols.add(cumSplitCur[i]);
        }

        // czesc dotycząca kursów

        List<String> ratesStringCumulated = Stream.of(ListOfRatesA, ListOfRatesB)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        String bothTablesRates = ratesStringCumulated.toString();

        String step1R = bothTablesRates.replace(", "," ");
        String step2R = step1R.replace("[", "");
        String step3R = step2R.replace("]","");
        String step4R = step3R.replace(",", ".");

        String[] cumSplitRat = step4R.split(" ");

        List<Double> ratesD = new ArrayList<>(); // <----------------------------------- KURSY WALUT

        for (int i = 0; i < cumSplitRat.length; i++){
            ratesD.add(Double.parseDouble(cumSplitRat[i]));
        }

        double [] pref = new double[prefixes.size()];
        double [] ratesArr = new double[ratesD.size()];
        double [] ratesRecounted = new double[ratesD.size()];

        for (int i = 0; i < prefixes.size(); i++){
            pref[i] = prefixes.get(i);
            ratesArr[i] = ratesD.get(i);
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



        double res = 0;
        double out = 0;
        if (!currencyCode.equals("PLN"))
            try {
                out = symbolRate.get(currencyCode);
            }
            catch (Exception e) {
            }
        else
            out = 1.0;

        res = 1.0/out;
        return res;

    }


}