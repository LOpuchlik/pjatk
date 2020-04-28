package zad1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GuiClient extends Application {

    private Stage window;

    // variables for passing to Client class and the unused.MainServer class
    private static String langShort;
    private static String wordToTranslate;
    private static int portInput;

    public static int getPortInput() {
        return portInput;
    }

    public static void setPortInput(int portInput) {
        GuiClient.portInput = portInput;
    }



    // getters and setters
    public String getLangShort() {
        return langShort;
    }

    private void setLangShort(String langShort) {
        GuiClient.langShort = langShort;
    }

    public static String getWordToTranslate() {
        return wordToTranslate;
    }

    public static void setWordToTranslate(String wordToTranslate) {
        GuiClient.wordToTranslate = wordToTranslate;
    }
// ------------------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Dictionary prompt - TPO3");


        VBox disp = new VBox();
        
        //change lower bound of available ports when adding a new dictionary
        Slider slider = new Slider(1028, 49151, 1); // private/dynamic from 49152 to 65535
        slider.setPadding(new Insets(0, 15, 0, 15));
        slider.setMin(1028);
        slider.setMax(49151);
        slider.setValue(1028);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(5347);
        
        Label label = new Label("");
        label.setPadding(new Insets(0, 0, 0, 15));
        Label displayLabel = new Label(" TRANSLATION");
        displayLabel.setFont(new Font("Arial", 12));
        displayLabel.setPadding(new Insets(1,200,1,210));
        TextArea display = new TextArea();
        display.setMaxWidth(540);
        display.setMaxHeight(5);
        display.setPadding(new Insets(3,3,3,3));
        display.setEditable(false);
        disp.getChildren().addAll(label, slider, displayLabel, display);


        GridPane topPanel = new GridPane();
        topPanel.setPadding(new Insets(15,15,15,15));
        topPanel.setVgap(10);
        topPanel.setHgap(15);

        // LangShortcut label
        Label langShortcutLabel = new Label("Language shortcut:");
        GridPane.setConstraints(langShortcutLabel, 0, 0);

        // LangShortcut choiceBox
        ComboBox<String> langChoiceBox = new ComboBox<>();
        List<String> langShorts = new ArrayList<>(); // nowy jzyk trzeba bedzie dodawac do tej listy
        langShorts.add("EN");
        langShorts.add("ES");
        langShorts.add("DE");
        //langShorts.add("RU");
        langChoiceBox.setPromptText("Choose language");
        langChoiceBox.getItems().addAll(langShorts);

        GridPane.setConstraints(langChoiceBox, 2, 0);
        langChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> setLangShort(newValue));


        // Word label
        Label wordLabel = new Label("Word to translate:");
        GridPane.setConstraints(wordLabel, 0, 1);

        // Wordldist choiceBox
        ComboBox<String> wordsChoice = new ComboBox<>();
        List<String> words = new ArrayList<>();
        words.add("choinka");
        words.add("dom");
        words.add("arbuz");
        words.add("wiatr");
        words.add("jajko");
        words.add("szynka");
        words.add("czekolada");
        words.add("pies");
        words.add("kot");
        words.add("ryba");
        words.add("cokolwiek");
        wordsChoice.setPromptText("Choose word");
        wordsChoice.getItems().addAll(words);
        GridPane.setConstraints(wordsChoice, 2, 1);

        wordsChoice.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> setWordToTranslate(newValue));

        setPortInput(1028);
        label.setText("Port: "+ portInput);
        // Adding Listener to value property.
        slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    label.setText("Port: " + (int) slider.getValue());
                    setPortInput((int)slider.getValue());
                });

        // Send button
        Button sendRequest = new Button("Submit");
        GridPane.setConstraints(sendRequest, 4, 1);
        sendRequest.setOnAction(evnt -> {

            langShort = getLangShort();
            wordToTranslate = getWordToTranslate().toLowerCase();
            portInput = getPortInput();
            //System.out.println(langShort + ", " + wordToTranslate + ", " + portInput);


            new Thread(() -> {
                try {
                    new GuiClientService(wordToTranslate, portInput, langShort);
                    display.setText(GuiClientService.response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });


        // Close window button
        Button closeWindow = new Button("Close window");
        GridPane.setConstraints(closeWindow, 5, 1);
        closeWindow.setOnAction(evnt -> window.close());

        topPanel.getChildren().addAll(langShortcutLabel, langChoiceBox, wordLabel, wordsChoice, sendRequest, closeWindow);


        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topPanel);
        mainLayout.setCenter(disp);

        Scene scene = new Scene(mainLayout, 540, 215);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

