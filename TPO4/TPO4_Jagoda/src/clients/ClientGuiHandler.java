package clients;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientGuiHandler {

    private Client client;

    @FXML
    private TextArea newsArea;

    @FXML
    private TextArea topicsArea;

    @FXML
    private TextField subscribeField;

    @FXML
    private TextField unsubscribeField;

    @FXML
    private Label logLabel;

    public void initialize() throws IOException {
        this.client = new Client(this);

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

}
