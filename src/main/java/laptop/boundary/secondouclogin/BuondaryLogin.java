package laptop.boundary.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class BuondaryLogin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vbox1;
    @FXML
    private Label emailL;
    @FXML
    private Label passL;
    @FXML
    private VBox vbox2;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private VBox vbox3;
    @FXML
    private Button buttonL;
    @FXML
    private Button buttonReg;
    @FXML
    private Button buttonCambio;
    @FXML
    private Button buttonI;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private VBox vbox4;

    private ControllerLogin cL;
    private Scene scene;

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @FXML
    private void login() throws IOException, CsvValidationException, IdException, SQLException, ClassNotFoundException {


        String type = "";
        if (databaseButton.isSelected()) type = "database";
        if (fileButton.isSelected()) type = "file";
        if (memoriaButton.isSelected()) type = "memoria";

        if (cL.login(emailTF.getText(), passTF.getText(), type).equals("NONVALIDO")) {

            vis.setIsLogged(false);
            throw new IOException(" user role not valid!!");
        }
        if (cL.login(emailTF.getText(), passTF.getText(), type).equals("ADMIN")) {

            vis.setIsLogged(true);


            //caricare scehrmata admin

            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/admin.fxml")));
            stage.setTitle("Benvenuto nella schermata di admin");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } else {
            vis.setIsLogged(true);


            Stage stage;
            Parent root;
            stage = (Stage) buttonL.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page dopo il login scrittore/utente/editore");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    @FXML
    private void registra() throws IOException {
        if (checkPersistenza()) {
            Stage stage;
            Parent root;
            stage = (Stage) buttonReg.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/registraUtente.fxml")));
            stage.setTitle("Benvenuto nella home page dopo il login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void cambia() throws CsvValidationException, SQLException, IOException {

        String type = "";
        if (databaseButton.isSelected()) type = "database";
        if (fileButton.isSelected()) type = "file";
        if (memoriaButton.isSelected()) type = "memoria";

        if(cL.userPresente(emailTF.getText(),passTF.getText(),type))
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonCambio.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/aggiornaPassword.fxml")));
            stage.setTitle("Benvenuto nella home page per cambiare credenziali");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            throw new IOException(" user not found!!");
        }




    }


    @FXML
    private void indietro() throws IOException {
        if(checkPersistenza())
            homePage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cL=new ControllerLogin();
        if(vis.getTipologiaApplicazione().equals("demo"))
        {
            databaseButton.setVisible(false);
            fileButton.setVisible(false);
            buttonReg.setVisible(false);
            buttonCambio.setVisible(false);
        }
        else memoriaButton.setVisible(false);

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected() || fileButton.isSelected() || memoriaButton.isSelected();
    }

    private void homePage() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) buttonI.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella home page dopo il login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
