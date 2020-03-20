import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;*/

import java.math.BigDecimal;


public class OpenWeatherMap {
    public static void main(String[] args) throws UnirestException {

    String host = "http://api.openweathermap.org/data/2.5/weather?q=";
    String location = "Warszawa,pl";
    String unitSystem = "&units=metric";
    String lang = "&lang=pl";
    String apiKey = "&appid=API_KEY"; // <-- zamiast API_KEY wpisać poprawny api key

    String query = host + location + unitSystem + apiKey + lang;
    
    HttpResponse <JsonNode> response = Unirest.get(query).asJson();
    JSONObject jsonObject = response.getBody().getObject();


    String city = jsonObject.getString("name");
    double temperature = Math.round(jsonObject.getJSONObject("main").getDouble("temp"))*(10d/10);
    double pressure = jsonObject.getJSONObject("main").getDouble("pressure");
    double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
    String countryCode = jsonObject.getJSONObject("sys").getString("country");
    double wind = jsonObject.getJSONObject("wind").getDouble("speed");
    String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");


    String forecast = "City: " + city + ", " + countryCode + "\n"
            + "Description: " + description +"\n"
            + "Temperature: " + temperature + " C\n"
            + "Wind: " + wind + " km/h\n"
            + "Pressure: " + pressure + " hPa\n"
            + "Humidity: " + humidity + "%\n";


    System.out.println("\n\tFORECAST \n\n" + forecast);


    String hostCurrency = "https://api.exchangeratesapi.io/latest?base=";
    String againstCurrency = "PLN";
    String queryCurrency = hostCurrency + againstCurrency;
    HttpResponse <JsonNode> responseCurrency = Unirest.get(queryCurrency).asJson();
    JSONObject jsonObjectCurrency = responseCurrency.getBody().getObject();

    BigDecimal jso = jsonObjectCurrency.getJSONObject("rates").getBigDecimal("EUR");
    System.out.println(jso);

    
/*    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();
    JsonElement je = jp.parse(responseCurrency.getBody().toString());
    String prettyJsonString = gson.toJson(je);
    System.out.println(prettyJsonString);*/

    }
}