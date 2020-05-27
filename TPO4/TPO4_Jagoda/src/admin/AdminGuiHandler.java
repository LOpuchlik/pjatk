package admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class AdminGuiHandler {

    private Admin admin;

    @FXML
    private TextField registerTopicTxtField;

    @FXML
    private Button registerTopicBttn;

    @FXML
    private TextField removeTopicTxtField;

    @FXML
    private Button removeTopicBttn;

    @FXML
    private ComboBox<String> chooseTopicCmbBox;

    @FXML
    private TextArea newsTxtArea;

    @FXML
    private TextArea topicsTextArea;

    @FXML
    private Button publishNewsBttn;

    @FXML
    private Button refreshBttn;

    @FXML
    private Label msgLabel;

    public void initialize() throws IOException {
        this.admin = new Admin(this);
        this.chooseTopicCmbBox.getItems().clear();
        this.chooseTopicCmbBox.setValue("Choose topic");
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

    public void register(ActionEvent actionEvent) throws IOException {
        String topic = this.registerTopicTxtField.getText();
        setMessageForMessageFiled("New topic has been added");
        this.admin.registerTopic(topic);
    }

    public void deregister(ActionEvent actionEvent) throws IOException {
        String topic = this.removeTopicTxtField.getText();
        setMessageForMessageFiled("Topic has been removed");
        this.admin.deregisterTopic(topic);
    }

    public void publishNews(ActionEvent actionEvent) throws IOException {
        String news = this.newsTxtArea.getText();
        String topic = this.chooseTopicCmbBox.getValue();
        setMessageForMessageFiled("News on topic have been sent!");
        this.admin.publishNews(topic + "_" + news);
    }

    public void refreshTopics(ActionEvent actionEvent) throws IOException {
        setMessageForMessageFiled("Topic list has been refreshes!");
        this.admin.refreshTopics();
    }

    public void handleRefreshedTopics(String message) {
        // Set all topics to text field
        this.topicsTextArea.setText("");
        String[] topics = message.split(" ");
        for (String s: topics) {
            this.topicsTextArea.appendText(s + "\n");
        }

        //populate combobox with new values
        this.chooseTopicCmbBox.getItems().clear();
        this.chooseTopicCmbBox.getItems().addAll(topics);
        this.chooseTopicCmbBox.getItems().remove(topics[0]); // remove header
    }

    public void clearAll(ActionEvent actionEvent) {
        this.newsTxtArea.setText("");
        this.topicsTextArea.setText("");
        this.registerTopicTxtField.setText("");
        this.removeTopicTxtField.setText("");
    }

    private void setMessageForMessageFiled(String msg) {
        this.msgLabel.setText(msg);
    }


}
