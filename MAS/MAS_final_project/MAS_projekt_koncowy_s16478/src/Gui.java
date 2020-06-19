import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Gui extends Application {
    static String teamToDisplay="";
    Label teamSignature = new Label();
    Stage window;
    Scene scene1, scene2, scene3;
    static ObservableList<Employee> allEmployees = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

//--------------------------------------------------------
        // okienko 1
        TextField signature = new TextField();
        signature.setPromptText("input team signature");
        signature.setPadding(new Insets(10));
        signature.setPrefWidth(150);
        signature.maxWidth(150);
        Button createTeamButton = new Button("Create team");
        createTeamButton.setAlignment(Pos.CENTER); // nie centeruje mi
        createTeamButton.setOnAction(evnt -> {
            teamSignature.setText(signature.getText());


            //todo metoda tworzaca nowy zespol
            // jakies addTeam
            // Team t = new Team(signature.getText());
            //dodaje to do extensji teamow <-- dodaje sie samo
            //plus zmienia ta scene na kolejna
            window.setScene(scene2);


        });

        // Layout 1
        VBox vBoxLayout = new VBox(20); // all the objects are on top of each other
        vBoxLayout.setPadding(new Insets(15));
        vBoxLayout.getChildren().addAll(signature, createTeamButton);
        scene1 = new Scene(vBoxLayout, 300, 200);

//---------------------------------------------------------------------
// okienko 2
        TableView<Employee> table = new TableView<>();

        TableColumn<Employee, String> employeeColumn = new TableColumn<>("Employee");
        employeeColumn.setMinWidth(200);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        table.setItems(allEmployees);


        table.getColumns().addAll(employeeColumn);
        Button addEmployeeButton = new Button("Add employee");
        //addEmployeeButton.setOnAction(evnt -> {
        // tworzenie asocjacji pomiedzy wybranym pracownikiem i aktualnym zespolem
        // });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(evnt -> window.setScene(scene3));


        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15));
        vBox.getChildren().addAll(table ,addEmployeeButton, nextButton);
        scene2 = new Scene(vBox, 300, 200);


// -----------------------------------------------------------------------
// okienko 3

        Label teamCreated = new Label("Team has been created");
        GridPane.setConstraints(teamCreated,0,0);


        Label teamSignatureLabel = new Label("Team signature: ");
        GridPane.setConstraints(teamSignatureLabel,0,1);

        GridPane.setConstraints(teamSignature,1,1);
        teamSignature.setText(teamToDisplay);


        Label numberOfEmployees = new Label("Number of employees: ");
        GridPane.setConstraints(numberOfEmployees,0,2);
        Label numberOfEmployeesField = new Label();
        GridPane.setConstraints(numberOfEmployeesField,1,2);
        numberOfEmployeesField.setText("liczba osob w teamie"); // jakos wziac team size tu wciagnac


        Button closeButton = new Button("Close");
        GridPane.setConstraints(closeButton, 1,3);
        closeButton.setOnAction(evnt -> {
            window.close();
        });
        GridPane summaryGridPane = new GridPane();
        summaryGridPane.setVgap(5);
        summaryGridPane.setHgap(5);
        summaryGridPane.setPadding(new Insets(15));
        summaryGridPane.getChildren().addAll(teamCreated, teamSignatureLabel, teamSignature, numberOfEmployees, numberOfEmployeesField, closeButton);
        scene3 = new Scene(summaryGridPane, 300, 200);

//---------------------------------------------------------------------

        window.setScene(scene1);
        window.setTitle("Team");
        window.show();

    }
}
