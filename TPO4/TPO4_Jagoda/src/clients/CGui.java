package clients;

import admin.Admin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CGui extends Application {

    private Client client;

    private Label topicsLabel;
    private TextArea topicsArea;
    private Button topicsRefreshButton;

    private Label newsLabel;
    private TextArea newsArea;

    private TextField subscribeField;
    private Button subscribeButton;

    private TextField unsubscribeField;
    private Button unsubscribeButton;

    private Label logText;
    private Label logLabel;

    private final int COMPONENT_WIDTH = 400;
    private final int COMPONENT_HEIGHT = 100;

    @Override
    public void start(Stage stage) {

        this.client = new Client(this);
        start(this.client);

        // List of topics
        topicsLabel = new Label("List of topics");
        topicsArea = new TextArea();
        topicsArea.setMaxWidth(COMPONENT_WIDTH);
        topicsArea.setMaxHeight(COMPONENT_HEIGHT);
        topicsRefreshButton = new Button("Refresh");

        // Area to display news
        newsLabel = new Label("News display");
        newsArea = new TextArea();
        newsArea.setMaxWidth(COMPONENT_WIDTH);
        newsArea.setMaxHeight(COMPONENT_HEIGHT);


        // Subscribe
        subscribeButton = new Button("Subscribe");
        subscribeField = new TextField();
        subscribeField.setMaxWidth(COMPONENT_WIDTH);

        // Unsubscribe
        unsubscribeButton = new Button("Unsubscribe");
        unsubscribeField = new TextField();
        unsubscribeField.setMaxWidth(COMPONENT_WIDTH);

        // log messages
        logText = new Label("Log:");
        logLabel = new Label();



        //Creating a Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMinSize(520, 350);
        gridPane.setPrefSize(520, 350);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);



        //Arranging all the nodes in the grid
        gridPane.add(topicsLabel, 0, 0);
        gridPane.add(topicsArea, 0, 1);
        gridPane.add(topicsRefreshButton,1, 1);


        gridPane.add(newsLabel, 0, 2);
        gridPane.add(newsArea, 0, 3);


        gridPane.add(subscribeField, 0, 4);
        gridPane.add(subscribeButton, 1, 4);


        gridPane.add(unsubscribeField, 0, 5);
        gridPane.add(unsubscribeButton, 1, 5);

        gridPane.add(logText, 0, 6);
        gridPane.add(logLabel,0, 7);




// styling components
        topicsRefreshButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        subscribeButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        unsubscribeButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        logLabel.setStyle("-fx-text-fill: darkslateblue;");
        logText.setStyle("-fx-text-fill: magenta;");



// actions on buttons -------- START ------------------------------------------------------

        topicsRefreshButton.setOnAction( (e) -> {
            updateLog("Refreshed");
            try {
                this.client.refreshTopics();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        subscribeButton.setOnAction( (e) -> {
            String topic = this.subscribeField.getText();
            updateLog("Subscribed to: " + topic);
            try {
                this.client.subscribeToTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.subscribeField.setText("");
        });



        unsubscribeButton.setOnAction( (e) -> {
            String topic = this.unsubscribeField.getText();
            updateLog("Unsubscribed from: " + topic);
            try {
                this.client.unsubscribeFromTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.unsubscribeField.setText("");
        });

// actions on buttons -------- END ------------------------------------------------------

        Scene scene = new Scene(gridPane);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();
    }




    private void start(Client client) {
        Runnable runnable = () -> {
            try {
                client.startClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread clientServerThread = new Thread(runnable);
        clientServerThread.start();
    }



    public void subscribe(ActionEvent actionEvent) throws IOException {
        String topic = this.subscribeField.getText();
        updateLog("Subscribed to: " + topic);
        this.client.subscribeToTopic(topic);
        this.subscribeField.setText("");
    }


    public void unsubscribe(ActionEvent actionEvent) throws IOException {
        String topic = this.unsubscribeField.getText();
        updateLog("Unsubscribed from: " + topic);
        this.client.unsubscribeFromTopic(topic);
        this.unsubscribeField.setText("");
    }

    public void refreshTopics(ActionEvent actionEvent) throws IOException {
        updateLog("Refreshed");
        this.client.refreshTopics();
    }









    void displayListOfTopics(String message) {
        this.topicsArea.setText("");
        String[] topics = message.split(" ");
        for (String s: topics) {
            this.topicsArea.appendText(s + "\n");
        }
    }

    void displayNews(String message) {
        this.newsArea.appendText(message.substring(5) + "\n");
    }

    public void updateLog(String msg) {
        this.logLabel.setText(msg);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
