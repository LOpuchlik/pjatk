package zad1.admin;

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

        chooseTopicLabel = new Label("Choose a topic on which you want to add news");
        chooseTopicLabel.setPadding(new Insets(20,0,0,0));
        this.chooseTopicComboBox = new ComboBox<>();
        this.chooseTopicComboBox.getItems().clear();
        this.chooseTopicComboBox.setValue("Choose topic");
        this.chooseTopicComboBox.setPrefWidth(COMPONENT_WIDTH);

// set of topics
        topicsLabel = new Label("Set of topics");
        topicsArea = new TextArea();
        topicsArea.setMaxWidth(COMPONENT_WIDTH);
        topicsArea.setMaxHeight(COMPONENT_HEIGHT);
        refreshButton = new Button("Refresh");

// Area to type news
        typeNews = new Label("News prompt");
        newsArea = new TextArea();
        newsArea.setPromptText("type news");
        newsArea.setMaxWidth(COMPONENT_WIDTH);
        newsArea.setMaxHeight(COMPONENT_HEIGHT);
        sendNewsButton = new Button("Send news");

//Add new topic
        addNewTopicButton = new Button("Add topic");
        addTopicField = new TextField();
        addTopicField.setPromptText("give one topic at a time");
        addTopicField.setMaxWidth(COMPONENT_WIDTH);

//Remove existing topic
        removeTopicButton = new Button("Remove topic");
        removeTopicButton.setMinWidth(100);
        removeTopicButton.setPrefWidth(100);
        removeTopicField = new TextField();
        removeTopicField.setPromptText("give one topic at a time");
        removeTopicField.setMaxWidth(COMPONENT_WIDTH);

        logText = new Label("Log:");
        logLabel = new Label();


//Creating a Grid Pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMinSize(500, 460);
        gridPane.setPrefSize(520, 460);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);



//Arranging all the nodes in the grid

        gridPane.add(addTopicField, 0, 0);
        gridPane.add(addNewTopicButton, 1, 0);
        gridPane.add(removeTopicField,0, 1);
        gridPane.add(removeTopicButton,1, 1);

        gridPane.add(topicsLabel,0, 2);
        gridPane.add(topicsArea, 0, 3);
        gridPane.add(refreshButton, 1, 3);


        gridPane.add(chooseTopicLabel, 0, 4);
        gridPane.add(chooseTopicComboBox, 0, 5);

        gridPane.add(typeNews, 0, 6);
        gridPane.add(newsArea, 0, 7);
        gridPane.add(sendNewsButton, 1, 7);


        gridPane.add(logText,0, 8);
        gridPane.add(logLabel,0, 9);





// styling components
        addNewTopicButton.setStyle("-fx-background-color: #064a9c; -fx-text-fill: white; -fx-border-color: #0c72ed; -fx-border-width: 2;");
        removeTopicButton.setStyle("-fx-background-color: #064a9c; -fx-text-fill: white; -fx-border-color: #0c72ed; -fx-border-width: 2;");
        sendNewsButton.setStyle("-fx-background-color: #064a9c; -fx-text-fill: white; -fx-border-color: #0c72ed; -fx-border-width: 2;");
        refreshButton.setStyle("-fx-background-color: #064a9c; -fx-text-fill: white; -fx-border-color: #0c72ed; -fx-border-width: 2;");

        logText.setStyle("-fx-text-fill: #064a9c;");
        logLabel.setStyle("-fx-text-fill: #064a9c;");

        chooseTopicComboBox.setStyle("-fx-background-color: #a7c9f2; -fx-border-color: #0c72ed; -fx-border-width: 2;");



// actions on buttons -------- START ------------------------------------------------------
        addNewTopicButton.setOnAction( (e) -> {
            String topic = this.addTopicField.getText();
            updateLog("New topic has been added - " + topic);
            try {
                this.admin.addNewTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.addTopicField.setText("");
        });


        removeTopicButton.setOnAction( (e) -> {
            String topic = this.removeTopicField.getText();
            updateLog("Topic - " + topic+ " - has been removed");
            try {
                this.admin.removeTopic(topic);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.removeTopicField.setText("");
            this.chooseTopicComboBox.setValue("Choose topic");
        });


        sendNewsButton.setOnAction( (e) -> {
            String news = this.newsArea.getText();
            String topic = this.chooseTopicComboBox.getValue();
            updateLog("News on " + topic + " have been sent!");
            try {
                this.admin.sendNews(topic + ":" + news);
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
        stage.setX(50);
        stage.setY(300);
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
        Thread thread = new Thread(runnable);
        thread.start();
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