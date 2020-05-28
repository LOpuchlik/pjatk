package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class AdminGuiHandler {

    private Admin admin;

    @FXML
    private TextField addTopicField;

    @FXML
    private TextField removeTopicField;

    @FXML
    private ComboBox<String> chooseTopicComboBox;

    @FXML
    private TextArea newsArea;

    @FXML
    private TextArea topicsArea;

    @FXML
    private Label logLabel;

    public void initialize() throws IOException {
        this.admin = new Admin(this);
        this.chooseTopicComboBox.getItems().clear();
        this.chooseTopicComboBox.setValue("Choose topic");
        startAdminClient(this.admin);
    }

    private void log(String s) {
        System.out.println(s);
    }

    private void startAdminClient(Admin admin) {
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

    public void addNewTopic(ActionEvent actionEvent) throws IOException {
        String topic = this.addTopicField.getText();
        updateLog("New topic has been added: " + topic);
        this.admin.registerTopic(topic);
        this.addTopicField.setText("");
    }

    public void removeTopicFromList(ActionEvent actionEvent) throws IOException {
        String topic = this.removeTopicField.getText();
        updateLog("Topic: " + topic+ " has been removed");
        this.admin.deregisterTopic(topic);
        this.removeTopicField.setText("");
    }

    public void publishNews(ActionEvent actionEvent) throws IOException {
        String news = this.newsArea.getText();
        String topic = this.chooseTopicComboBox.getValue();
        updateLog("News on topic have been sent!");
        this.admin.publishNews(topic + ":" + news);
    }

    public void refreshTopics(ActionEvent actionEvent) throws IOException {
        updateLog("Refreshed!!");
        this.admin.refreshTopics();
    }

    public void handleRefreshedTopics(String message) {
        // Set all topics to text field
        this.topicsArea.setText("");
        String[] listOfTopics = message.split(" ");
        for (String s: listOfTopics) {
            this.topicsArea.appendText(s + "\n");
        }

        // wypelnianie comboboxa tematami
        this.chooseTopicComboBox.getItems().clear();
   /*     for (int i = 1; i < listOfTopics.length; i++) {
            this.chooseTopicComboBox.getItems().add(listOfTopics[i]);
        }*/
        this.chooseTopicComboBox.getItems().addAll(listOfTopics);
        this.chooseTopicComboBox.getItems().remove(listOfTopics[0]);
    }


    private void updateLog(String msg) {
        this.logLabel.setText(msg);
    }


}
