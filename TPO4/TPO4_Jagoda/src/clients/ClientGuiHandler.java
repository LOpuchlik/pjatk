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
    private TextField registerTopicTxtField;

    @FXML
    private Button subscribeBttn;

    @FXML
    private TextField removeTopicTxtField;

    @FXML
    private Button unsubscribeBttn;

    @FXML
    private TextArea newsTxtArea;

    @FXML
    private TextArea topicsTextArea;

    @FXML
    private Button clearAllBttn;

    @FXML
    private Button refreshTopicsBttn;

    @FXML
    private Label msgLabel; //editable="false"

    public void initialize() throws IOException {
        log("Client GUI initialized");
        this.client = new Client(this);
        startClient(this.client);
    }

    private void startClient(Client client) {
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
        String topic = this.registerTopicTxtField.getText();
        setMessageForMessageFieled("Subscribed to topic");
        this.client.subscribeToTopic(topic);
    }


    public void unsubscribe(ActionEvent actionEvent) throws IOException {
        String topic = this.removeTopicTxtField.getText();
        setMessageForMessageFieled("Unsubscribed from topic");
        this.client.unsubscribeFromTopic(topic);
    }

    public void refreshTopics(ActionEvent actionEvent) throws IOException {
        setMessageForMessageFieled("Refreshed topics");
        this.client.refreshTopics();
    }

    void handleRefreshedTopics(String message) {
        this.topicsTextArea.setText("");
        String[] topics = message.split(" ");
        for (String s: topics) {
            this.topicsTextArea.appendText(s + "\n");
        }
    }

    void handleNews(String message) {
        this.newsTxtArea.appendText(message.substring(6) + "\n");
    }

    public void clearAll(ActionEvent actionEvent) {
        setMessageForMessageFieled("Cleared all");
        this.newsTxtArea.setText("");
        this.topicsTextArea.setText("");
        this.registerTopicTxtField.setText("");
        this.removeTopicTxtField.setText("");
    }

    private void setMessageForMessageFieled(String msg) {
        this.msgLabel.setText(msg);
    }
    private void log(String s) {
        System.out.println(s);
    }

}
