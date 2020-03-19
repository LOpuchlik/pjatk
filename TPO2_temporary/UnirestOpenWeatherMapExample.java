import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


public class UnirestOpenWeatherMapExample {
    public static void main(String[] args) throws UnirestException {

    String host = "http://api.openweathermap.org/data/2.5/weather?q=";
    String apiKey = "API-TOKEN";
    String location = "Warsaw,pl";

    String query = host + location + "&appid=" + apiKey;
    System.out.println("Check path: " + query);


    HttpResponse <JsonNode> response = Unirest.get(query).asJson();
    JSONObject jsonObject = response.getBody().getObject();
    String jsonString = jsonObject.toString();
    System.out.println(jsonString);

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