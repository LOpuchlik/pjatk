package admin;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class AGui extends Application {

    private Admin admin;

    private TextField addTopicField;
    private  Button addNewTopicButton;

    private TextField removeTopicField;
    private  Button removeTopicButton;

    private Label chooseTopicLabel;
    private ComboBox<String> chooseTopicComboBox;

    private Label typeNews;
    private TextArea newsArea;
    private  Button sendNewsButton;

    private Label topicsLabel;
    private TextArea topicsArea;
    private Button refreshButton;

    private Label logLabel;
    private Label logText;

    private final int COMPONENT_WIDTH = 400;
    private final int COMPONENT_HEIGHT = 100;


    @Override
    public void start(Stage stage) {
        this.admin = new Admin(this);
        start(this.admin);

        chooseTopicLabel = new Label("Choose topic");
        this.chooseTopicComboBox = new ComboBox<>();
        this.chooseTopicComboBox.getItems().clear();
        this.chooseTopicComboBox.setValue("Choose topic");
        this.chooseTopicComboBox.setPrefWidth(COMPONENT_WIDTH);

// List of topics
        topicsLabel = new Label("List of topics");
        topicsArea = new TextArea();
        topicsArea.setMaxWidth(COMPONENT_WIDTH);
        topicsArea.setMaxHeight(COMPONENT_HEIGHT);
        refreshButton = new Button("Refresh");

// Area to type news
        typeNews = new Label("News prompt");
        newsArea = new TextArea();
        newsArea.setMaxWidth(COMPONENT_WIDTH);
        newsArea.setMaxHeight(COMPONENT_HEIGHT);
        sendNewsButton = new Button("Send news");

//Add new topic
        addNewTopicButton = new Button("Add topic");
        addTopicField = new TextField();
        addTopicField.setMaxWidth(COMPONENT_WIDTH);

//Remove existing topic
        removeTopicButton = new Button("Remove topic");
        removeTopicField = new TextField();
        removeTopicField.setMaxWidth(COMPONENT_WIDTH);

        logText = new Label("Log:");
        logLabel = new Label();



//Creating a Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMinSize(520, 450);
        gridPane.setPrefSize(520, 450);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);



//Arranging all the nodes in the grid
        gridPane.add(chooseTopicLabel, 0, 0);
        gridPane.add(chooseTopicComboBox, 0, 1);
        gridPane.add(topicsLabel,0, 2);
        gridPane.add(topicsArea, 0, 3);
        gridPane.add(refreshButton, 1, 3);
        gridPane.add(typeNews, 0, 4);
        gridPane.add(newsArea, 0, 5);
        gridPane.add(sendNewsButton, 1, 5);
        gridPane.add(addTopicField, 0, 6);
        gridPane.add(addNewTopicButton, 1, 6);
        gridPane.add(removeTopicField,0, 7);
        gridPane.add(removeTopicButton,1, 7);
        gridPane.add(logText,0, 8);
        gridPane.add(logLabel,0, 9);


// styling components
        addNewTopicButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        removeTopicButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        sendNewsButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        refreshButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        logLabel.setStyle("-fx-text-fill: darkslateblue;");
        logText.setStyle("-fx-text-fill: magenta;");



// actions on buttons -------- START ------------------------------------------------------
        addNewTopicButton.setOnAction( (e) -> {
            String topic = this.addTopicField.getText();
            updateLog("New topic has been added - " + topic);
            try {
                this.admin.registerTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.addTopicField.setText("");
        });


        removeTopicButton.setOnAction( (e) -> {
            String topic = this.removeTopicField.getText();
            updateLog("Topic - " + topic+ " - has been removed");
            try {
                this.admin.deregisterTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.removeTopicField.setText("");
        });


        sendNewsButton.setOnAction( (e) -> {
            String news = this.newsArea.getText();
            String topic = this.chooseTopicComboBox.getValue();
            updateLog("News on " + topic + " have been sent!");
            try {
                this.admin.publishNews(topic + ":" + news);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.newsArea.setText("");
        });

        refreshButton.setOnAction( (e) -> {
            updateLog("Topics have been refreshed!!");
            try {
                this.admin.refreshTopics();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

// actions on buttons -------- END ------------------------------------------------------

        Scene scene = new Scene(gridPane);
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }


    private void start(Admin admin) {
        Runnable runnable = () -> {
            try {
                admin.startClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread clientServerThread = new Thread(runnable);
        clientServerThread.start();
    }


    public void organizeTopics(String message) {
        // Set all topics to text field
        this.topicsArea.setText("");
        String[] listOfTopics = message.split(" ");
        for (String s: listOfTopics) {
            this.topicsArea.appendText(s + "\n");
        }
        // wypelnianie comboboxa tematami
        this.chooseTopicComboBox.getItems().clear();
        this.chooseTopicComboBox.getItems().addAll(listOfTopics);
        this.chooseTopicComboBox.getItems().remove(listOfTopics[0]); // usuwanie naglowka - TOPICS
    }


    private void updateLog(String msg) {
        this.logLabel.setText(msg);
    }

    public static void main(String args[]){
        launch(args);
    }
}