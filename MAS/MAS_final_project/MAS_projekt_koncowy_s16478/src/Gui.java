import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

    static Team t;
    TextArea teamMembersArea = new TextArea();


    static String teamToDisplay="";
    static TextField signature;
    Label teamSignature = new Label();
    Stage window;
    Scene scene1, scene2, scene3;
    static ObservableList<RegularEmployee> allEmployees = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

//--------------------------------------------------------
        // okienko 1
        Label label = new Label("Input team signature");
        signature = new TextField();
        signature.setPadding(new Insets(10));
        signature.setPrefWidth(150);
        signature.maxWidth(150);
        Button createTeamButton = new Button("Create team");
        createTeamButton.setOnAction(evnt -> {
            teamSignature.setText(signature.getText());
            // metoda tworzaca nowy zespol
            t = Team.addTeam(signature.getText()); // tworzy nowy zespol o podanej sygnaturze
            signature.setText("");
            window.setScene(scene2);

        });


        VBox vBoxLayout = new VBox(20); // all the objects are on top of each other
        vBoxLayout.setPadding(new Insets(15));
        vBoxLayout.getChildren().addAll(label, signature, createTeamButton);
        scene1 = new Scene(vBoxLayout, 300, 200);

//---------------------------------------------------------------------
// okienko 2
        TableView<RegularEmployee> table = new TableView<>();

        TableColumn<RegularEmployee, String> employeeColumn = new TableColumn<>("Employee");
        employeeColumn.setMinWidth(200);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table.setItems(allEmployees);


        table.getColumns().addAll(employeeColumn);
        Button addEmployeeButton = new Button("Add employee");
        addEmployeeButton.setOnAction(evnt -> {
            // tworzenie asocjacji pomiedzy wybranym pracownikiem i aktualnym zespolem

            if (table.getSelectionModel().getSelectedItem() != null) {
                RegularEmployee selectedEmployee = table.getSelectionModel().getSelectedItem();

                //Team.teamMembers.add(selectedEmployee);
                t.addEmployee(selectedEmployee);
                System.out.println(selectedEmployee.toString());
            }

        });


        Button nextButton = new Button("Next");
        nextButton.setOnAction(evnt -> window.setScene(scene3));

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(15));
        vBox.getChildren().addAll(table ,addEmployeeButton, nextButton);
        scene2 = new Scene(vBox, 250, 400);


// -----------------------------------------------------------------------
// okienko 3

        Label teamCreated = new Label("Team has been created");
        GridPane.setConstraints(teamCreated,0,0);

        Label teamSignatureLabel = new Label("Team signature: ");
        GridPane.setConstraints(teamSignatureLabel,0,1);

        GridPane.setConstraints(teamSignature,1,1);
        teamSignature.setText(teamToDisplay);

        GridPane.setConstraints(teamMembersArea,0,2);
        teamMembersArea.setEditable(false);
        teamMembersArea.setMaxWidth(100);
        //teamMembersArea.setText(String.valueOf(t.getTeamMembers());
        Button gotToScene1Button = new Button("GCreate another team");
        GridPane.setConstraints(gotToScene1Button, 0,3);
        gotToScene1Button.setOnAction(evnt -> window.setScene(scene1));

        Button closeButton = new Button("Close");
        GridPane.setConstraints(closeButton, 1,3);
        closeButton.setOnAction(evnt -> {
            window.close();
        });
        GridPane summaryGridPane = new GridPane();
        summaryGridPane.setVgap(5);
        summaryGridPane.setHgap(5);
        summaryGridPane.setPadding(new Insets(15));
        summaryGridPane.getChildren().addAll(teamCreated, teamSignatureLabel, teamSignature, teamMembersArea,  gotToScene1Button, closeButton);
        //summaryGridPane.getChildren().addAll(teamCreated, teamSignatureLabel, teamSignature, numberOfEmployees, numberOfEmployeesField, closeButton);
        scene3 = new Scene(summaryGridPane, 300, 200);

//---------------------------------------------------------------------
        window.setScene(scene1);
        window.setTitle("");
        window.show();

    }
}