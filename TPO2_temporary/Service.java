import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;


/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;*/


public class Service {

    private static String location;
    private static String country;
    private static String currencyAbbreviation;


    public Service (String country) {
        country = country;
    }


/*  String hostCurrency = "https://api.exchangeratesapi.io/latest?base=";
    String againstCurrency = "PLN";
    String queryCurrency = hostCurrency + againstCurrency;
    HttpResponse <JsonNode> responseCurrency = Unirest.get(queryCurrency).asJson();
    JSONObject jsonObjectCurrency = responseCurrency.getBody().getObject();

    BigDecimal jso = jsonObjectCurrency.getJSONObject("rates").getBigDecimal("EUR");
    System.out.println(jso);*/


/*    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();
    JsonElement je = jp.parse(responseCurrency.getBody().toString());
    String prettyJsonString = gson.toJson(je);
    System.out.println(prettyJsonString);*/



// gettery i settery
    public static String getCity() {
        return location;
    }

    public static void setCity(String loc) {
        location = loc;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String cou) {
        country = cou;
    }

    public static String getCurrencyAbbreviation() {
        return currencyAbbreviation;
    }

    public static void setCurrencyAbbreviation(String cAbb) {
        currencyAbbreviation = cAbb;
    }



// wlasciwe metody
    public static String getWeather(String location) {
        JSONObject jsonObject = null;
        HttpResponse<JsonNode> response = null;
        try {
            String host = "http://api.openweathermap.org/data/2.5/weather?q=";
            location = Gui.cityFill.getText();
            String unitSystem = "&units=metric";
            String apiKey = "&appid=API_KEY"; <- podac wlasciwy API KEY

            String query = host + location + unitSystem + apiKey;

            response = Unirest.get(query).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
            jsonObject = response.getBody().getObject();


            String cityName = jsonObject.getString("name");
            double temperatureValue = Math.round(jsonObject.getJSONObject("main").getDouble("temp")) * (10d / 10);
            double pressureValue = jsonObject.getJSONObject("main").getDouble("pressure");
            double humidityValue = jsonObject.getJSONObject("main").getDouble("humidity");
            double windValue = jsonObject.getJSONObject("wind").getDouble("speed");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");


            return "\t\t\tWEATHER FORECAST" +
                    "\nCity: " + cityName
                    + "\nDescription: " + description
                    + "\nTemperature: " + temperatureValue + " C"
                    + "\nWind: " + windValue + " km/h"
                    + "\nPressure: " + pressureValue + " hPa"
                    + "\nHumidity: " + humidityValue + "%";

    }


    public static Double getRateFor(String curr) { // wzgedem tego co podane jest w trzecim polu
       //sprawdzac ifem czy istnieje taka waluta jak podaje sie w trzecim polu
        curr = Gui.currencyFill.getText().toUpperCase();
        String[] locales = Locale.getISOCountries();
        HashMap<String, String> codeCountryMap = new HashMap<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            codeCountryMap.put(obj.getDisplayCountry(Locale.ENGLISH), obj.getCountry());
        }
        String codeFromCountry = codeCountryMap.get(Gui.countryFill.getText());
        Locale countryLocale = new Locale("", codeFromCountry);
        Currency currency = Currency.getInstance(countryLocale);
        String currencyCode = currency.getCurrencyCode();


        HttpResponse<JsonNode> responseCurrency = null;
        JSONObject jsonObjectCurrency = null;
        try {
            String hostCurrency = "https://api.exchangeratesapi.io/latest?base=";
            String againstCurrency = currencyCode; // z tej waluty zamieniamy na walute podana w polu 3
            String queryCurrency = hostCurrency + againstCurrency;
            responseCurrency = Unirest.get(queryCurrency).asJson();
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
        jsonObjectCurrency = responseCurrency.getBody().getObject();
        double rate = jsonObjectCurrency.getJSONObject("rates").getDouble(curr);

    return rate;
    }

    Double getNBPRate() { //zwraca kurs złotego wobec waluty danego kraju

    return null;
    }
}