package laptop.view.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.secondouclogin.ControllerAdmin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoundaryAdmin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private Button reportB;
    @FXML
    private Button raccoltaB;
    @FXML
    private Button utentiB;
    @FXML
    private Button logoutB;
    @FXML
    private VBox vbox1;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup;
    protected Scene scene;
    private ControllerAdmin cA;

    private static final String PERSISTENZAERRORE="persistenza non selezionata";

    //caricare le altre schermate
    @FXML
    private void report() throws IOException {
        if(checkPersistenza()) {
            Stage stage;
            Parent root;
            stage = (Stage) reportB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/report.fxml")));
            stage.setTitle("Benvenuto nella schermata del login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else throw new IOException("report "+ PERSISTENZAERRORE);
    }
    @FXML
    private void raccolta() throws IOException {
        if(checkPersistenza()) {
            Stage stage;
            Parent root;
            stage = (Stage) raccoltaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/raccolta.fxml")));
            stage.setTitle("Benvenuto nella schermata della raccolta");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else throw new IOException("racoclta "+PERSISTENZAERRORE);
    }
    @FXML
    private void utenti() throws IOException {
        if (checkPersistenza()) {
            Stage stage;
            Parent root;
            stage = (Stage) utentiB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/utenti.fxml")));
            stage.setTitle("Benvenuto nella schermata di utente");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else throw new IOException("utenti "+PERSISTENZAERRORE);
    }
    @FXML
    private void logout() throws CsvValidationException, SQLException, IOException {

        String type="";
        if (databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";
        if(cA.logout(type))
        {
            Stage stage;
            Parent root;
            stage = (Stage) logoutB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella schermata iniziale");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Logger.getLogger("logout").log(Level.INFO," logout success");

        }
        else
        {
            Stage stage;
            Parent root;
            stage = (Stage) utentiB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/admin.fxml")));
            stage.setTitle("Benvenuto nella schermata di admin");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Logger.getLogger("logout").log(Level.SEVERE," error with logoutt!!");
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cA=new ControllerAdmin();

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected() || fileButton.isSelected() || memoriaButton.isSelected();
    }

}
