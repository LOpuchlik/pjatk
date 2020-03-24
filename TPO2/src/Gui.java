import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

public class Gui extends Application {

    Service s;
    private WebView browser;
    private String host = "https://en.wikipedia.org/wiki/";

    // variables for user inputs
    private TextField countryFill;
    private TextField cityFill;
    private TextField currencyFill;

    // variables for prettyFormat method
    private String cityValue;
    private double temp;
    private String press;
    private String desc;
    private String descValue;
    private double humValue;
    private double windValue;
    private String countryCode;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TPO2");

        HBox topPanel = new HBox();

        TextArea weatherDisplay = new TextArea("Weather\n");
        weatherDisplay.setMaxSize(256,150);
        weatherDisplay.setEditable(false);
        // --------------------------------------------------------------------------
        TextArea currencyDisplay = new TextArea("Currency");
        currencyDisplay.setMaxSize(256, 150);
        currencyDisplay.setEditable(false);
        // --------------------------------------------------------------------------
        TextArea NBPDisplay = new TextArea("NBP");
        NBPDisplay.setMaxSize(256, 150);
        NBPDisplay.setEditable(false);
        // --------------------------------------------------------------------------
        Button changeData = new Button("Change data");
        changeData.setMinSize(256, 150);
        changeData.setMaxSize(256, 150);

        StackPane wikiWindow = new StackPane();
        browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(host+"Warsaw");

        changeData.setOnAction(evnt -> {
            Stage dialog = new Stage();
            dialog.setTitle("Input parameters in English");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            GridPane dialogVbox = new GridPane();
            dialogVbox.setPadding(new Insets(15,15,15,15));
            dialogVbox.setVgap(10);
            dialogVbox.setHgap(15);

            Label countryLabel = new Label("Country:");
            GridPane.setConstraints(countryLabel, 0, 0);
            countryFill = new TextField();
            countryFill.setPromptText("country");
            GridPane.setConstraints(countryFill, 1, 0);
            Label cityLabel = new Label("City:");
            GridPane.setConstraints(cityLabel, 0, 1);
            cityFill = new TextField();
            cityFill.setPromptText("city");
            GridPane.setConstraints(cityFill, 1, 1);
            Label currencyLabel = new Label("Currency:");
            GridPane.setConstraints(currencyLabel, 0, 2);
            currencyFill = new TextField();
            currencyFill.setPromptText("currency abbreviation");
            GridPane.setConstraints(currencyFill, 1, 2);


            Button performSearch = new Button("Search");
            GridPane.setConstraints(performSearch, 0, 3);
            performSearch.setOnAction(evt -> {
                s = new Service(countryFill.getText());
                s.setCountry(countryFill.getText());
                s.setCity(cityFill.getText());
                s.setCurrencyAbbrev(currencyFill.getText());
                weatherDisplay.setText("\t\t\tWEATHER FORECAST\n" + prettyFormat(s.getWeather(cityFill.getText())));
                currencyDisplay.setText("\t\t\tCURRENCY RATE\n" + s.getRateFor(currencyFill.getText()).toString());
                NBPDisplay.setText("\t\t\t\tNBP RATE\n" + s.getNBPRate());
                browser.getEngine().load(host+cityFill.getText());
                dialog.close();

            });
            Button cancel = new Button("Cancel");
            GridPane.setConstraints(cancel, 1, 3);
            cancel.setOnAction(evt -> dialog.close());

            dialogVbox.getChildren().addAll(countryLabel, countryFill, cityLabel, cityFill, currencyLabel, currencyFill, performSearch, cancel);

            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        });

// --------------------------------------------------------------------------------------------------

        topPanel.getChildren().addAll(weatherDisplay, currencyDisplay, NBPDisplay, changeData);
        wikiWindow.getChildren().addAll(browser);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topPanel);
        mainLayout.setCenter(wikiWindow);

        Scene scene = new Scene(mainLayout, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public String getCity() {
        return cityValue;
    }

    private void setCity(String city) {
        this.cityValue = city;
    }


    public String getDescription() {
        return descValue;
    }

    private void setDescription(String description) {
        this.descValue = description;
    }

    public double getTemp() {
        return temp;
    }

    private void setTemp(double temperature) {
        this.temp = temperature;
    }

    public String getPress() {
        return press;
    }

    private void setPress(String pressure) {
        this.press = pressure;
    }
    public String geteDesc() {
        return desc;
    }

    private void setDesc(String descr) {
        this.desc = descr;
    }

    public double getHum() {
        return humValue;
    }

    private void setHum(Double hum) {
        this.humValue = hum;
    }

    public double getWind() {
        return windValue;
    }

    private void setWind(Double wind) {
        this.windValue = wind;
    }

    public String getCountryCode() {
        return countryCode;
    }

    private void setCountryCode(String cC) {
        this.countryCode = cC.toUpperCase();
    }

    public String prettyFormat (String jObj) {
        String res = null;

        try {
            JSONObject jo = new JSONObject(s.getWeather(s.getCity()));
            setCity(jo.getString("name"));
            setCountryCode(jo.getJSONObject("sys").optString("country"));
            setDesc(jo.getJSONArray("weather").getJSONObject(0).optString("description"));
            setPress(jo.getJSONObject("main").optString("pressure"));
            setTemp(jo.getJSONObject("main").optDouble("temp"));
            setHum(jo.getJSONObject("main").optDouble("humidity"));
            setWind(jo.getJSONObject("wind").optDouble("speed"));
        }catch (Exception except) {

        }

        res = "City: "+ cityValue + ", " + countryCode +"\n"
                + "Description: " + desc + "\n"
                + "Temperature: " + Math.round(temp)*(100d/100) + " C\n"
                + "Pressure: " + press + " hPa\n"
                + "Wind: " + windValue + " km/h\n"
                + "Humidity: " + humValue + " %";
        return res;
    }

}
