import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
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

public class Gui extends Application {

    public static Stage window, dialog;
    private StackPane wikiWindow;
    private Button button;
    private WebEngine webEngine;
    private WebView browser;
    private String host = "https://en.wikipedia.org/wiki/";
    private String appendix = "";

    static TextField cityFill;
    static TextField countryFill;
    static TextField currencyFill;

    Service s;

/*    public Gui(Service s){
        this.s = s;
    }*/


   public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("TPO2");

        HBox topMenu = new HBox();

        TextArea weather = new TextArea("Weather\n");
        weather.setMinSize(256, 200);
        //weather.setStyle("-fx-background-color: #99CCFF;");
        weather.setEditable(false);
// --------------------------------------------------------------------------
        TextArea exchangeVsCurrency = new TextArea("Currency");
        exchangeVsCurrency.setMinSize(256, 200);
        //exchangeVsCurrency.setStyle("-fx-background-color: #99CCFF;");
        exchangeVsCurrency.setEditable(false);
// --------------------------------------------------------------------------
        TextArea exchangeRateVsPLN = new TextArea("NBP");
        exchangeRateVsPLN.setMinSize(256, 200);
        //exchangeRatevsPLN.setStyle("-fx-background-color: #99CCFF;");
        exchangeRateVsPLN.setEditable(false);
// --------------------------------------------------------------------------
        Button button = new Button("Change data");
        button.setMinSize(256, 200);

        StackPane wikiWindow = new StackPane();
        browser = new WebView();
        webEngine = browser.getEngine();
        webEngine.load(host);

        button.setOnAction(evnt -> {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            //dialog.initOwner(primaryStage);
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
            GridPane.setConstraints(performSearch, 1, 3);
            performSearch.setOnAction(evt -> {
                    Service.setCountry(countryFill.getText());
                    Service.setCity(cityFill.getText());
                    Service.setCurrencyAbbreviation(currencyFill.getText());
                    weather.setText(Service.getWeather(cityFill.getText()));
                    exchangeVsCurrency.setText("\t\t\tCURRENCY RATE\n" + Service.getRateFor(countryFill.getText()).toString());
                    //exchangeRateVsPLN.setText(Service.getNBPRate().toString());
                    System.out.println("Control chaeck: " + host+cityFill.getText());
                    browser.getEngine().load(host+cityFill.getText());
                    dialog.close();

            });




            dialogVbox.getChildren().addAll(countryLabel, countryFill, cityLabel, cityFill, currencyLabel, currencyFill, performSearch);

            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        });

// --------------------------------------------------------------------------------------------------

        topMenu.getChildren().addAll(weather, exchangeVsCurrency, exchangeRateVsPLN, button);
        wikiWindow.getChildren().addAll(browser);


        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topMenu);
        mainLayout.setCenter(wikiWindow);

        Scene scene = new Scene(mainLayout, 1024, 768);
        window.setScene(scene);
        window.show();
        
    }

}