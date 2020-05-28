package clients;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartClientGui extends Application {
    private static final String path = "../styles/clientGui_generated.fxml";

    @Override
    public void start(Stage window) throws IOException {
        window.setTitle("Client");
        window.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource(path));
        window.setScene(new Scene(root, 610, 350));
        window.show();
    }
}
