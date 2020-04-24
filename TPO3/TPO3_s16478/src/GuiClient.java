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
    static String langShort;
    static String wordToTranslate;
    static int portInput;

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
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Dictionary prompt - TPO3");

        VBox disp = new VBox();
        Label dispLab = new Label("TRANSLATION");
        dispLab.setFont(new Font("Arial", 12));
        dispLab.setPadding(new Insets(1,200,1,210));
        TextArea display = new TextArea();
        display.setPadding(new Insets(3,3,3,3));
        display.setEditable(false);
        disp.getChildren().addAll(dispLab, display);


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
        langChoiceBox.setPromptText("Choose language");
        langChoiceBox.getItems().addAll(langShorts);

        GridPane.setConstraints(langChoiceBox, 2, 0);
        // Listen for selection changes
        langChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> setLangShort(newValue));


        // Word label
        Label wordLabel = new Label("Word to translate:");
        GridPane.setConstraints(wordLabel, 0, 1);

        // Wordldist choiceBox
        ComboBox<String> wordsChoice = new ComboBox<>();
        List<String> words = new ArrayList<>();
        words.add("choinka");
        words.add("słońce");
        words.add("księżyc");
        words.add("wiatr");
        words.add("jajko");
        words.add("szynka");
        words.add("czekolada");
        words.add("pies");
        words.add("kot");
        words.add("ryba");
        wordsChoice.setPromptText("Choose word");
        wordsChoice.getItems().addAll(words);
        GridPane.setConstraints(wordsChoice, 2, 1);
        // Listen for selection changes
        wordsChoice.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> setWordToTranslate(newValue));


        // Port label
        Label portLabel = new Label("Port no.:");
        GridPane.setConstraints(portLabel, 0, 2);

        // Port field
        TextField portField = new TextField();
        portField.setPromptText("port no.");
        GridPane.setConstraints(portField, 2, 2);

        // Port range label
        Label portRangeLabel = new Label("(1024-65535)");
        GridPane.setConstraints(portRangeLabel, 0, 3);


        // Send button
        Button sendRequest = new Button("Submit");
        GridPane.setConstraints(sendRequest, 4, 1);
        sendRequest.setOnAction(evnt -> {

            langShort = getLangShort();
            wordToTranslate = getWordToTranslate().toLowerCase();
            try {
                if (!((Integer.parseInt(portField.getText()) < 1024 || (Integer.parseInt(portField.getText())) > 65535))) {
                    portInput = Integer.parseInt(portField.getText());
                    portField.setText("");
                }
                else
                    System.out.println("You have input wrong value! Try again");
            } catch (Exception exc) {
                System.err.println("Port number has to be of int type in range 1024 - 65535. Try again!");
            }
            System.out.println(langShort + ", " + wordToTranslate + ", " + portInput);


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

        topPanel.getChildren().addAll(langShortcutLabel, langChoiceBox, wordLabel, wordsChoice, portLabel, portField, portRangeLabel, sendRequest, closeWindow);


        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topPanel);
        mainLayout.setCenter(disp);

        Scene scene = new Scene(mainLayout, 540, 250);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

