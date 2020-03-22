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

    Service s;
    private Stage window, dialog;
    private WebView browser;
    private WebEngine webEngine;
    private String host = "https://en.wikipedia.org/wiki/";

    TextField countryFill;
    TextField cityFill;
    TextField currencyFill;


    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
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
        webEngine = browser.getEngine();
        webEngine.load(host+"Warsaw");

        changeData.setOnAction(evnt -> {
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
                s = new Service(countryFill.getText());
                s.setCountry(countryFill.getText());
                s.setCity(cityFill.getText());
                s.setCurrencyAbbrev(currencyFill.getText());
                System.out.println("Control check: " + host+cityFill.getText());
                System.out.println(s.print());
                browser.getEngine().load(host+cityFill.getText());
                dialog.close();

            });

            dialogVbox.getChildren().addAll(countryLabel, countryFill, cityLabel, cityFill, currencyLabel, currencyFill, performSearch);

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
        window.setScene(scene);
        window.show();


 /*       button.setOnAction(evnt -> {
            s = new Service("Dog");
            ta.setText(s.getName());
        });*/


    }
}