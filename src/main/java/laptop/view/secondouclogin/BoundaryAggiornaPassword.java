package laptop.view.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class BoundaryAggiornaPassword implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TextField vecchiaMTF;
    @FXML
    private PasswordField vecchiaPTF;
    @FXML
    private PasswordField nuovaPTF;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonI;
    @FXML
    private VBox vbox;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup;

    private ControllerAggiornaPassword cAP;
    private Scene scene;

    @FXML
    private void aggiorna() throws IOException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
        String type="";
        if(databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";
        if(cAP.aggiorna(nuovaPTF.getText(),type))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/login.fxml")));
            stage.setTitle("Benvenuto nella schermata del login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            throw new SQLException(" not updated");
        }
    }

    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/login.fxml")));
        stage.setTitle("Benvenuto nella schermata del login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cAP=new ControllerAggiornaPassword();


            vecchiaMTF.setText(cAP.getEmail());
            vecchiaPTF.setText(cAP.getPass());


    }
}
