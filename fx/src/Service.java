import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class Service {

    String country;
    String city;
    String currencyAbbrev;

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

            String query = host + city + unitSystem + apiKey;

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
        return jsonObjectCurrency.getJSONObject("rates").getDouble(currencyAbbrev);

    }

    Double getNBPRate() { //zwraca kurs złotego wobec waluty danego kraju

        return null;
    }


}
