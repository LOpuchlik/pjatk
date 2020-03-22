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

    public String print() {
        return country + " " + city + " " + currencyAbbrev;
    }

    public String getWeather(String city) {
        JSONObject jsonObject = null;
        HttpResponse<JsonNode> response = null;
        try {
            String host = "http://api.openweathermap.org/data/2.5/weather?q=";
            String unitSystem = "&units=metric";
            String apiKey = "&appid=API_KEY";

            String query = host + city + unitSystem + apiKey;

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

}
