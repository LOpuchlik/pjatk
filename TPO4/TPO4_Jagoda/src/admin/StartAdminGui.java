package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartAdminGui extends Application {
    private static final String path = "../styles/adminGui_generated.fxml";

    @Override
    public void start(Stage window) throws IOException {
        window.setTitle("Admin");
        window.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource(path));
        window.setScene(new Scene(root, 610, 450));
        window.show();
    }
}
