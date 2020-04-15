package zad1.gui;

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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class GuiClient extends Application {


    public static void main(String[] args) {
        launch();
    }

    private Stage window;


    // variables for passing to Client class and the MainServer class
    public static String langShort;
    public static String wordToTranslate;
    public static int portInput;


    public String getLangShort() {
        return langShort;
    }

    private void setLangShort(String langShort) {
        GuiClient.langShort = langShort;
    }


// ----------------------------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Dictionary prompt - TPO3");


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
        langChoiceBox.setPromptText("Choose language       ");
        langChoiceBox.getItems().addAll(langShorts);

        GridPane.setConstraints(langChoiceBox, 2, 0);
        // Listen for selection changes
        langChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> setLangShort(newValue));


        // Word label
        Label wordLabel = new Label("Word to translate:");
        GridPane.setConstraints(wordLabel, 0, 1);

        // Word field
        TextField wordField = new TextField();
        wordField.setPromptText("word");
        GridPane.setConstraints(wordField, 2, 1);

        // Port label
        Label portLabel = new Label("Port no.:");
        GridPane.setConstraints(portLabel, 0, 2);

        // Port field
        TextField portField = new TextField();
        portField.setPromptText("port no.");
        GridPane.setConstraints(portField, 2, 2);



        // Send button
        Button sendRequest = new Button("Send");
        GridPane.setConstraints(sendRequest, 4, 1);
        sendRequest.setOnAction(evnt -> {

            langShort = getLangShort();
            wordToTranslate = wordField.getText().toLowerCase();
            wordField.setText("");
            try {
                 portInput = Integer.parseInt(portField.getText().toLowerCase());
                 portField.setText("");
            } catch (Exception exc) {
                System.err.println("Port number has to be of int type. Try again!");
            }

            Thread t = new Thread(()-> {
                try {
                    ServerSocket server = new ServerSocket(8888);
                    Socket clientSocket = new Socket("localhost", 8888);
                    server.accept();
                    System.out.println("Check point - OK!");
                    String infoToPass = langShort + ", " + wordToTranslate + ", " + portInput+".";
                    System.out.println(infoToPass);

                    //teraz ten clientSocket musi przekazać do Serwera Głównego to co ma w infoToPass



                    // Serwer czekający na odpowiedz od serwera słownikowego
                    ServerSocket clientAwaitingReplyOnPort = new ServerSocket(portInput);
                    // jakis change listener potrzebny?
                    Socket dictServerConnection = clientAwaitingReplyOnPort.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            //check
            //System.out.println(langShort + " " + wordToTranslate + " " + portInput);
        });

        // Close window button
        Button closeWindow = new Button("Close window");
        GridPane.setConstraints(closeWindow, 5, 1);
        closeWindow.setOnAction(evnt -> window.close());

        topPanel.getChildren().addAll(langShortcutLabel, langChoiceBox, wordLabel, wordField, portLabel, portField, sendRequest, closeWindow);



        VBox disp = new VBox();
        Label dispLab = new Label("DISPLAY");
        dispLab.setFont(new Font("Arial", 16));
        dispLab.setPadding(new Insets(1,200,1,210));
        TextArea display = new TextArea();
        display.setPadding(new Insets(3,3,3,3));
        display.setEditable(false);
        disp.getChildren().addAll(dispLab, display);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topPanel);
        mainLayout.setCenter(disp);

        Scene scene = new Scene(mainLayout, 520, 250);
        window.setScene(scene);
        window.show();

    }

}
