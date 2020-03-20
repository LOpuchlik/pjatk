import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;



public class OpenWeatherMap {
    public static void main(String[] args) throws UnirestException {

    String host = "http://api.openweathermap.org/data/2.5/weather?q=";
    String location = "Warszawa,pl";
    String unitSystem = "&units=metric";
    String lang = "&lang=pl";
    String apiKey = "&appid=API_KEY";

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
    
    }
}

/* Necessary external libraries for Unirest requests and responses:

commons-codec-1.10.jar
commons-logging-1.2.jar
httpasyncclient-4.1.4.jar
httpclient-4.5.6.jar
httpcore-4.4.10.jar
httpcore-nio-4.4.7.jar
httpmime-4.5.2.jar
json-20190722.jar
unirest-java-1.4.9.jar

*/